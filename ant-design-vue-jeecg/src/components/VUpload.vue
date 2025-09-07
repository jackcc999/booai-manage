<template>
    <a-upload class="upload-file-btn" :accept="accept" :multiple="false" :customRequest="uploadFileAction" :showUploadList="false">上传</a-upload>
</template>
<script>
    import { uploadFileAction } from '@/api/manage'
    export default {
        name: "JDictSelectTag",
        props: {
            accept: {
                type: String,
                default: '*',
                required: false
            },
            type: {
                type: String,
                default: 'default',
                required: false
            },
        },
        created() {

        },

        computed: {
        },

        methods: {
            uploadFileAction (data) {
                this.$emit('loading', true);
                uploadFileAction({file: data.file, type: this.type}).then(res => {
                    if(res.success) {
                        this.$emit('change', res.result);
                    } else {
                        this.$message.warning(res.message);
                    }

                    this.$emit('loading', false);
                });
            },
        },
        model:{
          prop: 'value',
          event: 'change'
        }
    }
</script>