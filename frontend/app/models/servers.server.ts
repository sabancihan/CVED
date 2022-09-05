import springApi from "~/config/axios_interceptor";

export async function getServers() : Promise<Array<Server>> {
    
    const response = await springApi.get<Array<Server>>("/api/management/server");
    return response.data;

}

type Server = {
    ipAddress: string;
    port: number;
}
