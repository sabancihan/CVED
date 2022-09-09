import springApi from "~/config/axios_interceptor";



export async function getServers(token: string) : Promise<Array<Server>> {
    
   

   
    const response = await springApi.get<Array<Server>>(`management/server`, {headers : { Authorization: `${token}`}});


            return response.data;
        



 




}

export async function getServer(token: string,id: string) : Promise<Server> {
 
    const response = await springApi.get<Server>(`management/server/${id}`, {headers : { Authorization: `${token}`}});

     return response.data;
    



}

export async function createServer(token : string,server: 
    Pick<Server,"cpu" | "ram" | "port" | "ipAddress" | "disk" | "software">
    ) {

        const response = await springApi.post<Server>(`management/server`, server,{headers : {Authorization: `${token}`, 'Content-Type': 'application/json'}});

        return response.data;
}


type Server = {
    id: string,
    ipAddress: string;
    port: number;
    cpu: string;
    ram: string;
    disk: string;
    software: Array<SoftwareVersioned>
    user: String
}


export type SoftwareVersioned = {
    version: string;
    software: Software
}

type Software = {
    id: {
        vendor_name: string;
        product_name: string;
    }
}