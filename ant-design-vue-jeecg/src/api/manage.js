import Vue from 'vue'
import { axios } from '@/utils/request'
import signMd5Utils from '@/utils/encryption/signMd5Utils'

const api = {
  user: '/mock/api/user',
  role: '/mock/api/role',
  service: '/mock/api/service',
  permission: '/mock/api/permission',
  permissionNoPager: '/mock/api/permission/no-pager'
}

export default api

export function downloadAction(url, parameter, fileName, callback) {
  let sign = signMd5Utils.getSign(url, parameter);
  //将签名和时间戳，添加在请求接口 Header
  let signHeader = {"X-Sign": sign,"X-TIMESTAMP": signMd5Utils.getDateTimeToString()};

  axios({
    url: url,
    timeout: 600000,
    method:'post' ,
    data: parameter,
    headers: signHeader,
    responseType:'blob',
    onDownloadProgress: function (event) {
      console.log("下载进度: " + bytesToSize(event.loaded) + "/" + bytesToSize(event.total));
    }
  }).then(res => {
    if(res.type == "application/json") {
      // 创建FileReader实例
      var reader  = new FileReader();
      // 读取文件,返回结果为字符串
      reader.readAsText(res, 'utf-8');
      // 读取完成后,**获取reader.result**
      reader.onload = function() {
        // 将字符串转换为JSON格式
        let res = JSON.parse(reader.result);
        typeof callback == "function" && callback({success: false, message: res.message});
      }


      return;
    }

    // var disposition = res.headers['content-disposition']
    // var fileName = decodeURI(disposition.substring(disposition.indexOf('filename=') + 9, disposition.length));


    if (typeof window.navigator.msSaveBlob !== 'undefined') {
      window.navigator.msSaveBlob(new Blob([res]), fileName)
    } else {
      let url = window.URL.createObjectURL(new Blob([res]))
      let link = document.createElement('a')
      link.style.display = 'none'
      link.href = url
      link.setAttribute('download', fileName)
      document.body.appendChild(link)
      link.click()
      document.body.removeChild(link) //下载完成移除元素
      window.URL.revokeObjectURL(url) //释放掉blob对象
    }

    typeof callback == "function" && callback({success: true, message: "ok"});
  });
}


export function downloadTplAction(fileName) {
  var url = "/v2/download/templates";
  var parameter = {file: fileName};

  let sign = signMd5Utils.getSign(url, parameter);
  //将签名和时间戳，添加在请求接口 Header
  let signHeader = {"X-Sign": sign,"X-TIMESTAMP": signMd5Utils.getDateTimeToString()};

  axios({
    url: url,
    method:'get' ,
    params: parameter,
    headers: signHeader,
    responseType:'blob',
  }).then(res => {
    if (typeof window.navigator.msSaveBlob !== 'undefined') {
      window.navigator.msSaveBlob(new Blob([res]), fileName)
    } else {
      let url = window.URL.createObjectURL(new Blob([res]))
      let link = document.createElement('a')
      link.style.display = 'none'
      link.href = url
      link.setAttribute('download', fileName)
      document.body.appendChild(link)
      link.click()
      document.body.removeChild(link) //下载完成移除元素
      window.URL.revokeObjectURL(url) //释放掉blob对象
    }
  });
}


//post
export function postAction(url,parameter) {
  let sign = signMd5Utils.getSign(url, parameter);
  //将签名和时间戳，添加在请求接口 Header
  // update-begin--author:taoyan---date:20220421--for: VUEN-410【签名改造】 X-TIMESTAMP牵扯
  let signHeader = {"X-Sign": sign,"X-TIMESTAMP": signMd5Utils.getTimestamp()};
  // update-end--author:taoyan---date:20220421--for: VUEN-410【签名改造】 X-TIMESTAMP牵扯

  return axios({
    url: url,
    method:'post' ,
    data: parameter,
    headers: signHeader
  })
}

//post method= {post | put}
export function httpAction(url,parameter,method) {
  let sign = signMd5Utils.getSign(url, parameter);
  //将签名和时间戳，添加在请求接口 Header
  // update-begin--author:taoyan---date:20220421--for: VUEN-410【签名改造】 X-TIMESTAMP牵扯
  let signHeader = {"X-Sign": sign,"X-TIMESTAMP": signMd5Utils.getTimestamp()};
  // update-end--author:taoyan---date:20220421--for: VUEN-410【签名改造】 X-TIMESTAMP牵扯

  return axios({
    url: url,
    method:method ,
    data: parameter,
    headers: signHeader
  })
}

//put
export function putAction(url,parameter) {
  return axios({
    url: url,
    method:'put',
    data: parameter
  })
}

//get
export function getAction(url,parameter) {
  let sign = signMd5Utils.getSign(url, parameter);
  //将签名和时间戳，添加在请求接口 Header
  // update-begin--author:taoyan---date:20220421--for: VUEN-410【签名改造】 X-TIMESTAMP牵扯
  let signHeader = {"X-Sign": sign,"X-TIMESTAMP": signMd5Utils.getTimestamp()};
  // update-end--author:taoyan---date:20220421--for: VUEN-410【签名改造】 X-TIMESTAMP牵扯

  return axios({
    url: url,
    method: 'get',
    params: parameter,
    headers: signHeader
  })
}

//deleteAction
export function deleteAction(url,parameter) {
  return axios({
    url: url,
    method: 'delete',
    params: parameter
  })
}

export function getUserList(parameter) {
  return axios({
    url: api.user,
    method: 'get',
    params: parameter
  })
}

export function getRoleList(parameter) {
  return axios({
    url: api.role,
    method: 'get',
    params: parameter
  })
}

export function getServiceList(parameter) {
  return axios({
    url: api.service,
    method: 'get',
    params: parameter
  })
}

export function getPermissions(parameter) {
  return axios({
    url: api.permissionNoPager,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveService(parameter) {
  return axios({
    url: api.service,
    method: parameter.id == 0 ? 'post' : 'put',
    data: parameter
  })
}

/**
 * 下载文件 用于excel导出
 * @param url
 * @param parameter
 * @returns {*}
 */
export function downFile(url,parameter, method='get'){
  if(method=='get'){
    return axios({
      url: url,
      params: parameter,
      method: method ,
      responseType: 'blob'
    })
  }else{
    return axios({
      url: url,
      method: method,
      data: parameter,
      responseType: 'blob'
    })
  }

}

/**
 * 下载文件
 * @param url 文件路径
 * @param fileName 文件名
 * @param parameter
 * @returns {*}
 */
export function downloadFile(url, fileName, parameter) {
  return downFile(url, parameter).then((data) => {
    if (!data || data.size === 0) {
      Vue.prototype['$message'].warning('文件下载失败')
      return
    }
    if (typeof window.navigator.msSaveBlob !== 'undefined') {
      window.navigator.msSaveBlob(new Blob([data]), fileName)
    } else {
      let url = window.URL.createObjectURL(new Blob([data]))
      let link = document.createElement('a')
      link.style.display = 'none'
      link.href = url
      link.setAttribute('download', fileName)
      document.body.appendChild(link)
      link.click()
      document.body.removeChild(link) //下载完成移除元素
      window.URL.revokeObjectURL(url) //释放掉blob对象
    }
  })
}

/**
 * 文件上传 用于富文本上传图片
 * @param url
 * @param parameter
 * @returns {*}
 */
export function uploadAction(url, parameter){
  return axios({
    url: url,
    data: parameter,
    method:'post' ,
    headers: {
      'Content-Type': 'multipart/form-data',  // 文件上传
    },
  })
}

export function uploadFileAction(params) {
  return axios({
    url: '/v2/files/doUpload',
    data: params,
    method:'post' ,
    headers: {
      'Content-Type': 'multipart/form-data',  // 文件上传
    },
  })
}

export function bytesToSize(bytes) {
    if (bytes === 0) return '0 B';
    var k = 1024;
    var sizes = ['B','KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
    var i = Math.floor(Math.log(bytes) / Math.log(k))
    return (bytes / Math.pow(k, i)).toFixed(2) + ' ' + sizes[i];
    //toPrecision(3) 后面保留两位小数，如1.00GB
}

/**
 * 获取文件服务访问路径
 * @param avatar
 * @param subStr
 * @returns {*}
 */
export function getFileAccessHttpUrl(avatar,subStr) {
  if(!subStr) subStr = 'http'
  try {
    if(avatar && avatar.startsWith(subStr)){
      return avatar;
    }else{
      if(avatar &&　avatar.length>0 && avatar.indexOf('[')==-1){
        return window._CONFIG['staticDomainURL'] + "/" + avatar;
      }
    }
  }catch(err){
   return;
  }
}
