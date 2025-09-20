<template>
        <j-modal
        :title="title"
        :maskClosable="false"
        :visible="visible"
        width="80%"
        @ok="handleOk"
        :okButtonProps="{ class:{'jee-hidden': disableSubmit} }"
        @cancel="handleCancel"
        cancelText="关闭">
        <vip-form ref="realForm" @ok="submitCallback" :disabled="disableSubmit"></vip-form>
    </j-modal>
</template>

<script>
import VipForm from './VipForm'
export default {
    name: 'VipModal',
    components: {
        VipForm
    },
    data () {
        return {
            title: '',
            width: 800,
            visible: false,
            disableSubmit: false
        }
    },

    methods: {
        add () {
            this.visible=true
            this.$nextTick(() => {
                this.$refs.realForm.add();
            })
        },

        edit (record) {
            this.visible=true
            this.$nextTick(() => {
                this.$refs.realForm.edit(record);
            })
        },

        close () {
            this.$emit('close');
            this.visible = false;
        },

        handleOk () {
            this.$refs.realForm.submitForm();
        },

        submitCallback(){
            this.$emit('ok');
            this.visible = false;
        },

        handleCancel () {
            this.close()
        }
    }
}
</script>
