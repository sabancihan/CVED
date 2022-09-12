import springApi from "~/config/axios_interceptor";
import { SoftwareVersioned, SoftwareVersionedPost } from "./servers.server";



export async function getServerSoftware(token: string,serverId:string) : Promise<Array<SoftwareVersioned>> {
    
   

   
    const response = await springApi.get<Array<SoftwareVersioned>>(`management/softwareVersioned/${serverId}`, {headers : { Authorization: `${token}`}});


     return response.data;
        



 




}



export async function removeSoftware(token : string,softwareId: 
    String
    ) {

        const response = await springApi.delete<SoftwareVersioned>(`management/softwareVersioned/${softwareId}`,{headers : {Authorization: `${token}`}});

        return response.data;
}


export async function createSoftware(token : string,softwareVersioned: 
    SoftwareVersionedPost
    ) {

        const response = await springApi.post<SoftwareVersioned>(`management/softwareVersioned`, softwareVersioned,{headers : {Authorization: `${token}`, 'Content-Type': 'application/json'}});

        return response.data;
}

