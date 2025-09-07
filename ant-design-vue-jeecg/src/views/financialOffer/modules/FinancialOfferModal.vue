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
        <financial-offer-form ref="realForm" @ok="submitCallback" :disabled="disableSubmit"></financial-offer-form>
    </j-modal>
</template>

<script>
import FinancialOfferForm from './FinancialOfferForm'
export default {
    name: 'FinancialOfferModal',
    components: {
        FinancialOfferForm
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
