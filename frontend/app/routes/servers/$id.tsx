import { json, LoaderFunction } from "@remix-run/node";
import { useLoaderData } from "@remix-run/react";
import { getServer } from "~/models/servers.server";
import invariant from "tiny-invariant";
import { userToken } from "~/cookies";

type  LoaderData = {
    server:  Awaited<ReturnType<typeof getServer>>;
}

export const loader: LoaderFunction = async ({
    params ,request
  }) => {

    const cookie = await userToken.parse(request.headers.get("Cookie"));

    if (!cookie) {
      return json({servers: []}, {status: 401});
  }
  
    console.log(params);
    invariant(params.id, "id is required");

  

    const server = await getServer(cookie,params.id);

    invariant(server, "Server not found");

    return json<LoaderData>({server});
  };

export default function ServerDetail() {
    const {server} = useLoaderData() as LoaderData;

    const softwares = server.software.map(({software,version}) => {
        const key = `${software.id.vendor_name} ${software.id.product_name} ${version}`
       return <li  key={key} className="capitalize">{key}</li>

    })
    
    
    return (
        <main className="mx-auto max-w-4xl h-screen">
          <div className="h-full flex items-center">
            <div key={server.id} className="w-full  text-center rounded-md bg-gray-300">
                          <h1 className="text-white text-3xl border-b-2">Sunucu: {server.ipAddress}:{server.port}</h1>
                          <h1 className="capitalize">İşlemci:  {server.cpu}</h1>
                          <h1 className="capitalize">Ram: {server.ram}</h1>
                          <h1 className="capitalize">Disk: {server.disk}</h1>
                          <h1 className="text-white text-xl border-y-2">Uygulamalar</h1>
                          <ul>
                              {softwares}
                          </ul>
                      </div>
          </div>
               
        </main>

        
      );
    
}