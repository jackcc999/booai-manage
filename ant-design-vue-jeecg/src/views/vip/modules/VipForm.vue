<template>
    <a-spin :spinning="confirmLoading">
        <j-form-container :disabled="formDisabled">
            <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
                <a-row>
                    <a-col :span="12">
                        <a-form-model-item label="会员名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="name">
                            <a-input v-model="model.name" placeholder="请输入会员名称"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="会员等级" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="level">
                            <a-input v-model="model.level" placeholder="请输入会员等级"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="会员周期" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="period">
                            <a-input-group compact>
                                <a-input-number v-model="model.period" placeholder="请输入会员周期" style="width:calc(100% - 60px)" />
                                <span class="input-group-addon" style="width: 60px;">个月</span>
                            </a-input-group>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="会员价格" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="price">
                            <a-input-group compact>
                                <a-input-number v-model="model.price" placeholder="请输入会员价格" style="width:calc(100% - 60px)" />
                                <span class="input-group-addon" style="width: 60px;">美元</span>
                            </a-input-group>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="会员描述" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="description">
                            <a-textarea v-model="model.description" rows="4" placeholder="请输入会员描述"  ></a-textarea>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="排序" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sort">
                            <a-input-number v-model="model.sort" placeholder="请输入排序 小到大" style="width: 100%" />
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
    name: 'VipForm',
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
                period: 1,
                sort: 100
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
                name: [
                    { required: true, message: '请输入会员名称!'},
               ],

                level: [
                    { required: true, message: '请输入会员等级!'},
               ],

                period: [
                    { required: true, message: '请输入会员周期 单位：月!'},
               ],

                price: [
                    { required: true, message: '请输入会员价格!'},
               ],

                description: [
                    { required: true, message: '请输入会员描述!'},
               ],

                sort: [
                    { required: true, message: '请输入排序 小到大!'},
                ],
            },
            url: {
                add: "/manage/vip/add",
                edit: "/manage/vip/edit",
                queryById: "/manage/vip/queryById"
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
