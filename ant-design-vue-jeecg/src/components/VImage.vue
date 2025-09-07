<template>
    <a-col :span="span" class="img-view">
        <img :src="url" style="max-width: 100%" />
    </a-col>
</template>

<style type="text/css">
.img-view {
    text-align: center;
    background: #ffffff;
}
</style>

<script>
export default {
    name: 'VImage',
    props: {
        src: String,
        span: Number,
    },
    data() {
        return {
            url: require('@/assets/loading.svg'),
            nodata: require('@/assets/nodata.svg'),
        };
    },

    mounted() {
        var img = new Image()
        img.src = this.src
        img.onerror = () => { // 图片加载错误时的替换图片
            this.url = this.nodata
        }

        img.onload = () => { // 图片加载成功后把地址给原来的img
            this.url = img.src
        }
    },

    methods: {
    }
};
</script>
