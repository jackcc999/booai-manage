<template>
    <a-card :bordered="false">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
            <a-form layout="inline" @keyup.enter.native="searchQuery">
                <a-row :gutter="24">
                    <a-col :xl="5" :lg="6" :md="8" :sm="24">
                        <a-form-item label="用户名">
                            <j-input placeholder="请输入用户名" v-model="queryParam.username"></j-input>
                        </a-form-item>
                    </a-col>

                    <a-col :xl="5" :lg="6" :md="8" :sm="24">
                        <a-form-item label="登录类型">
                            <a-select v-model="queryParam.loginType" placeholder="请选择登录类型">
                                <a-select-option value="GOOGLE">Google</a-select-option>
                                <a-select-option value="APPLE">Apple</a-select-option>
                                <a-select-option value="GUEST">游客</a-select-option>
                            </a-select>
                        </a-form-item>
                    </a-col>

                    <a-col :xl="5" :lg="6" :md="8" :sm="24">
                        <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                            <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
                            <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
                            <!-- <a-button @click="handleAdd" type="primary" icon="plus" style="margin-left: 8px">新增</a-button> -->
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

    <user-modal ref="modalForm" @ok="modalFormOk"></user-modal>
    </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import UserModal from './modules/UserModal'
import { loginType } from '@/utils/enums'

export default {
    name: 'UserList',
    mixins: [JeecgListMixin, mixinDevice],
    components: {
        UserModal
    },
    data () {
        return {
            description: 'user管理页面',
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
                    title:'邮箱地址',
                    align:"center",
                    dataIndex: 'email',
                    customRender: function (text) {
                        return text ? text : '-';
                    }
                },
                {
                    title:'Google ID',
                    align:"center",
                    dataIndex: 'googleId',
                    customRender: function (text) {
                        return text ? text : '-';
                    }
                },
                {
                    title:'Apple ID',
                    align:"center",
                    dataIndex: 'appleId',
                    customRender: function (text) {
                        return text ? text : '-';
                    }
                },
                {
                    title:'登录类型',
                    align:"center",
                    dataIndex: 'loginType',
                    customRender: function (text) {
                        return loginType(text);
                    }
                },
                {
                    title:'设备类型',
                    align:"center",
                    dataIndex: 'deviceType',
                    customRender: function (text) {
                        return text === 1 ? 'ios' : 'Android';
                    }
                },
                {
                    title:'设备ID',
                    align:"center",
                    dataIndex: 'deviceCode',
                    customRender: function (text) {
                        return text ? text : '-';
                    }
                },
                {
                    title:'创建时间',
                    align:"center",
                    dataIndex: 'createdAt'
                }
            ],
            url: {
                list: "/manage/user/list",
                delete: "/manage/user/delete",
                deleteBatch: "/manage/user/deleteBatch",
                exportXlsUrl: "/manage/user/exportXls",
                importExcelUrl: "manage/user/importExcel",

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
            fieldList.push({type:'string',value:'username',text:'用户名'})
            fieldList.push({type:'string',value:'email',text:'邮箱地址'})
            fieldList.push({type:'string',value:'googleId',text:'Google ID'})
            fieldList.push({type:'string',value:'appleId',text:'Apple ID'})
            fieldList.push({type:'string',value:'loginType',text:'登录类型（GOOGLE：Google，APPLE：Apple，GUEST：游客）'})
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
