<template>
    <a-card :bordered="false">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
            <a-form layout="inline" @keyup.enter.native="searchQuery">
                <a-row :gutter="24">
                    <a-col :xl="5" :lg="6" :md="8" :sm="24">
                        <a-form-item label="咨询标题">
                            <j-input placeholder="请输入咨询标题" v-model="queryParam.title"></j-input>
                        </a-form-item>
                    </a-col>
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

    <information-modal ref="modalForm" @ok="modalFormOk"></information-modal>
    </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import InformationModal from './modules/InformationModal'

export default {
    name: 'InformationList',
    mixins: [JeecgListMixin, mixinDevice],
    components: {
        InformationModal
    },
    data () {
        return {
            description: '咨询管理页面',
            // 表头
            columns: [
                {
                    title:'标题',
                    align:"center",
                    dataIndex: 'title'
                },
                // {
                //     title:'内容',
                //     align:"center",
                //     dataIndex: 'content',
                //     scopedSlots: { customRender: 'htmlSlot' }
                // },
                {
                    title:'链接',
                    align:"center",
                    dataIndex: 'link'
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
                list: "/manage/information/list",
                delete: "/manage/information/delete",
                deleteBatch: "/manage/information/deleteBatch",
                exportXlsUrl: "/manage/information/exportXls",
                importExcelUrl: "manage/information/importExcel",

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
            fieldList.push({type:'string',value:'title',text:'标题'})
            fieldList.push({type:'string',value:'content',text:'内容'})
            fieldList.push({type:'string',value:'link',text:'链接'})
            fieldList.push({type:'string',value:'image',text:'图片'})
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
