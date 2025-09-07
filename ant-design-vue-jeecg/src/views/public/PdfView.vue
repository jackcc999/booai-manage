<template>
    <a-drawer
        :title="title"
        placement="right"
        width="1024"
        :closable="false"
        :visible="visible"
        @close="handleCancel">
        <!-- <vue-pdf-app :config="config" :pdf="url" @after-created="afterCreatedHandler" style="height: calc(100vh - 105px)"></vue-pdf-app> -->
    </a-drawer>
</template>

<script>
// import VuePdfApp from "vue3-pdf-app";
// import "vue3-pdf-app/dist/icons/main.css";


export default {
    name: 'AuditForm',
    components: {
        // VuePdfApp
    },
    data() {
        return {
            visible: false,
            title: "查看PDF",
            url: "",
            config: {
                toolbar: {
                    toolbarViewerRight: {
                        presentationMode: true,
                        openFile: false,
                        print: true,
                        download: true,
                        viewBookmark: false,
                    },
                    toolbarViewerMiddle: {
                        zoomOut: true,
                        zoomIn: true,
                        scaleSelectContainer: false,
                    },
                }
            },
        };
    },
    methods: {
        show: function (file, title) {
            this.title = title ? title : "查看PDF";
            this.url = this.buildViewPdf(file);
            this.visible = true;
        },

        handleCancel: function() {
            this.visible = false;
        },

        afterCreatedHandler(app) {
            const oldOpen = app.open.bind(app);
            app.open = function (file, args = {}) {
                return oldOpen(file, {
                    ...args,
                    cMapUrl: 'https://cdn.jsdelivr.net/npm/pdfjs-dist@2.2.228/cmaps/',
                    cMapPacked: true,
                });
            }
        },

        buildViewPdf: function (file) {
            return file.startsWith('http') ? file : `${window._CONFIG['domianURL']}/v2/files/view?id=${img}`;
        }
    }
};
</script>