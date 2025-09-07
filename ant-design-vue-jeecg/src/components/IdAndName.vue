<template>
  <a-select :value="value" :mode="multiple ? 'multiple' : 'tag'" :placeholder="placeholder" :allowClear="allowClear" :showSearch="showSearch" :filterOption="filterSelectOption" @change="handleInput">
    <a-select-option v-for="item in options" :key="item.id">{{item.name}}</a-select-option>
  </a-select>
</template>

<script>
  import {getAction} from '@/api/manage'

  export default {
    name: "JDictSelectTag",
    props: {
      value: [String, Number, Array],
      url: {
        type: String,
        default: '',
        required: true,
      },
      multiple: {
        type: Boolean,
        required: false,
        default: false,
      },
      disabled: {
        type: Boolean,
        required: false,
        default: false,
      },
      showSearch: {
        type: Boolean,
        required: false,
        default: false,
      },
      placeholder: {
        type: String,
        required: false,
        default: '',
      },
      allowClear: {
        type: Boolean,
        required: false,
        default: true,
      },
      getPopupContainer:{
        type: Function,
        default: (node) => node.parentNode
      }
    },
    data() {
      return {
        options: [],
      }
    },
    watch:{
      url:{
        immediate:true,
        handler() {
          this.initDictData()
        },
      }
    },
    created() {

    },
    computed: {
      getValueSting(){
        return this.value;
      },
    },
    methods: {
      initDictData() {
        getAction(this.url).then(res => {
          if (res.success) {
            this.options = res.result;
          }
        })
      },

      filterSelectOption (value, option) {
        return option.componentOptions.children[0].text.indexOf(value) >= 0
      },

      handleInput(e='') {
        let val;
        if(Object.keys(e).includes('target')){
          val = e.target.value
        }else{
          val = e
        }
        console.log(val);
        this.$emit('change', val);
        //LOWCOD-2146 【菜单】数据规则，选择自定义SQL 规则值无法输入空格
        this.$emit('input', val);
      },
    },
    model:{
      prop: 'value',
      event: 'change'
    }
  }
</script>

<style scoped>
</style>