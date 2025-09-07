<template>
    <a-modal
        :title="title"
        :visible="visible"
        :maskClosable="false"
        :footer="null"
        width="80%"
        @cancel="handleCancel"
        cancelText="关闭">

        <a-spin :spinning="loading">
            <table class="record table-bordered">
                <tbody>
                    <tr>
                        <th>版本号</th>
                        <td>{{appInfo.version ? appInfo.version : '-'}}</td>
                        <th>客户端IP</th>
                        <td>{{appInfo.remoteIp}}</td>
                        <th>编译时间</th>
                        <td>{{appInfo.buildTime ? appInfo.buildTime : '-'}}</td>
                    </tr>
                    <tr>
                        <th>Git分支</th>
                        <td>{{appInfo.buildBranch ? appInfo.buildBranch : '-'}}</td>
                        <th>提交记录</th>
                        <td>{{appInfo.commitMessage ? appInfo.commitMessage : '-'}}</td>
                        <th>提交时间</th>
                        <td>{{appInfo.commitTime ? appInfo.commitTime : '-'}}</td>
                    </tr>
                    <tr>
                        <th>最后提交人</th>
                        <td>{{appInfo.commitAuthor ? appInfo.commitAuthor : '-'}}</td>
                        <th>提交HASH</th>
                        <td>{{appInfo.commitHash ? appInfo.commitHash : '-'}}</td>
                        <th>服务器时间</th>
                        <td>{{appInfo.dateTime}}</td>
                    </tr>
          <!--       </tbody>
            </table>

            <table class="record table-bordered" style="margin-top: 15px;">
                <tbody> -->
                    <tr>
                        <th>操作系统名称</th>
                        <td>{{appInfo.osName}}</td>
                        <th>主机名称</th>
                        <td>{{appInfo.hostName}}</td>
                        <th>程序启动时间</th>
                        <td>{{appInfo.startTime}}</td>
                    </tr>
                    <tr>
                        <th>PID</th>
                        <td>{{appInfo.pid}}</td>
                        <th>线程总数</th>
                        <td>{{appInfo.threadCount}}</td>
                        <th>程序运行时长</th>
                        <td>{{appInfo.upTime}}</td>
                    </tr>
                    <!-- <tr>
                        <th>程序最大可使用内存</th>
                        <td>{{appInfo.maxMemory}}</td>
                        <th>内存总量</th>
                        <td>{{appInfo.totalMemory}}</td>
                        <th>已使用内存</th>
                        <td>{{appInfo.usedMemory}}</td>
                    </tr> -->
                </tbody>
            </table>
        </a-spin>
    </a-modal>
</template>


<style type="text/css">

.record.table-bordered {
    border-collapse:separate;
    border:none;
    border-spacing: 0;
    text-align: center;
    height: 30px;
    line-height: 30px;
    margin: 0 auto;
    font-size: 14px;
    width: 100%;
    border-top: 1px solid #e8e8e8;
    border-left: 1px solid #e8e8e8;
}

.record.table-bordered th, .record.table-bordered td {
    border: 1px solid #e8e8e8;
    border-top: none;
    border-left: none;
    padding: 4px 5px;
    vertical-align: middle;
    border-spacing: 0;
    font-weight: normal;
}

.record.table-bordered th {
    color: rgba(0, 0, 0, 0.85);
    background-color: #fafafa;
    width: 13.33%;

}

.record.table-bordered td {
    color: rgba(0, 0, 0, 0.65);
    width: 20%;
}


</style>

<script>
import {getAction} from "@api/manage";


export default {
    name: 'AuditForm',
    data() {
        return {
            title: '版本信息',
            // 遮罩层
            visible: false,
            loading: true,
            appInfo: {},
        };
    },
    methods: {
        // 显示弹框
        show: function() {
            this.visible = true;
            this.loading = true;
            getAction("/v2/app/init").then(res => {
                if(res.success) {
                    this.appInfo = res.result;
                } else {
                    this.$message.warning(res.message);
                }
            }).finally(res => {
                this.loading = false;
            });
        },

        handleCancel:function () {
            this.visible = false;
        },
    }
};
</script>