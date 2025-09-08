<template>
    <a-card :bordered="false">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
            <a-form layout="inline" @keyup.enter.native="searchQuery">
                <a-row :gutter="24">
                    <a-col :xl="5" :lg="6" :md="8" :sm="24">
                        <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                            <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
                            <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
                            <a-button @click="handleAdd" type="primary" icon="plus" style="margin-left: 8px">新增</a-button>
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


            <template slot="fileSlot" slot-scope="text, record">
                <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
                <a v-else :href="host + '/files/download?id=' + record.url">{{record.fileName}}</a>
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

    <app-version-modal ref="modalForm" @ok="modalFormOk"></app-version-modal>
    </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import AppVersionModal from './modules/AppVersionModal'

export default {
    name: 'AppVersionList',
    mixins: [JeecgListMixin, mixinDevice],
    components: {
        AppVersionModal
    },
    data () {
        return {
            description: '版本管理管理页面',
            // 表头
            columns: [
                {
                    title:'版本名称',
                    align:"center",
                    dataIndex: 'name'
                },
                {
                    title:'类型',
                    align:"center",
                    dataIndex: 'type'
                },
                {
                    title:'版本号',
                    align:"center",
                    dataIndex: 'version'
                },
                {
                    title:'版本描述',
                    align:"center",
                    dataIndex: 'description'
                },
                {
                    title:'版本文件',
                    align:"center",
                    dataIndex: 'url',
                    scopedSlots: { customRender: 'fileSlot' }
                },
                {
                    title:'状态',
                    align:"center",
                    dataIndex: 'status',
                    customRender: function (text) {
                        return text == 1 ? '启用' : '禁用';
                    }
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
                list: "/manage/appVersion/list",
                delete: "/manage/appVersion/delete",
                deleteBatch: "/manage/appVersion/deleteBatch",
                exportXlsUrl: "/manage/appVersion/exportXls",
                importExcelUrl: "manage/appVersion/importExcel",
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
        host: function () {
            return window._CONFIG['domianURL'];
        }
    },
    methods: {


        initDictConfig() {
        },

        getSuperFieldList() {
            let fieldList = [];
            fieldList.push({type:'string',value:'name',text:'版本名称'})
            fieldList.push({type:'string',value:'type',text:'类型 ios、android'})
            fieldList.push({type:'string',value:'version',text:'版本号'})
            fieldList.push({type:'string',value:'description',text:'版本描述'})
            fieldList.push({type:'string',value:'url',text:'版本文件'})
            fieldList.push({type:'int',value:'status',text:'状态'})
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
