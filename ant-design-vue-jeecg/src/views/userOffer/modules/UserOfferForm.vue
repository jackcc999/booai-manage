<template>
    <a-spin :spinning="confirmLoading">
        <j-form-container :disabled="formDisabled">
            <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
                <a-row>
                    <a-col :span="24">
                        <a-form-model-item label="用户ID" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="userId">
                            <a-input-number v-model="model.userId" placeholder="请输入用户ID" style="width: 100%" />
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="24">
                        <a-form-model-item label="活动ID" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="offerId">
                            <a-input-number v-model="model.offerId" placeholder="请输入活动ID" style="width: 100%" />
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="24">
                        <a-form-model-item label="状态（APPLYING: 申请中, COMPLETED: 已完成, REJECTED: 已拒绝, REWARDING: 领取奖励中）" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="status">
                            <a-input v-model="model.status" placeholder="请输入状态（APPLYING: 申请中, COMPLETED: 已完成, REJECTED: 已拒绝, REWARDING: 领取奖励中）"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="24">
                        <a-form-model-item label="活动分类（BANK：银行，CREDIT_CARD：信用卡，BROKER：券商）" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="offerCategory">
                            <a-input v-model="model.offerCategory" placeholder="请输入活动分类（BANK：银行，CREDIT_CARD：信用卡，BROKER：券商）"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="24">
                        <a-form-model-item label="活动标题" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="offerTitle">
                            <a-input v-model="model.offerTitle" placeholder="请输入活动标题"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="24">
                        <a-form-model-item label="活动描述" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="offerDescription">
                            <a-textarea v-model="model.offerDescription" rows="4" placeholder="请输入活动描述" />
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="24">
                        <a-form-model-item label="活动提供机构名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="offerProvider">
                            <a-input v-model="model.offerProvider" placeholder="请输入活动提供机构名称"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="24">
                        <a-form-model-item label="活动链接URL" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="offerUrl">
                            <a-input v-model="model.offerUrl" placeholder="请输入活动链接URL"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="24">
                        <a-form-model-item label="奖励列表（JSON）" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="offerRewardList">
                            <a-textarea v-model="model.offerRewardList" rows="4" placeholder="请输入奖励列表（JSON）" />
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="24">
                        <a-form-model-item label="权益列表（JSON）" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="offerBenefitList">
                            <a-textarea v-model="model.offerBenefitList" rows="4" placeholder="请输入权益列表（JSON）" />
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="24">
                        <a-form-model-item label="审批时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="approvalTime">
                            <j-date placeholder="请选择审批时间" v-model="model.approvalTime"    style="width: 100%" />
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="24">
                        <a-form-model-item label="完成时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="completedTime">
                            <j-date placeholder="请选择完成时间" v-model="model.completedTime"    style="width: 100%" />
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="24">
                        <a-form-model-item label="创建时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="createdAt">
                            <j-date placeholder="请选择创建时间" v-model="model.createdAt"    style="width: 100%" />
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="24">
                        <a-form-model-item label="更新时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="updatedAt">
                            <j-date placeholder="请选择更新时间" v-model="model.updatedAt"    style="width: 100%" />
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
                    httpAction(httpurl, this.model, method).then(res => {
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
