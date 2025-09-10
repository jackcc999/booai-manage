<template>
    <a-card :bordered="false">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
            <a-form layout="inline" @keyup.enter.native="searchQuery">
                <a-row :gutter="24">
                    <a-col :xl="8" :lg="8" :md="8" :sm="24">
                        <a-form-item label="用户ID">
                            <a-input placeholder="请输入用户ID" v-model="queryParam.userId"></a-input>
                        </a-form-item>
                    </a-col>
                    <a-col :xl="8" :lg="8" :md="8" :sm="24">
                        <a-form-item label="活动ID">
                            <a-input placeholder="请输入活动ID" v-model="queryParam.offerId"></a-input>
                        </a-form-item>
                    </a-col>
                    <a-col :xl="8" :lg="8" :md="8" :sm="24">
                        <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                            <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
                            <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
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
                <a-popconfirm v-has="'com.financial:user_collect:delete'" title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                    <a>删除</a>
                </a-popconfirm>
            </span>
        </a-table>
    </div>

    <user-collect-modal ref="modalForm" @ok="modalFormOk"></user-collect-modal>
    </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import UserCollectModal from './modules/UserCollectModal'

export default {
    name: 'UserCollectList',
    mixins: [JeecgListMixin, mixinDevice],
    components: {
        UserCollectModal
    },
    data () {
        return {
            description: '用户收藏管理页面',
            // 表头
            columns: [
                {
                    title:'用户ID',
                    align:"center",
                    dataIndex: 'userId'
                },
                {
                    title:'用户名',
                    align:"center",
                    dataIndex: 'username'
                },
                {
                    title:'开户活动标题',
                    align:"left",
                    dataIndex: 'offerTitle'
                },
                {
                    title:'创建时间',
                    align:"center",
                    dataIndex: 'createdAt'
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
                list: "/manage/userCollect/list",
                delete: "/manage/userCollect/delete",
                deleteBatch: "/manage/userCollect/deleteBatch",
                exportXlsUrl: "/manage/userCollect/exportXls",
                importExcelUrl: "manage/userCollect/importExcel",

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
            fieldList.push({type:'int',value:'offerId',text:'活动ID'})
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
