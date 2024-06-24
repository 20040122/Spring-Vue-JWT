<template>
  <el-card :body-style="{ padding: '20px' }" class="product-card">
    <img :src="product.photo" class="image" />
    <div style="padding: 14px">
      <span>{{ product.productName }}</span>
      <div class="description">{{ product.description }}</div> <!-- 添加描述 -->
      <div class="bottom clearfix">
        <span class="price">￥{{ product.singlePrice }}</span>
        <el-button type="primary" @click="addToCart(product)">
          加入购物车
        </el-button>
      </div>
    </div>
  </el-card>
</template>

<script setup>
const props = defineProps({
  product: {
    type: Object,
    required: true,
  },
});

const addToCart = (product) => {
  console.log(`商品 ${product.productName} 已添加到购物车`);
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