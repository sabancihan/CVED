import springApi from "~/config/axios_interceptor";

export async function getServers() : Promise<Array<Server>> {
    
    return [
        {
            id: 1,
            ipAddress: "125.201.74.234",
            port: 25565,
            cpu: "Ryzen 5 3600",
            ram: "16 GB",
            disk: "500 GB",
            software: []
        },
        {
            id: 2,
            ipAddress: "55.72.13.31",
            port: 2225,
            cpu : "Apple M1",
            ram: "8 GB",
            disk: "256 GB",
            software: []
        },

    ]
    //const response = await springApi.get<Array<Server>>("/api/management/server");
    //return response.data;

}

export async function getServer(id: number) : Promise<Server> {
    return  {
        id : id,
        ipAddress: `${id}.${id}.${id}.${id}`,
        port: id,
        cpu: "Intel Core i7-10700K",
        ram: "32 GB",
        disk: "1 TB SSD",
        software: []
    }

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
    id: number,
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