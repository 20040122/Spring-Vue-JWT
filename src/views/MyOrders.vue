<template>
  <el-container class="orders-container">
    <el-header>
      <h2>我的订单</h2>
    </el-header>
    <el-main>
      <el-table :data="orders" style="width: 100%">
        <el-table-column prop="orderId" label="订单编号" width="100" />
        <el-table-column prop="orderDate" label="日期" width="180" />
        <el-table-column prop="deliveryStatus" label="状态" width="100" />
        <el-table-column prop="totalPrice" label="总金额" width="180" />
        <el-table-column prop="deliveryAddress" label="收货地址" />
        <el-table-column label="操作">
          <template #default="scope">
            <el-button @click="viewOrder(scope.row)" type="primary" size="small"
              >查看详情</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { ElMessageBox, ElMessage } from "element-plus";
import axiosInstance from "@/global/axiosConfig";
import { APIURL } from "@/global/config";
import { checkOrder } from "@/global/utils";

const orders = ref([]);

const fetchOrders = async () => {
  try {
    const response = await axiosInstance.get(APIURL.fetchOrders);
    orders.value = response.data;
    console.log(orders.value);
  } catch (error) {
    ElMessage.error("获取订单信息失败");
    console.error(error);
  }
};

const viewOrder = async (order: any) => {
  try {
    const orderDetail = await checkOrder(order.orderId);

    let message = `
      <p>订单编号: ${orderDetail.order_id}</p>
      <p>日期: ${orderDetail.order_date}</p>
      <p>状态: ${orderDetail.delivery_status}</p>
      <p>总金额: ${orderDetail.total_price}</p>
      <p>收货地址: ${orderDetail.delivery_address}</p>
      <p>商品列表:</p>
    `;

    const orderItems = Object.values(orderDetail.order_item as Record<string, any>).map(item => {
      const product = item[Object.keys(item)[0]];
      return {
        product_name: product.productName,
        quantity: item.quantity,
        unit_price: product.singlePrice,
        subtotal: item.sum_price
      };
    });

    orderItems.forEach((item: any) => {
      message += `
        <p>商品名称: ${item.product_name}</p>
        <p>商品数量: ${item.quantity}</p>
        <p>商品单价: ${item.unit_price}</p>
        <p>小计: ${item.subtotal}</p>
        <hr />
      `;
    });

    ElMessageBox.alert("", {
      title: "订单详情",
      message: message,
      dangerouslyUseHTMLString: true,
    });
  } catch (error) {
    ElMessage.error("获取订单详情失败");
    console.error(error);
  }
};



onMounted(() => {
  fetchOrders();
});
</script>

<style scoped>
.orders-container {
  width: 1000px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
</style>
