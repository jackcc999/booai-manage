<template>
    <div>
        <ImageView ref="imageView"></ImageView>
        <PdfView ref="pdfView"></PdfView>
    </div>
</template>

<script>
import { postAction } from '@/api/manage';
import ImageView from '@/views/public/ImageView';
import PdfView from '@/views/public/PdfView';


export default {
    name: 'FileView',
    components: {
        ImageView, PdfView
    },
    data() {
        return {

        };
    },

    created: function () {

    },
    methods: {
        show: function (id) {
            var idList = id.split(",");
            postAction("/v2/files/getFilesList", {ids: idList}).then(res => {
                if(res.length == 0) {
                    this.$message.warning('文件不存在');
                } else {
                    const info = res[0]
                    if(info.canView == 0) {
                        this.$message.warning('文件【' + info.filename + '】不支持预览');
                    } else {
                        if(info.filename.endsWith('.pdf')) {
                            this.$refs.pdfView.show(info.id, info.filename);
                        } else {
                            this.$refs.imageView.show(info.id, info.filename);
                        }
                    }
                }
            });
        },
    }
};
</script>