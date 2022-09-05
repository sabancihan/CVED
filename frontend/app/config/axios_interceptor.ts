
import axios, { AxiosError, AxiosRequestConfig } from "axios"
import spring_config from './spring_config';



const springApi = axios.create({
    baseURL:  spring_config.apiBaseUrl + "api/"
});



const controller = new AbortController();


springApi.interceptors.request.use(
    (config:AxiosRequestConfig) => {
        
        const token = localStorage.getItem(spring_config.accessToken);
        if (!token) {
            window.location.href = "/?error=Giriş yapmanız gerekiyor";
            
            controller.abort()
        }

        config.headers = {
            Authorization: `Bearer ${token}`,
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
            window.location.href = "/?error=Yetkiniz yok veya tekrar girmeniz gerekiyor";
            localStorage.removeItem(spring_config.accessToken);
            
        }
        return Promise.reject(error);
    }
);

export default springApi;

