// 全局APIURL配置
const BASEURL = "//192.168.31.178:8080";
export const APIURL = {
  // 用户相关
  login: BASEURL + "/api/users/login",
  register: BASEURL + "/api/users/register",
  logout: BASEURL + "/api/users/logout",
  updateUser: BASEURL + "/api/users/updateUserProfile",
  updatePassword: BASEURL + "/api/users/updateUserPassword",

  // 商品相关
  product: BASEURL + "/api/product",
  image: BASEURL + "/image/",
};
