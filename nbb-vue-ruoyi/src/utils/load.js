import {Loading} from 'element-ui';


let loadingInstance;//定时器
let needLoadingRequestCount = 0
export const showFullScreenLoading = () => {
  if (needLoadingRequestCount === 0) {
    loadingInstance = Loading.service({
      lock: true,
      text: '正在加载，请稍后...',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)'
    })
  }
  needLoadingRequestCount++
}

export const tryHideFullScreenLoading = () => {
  if (needLoadingRequestCount <= 0) return
  needLoadingRequestCount--
  if (needLoadingRequestCount === 0) {
    loadingInstance.close();
  }
}
