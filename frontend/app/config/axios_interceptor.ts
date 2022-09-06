
import { redirect } from "@remix-run/node";
import axios, { AxiosError, AxiosRequestConfig } from "axios"
import spring_config from './spring_config';



const springApi = axios.create({
    baseURL:  spring_config.apiBaseUrl + "api/"
});



const controller = new AbortController();


springApi.interceptors.request.use(
    (config:AxiosRequestConfig) => {
        
      
        if (config?.headers?.Authorization === undefined) {
            //window.location.href = "/?error=Giriş yapmanız gerekiyor";

            controller.abort()
            return null;


            
        }

  

        return config;
    }
);

springApi.interceptors.response.use(
    (response) => {
        return response;
    },
    (error:AxiosError) => {
        if (error.response?.status === 401) {
            
            return null;

            
        }
        return Promise.reject(error);
    }
);

export default springApi;

