<template>
    <a-modal
        :title="title"
        :visible="visible"
        :footer="null"
        :maskClosable="false"
        @ok="handleCancel"
        @cancel="handleCancel"
        width="70%"
        cancelText="关闭">

        <div class="table-page-search-wrapper log-view">
            <a-row :gutter="24">
                <a-col :xl="24" :lg="24" :md="24" :sm="24">
                    <pre v-if="isJson" v-html="content" style="height: 420px;overflow-y: auto;"></pre>
                    <div v-else style="height: 420px;overflow-y: auto;">{{content}}</div>
                </a-col>
            </a-row>
        </div>
    </a-modal>
</template>


<script>
import { jsonFormat } from "@/utils/util";

export default {
    name: 'AuditForm',
    data() {
        return {
            title: '查看',
            // 遮罩层
            visible: false,
            isJson: false,

            content: ""
        };
    },
    methods: {
        // 显示弹框
        show: function(title, content) {
            this.title = title;
            this.content = content;
            this.isJson = false;

            try
            {
                this.content = JSON.stringify(JSON.parse(content), null, '    ');
                this.isJson = true;
            }
            catch(e) {
            }

            this.visible = true;
        },

        handleCancel:function () {
            this.visible = false;
        }
    }
};
</script>
