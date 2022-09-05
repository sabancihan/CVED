import axios from "axios";
import invariant from "tiny-invariant";
import config from "~/config/spring_config";



type Login = {
    username: string,
    password: string,
    _csrf?: string
}


export async function login(information: Login ) : Promise<string | void> {

    
    const headers =  await axios.get(config.apiBaseUrl + "login",{
        withCredentials: true
    }).then(response => {
        return response.headers;
    });

    const cookie = headers["set-cookie"]

    if (!cookie) {
        throw new Error("Cookie not found");
    }


    information._csrf = "0445c8d9-fb55-4a0a-b50e-fbc0a51e0576"//cookie[0].split(";")[0].split("=")[1];

    console.log(information);




    return await axios.post(config.apiBaseUrl + "login", information ,  {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Cookie': "XSRF-TOKEN=" + information._csrf,
            'Accept' : '*/*'
        },
        withCredentials: true
        
    }).then((response) => {
        const header = response.headers["Authorization"];
        if (!header)
            throw new Error("No Authorization token found");

        return header;
    }
    ).catch((error) => {
        //console.log(error);
    });
    
}