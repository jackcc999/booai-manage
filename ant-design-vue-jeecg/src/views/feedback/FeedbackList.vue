<template>
    <a-card :bordered="false">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
            <a-form layout="inline" @keyup.enter.native="searchQuery">
                <a-row :gutter="24">
                    <a-col :xl="8" :lg="8" :md="8" :sm="24">
                        <a-form-item label="用户ID">
                            <a-input placeholder="请输入用户ID" v-model="queryParam.userId" @change="searchQuery"></a-input>
                        </a-form-item>
                    </a-col>

                    <a-col :xl="8" :lg="8" :md="8" :sm="24">
                        <a-form-item label="反馈内容">
                            <j-input placeholder="请输入反馈内容" v-model="queryParam.content" @change="searchQuery"></j-input>
                        </a-form-item>
                    </a-col>

                    <a-col :xl="8" :lg="8" :md="8" :sm="24">
                        <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                            <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
                            <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>

                            <a-button type="primary" icon="download" @click="handleExportXls('用户反馈')" style="margin-left: 8px">导出</a-button>

                            <a-dropdown v-if="selectedRowKeys.length > 0" style="margin-left: 8px">
                                <a-menu slot="overlay">
                                    <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/><span>删除</span></a-menu-item>
                                </a-menu>
                                <a-button>
                                    <span>批量操作</span><a-icon type="down"/>
                                </a-button>
                            </a-dropdown>
                        </span>
                    </a-col>
                </a-row>
            </a-form>
        </div>
        <!-- 查询区域-END -->

        <!-- table区域-begin -->
        <div>
        <a-table
            ref="table"
            size="middle"
            :scroll="{x:auto}"
            bordered
            rowKey="id"
            :columns="columns"
            :dataSource="dataSource"
            :pagination="ipagination"
            :loading="loading"
            :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
            class="j-table-force-nowrap"
            @change="handleTableChange">

            <template slot="htmlSlot" slot-scope="text">
                <div v-html="text"></div>
            </template>

            <template slot="imgSlot" slot-scope="text,record">
                <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
                <img v-else :src="getImgView(text)" :preview="record.id" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
            </template>


            <template slot="fileSlot" slot-scope="text">
                <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
                <a-button v-else :ghost="true" type="primary" icon="download" size="small" @click="downloadFile(text)">下载</a-button>
            </template>

            <span slot="action" slot-scope="text, record">
                <a @click="handleDetail(record)">详情</a>

                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)" style="margin-left: 8px">
                    <a>删除</a>
                </a-popconfirm>
            </span>
        </a-table>
    </div>

    <feedback-modal ref="modalForm" @ok="modalFormOk"></feedback-modal>
    </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import FeedbackModal from './modules/FeedbackModal'

export default {
    name: 'FeedbackList',
    mixins: [JeecgListMixin, mixinDevice],
    components: {
        FeedbackModal
    },
    data () {
        return {
            description: '用户反馈管理页面',
            // 表头
            columns: [
                {
                    title:'用户ID',
                    align:"center",
                    dataIndex: 'userId'
                },
                {
                    title:'反馈内容',
                    align:"center",
                    dataIndex: 'content'
                },
                {
                    title:'创建时间',
                    align:"center",
                    dataIndex: 'createdAt'
                },
                {
                    title:'更新时间',
                    align:"center",
                    dataIndex: 'updatedAt'
                },
                {
                    title: '操作',
                    dataIndex: 'action',
                    align:"center",
                    fixed:"right",
                    width:147,
                    scopedSlots: { customRender: 'action' }
                }
            ],
            url: {
                list: "/manage/feedback/list",
                delete: "/manage/feedback/delete",
                deleteBatch: "/manage/feedback/deleteBatch",
                exportXlsUrl: "/manage/feedback/exportXls",
                importExcelUrl: "manage/feedback/importExcel",

            },
            dictOptions: {},
            superFieldList: [],
            isorter:{
                column: 'id',
                order: 'desc',
            },
        }
    },
    created() {
        this.getSuperFieldList();
    },
    computed: {
        importExcelUrl: function(){
            return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
        },
    },
    methods: {


        initDictConfig() {
        },

        getSuperFieldList() {
            let fieldList = [];
            fieldList.push({type:'int',value:'userId',text:'用户ID'})
            fieldList.push({type:'string',value:'content',text:'反馈内容'})
            fieldList.push({type:'date',value:'createdAt',text:'创建时间'})
            fieldList.push({type:'date',value:'updatedAt',text:'更新时间'})
            this.superFieldList = fieldList
        }
    }
 }
</script>
<style scoped>
    @import '~@assets/less/common.less';
</style>
