<template>
    <a-spin :spinning="confirmLoading">
        <j-form-container :disabled="formDisabled">
            <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
                <a-row>
                    <a-col :span="12">
                        <a-form-model-item label="活动分类" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="category">
                            <a-radio-group v-model:value="model.category" placeholder="请选择活动分类">
                                <a-radio value="BANK">银行</a-radio>
                                <a-radio value="CREDIT_CARD">信用卡</a-radio>
                                <a-radio value="BROKER">券商</a-radio>
                            </a-radio-group>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="二级分类" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="childCategory">
                            <a-input v-model="model.childCategory" placeholder="请输入二级分类"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="活动链接URL" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="url">
                            <a-input v-model="model.url" placeholder="请输入活动链接URL"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="活动状态" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="status">
                            <a-radio-group v-model:value="model.status" placeholder="请选择状态">
                                <a-radio :value="1">展示</a-radio>
                                <a-radio :value="0">隐藏</a-radio>
                            </a-radio-group>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="开户权益" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="benefitList">
                            <a-textarea v-model="model.benefitList" rows="4" placeholder="请输入开户权益"  ></a-textarea>
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
    name: 'SquareForm',
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

                userId: [
                    { required: true, message: '请输入用户ID!'},
                ],

                category: [
                    { required: true, message: '请输入活动分类!'},
                ],

                childCategory: [
                    { required: true, message: '请输入二级分类!'},
                ],

                url: [
                    { required: true, message: '请输入活动链接URL!'},
                ],

                benefitList: [
                    { required: true, message: '请输入开户权益!'},
                ],

                status: [
                    { required: true, message: '请输入活动状态!'},
                ],

                isDeleted: [
                    { required: true, message: '请输入是否已删除（0：未删除，1：已删除）!'},
                ],

                createdAt: [
                    { required: true, message: '请输入创建时间!'},
                ],

                updatedAt: [
                    { required: true, message: '请输入更新时间!'},
                ],
            },
            url: {
                add: "/manage/square/add",
                edit: "/manage/square/edit",
                queryById: "/manage/square/queryById"
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
