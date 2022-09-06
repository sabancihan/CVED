import springApi from "~/config/axios_interceptor";



export async function getServers(token: string) : Promise<Array<Server>> {
    
   

   
    const response = await springApi.get<Array<Server>>(`management/server`, {headers : { Authorization: `${token}`}});


            return response.data;
        



 




}

export async function getServer(token: string,id: string) : Promise<Server> {
 
    const response = await springApi.get<Server>(`management/server/${id}`, {headers : { Authorization: `${token}`}});

     return response.data;
    



}

export async function createServer(server: 
    Pick<Server,"cpu" | "ram" | "port" | "ipAddress" | "disk">
    ) {
    return  {
        id : 3,
        ipAddress: `15.21.11.13`,
        port: 1888,
        cpu: "Intel i5-9600K",
        ram: "64 GB",
        disk: "1 TB SSD",
        software: []
    }
}


type Server = {
    id: string,
    ipAddress: string;
    port: number;
    cpu: string;
    ram: string;
    disk: string;
    software: Array<Software>
}


type Software = {
    version: string;
    vendor: string;
    name: string;
}