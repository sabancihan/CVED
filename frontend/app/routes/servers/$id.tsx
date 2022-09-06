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
    
    return (
        <main className="mx-auto max-w-4xl">
                   <div key={server.id} className="w-full text-center rounded-md bg-gray-300">
                        {server.ipAddress}:{server.port}
                        <h1>{server.cpu}</h1>
                        <h1>{server.ram}</h1>
                        <h1>{server.disk}</h1>
                    </div>
        </main>

        
      );
    
}