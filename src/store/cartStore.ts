import { defineStore } from "pinia";

interface Product {
  productId: number;
  productName: string;
  price: number;
  quantity: number;
}

interface CartState {
  selectedProducts: Product[];
  totalPrice: number;
}

export const useCartStore = defineStore("cart", {
  state: (): CartState => ({
    selectedProducts: [],
    totalPrice: 0,
  }),
  getters: {},
  actions: {
    addProduct(product: Product) {
      const existingProduct = this.selectedProducts.find(
        (p) => p.productId === product.productId
      );
      if (existingProduct) {
        existingProduct.quantity += product.quantity;
      } else {
        this.selectedProducts.push(product);
      }
      this.updateTotalPrice();
    },
    removeProduct(productId: number) {
      this.selectedProducts = this.selectedProducts.filter(
        (p) => p.productId !== productId
      );
      this.updateTotalPrice();
    },
    clearCart() {
      this.selectedProducts = [];
      this.updateTotalPrice();
    },
    updateTotalPrice() {
      this.totalPrice = parseFloat(
        this.selectedProducts
          .reduce((sum, product) => {
            return sum + product.price * product.quantity;
          }, 0)
          .toFixed(2)
      );
    },
  },
});
