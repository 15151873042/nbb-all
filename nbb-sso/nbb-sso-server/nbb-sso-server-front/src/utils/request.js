import axios from 'axios'
import {getToken} from "@/utils/auth";
import errorCode from "@/utils/errorCode";
import {Message} from "element-ui";
import {tansParams} from "@/utils/ruoyi";


let tokenName = process.env.VUE_APP_COOKIE_NAME

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'

// 创建axios实例
const service = axios.create({
    baseURL: process.env.VUE_APP_BASE_API, // axios中请求配置有baseURL选项，表示请求URL公共部分
    timeout: 10000, // 超时
    headers: {
        [tokenName]: getToken()
    }
})

// request拦截器
service.interceptors.request.use(config => {
    // get请求映射params参数，逐层解析
    if (config.method === 'get' && config.params) {
        let url = config.url + '?' + tansParams(config.params);
        url = url.slice(0, -1);
        config.params = {};
        config.url = url;
    }

    return config
}, error => {
    console.log(error)
    Promise.reject(error)
})

// response拦截器
service.interceptors.response.use(res => {
    // 未设置状态码则默认成功状态
    const code = res.data.code || 200
    // 获取错误信息
    const msg = errorCode[code] || res.data.msg || errorCode['default']

    // 二进制数据则直接返回
    if(res.request.responseType === 'blob' || res.request.responseType === 'arraybuffer') {
        return res.data
    }

    if (code == 401) {
        location.href = "/login";
    } else if (code === 500) {
        Message({
            message: msg,
            type: 'error'
        })
        return Promise.reject(new Error(`调用后台接口${res.config.url}出错，错误信息为：${msg}`))
    } else {
      return res.data
    }
}, error => {
    console.log('err' + error)
    let { message } = error;
    if (message == "Network Error") {
        message = "后端接口连接异常";
    }
    else if (message.includes("timeout")) {
        message = "系统接口请求超时";
    }
    else if (message.includes("Request failed with status code")) {
        message = "系统接口" + message.substr(message.length - 3) + "异常";
    }
    Message({
        message: message,
        type: 'error',
        duration: 5 * 1000
    })
    return Promise.reject(error)
})


export default service
