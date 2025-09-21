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
                        <a-form-item label="机构名称">
                            <j-input placeholder="请输入机构名称" v-model="queryParam.provider" @change="searchQuery"></j-input>
                        </a-form-item>
                    </a-col>

                    <a-col :xl="8" :lg="8" :md="8" :sm="24">
                        <a-form-item label="卡分类">
                            <a-select v-model="queryParam.category" placeholder="请选择卡分类" @change="searchQuery">
                                <a-select-option value="BANK">银行</a-select-option>
                                <a-select-option value="CREDIT_CARD">信用卡</a-select-option>
                                <a-select-option value="BROKER">券商</a-select-option>
                            </a-select>
                        </a-form-item>
                    </a-col>

                    <a-col :xl="8" :lg="8" :md="8" :sm="24">
                        <a-form-item label="二级分类">
                            <j-input placeholder="请输入二级分类" v-model="queryParam.childCategory" @change="searchQuery"></j-input>
                        </a-form-item>
                    </a-col>

                    <a-col :xl="8" :lg="8" :md="8" :sm="24">
                        <a-form-item label="活动状态">
                            <a-select v-model="queryParam.status" placeholder="请选择活动状态" @change="searchQuery">
                                <a-select-option :value="1">展示</a-select-option>
                                <a-select-option :value="0">隐藏</a-select-option>
                            </a-select>
                        </a-form-item>
                    </a-col>

                    <a-col :xl="8" :lg="8" :md="8" :sm="24">
                        <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                            <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
                            <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
                            <!-- <a-button @click="handleAdd" type="primary" icon="plus" style="margin-left: 8px">新增</a-button> -->

                            <a-button type="primary" icon="download" @click="handleExportXls('活动广场')" style="margin-left: 8px">导出</a-button>
                            <!-- <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" style="margin-left: 8px" @change="handleImportExcel">
                                <a-button type="primary" icon="import">导入</a-button>
                            </a-upload> -->


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

    <square-modal ref="modalForm" @ok="modalFormOk"></square-modal>
    </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import SquareModal from './modules/SquareModal'

export default {
    name: 'SquareList',
    mixins: [JeecgListMixin, mixinDevice],
    components: {
        SquareModal
    },
    data () {
        return {
            description: '活动广场管理页面',
            // 表头
            columns: [
                {
                    title:'用户ID',
                    align:"center",
                    dataIndex: 'userId'
                },
                {
                    title:'活动分类',
                    align:"center",
                    dataIndex: 'category'
                },
                {
                    title:'二级分类',
                    align:"center",
                    dataIndex: 'childCategory'
                },
                {
                    title:'机构名称',
                    align:"center",
                    dataIndex: 'provider'
                },
                {
                    title:'活动链接URL',
                    align:"center",
                    dataIndex: 'url'
                },
                {
                    title:'开户权益',
                    align:"center",
                    dataIndex: 'benefitList',
                    scopedSlots: { customRender: 'htmlSlot' }
                },
                {
                    title:'活动状态',
                    align:"center",
                    dataIndex: 'status',
                    customRender: function (text) {
                        return text == 1 ? '展示' : '隐藏';
                    }
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
                list: "/manage/square/list",
                delete: "/manage/square/delete",
                deleteBatch: "/manage/square/deleteBatch",
                exportXlsUrl: "/manage/square/exportXls",
                importExcelUrl: "manage/square/importExcel",

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
            fieldList.push({type:'string',value:'category',text:'活动分类'})
            fieldList.push({type:'string',value:'childCategory',text:'二级分类'})
            fieldList.push({type:'string',value:'url',text:'活动链接URL'})
            fieldList.push({type:'string',value:'benefitList',text:'开户权益'})
            fieldList.push({type:'int',value:'status',text:'活动状态'})
            fieldList.push({type:'int',value:'isDeleted',text:'是否已删除（0：未删除，1：已删除）'})
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
