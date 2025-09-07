Ant Design Jeecg Vue
====

当前最新版本： 3.4.3（发布日期：20221107）

#### 前端技术

- 基础框架：[ant-design-vue](https://github.com/vueComponent/ant-design-vue) - Ant Design Of Vue 实现
- JavaScript框架：Vue
- Webpack
- node
- yarn
- eslint
- @vue/cli 3.2.1
- [vue-cropper](https://github.com/xyxiao001/vue-cropper) - 头像裁剪组件
- [@antv/g2](https://antv.alipay.com/zh-cn/index.html) - Alipay AntV 数据可视化图表
- [Viser-vue](https://viserjs.github.io/docs.html#/viser/guide/installation)  - antv/g2 封装实现



项目下载和运行
----

- 安装依赖
```
yarn install
```

- 开发模式运行
```
yarn run serve
```

- 编译项目
```
yarn run build
```

- 修改标题和logo

```
src\components\layouts\UserLayout.vue
src\components\tools\Logo.vue
src\components\layouts\TabLayout.vue
src\components\page\GlobalHeader.vue
src\components\page\GlobalFooter.vue
public\index.html
```

- Nginx配置

```nginx
location /
{
    # 用于配合 browserHistory使用
    try_files $uri $uri/ /index.html;
}
```



