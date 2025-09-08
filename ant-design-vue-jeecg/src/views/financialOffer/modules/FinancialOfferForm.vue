<template>
    <a-spin :spinning="confirmLoading">
        <j-form-container :disabled="formDisabled">
            <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
                <a-row>
                    <a-col :span="12">
                        <a-form-model-item label="活动分类" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="category">
                            <a-select v-model="model.category" placeholder="请选择活动分类">
                                <a-select-option value="BANK">银行</a-select-option>
                                <a-select-option value="CREDIT_CARD">信用卡</a-select-option>
                                <a-select-option value="BROKER">券商</a-select-option>
                            </a-select>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="二级分类" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="childCategory">
                            <a-input v-model="model.childCategory" placeholder="请输入二级分类"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="标题" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="title">
                            <a-input v-model="model.title" placeholder="请输入标题"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="描述" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="description">
                            <j-editor v-model="model.description" placeholder="请输入描述" />
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="活动提供机构名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="provider">
                            <a-input v-model="model.provider" placeholder="请输入活动提供机构名称"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="奖励列表" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="rewardList">
                            <a-textarea v-model="model.rewardList" rows="4" placeholder='示例:[{"name":"奖励名称","description":"奖励描述","estimatedValue":"估算价值","rights":"权益","reward":"奖励"}]' />
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="权益列表" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="benefitList">
                            <j-editor v-model="model.benefitList" />
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="活动链接URL" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="url">
                            <a-input v-model="model.url" placeholder="请输入活动链接URL"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="活动截止日期" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="expirationDate">
                            <j-date placeholder="请选择活动截止日期" v-model="model.expirationDate"    style="width: 100%" />
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="排序" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sortOrder">
                            <a-input-number v-model="model.sortOrder" placeholder="请输入排序因子，值越小越靠前" style="width: 100%" />
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="消费金额要求" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="spendingAmount">
                            <a-input-group compact>
                                <a-input-number v-model="model.spendingAmount" placeholder="请输入消费金额要求" style="width:calc(100% - 60px)" />
                                <span class="input-group-addon" style="width: 60px;">美元</span>
                            </a-input-group>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="完成消费要求的时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="spendingDurationDays">
                            <a-input-group compact>
                                <a-input-number v-model="model.spendingDurationDays" placeholder="请输入完成消费要求的时间" style="width: calc(100% - 45px)" />
                                <span class="input-group-addon" style="width: 45px;">天</span>
                            </a-input-group>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="存款金额要求" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="depositAmount">
                            <a-input-group compact>
                                <a-input-number v-model="model.depositAmount" placeholder="请输入存款金额要求" style="width:calc(100% - 60px)" />
                                <span class="input-group-addon" style="width: 60px;">美元</span>
                            </a-input-group>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="完成存款要求的时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="depositDurationDays">
                            <a-input-group compact>
                                <a-input-number v-model="model.depositDurationDays" placeholder="请输入完成存款要求的时间" style="width:calc(100% - 45px)"  />
                                <span class="input-group-addon" style="width: 45px;">天</span>
                            </a-input-group>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="年费金额" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="annualFee">
                            <a-input-group compact>
                                <a-input-number v-model="model.annualFee" placeholder="请输入年费金额" style="width:calc(100% - 60px)" />
                                <span class="input-group-addon" style="width: 60px;">美元</span>
                            </a-input-group>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="返现金额" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="returnAmount">
                            <a-input-group compact>
                                <a-input-number v-model="model.returnAmount" placeholder="请输入返现金额" style="width:calc(100% - 60px)" />
                                <span class="input-group-addon" style="width: 60px;">美元</span>
                            </a-input-group>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="返现说明" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="returnRemark">
                            <a-input v-model="model.returnRemark" placeholder="请输入返现说明"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="获得积分" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="getPoint">
                            <a-input-number v-model="model.getPoint" placeholder="请输入获得积分" style="width:100%" />
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="积分说明" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="pointRemark">
                            <a-input v-model="model.pointRemark" placeholder="请输入积分说明"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="免年费条件说明" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="feeWaiverCondition">
                            <a-input v-model="model.feeWaiverCondition" placeholder="请输入免年费条件说明"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="活动状态" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="status">
                            <a-select v-model="model.status" placeholder="请选择状态">
                                <a-select-option :value="1">展示</a-select-option>
                                <a-select-option :value="0">隐藏</a-select-option>
                            </a-select>
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
import JEditor from '@/components/jeecg/JEditor'

export default {
    name: 'FinancialOfferForm',
    components: {
        JEditor
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
                status: 1
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
                category: [
                    { required: true, message: '请选择活动分类' },
                ],
                childCategory: [
                    { required: true, message: '请输入二级分类' },
                ],
                title: [
                    { required: true, message: '请输入标题' },
                ],
                description: [
                    { required: true, message: '请输入描述' },
                ],
                provider: [
                    { required: true, message: '请输入活动提供机构名称' },
                ],
                url: [
                    { required: true, message: '请输入活动链接URL' },
                ],
                expirationDate: [
                    { required: true, message: '请选择活动截止日期' },
                ],
            },
            url: {
                add: "/manage/financialOffer/add",
                edit: "/manage/financialOffer/edit",
                queryById: "/manage/financialOffer/queryById"
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
