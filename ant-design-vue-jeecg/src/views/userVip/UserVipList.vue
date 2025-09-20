<template>
    <a-card :bordered="false">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
            <a-form layout="inline" @keyup.enter.native="searchQuery">
                <a-row :gutter="24">
                    <a-col :xl="8" :lg="8" :md="8" :sm="24">
                        <a-form-item label="用户ID">
                            <j-input placeholder="请输入用户ID" v-model="queryParam.userId"></j-input>
                        </a-form-item>
                    </a-col>

                    <a-col :xl="8" :lg="8" :md="8" :sm="24">
                        <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                            <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
                            <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
                            <a-button @click="handleAdd" type="primary" icon="plus" style="margin-left: 8px">新增</a-button>

                            <a-button type="primary" icon="download" @click="handleExportXls('user_vip')" style="margin-left: 8px">导出</a-button>
                            <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" style="margin-left: 8px" @change="handleImportExcel">
                                <a-button type="primary" icon="import">导入</a-button>
                            </a-upload>


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
                <a @click="handleEdit(record)">编辑</a>

                <a-divider type="vertical" />
                <a-dropdown>
                    <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
                    <a-menu slot="overlay">
                        <!-- <a-menu-item>
                            <a @click="handleDetail(record)">详情</a>
                        </a-menu-item> -->
                        <a-menu-item>
                            <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                                <a>删除</a>
                            </a-popconfirm>
                        </a-menu-item>
                    </a-menu>
                </a-dropdown>
            </span>
        </a-table>
    </div>

    <user-vip-modal ref="modalForm" @ok="modalFormOk"></user-vip-modal>
    </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import UserVipModal from './modules/UserVipModal'

export default {
    name: 'UserVipList',
    mixins: [JeecgListMixin, mixinDevice],
    components: {
        UserVipModal
    },
    data () {
        return {
            description: 'user_vip管理页面',
            // 表头
            columns: [
                {
                    title:'用户ID',
                    align:"center",
                    dataIndex: 'userId'
                },
                {
                    title:'VIP名称',
                    align:"center",
                    dataIndex: 'vipName'
                },
                {
                    title:'VIP价格',
                    align:"center",
                    dataIndex: 'price',
                    customRender(text) {
                        return text.toFixed(2) + ' 美元'
                    },
                },
                {
                    title:'开始日期',
                    align:"center",
                    dataIndex: 'beginDate'
                },
                {
                    title:'结束日期',
                    align:"center",
                    dataIndex: 'endDate'
                },
                {
                    title:'创建时间',
                    align:"center",
                    dataIndex: 'createAt'
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
                list: "/manage/userVip/list",
                delete: "/manage/userVip/delete",
                deleteBatch: "/manage/userVip/deleteBatch",
                exportXlsUrl: "/manage/userVip/exportXls",
                importExcelUrl: "manage/userVip/importExcel",

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
            fieldList.push({type:'int',value:'vipId',text:'VIP ID'})
            fieldList.push({type:'number',value:'price',text:'VIP价格'})
            fieldList.push({type:'date',value:'benginDate',text:'开始日期'})
            fieldList.push({type:'date',value:'endDate',text:'结束日期'})
            fieldList.push({type:'date',value:'createAt',text:'创建时间'})
            fieldList.push({type:'date',value:'updateAt',text:'更新时间'})
            this.superFieldList = fieldList
        }
    }
 }
</script>
<style scoped>
    @import '~@assets/less/common.less';
</style>
