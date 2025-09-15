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
                        <a-form-item label="状态">
                            <a-select v-model="queryParam.status" placeholder="请选择状态">
                                <a-select-option value="APPLYING">申请中</a-select-option>
                                <a-select-option value="COMPLETED">已完成</a-select-option>
                                <a-select-option value="REJECTED">已拒绝</a-select-option>
                                <a-select-option value="REWARDING">领取奖励中</a-select-option>
                                <a-select-option value="DEPOSITED">已存款</a-select-option>
                            </a-select>
                        </a-form-item>
                    </a-col>

                    <a-col :xl="8" :lg="8" :md="8" :sm="24">
                        <a-form-item label="活动分类">
                            <a-select v-model="queryParam.offerCategory" placeholder="请选择活动分类">
                                <a-select-option value="BANK">银行</a-select-option>
                                <a-select-option value="CREDIT_CARD">信用卡</a-select-option>
                                <a-select-option value="BROKER">券商</a-select-option>
                            </a-select>
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

            <template slot="linkSlot" slot-scope="text,record">
                <a @click="handleOfferInfo(record)">{{text}}</a>
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
                <a v-has="'com.financial:user_offer:edit'" @click="handleEdit(record)">编辑</a>

                <a-divider type="vertical" />
                <a-dropdown>
                    <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
                    <a-menu slot="overlay">
                        <a-menu-item>
                            <a @click="handleDetail(record)">详情</a>
                        </a-menu-item>
                        <a-menu-item v-has="'com.financial:user_offer:delete'">
                            <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                                <a>删除</a>
                            </a-popconfirm>
                        </a-menu-item>
                    </a-menu>
                </a-dropdown>
            </span>
        </a-table>
    </div>

    <user-offer-modal ref="modalForm" @ok="modalFormOk"></user-offer-modal>
    <financial-offer-modal ref="offerInfo"></financial-offer-modal>
    </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import UserOfferModal from './modules/UserOfferModal'
import FinancialOfferModal from '@/views/financialOffer/modules/FinancialOfferModal'
import { offerCategory, userOfferState } from '@/utils/enums'



export default {
    name: 'UserOfferList',
    mixins: [JeecgListMixin, mixinDevice],
    components: {
        UserOfferModal,FinancialOfferModal
    },
    data () {
        return {
            description: '用户参与活动管理页面',
            // 表头
            columns: [
                {
                    title:'ID',
                    align:"center",
                    dataIndex: 'id'
                },
                {
                    title:'开户用户',
                    align:"center",
                    dataIndex: 'username'
                },
                {
                    title:'开户活动ID',
                    align:"center",
                    dataIndex: 'offerId'
                },
                {
                    title:'开户机构名称',
                    align:"center",
                    dataIndex: 'offerProvider'
                },
                {
                    title:'状态',
                    align:"center",
                    dataIndex: 'status',
                    customRender: function (text) {
                        return userOfferState(text);
                    }
                },
                // {
                //     title:'活动分类',
                //     align:"center",
                //     dataIndex: 'offerCategory',
                //     customRender: function (text) {
                //         return offerCategory(text);
                //     }
                // },
                {
                    title:'二级分类',
                    align:"center",
                    dataIndex: 'offerChildCategory',
                    customRender: function (text) {
                        return text ? text : '-';
                    }
                },
                {
                    title:'活动标题',
                    align:"center",
                    dataIndex: 'offerTitle',
                    scopedSlots: {customRender: 'linkSlot'}
                },

                {
                    title:'开户日期',
                    align:"center",
                    dataIndex: 'openDate',
                    customRender: function (text) {
                        return text ? text : '-';
                    }
                },
                {
                    title:'入金金额',
                    align:"center",
                    dataIndex: 'investment',
                    customRender: function (text) {
                        return text ? text : '-';
                    }
                },
                {
                    title:'返现/积分',
                    align:"center",
                    dataIndex: 'returnPoint',
                    customRender: function (text) {
                        return text ? text : '-';
                    }
                },
                {
                    title:'记录来源',
                    align:"center",
                    dataIndex: 'source',
                    customRender: function (text) {
                        return text === 0 ? '我已开户' : '我要开户';
                    }
                },



                // {
                //     title:'卡户银行/机构',
                //     align:"center",
                //     dataIndex: 'bankName',
                //     customRender: function (text) {
                //         return text ? text : '-';
                //     }
                // },
                // {
                //     title:'审批时间',
                //     align:"center",
                //     dataIndex: 'approvalTime'
                // },
                // {
                //     title:'完成时间',
                //     align:"center",
                //     dataIndex: 'completedTime'
                // },
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
                list: "/manage/userOffer/list",
                delete: "/manage/userOffer/delete",
                deleteBatch: "/manage/userOffer/deleteBatch",
                exportXlsUrl: "/manage/userOffer/exportXls",
                importExcelUrl: "manage/userOffer/importExcel",

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
        handleOfferInfo (record) {
            this.$refs.offerInfo.edit(record);
            this.$refs.offerInfo.title="详情";
            this.$refs.offerInfo.disableSubmit = true;
        },

        initDictConfig() {
        },

        getSuperFieldList() {
            let fieldList = [];
            fieldList.push({type:'int',value:'userId',text:'用户ID'})
            fieldList.push({type:'int',value:'offerId',text:'活动ID'})
            fieldList.push({type:'string',value:'status',text:'状态（APPLYING: 申请中, COMPLETED: 已完成, REJECTED: 已拒绝, REWARDING: 领取奖励中）'})
            fieldList.push({type:'string',value:'offerCategory',text:'活动分类（BANK：银行，CREDIT_CARD：信用卡，BROKER：券商）'})
            fieldList.push({type:'string',value:'offerTitle',text:'活动标题'})
            fieldList.push({type:'string',value:'offerDescription',text:'活动描述'})
            fieldList.push({type:'string',value:'offerProvider',text:'活动提供机构名称'})
            fieldList.push({type:'string',value:'offerUrl',text:'活动链接URL'})
            fieldList.push({type:'string',value:'offerRewardList',text:'奖励列表（JSON）'})
            fieldList.push({type:'string',value:'offerBenefitList',text:'权益列表（JSON）'})
            fieldList.push({type:'date',value:'approvalTime',text:'审批时间'})
            fieldList.push({type:'date',value:'completedTime',text:'完成时间'})
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
