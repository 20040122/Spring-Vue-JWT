<template>
  <el-card :body-style="{ padding: '20px' }" class="product-card">
    <img :src="product.photo" class="image" />
    <div style="padding: 14px">
      <span>{{ product.productName }}</span>
      <div class="description">{{ product.description }}</div>
      <!-- 添加描述 -->
      <div class="bottom clearfix">
        <span class="price">￥{{ product.singlePrice }}</span>
        <el-input-number
          v-model="selectedProduct.quantity"
          :min="0"
          :max="999"
        />
        <el-button
          type="primary"
          @click="addToCart"
          :disabled="selectedProduct.quantity === 0"
        >
          加入购物车
        </el-button>
      </div>
    </div>
  </el-card>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import { useCartStore } from "@/store/cartStore";

const cartStore = useCartStore();

const props = defineProps({
  product: {
    type: Object,
    required: true,
  },
});

// 选择的商品
let selectedProduct = ref({
  productId: props.product.productId,
  productName: props.product.productName,
  price: props.product.singlePrice,
  quantity: 0,
});

const addToCart = () => {
  const productToAdd = { ...selectedProduct.value }; // 复制当前选择的商品信息 解构赋值创建一个新对象
  selectedProduct.value.quantity = 0; // 清零选择器里的数量
  // 结合 Pinia 实现购物车状态管理
  cartStore.addProduct(productToAdd);
  console.log(`商品 ${productToAdd.productName} 已添加到购物车`);
};
</script>

<style scoped>
.product-card {
  margin-bottom: 20px; /* 增加纵向的 margin */
}

.image {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.description {
  margin-top: 10px; /* 增加描述的上边距 */
  color: #888;
  font-size: 14px;
}

.price {
  color: #f56c6c;
  font-size: 18px;
  margin-right: 10px;
}

.bottom {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
