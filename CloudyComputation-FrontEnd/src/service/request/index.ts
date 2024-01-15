import axios from 'axios'
import type { InternalAxiosRequestConfig, AxiosInstance, AxiosResponse, AxiosRequestConfig } from 'axios'

interface TXCRequsetInterceptors {
  requestInterceptor?: (config: InternalAxiosRequestConfig) => InternalAxiosRequestConfig
  requestInterceptorCatch?: (error: any) => any
  responseInterceptor?: (res: AxiosResponse) => AxiosResponse
  responseInterceptorCatch?: (error: any) => any
}

// 扩展属性，可以传入拦截器
interface TXCRequestConfig extends AxiosRequestConfig {
  interceptors?: TXCRequsetInterceptors
}

class TXCRequset {
  instance: AxiosInstance
  interceptors?: TXCRequsetInterceptors

  constructor(config: TXCRequestConfig) {
    this.instance = axios.create(config)
    this.interceptors = config.interceptors

    // 自定义的interceptor
    this.instance.interceptors.request.use(
      this.interceptors?.requestInterceptor,
      this.interceptors?.requestInterceptorCatch
    )

    this.instance.interceptors.response.use(
      this.interceptors?.responseInterceptor,
      this.interceptors?.responseInterceptorCatch
    )
    // 公共interceptor
    this.instance.interceptors.request.use(
      (config) => {        
        return config
      },
      (err) => {
        return err
      }
    )

    this.instance.interceptors.response.use(
      (res) => {
        res.data.code = res.status === 200 ? 0: 1;
        const contentType = res.headers['content-type'];
        if (contentType && contentType.startsWith('application/octet-stream')) {
          return res; // 是照片请求，返回整个响应对象
        } else {
          return res.data; // 不是照片请求，返回响应的 data 属性
        }
      },
      (err) => {
        return err
      }
    )
  }

  request<T>(config: TXCRequestConfig): Promise<T> {
    return new Promise((resolve, reject) => {
      this.instance.request<any,T>(config).then((res) => {
        console.log(res)
        resolve(res)
      })
      .catch((err)=>{
        return err
      })
    })
  }
}

export default TXCRequset