<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail" class="app">
        <a-row>
          <a-col :span="12">
            <a-form-model-item label="应用名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="appName">
              <a-input v-model="model.appName" placeholder="请输入应用名称"  ></a-input>
            </a-form-model-item>
          </a-col>

          <a-col :span="12">
            <a-form-model-item label="请求地址" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="url">
              <a-input v-model="model.url" placeholder="请输入请求地址"  ></a-input>
            </a-form-model-item>
          </a-col>

          <a-col :span="12">
            <a-form-model-item label="应用编码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="appCode">
              <a-input v-model="model.appCode" placeholder="请输入应用编码"  ></a-input>
            </a-form-model-item>
          </a-col>

          <a-col :span="12">
            <a-form-model-item label="同步接口" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="adapter">
              <a-input v-model="model.adapter" placeholder="请输入同步接口"  ></a-input>
            </a-form-model-item>
          </a-col>

          <a-col :span="12">
            <a-form-model-item label="应用ID" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="appId">
              <a-input-group compact>
                <a-input v-model="model.appId" placeholder="请生成应用ID或点击右侧的生成按钮" style="width:calc(100% - 64px)" />
                <a-button type="primary" @click="genOneId('appId')">生成</a-button>
              </a-input-group>
            </a-form-model-item>
          </a-col>

          <a-col :span="12">
            <a-form-model-item label="配置数据" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="configData">
              <a-input v-model="model.configData" placeholder="请输入配置数据"  ></a-input>
            </a-form-model-item>
          </a-col>

          <a-col :span="12">
            <a-form-model-item label="应用密钥" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="secret">
              <a-input-group compact>
                <a-input v-model="model.secret" placeholder="请生成应用密钥点击右侧的生成按钮" style="width:calc(100% - 64px)" />
                <a-button type="primary" @click="genOneId('secret')">生成</a-button>
              </a-input-group>
            </a-form-model-item>
          </a-col>

          <a-col :span="12">
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="memo">
              <a-input v-model="model.memo" placeholder="请输入备注"  ></a-input>
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

          <a-col :span="12">
            <a-form-model-item label="IP白名单" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="remoteIps">
              <a-input v-model="model.remoteIps" placeholder="请输入IP白名单，多个用逗号隔开"  ></a-input>
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
  </a-spin>
</template>

<style type="text/css">
.app .ant-input-disabled{color: rgba(0,0,0,0.6);background-color:#ffffff;cursor:default;}
</style>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: 'OpenApiForm',
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
          appId: "",
          secret: "",
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
           appCode: [
              { required: true, message: '请输入应用编码'},
           ],
           appId: [
              { required: true, message: '请生成应用ID'},
           ],
           appName: [
              { required: true, message: '请输入应用名称'},
           ],
           secret: [
              { required: true, message: '请生成应用密钥'},
           ],
           status: [
              { required: true, message: '请选择状态'},
           ],
        },
        url: {
          add: "/v2/appInfo/add",
          edit: "/v2/appInfo/edit",
          queryById: "/v2/appInfo/queryById"
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
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            httpAction(httpurl,this.model,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }

        })
      },

      genOneId: function (type) {
        if(this.model.id > 0 && type == 'appId') {
          this.$message.warning("应用ID不能修改");
          return;
        }

        getAction("/v2/appInfo/genId", {type: type}).then(res => {
          if(res.success) {
            if(type == "appId") {
              this.model.appId = res.result;
            } else {
              this.model.secret = res.result;
            }
          } else {
            this.$message.warning(res.message);
          }
        });
      }
    }
  }
</script>