<template>
  <el-footer class="cart-footer">
    <el-row type="flex" justify="space-between" align="middle">
      <el-col :span="12">
        <div class="total-amount">总金额: ￥{{ totalPrice }}</div>
      </el-col>
      <el-col :span="12" class="button-col">
        <el-popover placement="left" :width="600" trigger="click">
          <template #reference>
            <el-button style="margin-right: 16px">购物车</el-button>
          </template>
          <el-table :data="cartStore.selectedProducts" style="width: 100%">
            <el-table-column prop="productName" label="商品名称" />
            <el-table-column prop="price" label="单价" />
            <el-table-column prop="quantity" label="数量" />
            <el-table-column label="小计">
              <template #default="{ row }">
                {{ (row.price * row.quantity).toFixed(2) }}
              </template>
            </el-table-column>
            <el-table-column prop="quantity" label="操作">
              <template #default="{ row }">
                <el-button
                  type="default"
                  @click="cartStore.removeProduct(row.productId)"
                  >删除</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </el-popover>
        <el-button
          type="primary"
          @click="checkout"
          :disabled="selectedProductsAmount === 0"
        >
          去结算
        </el-button>
      </el-col>
    </el-row>
  </el-footer>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useCartStore } from "@/store/cartStore";
import router from "@/router";

const cartStore = useCartStore();

// 选择的商品类数量
const selectedProductsAmount = computed(() => cartStore.selectedProducts.length);

// 总金额
const totalPrice = computed(() => cartStore.totalPrice);

const checkout = () => {
  router.push("/orderConfirm");
};
</script>

<style scoped>
.cart-footer {
  padding: 10px;
  background-color: #f5f5f5;
  border-top: 1px solid #ebeef5;
}

.total-amount {
  font-size: 18px;
  font-weight: bold;
}

.button-col {
  text-align: right;
}
</style>
