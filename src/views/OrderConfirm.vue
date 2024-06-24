<template>
  <div class="container">
    <div>
      <el-steps :active="activeStep" finish-status="success">
        <el-step title="确认购物车" />
        <el-step title="支付订单(模拟)" />
      </el-steps>

      <el-card class="order-card" v-show="activeStep === 0">
        <div slot="header">
          <h2>确认信息</h2>
        </div>
        <div>
          <el-table :data="selectedProducts" stripe style="width: 100%">
            <el-table-column prop="productName" label="商品名称" />
            <el-table-column prop="price" label="价格" />
            <el-table-column prop="quantity" label="数量" />
            <el-table-column label="小计">
              <template #default="{ row }">
                <span>￥{{ (row.price * row.quantity).toFixed(2) }}</span>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div class="total-price">
          <span>总价：￥{{ totalPrice }}</span>
        </div>
        <!-- 确认收货地址 -->
        <h2>确认收货地址</h2>
        <el-form label-width="80px">
          <el-form-item label="收货地址">
            <el-input v-model="address" />
          </el-form-item>
        </el-form>
        <div class="next-button">
          <el-button type="primary" @click="nextStep">下一步</el-button>
        </div>
      </el-card>

      <el-card class="order-card" v-show="activeStep === 1">
        <div slot="header">
          <h2>支付订单(模拟)</h2>
        </div>
        <el-button type="primary" @click="onPaySuccess"
          >点击模拟支付接口返回完成</el-button
        >
      </el-card>

      <el-card class="order-card" v-show="activeStep === 2">
        <div slot="header">
          <h2>提交完成</h2>
        </div>
        <!-- 提交完成内容 -->
        <el-row justify="center">
          <el-col :sm="12" :lg="6">
            <el-result
              icon="success"
              title="提交完成！"
              sub-title="您的订单已经提交完成！"
            >
              <template #extra>
                <el-button type="primary" @click="goOrders"
                  >愉快的查看订单</el-button
                >
              </template>
            </el-result>
          </el-col>
        </el-row>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useCartStore } from "@/store/cartStore";
import { useUserStore } from "@/store/userStore";
import { useRouter } from "vue-router";
import { ref } from "vue";
import { submitOrder } from "@/global/utils";

const cartStore = useCartStore();
const userStore = useUserStore();

// 获取默认收货地址
const address = ref(userStore.getUser.default_address);

// 获取购物车中选中的商品信息
const selectedProducts = ref(cartStore.selectedProducts);

// 获取总价
const totalPrice = ref(cartStore.totalPrice);

// 步骤条的当前步骤
const activeStep = ref(0);

// 下一步操作
const nextStep = () => {
  if (activeStep.value < 2) {
    activeStep.value++;
  }
};

// 查看订单
const router = useRouter();
const goOrders = () => {
  router.push("/orders");
};

// 模拟支付接口返回完成
const onPaySuccess = () => {
  // 构建提交订单项
  interface OrderItem {
    [productId: string]: number;
  }
  const orderItem: OrderItem = {};
  selectedProducts.value.forEach((product) => {
    orderItem[product.productId] = product.quantity;
  });
  // !提交订单api
  submitOrder(address.value, orderItem).then(() => {
    // 提交订单成功后，跳转到下一步
    nextStep();
  });
};
</script>

<style scoped>
.container {
  width: 80%;
  margin: 0 auto;
}

.order-card {
  margin-top: 20px;
}

.total-price {
  margin-top: 10px;
  text-align: right;
}

.next-button {
  margin-top: 20px;
  text-align: right;
}
</style>
