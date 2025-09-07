<!-- <template>
    <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataList"
        :pagination="false"
        class="j-table-force-nowrap">

        <template v-slot:bodyCell="{ text, record, index, column }">
            <template v-if="column.dataIndex === 'action'">
                <a @click="downloadIdFile(record)">下载</a>
                <a @click="viewIdImg(record)" v-if="record.canView" style="margin-left: 8px;">预览</a>
                <a @click="deleteFile(index, record)" v-if="!disabled" style="margin-left: 8px;">删除</a>
            </template>
        </template>
    </a-table>
    <ImageView ref="imageView"></ImageView>
    <PdfView ref="pdfView"></PdfView>
</template>

<script>
import { postAction } from '@/api/manage';
import { useGlobSetting } from '@/hooks/setting';
import { downloadIdFile } from '@/utils/common/renderUtils';
import ImageView from '@/views/public/ImageView.vue';
import PdfView from '@/views/public/PdfView.vue';


const globSetting = useGlobSetting();
const baseApiUrl = globSetting.domainUrl;

export default {
    name: 'FileTable',
    props: {
        //表单禁用
        ids: {
            type: String,
            default: '',
            required: true
        },
        disabled: {
            type: Boolean,
            default: false,
            required: false
        }
    },
    components: {
        ImageView, PdfView
    },
    data() {
        return {
            dataList: [],
            columns: [
                {
                    title:'序号',
                    align:"center",
                    dataIndex: 'index',
                    customRender:({text, record, index}) =>{
                        return index + 1;
                    },
                },
                {
                    title:'文件名称',
                    align:"center",
                    dataIndex: 'filename'
                },
                {
                    title:'文件大小',
                    align:"center",
                    dataIndex: 'size'
                },
                {
                    title:'操作',
                    align:"center",
                    dataIndex: 'action'
                },
            ],
        };
    },
    watch:{
        ids:{
            immediate:true,
            handler() {
                this.initData()
            }
        }
    },
    methods: {
        initData: function () {
            console.log(this.ids);
            var idList = this.ids.split(",");
            postAction("/v2/files/getFilesList", {ids: idList}).then(res => {
                this.dataList = res;
            });
        },

        viewIdImg: function (record) {
            if(record.filename.endsWith('.pdf')) {
                this.$refs.pdfView.show(record.id);
            } else {
                this.$refs.imageView.show(record.id);
            }
        },

        deleteFile: function (index, record) {
            this.dataList.splice(index, 1);
            this.$emit('delete', index, record);
        },

        downloadIdFile: function (record) {
            downloadIdFile(record.id);
        },
    }
};
</script>
 -->