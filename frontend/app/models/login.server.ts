import axios from "axios";
import invariant from "tiny-invariant";
import config from "~/config/spring_config";
import qs from 'qs';



type Login = {
    username: string,
    password: string,
    _csrf: string
}

type Register = {
    username: String,
    password: String,
    name: String,
    surname: String,
    email: String,
    _csrf: String,
}

export async function getCsrf() {
    const response = await axios.get(config.apiBaseUrl + "login");
    const csrf = response.headers["set-cookie"]
    invariant(csrf, "CSRF token not found");
    return csrf[0].split(";")[0].split("=")[1];
}


export async function register(information: Register ) : Promise<boolean | void> {







     return await axios.post(config.apiBaseUrl + "register", information ,  {
        headers: {
            'X-XSRF-TOKEN': '9992fdd4-3dbb-4e1a-a4c5-e54ae6f6d1da', 
            'Content-Type': 'application/json', 
            'Cookie': 'XSRF-TOKEN=9992fdd4-3dbb-4e1a-a4c5-e54ae6f6d1da'
            
        }
        
    }).then((response) => {
   
        return response.status === 200;
    }
    ).catch((error) => {
        console.log(error);
    }); 
    
}



export async function login(information: Login ) : Promise<string | void> {

    

     return await axios.post(config.apiBaseUrl + "login", qs.stringify(information) ,  {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Cookie': "XSRF-TOKEN=" + information._csrf,
            'Accept' : '*/*'
        },
        withCredentials: true
        
    }).then((response) => {
        const header = response.headers["authorization"];
        if (!header)
            throw new Error("No Authorization token found");

        return header;
    }
    ).catch((error) => {
        console.log(error);
    }); 
    
}