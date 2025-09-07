<template>
    <a-auto-complete :value="text" @change="onChange" @select="onSelect" :placeholder="placeholder" option-label-prop="key">
        <template slot="dataSource">
            <a-select-option v-for="item in options" :key="item.id">{{item.name}}</a-select-option>
        </template>
    </a-auto-complete>
</template>

<script>
import { getAction } from '@/api/manage'
import { toJsonString } from '@/utils/util'

export default {
    name: 'AutoComplete',
    props: {
        value: [String, Number, Array],
        placeholder: {
            type: String,
            required: false,
            default: '',
        },
        url: String,
        keyId: {
            type: String,
            required: false,
            default: 'keyword'
        },
        //pa: String,
    },
    data() {
        return {
            options: [],
            text: undefined,
        }
    },
    // computed: {
    //     pa(newName, oldName) {
    //         console.log(newName, oldName);
    //     }
    // },
    created() {
        this.text = this.value
    },
    methods: {
        onChange: function(name) { //输入框时输入中事件
            if (!name) {
                this.options = new Array;
            } else {
                var params = {}
                params[this.keyId] = name
                getAction(this.url, params).then(res => {
                    this.options = res.result;
                });
            }
        },

        onSelect: function(key) {
            for(var i = 0; i < this.options.length; i++) {
                var item = this.options[i];
                if(key != item.id) {
                    continue;
                }

                console.log('AutoComplete', toJsonString(item));
                this.$emit('input', item.name);
                this.$emit('change', item);
            }
        },
    }
}
</script>