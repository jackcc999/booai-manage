<template>
    <a-card :bordered="false">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
            <a-form layout="inline" @keyup.enter.native="searchQuery">
                <a-row :gutter="24">
                    <a-col :xl="8" :lg="8" :md="8" :sm="24">
                        <a-form-item label="开户活动标题">
                            <j-input placeholder="请输入开户活动标题" v-model="queryParam.title" @change="searchQuery"></j-input>
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
                        <a-form-item label="机构名称">
                            <j-input placeholder="请输入机构名称" v-model="queryParam.provider" @change="searchQuery"></j-input>
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
                            <a-button @click="handleAdd" type="primary" icon="plus" style="margin-left: 8px">新增</a-button>

                            <a-button type="primary" icon="download" @click="handleExportXls('活动')" style="margin-left: 8px">导出</a-button>
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

                <template slot="linkSlot" slot-scope="text,record">
                    <a-tooltip>
                        <template #title>{{record.description}}</template>
                        <a :href="record.url" target="_blank">{{text}}</a>
                    </a-tooltip>
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
                    <a @click="handleCopy(record)" style="margin-left: 8px;">复制</a>

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

        <financial-offer-modal ref="modalForm" @ok="modalFormOk"></financial-offer-modal>
    </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import FinancialOfferModal from './modules/FinancialOfferModal'
import { offerCategory } from '@/utils/enums'
import { postAction } from '@/api/manage'

export default {
    name: 'FinancialOfferList',
    mixins: [JeecgListMixin, mixinDevice],
    components: {
        FinancialOfferModal
    },
    data () {
        return {
            description: 'financial_offer管理页面',
            // 表头
            columns: [
                {
                    title:'卡分类',
                    align:"center",
                    dataIndex: 'category',
                    customRender: function (text) {
                        return offerCategory(text);
                    }
                },
                {
                    title:'二级分类',
                    align:"center",
                    dataIndex: 'childCategory',
                    customRender: function (text) {
                        return text ? text : '-';
                    }
                },
                {
                    title:'开户活动标题',
                    align:"left",
                    dataIndex: 'title',
                    scopedSlots: {customRender: 'linkSlot'}
                },
                {
                    title:'金融机构名称',
                    align:"center",
                    dataIndex: 'provider'
                },
                // {
                //     title:'活动url链接',
                //     align:"center",
                //     dataIndex: 'url'
                // },
                {
                    title:'存款金额要求',
                    align:"center",
                    dataIndex: 'depositAmount',
                    customRender: function (text) {
                        return text ? text + " 美元" : '-';
                    }
                },
                {
                    title:'存款要求时间',
                    align:"center",
                    dataIndex: 'depositDurationDays',
                    customRender: function (text) {
                        return text ? text + " 天" : '-';
                    }
                },
                {
                    title:'存款持有天数',
                    align:"center",
                    dataIndex: 'depositHoldDays',
                    customRender: function (text) {
                        return text ? text + " 天" : '-';
                    }
                },
                {
                    title:'返现金额',
                    align:"center",
                    dataIndex: 'returnAmount',
                    customRender: function (text) {
                        return text ? text + " 美元" : '-';
                    }
                },
                {
                    title:'返现说明',
                    align:"center",
                    dataIndex: 'returnRemark',
                    customRender: function (text) {
                        return text ? text : '-';
                    }
                },
                {
                    title:'消费金额要求',
                    align:"center",
                    dataIndex: 'spendingAmount',
                    customRender: function (text) {
                        return text ? text + " 美元" : '-';
                    }
                },
                {
                    title:'消费要求时间',
                    align:"center",
                    dataIndex: 'spendingDurationDays',
                    customRender: function (text) {
                        return text ? text + " 天" : '-';
                    }
                },
                {
                    title:'获得积分',
                    align:"center",
                    dataIndex: 'getPoint',
                    customRender: function (text) {
                        return text ? text : '-';
                    }
                },
                {
                    title:'积分说明',
                    align:"center",
                    dataIndex: 'pointRemark',
                    customRender: function (text) {
                        return text ? text : '-';
                    }
                },
                {
                    title:'活动截止日期',
                    align:"center",
                    dataIndex: 'expirationDate'
                },
                {
                    title:'年费金额',
                    align:"center",
                    dataIndex: 'annualFee',
                    customRender: function (text) {
                        return text ? text + " 美元" : '-';
                    }
                },
                {
                    title:'年费说明',
                    align:"center",
                    dataIndex: 'annualRemark',
                    customRender: function (text) {
                        return text ? text : '-';
                    }
                },
                {
                    title:'月费金额',
                    align:"center",
                    dataIndex: 'monthFee',
                    customRender: function (text) {
                        return text ? text + " 美元" : '-';
                    }
                },
                {
                    title:'月费说明',
                    align:"center",
                    dataIndex: 'monthRemark',
                    customRender: function (text) {
                        return text ? text : '-';
                    }
                },
                // {
                //     title:'免年费条件说明',
                //     align:"center",
                //     dataIndex: 'feeWaiverCondition'
                // },
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
                list: "/manage/financialOffer/list",
                delete: "/manage/financialOffer/delete",
                deleteBatch: "/manage/financialOffer/deleteBatch",
                exportXlsUrl: "/manage/financialOffer/exportXls",
                importExcelUrl: "manage/financialOffer/importExcel",

            },
            dictOptions: {},
            superFieldList: [],
            isorter:{
                column: 'sortOrder',
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
        handleCopy (record) {
            this.loading = true
            postAction('/manage/financialOffer/copy', record).then(res => {
                if(res.success) {
                    this.$message.success('复制成功')
                    this.searchQuery()
                } else {
                    this.$message.warning(res.message)
                }
            }).finally(() => {
                this.loading = false
            })
        },

        initDictConfig() {
        },

        getSuperFieldList() {
            let fieldList = [];
            fieldList.push({type:'string',value:'category',text:'活动分类'})
            fieldList.push({type:'string',value:'title',text:'标题'})
            fieldList.push({type:'string',value:'description',text:'描述'})
            fieldList.push({type:'string',value:'provider',text:'活动提供机构名称'})
            fieldList.push({type:'string',value:'rewardList',text:'奖励列表（JSON）'})
            fieldList.push({type:'string',value:'benefitList',text:'权益列表（JSON）'})
            fieldList.push({type:'string',value:'url',text:'活动链接URL'})
            fieldList.push({type:'date',value:'expirationDate',text:'活动截止日期'})
            fieldList.push({type:'int',value:'sortOrder',text:'排序因子，值越小越靠前'})
            fieldList.push({type:'number',value:'spendingAmount',text:'消费金额要求'})
            fieldList.push({type:'int',value:'spendingDurationDays',text:'完成消费要求的时间（天）'})
            fieldList.push({type:'number',value:'depositAmount',text:'存款金额要求'})
            fieldList.push({type:'int',value:'depositDurationDays',text:'完成存款要求的时间（天）'})
            fieldList.push({type:'number',value:'annualFee',text:'年费金额'})
            fieldList.push({type:'string',value:'feeWaiverCondition',text:'免年费条件说明'})
            fieldList.push({type:'int',value:'status',text:'活动状态：0-隐藏，1-展示'})
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
