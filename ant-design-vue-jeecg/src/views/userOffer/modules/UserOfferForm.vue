<template>
    <a-spin :spinning="confirmLoading">
        <j-form-container :disabled="formDisabled">
            <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
                <a-row>
                    <a-col :span="12">
                        <a-form-model-item label="用户ID" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="userId">
                            <a-input-number v-model="model.userId" placeholder="请输入用户ID" disabled style="width: 100%" />
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="活动ID" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="offerId">
                            <a-input-number v-model="model.offerId" placeholder="请输入活动ID" disabled style="width: 100%" />
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="status">
                            <a-select v-model="model.status" placeholder="请选择状态">
                                <a-select-option value="APPLYING">申请中</a-select-option>
                                <a-select-option value="COMPLETED">已完成</a-select-option>
                                <a-select-option value="REJECTED">已拒绝</a-select-option>
                                <a-select-option value="REWARDING">领取奖励中</a-select-option>
                                <a-select-option value="DEPOSITED">已存款</a-select-option>
                            </a-select>
                        </a-form-model-item>
                    </a-col>
                    <!-- <a-col :span="12">
                        <a-form-model-item label="活动分类" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="offerCategory">
                            <a-select v-model="model.offerCategory" placeholder="请选择活动分类">
                                <a-select-option value="BANK">银行</a-select-option>
                                <a-select-option value="CREDIT_CARD">信用卡</a-select-option>
                                <a-select-option value="BROKER">券商</a-select-option>
                            </a-select>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="二级分类" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="offerChildCategory">
                            <a-input v-model="model.offerChildCategory" placeholder="请输入二级分类"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="活动标题" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="offerTitle">
                            <a-input v-model="model.offerTitle" placeholder="请输入活动标题"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="活动描述" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="offerDescription">
                            <a-textarea v-model="model.offerDescription" rows="4" placeholder="请输入活动描述" />
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="活动提供机构名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="offerProvider">
                            <a-input v-model="model.offerProvider" placeholder="请输入活动提供机构名称"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="活动链接URL" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="offerUrl">
                            <a-input v-model="model.offerUrl" placeholder="请输入活动链接URL"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="奖励列表" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="offerRewardList">
                            <a-textarea v-model="model.offerRewardList" rows="4" placeholder="请输入奖励列表" />
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="权益列表" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="offerBenefitList">
                            <a-textarea v-model="model.offerBenefitList" rows="4" placeholder="请输入权益列表" />
                        </a-form-model-item>
                    </a-col> -->
                    <a-col :span="12">
                        <a-form-model-item label="开户银行/机构" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="bankName">
                            <a-input v-model="model.bankName" placeholder="请输入开户银行/机构"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="开户日期" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="openDate">
                            <j-date placeholder="请选择开户日期" v-model="model.openDate"    style="width: 100%" />
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="投资金额" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="investment">
                            <a-input v-model="model.investment" placeholder="请输入投资金额"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="返现/积分" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="returnPoint">
                            <a-input v-model="model.returnPoint" placeholder="请输入返现/积分"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="记录来源" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="source">
                            <a-select v-model="model.source" placeholder="请选择记录来源">
                                <a-select-option :value="0">我已开户</a-select-option>
                                <a-select-option :value="1">我要开户</a-select-option>
                            </a-select>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="审批时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="approvalTime">
                            <j-date placeholder="请选择审批时间" v-model="model.approvalTime" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="完成时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="completedTime">
                            <j-date placeholder="请选择完成时间" v-model="model.completedTime" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
                        </a-form-model-item>
                    </a-col>
                </a-row>
            </a-form-model>
        </j-form-container>
    </a-spin>
</template>

<script>

import { httpAction, getAction } from '@/api/manage'
import { validateDuplicateValue } from '@/utils/util'

export default {
    name: 'UserOfferForm',
    components: {
    },
    props: {
        //表单禁用
        disabled: {
            type: Boolean,
            default: false,
            required: false
        }
    },
    data () {
        return {
            model:{
            },
            labelCol: {
                xs: { span: 24 },
                sm: { span: 5 },
            },
            wrapperCol: {
                xs: { span: 24 },
                sm: { span: 16 },
            },
            confirmLoading: false,
            validatorRules: {

            },
            url: {
                add: "/manage/userOffer/add",
                edit: "/manage/userOffer/edit",
                queryById: "/manage/userOffer/queryById"
            }
        }
    },
    computed: {
        formDisabled(){
            return this.disabled
        },

    },
    created () {
        //备份model原始值
        this.modelDefault = JSON.parse(JSON.stringify(this.model));
    },
    methods: {
        add () {
            this.edit(this.modelDefault);
        },

        edit (record) {
            this.model = Object.assign({}, record);
            this.visible = true;
        },

        submitForm () {
            const that = this;
            // 触发表单验证
            this.$refs.form.validate(valid => {
                if (valid) {
                    that.confirmLoading = true;
                    let httpurl = '';
                    let method = '';
                    if(!this.model.id){
                        httpurl += this.url.add;
                        method = 'post';
                    }else{
                        httpurl += this.url.edit;
                        method = 'put';
                    }

                    var params = {
                        id: this.model.id,
                        status: this.model.status,
                        bankName: this.model.bankName,
                        openDate: this.model.openDate,
                        investment: this.model.investment,
                        returnPoint: this.model.returnPoint,
                        source: this.model.source,
                        approvalTime: this.model.approvalTime,
                        completedTime: this.model.completedTime,
                    }

                    httpAction(httpurl, params, method).then(res => {
                        if(res.success) {
                            that.$message.success(res.message);
                            that.$emit('ok');
                        } else {
                            that.$message.warning(res.message);
                        }
                    }).finally(() => {
                        that.confirmLoading = false;
                    })
                }
            })
        },


    }
}
</script>
