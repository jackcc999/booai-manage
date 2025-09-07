<template>
    <a-modal
        :title="title"
        :visible="visible"
        :footer="null"
        :width="width"
        @cancel="handleCancel"
        cancelText="关闭">
        <div>
            <img v-for="image in images" :src="image" :style="{width: imgWidth}" />
        </div>
    </a-modal>
</template>


<script>
import { getFileAccessHttpUrl } from '@/api/manage'

export default {
    name: 'AuditForm',
    data() {
        return {
            title: '图片预览',
            // 遮罩层
            visible: false,
            width: "70%",
            imgWidth: "100%",
            images: [],
        };
    },
    methods: {
        // 显示弹框
        show: function(images, title) {
            var imgs = new Array
            if(typeof images === 'string') {
                imgs.push(this.buildViewImage(images));
            } else {
                for(var i = 0; i < images.length; i++) {
                   imgs.push(this.buildViewImage(images[i]));
                }
            }

            this.title = title ? title : "图片预览";
            this.images = imgs;
            this.setImageWidth();
            this.width = this.images.length == 1 ? "50%" : "70%";
            this.visible = true;

            console.log(window.innerHeight)
        },

        handleCancel:function () {
            this.visible = false;
        },

        setImageWidth: function () {
            var imgWidth = '100%';
            if(this.images.length > 1 && this.images.length <= 4) {
                imgWidth = '50%';
            } else if(this.images.length > 5 && this.images.length <= 6) {
                imgWidth = '33.3%';
            } else if(this.images.length > 7 && this.images.length > 9) {
                imgWidth = '25%';
            }
            this.imgWidth = imgWidth;
        },

        buildViewImage: function (img) {
            if(img.startsWith('http')) {
                return img
            } else if(/^[0-9]+$/.test(img)) {
                return `${window._CONFIG['domianURL']}/v2/files/view?id=${img}`
            } else {
                return getFileAccessHttpUrl(img)
            }
        }
    }
};
</script>