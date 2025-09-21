<template>
    <a-card :bordered="false">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
            <a-form layout="inline" @keyup.enter.native="searchQuery">
                <a-row :gutter="24">
                    <a-col :xl="6" :lg="7" :md="8" :sm="24">
                        <a-form-item label="应用ID">
                            <j-input placeholder="请输入应用ID" v-model="queryParam.appId" @change="searchQuery"></j-input>
                        </a-form-item>
                    </a-col>

                    <a-col :xl="6" :lg="7" :md="8" :sm="24">
                        <a-form-item label="应用名称">
                            <j-input placeholder="请输入应用名称" v-model="queryParam.appName" @change="searchQuery"></j-input>
                        </a-form-item>
                    </a-col>

                    <a-col :xl="6" :lg="7" :md="8" :sm="24">
                        <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                            <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
                            <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
                            <a-button @click="handleAdd" type="primary" icon="plus" style="margin-left: 8px" v-has="'appInfo:add'">新增</a-button>
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
                :scroll="{x:true}"
                bordered
                rowKey="id"
                :columns="columns"
                :dataSource="dataSource"
                :pagination="ipagination"
                :loading="loading"
                class="j-table-force-nowrap"
                @change="handleTableChange">

                <span slot="action" slot-scope="text, record">
                    <a @click="handleEdit(record)" v-has="'appInfo:edit'">编辑</a>

                    <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)"  v-has="'appInfo:delete'" style="margin-left: 8px">
                        <a>删除</a>
                    </a-popconfirm>
                </span>

            </a-table>
        </div>

        <app-info-modal ref="modalForm" @ok="modalFormOk"></app-info-modal>
    </a-card>
</template>

<script>

    import '@/assets/less/TableExpand.less'
    import { mixinDevice } from '@/utils/mixin'
    import { JeecgListMixin } from '@/mixins/JeecgListMixin'
    import AppInfoModal from './modules/AppInfoModal'

    export default {
        name: 'OpenApiList',
        mixins:[JeecgListMixin, mixinDevice],
        components: {
            AppInfoModal
        },
        data () {
            return {
                description: 'mi_open_api管理页面',
                // 表头
                columns: [
                    {
                        title:'应用名称',
                        align:"center",
                        dataIndex: 'appName'
                    },
                    {
                        title:'应用编码',
                        align:"center",
                        dataIndex: 'appCode',
                        customRender: function (text) {
                            return text ? text : "-";
                        }
                    },
                    {
                        title:'应用ID',
                        align:"center",
                        dataIndex: 'appId'
                    },
                    {
                        title:'请求地址',
                        align:"center",
                        dataIndex: 'url',
                        customRender: function (text) {
                            return text ? text : "-";
                        }
                    },
                    {
                        title:'同步接口',
                        align:"center",
                        dataIndex: 'adapter',
                        customRender: function (text) {
                            return text ? text : "-";
                        }
                    },
                    {
                        title:'备注',
                        align:"center",
                        dataIndex: 'memo',
                        customRender: function (text) {
                            return text ? text : "-";
                        }
                    },
                    {
                        title:'状态',
                        align:"center",
                        dataIndex: 'status',
                        customRender: function (text) {
                            return text == 1 ? "启用" : "禁用";
                        }
                    },
                    {
                        title:'更新用户',
                        align:"center",
                        dataIndex: 'updateUser'
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
                    list: "/v2/appInfo/list",
                    delete: "/v2/appInfo/delete",
                    deleteBatch: "/v2/appInfo/deleteBatch",
                    exportXlsUrl: "/v2/appInfo/exportXls",
                    importExcelUrl: "v2/appInfo/importExcel",

                },
                dictOptions:{},
                superFieldList:[],
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
            initDictConfig(){
            },
            getSuperFieldList(){
                let fieldList=[];
                fieldList.push({type:'string',value:'appId',text:'应用ID'})
                fieldList.push({type:'string',value:'appName',text:'应用名称'})
                fieldList.push({type:'string',value:'secret',text:'应用密钥'})
                fieldList.push({type:'int',value:'status',text:'状态 1：启用、0：禁用'})
                fieldList.push({type:'string',value:'memo',text:'备注'})
                fieldList.push({type:'string',value:'createUser',text:'创建用户'})
                fieldList.push({type:'string',value:'updateUser',text:'更新用户'})
                this.superFieldList = fieldList
            }
        }
    }
</script>
<style scoped>
    @import '~@assets/less/common.less';
</style>
