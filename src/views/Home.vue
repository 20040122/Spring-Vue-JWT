<template>
  <el-container style="height: calc(100vh - 100px); overflow: hidden">
    <!-- 左侧分类菜单 -->
    <el-aside width="200px" class="aside">
      <el-menu
        default-active="0"
        class="el-menu-vertical"
        @select="handleSelect"
      >
        <el-menu-item
          v-for="(category, index) in categories"
          :key="index"
          :index="index.toString()"
        >
          {{ category }}
        </el-menu-item>
      </el-menu>
    </el-aside>

    <!-- 右侧商品展示 -->
    <el-container>
      <el-main class="main">
        <el-row :gutter="20">
          <el-col
            v-for="product in selectedCategoryProducts"
            :key="product.productId"
            :span="8"
          >
            <ProductCard :product="product" />
          </el-col>
        </el-row>
      </el-main>
      <el-footer class="footer"> Footer内容 </el-footer>
    </el-container>
  </el-container>
</template>

<script lang="ts" setup>
import { ref, computed } from "vue";
import ProductCard from "@/components/ProductCard.vue";
import axios from "axios";
import { APIURL } from "@/global/config";
import { useUserStore } from "@/store/userStore";

const products = ref([
  // 默认值
  {
    productId: 0,
    productName: "",
    kind: "",
    singlePrice: 0,
    photo: "",
  },
]);
const categories = ref([
  // 默认值
  "默认分类",
]);
const selectedCategoryIndex = ref(0);

// 请求后端产品接口
axios.get(APIURL.product).then((res) => {
  console.log(res.data);
  products.value = res.data;

  // 计算所有的产品种类
  const kinds = new Set(products.value.map((product) => product.kind));
  categories.value = Array.from(kinds);

  // 替换图片路径
  products.value.forEach((product) => {
    product.photo = APIURL.image + product.photo;
  });
});

const handleSelect = (key: string) => {
  selectedCategoryIndex.value = parseInt(key);
};

const selectedCategoryProducts = computed(() => {
  const selectedCategory = categories.value[selectedCategoryIndex.value];
  return products.value.filter(
    (product) => product.kind === selectedCategory
  );
});
</script>

<style scoped>
body {
  margin: 0;
  overflow: hidden;
}

.aside {
  overflow-y: auto; /* 如果分类菜单项过多可以滚动 */
  border-right: 1px solid #ebebeb; /* 增加一个右边框，分割内容 */
}

.main {
  overflow-y: auto; /* 允许内容区垂直滚动 */
  padding: 20px;
  height: calc(100vh - 80px - 60px); /* 减去header和footer的高度 */
  box-sizing: border-box; /* 包含内边距和边框在总高度和宽度内 */
}

.footer {
  padding: 10px;
  text-align: center;
  height: 60px;
  border: 1px solid red;
}

/* 自定义滚动条样式 */
.aside::-webkit-scrollbar,
.main::-webkit-scrollbar {
  width: 8px; /* 滚动条宽度 */
  height: 8px; /* 滚动条高度 */
}

.aside::-webkit-scrollbar-track,
.main::-webkit-scrollbar-track {
  background: #f1f1f1; /* 滚动条轨道背景颜色 */
}

.aside::-webkit-scrollbar-thumb,
.main::-webkit-scrollbar-thumb {
  background: #888; /* 滚动条滑块背景颜色 */
  border-radius: 4px; /* 滚动条滑块圆角 */
}

.aside::-webkit-scrollbar-thumb:hover,
.main::-webkit-scrollbar-thumb:hover {
  background: #555; /* 滚动条滑块悬停背景颜色 */
}

.el-menu-vertical {
  width: 100%;
}
</style>
