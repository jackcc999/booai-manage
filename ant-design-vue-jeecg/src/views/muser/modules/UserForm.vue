<template>
    <a-spin :spinning="confirmLoading">
        <j-form-container :disabled="formDisabled">
            <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
                <a-row>
                    <a-col :span="12">
                        <a-form-model-item label="用户名" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="username">
                            <a-input v-model="model.username" placeholder="请输入用户名"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="邮箱地址" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="email">
                            <a-input v-model="model.email" placeholder="请输入邮箱地址"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="Google ID" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="googleId">
                            <a-input v-model="model.googleId" placeholder="请输入Google ID"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="Apple ID" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="appleId">
                            <a-input v-model="model.appleId" placeholder="请输入Apple ID"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="登录类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="loginType">
                            <a-select v-model="model.loginType" placeholder="请选择登录类型">
                                <a-select-option value="GOOGLE">Google</a-select-option>
                                <a-select-option value="APPLE">Apple</a-select-option>
                                <a-select-option value="GUEST">游客</a-select-option>
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

export default {
    name: 'UserForm',
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
                add: "/manage/user/add",
                edit: "/manage/user/edit",
                queryById: "/manage/user/queryById"
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
