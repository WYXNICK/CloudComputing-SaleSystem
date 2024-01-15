import TXCRequset from './request/index';
import { BASE_URL } from './request/config';

const txcrequest = new TXCRequset({
  baseURL: BASE_URL,
  interceptors:{
    requestInterceptor:(config)=>{
      return config
    },
    responseInterceptor:(res)=>{
      return res
    }
  }
});

export default txcrequest;
