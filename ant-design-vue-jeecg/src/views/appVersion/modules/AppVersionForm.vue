<template>
    <a-spin :spinning="confirmLoading">
        <j-form-container :disabled="formDisabled">
            <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
                <a-row>
                    <a-col :span="12">
                        <a-form-model-item label="版本名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="name">
                            <a-input v-model="model.name" placeholder="请输入版本名称"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="版本类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="type">
                            <a-select v-model="model.type" placeholder="请选择类型">
                                <a-select-option value="android">android</a-select-option>
                                <a-select-option value="ios">ios</a-select-option>
                            </a-select>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="版本号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="version">
                            <a-input v-model="model.version" placeholder="请输入版本号"  ></a-input>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="版本文件" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="url">
                            <a-input-group compact :disabled="disabled">
                                <a-input v-model="model.fileName" readOnly placeholder="请选择文件" style="width:calc(100% - 64.5px);cursor: default;"></a-input>
                                <v-upload accept=".apk" @change="versionFileFileOk" @loading="(state) => confirmLoading=state">上传</v-upload>
                            </a-input-group>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="24">
                        <a-form-model-item label="版本描述" class="w24" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="description">
                            <a-textarea v-model="model.description" placeholder="请输入版本描述" :auto-size="{ minRows: 4, maxRows: 6 }" ></a-textarea>
                        </a-form-model-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-model-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="status">
                            <a-select v-model="model.status" placeholder="请选择状态">
                                <a-select-option :value="1">启用</a-select-option>
                                <a-select-option :value="0">禁用</a-select-option>
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
import VUpload from '@/components/VUpload'

export default {
    name: 'AppVersionForm',
    components: {
        VUpload
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
                name: [
                    { required: true, message: '请输入版本名称!'},
                ],
                type: [
                    { required: true, message: '请输入类型 ios、android!'},
                ],
                version: [
                    { required: true, message: '请输入版本号!'},
                ],
                description: [
                    { required: true, message: '请输入版本描述!'},
                ],
                url: [
                    { required: true, message: '请输入版本文件!'},
                ],
                status: [
                    { required: true, message: '请输入状态!'},
                ],
            },
            url: {
                add: "/manage/appVersion/add",
                edit: "/manage/appVersion/edit",
                queryById: "/manage/appVersion/queryById"
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

        versionFileFileOk(data) {
            this.model.url = data.id;
            this.model.fileName = data.filename;

            console.log(this.model)

            this.$refs.form.validateField('url');
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
