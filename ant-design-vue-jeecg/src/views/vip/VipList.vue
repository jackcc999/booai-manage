<template>
    <a-card :bordered="false">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
            <a-form layout="inline" @keyup.enter.native="searchQuery">
                <a-row :gutter="24">
                    <a-col :xl="8" :lg="8" :md="8" :sm="24">
                        <a-form-item label="会员名称">
                            <j-input placeholder="请输入会员名称" v-model="queryParam.name"></j-input>
                        </a-form-item>
                    </a-col>

                    <a-col :xl="8" :lg="8" :md="8" :sm="24">
                        <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                            <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
                            <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
                            <a-button @click="handleAdd" type="primary" icon="plus" style="margin-left: 8px">新增</a-button>

                            <a-button type="primary" icon="download" @click="handleExportXls('vip')" style="margin-left: 8px">导出</a-button>
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
                        <a-menu-item>
                            <a @click="handleDetail(record)">详情</a>
                        </a-menu-item>
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

    <vip-modal ref="modalForm" @ok="modalFormOk"></vip-modal>
    </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import VipModal from './modules/VipModal'

export default {
    name: 'VipList',
    mixins: [JeecgListMixin, mixinDevice],
    components: {
        VipModal
    },
    data () {
        return {
            description: 'vip管理页面',
            // 表头
            columns: [
                {
                    title:'会员名称',
                    align:"center",
                    dataIndex: 'name'
                },
                {
                    title:'会员等级',
                    align:"center",
                    dataIndex: 'level'
                },
                {
                    title:'会员周期',
                    align:"center",
                    dataIndex: 'period',
                    customRender (text) {
                        return text + ' 个月'
                    }
                },
                {
                    title:'会员价格',
                    align:"center",
                    dataIndex: 'price',
                    customRender (text) {
                        return text.toFixed(2) + ' 美元'
                    },
                },
                {
                    title:'会员描述',
                    align:"center",
                    dataIndex: 'description'
                },
                {
                    title:'排序',
                    align:"center",
                    dataIndex: 'sort'
                },
                {
                    title:'创建时间',
                    align:"center",
                    dataIndex: 'createAt'
                },
                {
                    title:'更新时间',
                    align:"center",
                    dataIndex: 'updateAt'
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
                list: "/manage/vip/list",
                delete: "/manage/vip/delete",
                deleteBatch: "/manage/vip/deleteBatch",
                exportXlsUrl: "/manage/vip/exportXls",
                importExcelUrl: "manage/vip/importExcel",

            },
            dictOptions: {},
            superFieldList: [],
            isorter:{
                column: 'sort',
                order: 'asc',
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
            fieldList.push({type:'string',value:'name',text:'会员名称'})
            fieldList.push({type:'string',value:'level',text:'会员等级'})
            fieldList.push({type:'int',value:'period',text:'会员周期 单位：月'})
            fieldList.push({type:'number',value:'price',text:'会员价格'})
            fieldList.push({type:'string',value:'description',text:'会员描述'})
            fieldList.push({type:'int',value:'sort',text:'排序 小到大'})
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
