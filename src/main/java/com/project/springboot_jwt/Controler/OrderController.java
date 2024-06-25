package com.project.springboot_jwt.Controler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.springboot_jwt.Enitity.*;
import com.project.springboot_jwt.Repository.OrderItemRepository;
import com.project.springboot_jwt.Repository.OrderRepository;
import com.project.springboot_jwt.Repository.ProductRepository;
import com.project.springboot_jwt.Services.OrderService;
import com.project.springboot_jwt.Services.UserServices;
import com.project.springboot_jwt.Utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserServices userService;
    private final JwtUtil jwtUtil;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public OrderController(OrderService orderService, UserServices userService, JwtUtil jwtUtil) {
        this.orderService = orderService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

@PostMapping("/createOrder")
public ResponseEntity<String> createOrder(HttpServletRequest request, @RequestBody JsonNode order_jn) {
    String authorizationHeader = request.getHeader("token");
    String deliveryAddress = order_jn.has("delivery_address") ? order_jn.get("delivery_address").asText() : null;
    String deliveryStatus = "待处理";

    JsonNode orderItemNode = order_jn.has("order_item") ? order_jn.get("order_item") : null;

    if (authorizationHeader != null && jwtUtil.validateToken(authorizationHeader)) {
        String userName = jwtUtil.extractUserName(authorizationHeader);
        Users user = userService.getUserByUsername(userName);
        Orders order = new Orders();
        order.setUserId(user.getUserId());
        order.setDeliveryAddress(deliveryAddress);
        order.setDeliveryStatus(deliveryStatus);
        Date date = new Date(System.currentTimeMillis());
        order.setOrderDate(date);


        AtomicReference<Float> totalPrice = new AtomicReference<>((float) 0); // 订单总价

        // 保存订单到数据库
        orderRepository.save(order);

        int orderId = order.getOrderId(); // 获取新保存订单的订单ID

        // 遍历订单项 并添加到数据库
        if (orderItemNode == null || !orderItemNode.isObject()) {
            System.out.println("Invalid order_item format");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        Iterator<Map.Entry<String, JsonNode>> fields = orderItemNode.fields();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String productIdStr = entry.getKey();
            JsonNode quantityNode = entry.getValue();

            // 解析 productId 和 quantity
            int productId;
            int quantity;

            try {
                productId = Integer.parseInt(productIdStr);
                quantity = quantityNode.asInt();
            } catch (NumberFormatException e) {
                System.out.println("Invalid productId format");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            // 查询产品信息
            Product product = productRepository.findByProductId(productId);
            if (product == null) {
                System.out.println("Product with ID " + productId + " not found");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            // 计算订单项总价
            float sumPrice = quantity * Float.parseFloat(product.getSinglePrice());
            // 计算订单总价
            totalPrice.updateAndGet(v -> v + sumPrice);
            // 创建订单项对象
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order); // 设置订单项关联的订单对象
            orderItem.setProductId(productId);
            orderItem.setQuantity(quantity);
            orderItem.setSumPrice(sumPrice);
            // 保存订单项到数据库
            orderItemRepository.save(orderItem);
        }
        // 更新订单总价并保存到数据库
        order.setTotalPrice(totalPrice.get());
        orderRepository.save(order);
        return ResponseEntity.ok("Order created successfully");
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
}

    @GetMapping("/fetchOrders")
    public ResponseEntity<List<Orders>> fetchOrders(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("token");

        if (authorizationHeader != null && jwtUtil.validateToken(authorizationHeader)) {
            String userName = jwtUtil.extractUserName(authorizationHeader);
            Users user = userService.getUserByUsername(userName);

            List<Orders> orders = orderRepository.findByUserId(user.getUserId());

            // 使用 Optional 包装 orders
            Optional<List<Orders>> optionalOrders = Optional.ofNullable(orders);

            // 如果 orders 不为空，则返回 orders；否则返回空列表
            List<Orders> resultOrders = optionalOrders.orElse(List.of());


            return ResponseEntity.ok(resultOrders);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping("/checkOrder")
    public ResponseEntity<JsonNode> checkOrder(HttpServletRequest request, @RequestBody JsonNode orderId_jn) {
        String authorizationHeader = request.getHeader("token");
        int orderId = orderId_jn.path("order_id").asInt();
        ObjectMapper objectMapper = new ObjectMapper();
        if (authorizationHeader != null && jwtUtil.validateToken(authorizationHeader)) {
            String userName = jwtUtil.extractUserName(authorizationHeader);
            Users user = userService.getUserByUsername(userName);
            Orders order = orderRepository.findByOrderId(orderId);
            List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
            String jsonString = "{\"order_id\":" + orderId +
                    ",\"user_id\":" + order.getUserId() +
                    ",\"order_date\":\"" + order.getOrderDate() +
                    "\",\"delivery_address\":\"" + order.getDeliveryAddress() +
                    "\",\"delivery_status\":\"" + order.getDeliveryStatus() +
                    "\",\"total_price\":" + order.getTotalPrice() +
                    ",\"order_item\":{" +
                    orderItems.stream().map(orderItem ->
                            "\"" + orderItem.getOrderItemId() + "\":{\"" + orderItem.getProductId() + "\":" +
                                    productRepository.findByProductId(orderItem.getProductId()).toString() +
                                    ",\"quantity\":" + orderItem.getQuantity() +
                                    ",\"sum_price\":" + orderItem.getSumPrice() + "}"
                    ).reduce((s1, s2) -> s1 + "," + s2).orElse("") + "}}";
            try{
                JsonNode jsonNode = objectMapper.readTree(jsonString);
                return ResponseEntity.ok(jsonNode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}

