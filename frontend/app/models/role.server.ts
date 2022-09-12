import springApi from "~/config/axios_interceptor";



export async function getRole(token: string) : Promise<boolean> {
    
   

   
    const response = await springApi.get<boolean>(`role/admin`, {headers : { Authorization: `${token}`}});


            return response.data;
        



}