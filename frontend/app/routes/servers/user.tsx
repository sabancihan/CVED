import { json, LoaderFunction } from "@remix-run/node";
import { Link, Outlet, useLoaderData } from "@remix-run/react";
import { getServers } from "~/models/servers.server";

export const loader: LoaderFunction = async () => {
    return json({ servers: await getServers() });
  };

  type LoaderData = {
    servers: Awaited<ReturnType<typeof getServers>>;
  };


export default function UserServers() {
    const { servers } = useLoaderData() as LoaderData;

    return (
      <main className="h-screen">
        <div className="flex flex-col items-center h-full">

          <div className="bg-gray-300 w-full text-center">
            <h1 className="text-5xl text-white">Sunucularım</h1>
          </div>


          <div className="flex  items-center h-full justify-center space-x-10">
            <div className="grid grid-cols-3 gap-1 grid-rows-2">
                {servers.map((server) => (
                    <div className="w-full text-center rounded-md bg-gray-300">
                        <Link className="text-3xl"  to={`/servers/${server.id}`}>
                            {server.ipAddress}:{server.port}
                        </Link>
                        <h1>{server.cpu}</h1>
                        <h1>{server.ram}</h1>
                        <h1>{server.disk}</h1>
                    </div>
                ))}
            </div>
            <Outlet/>
          </div>

       
        

        

        </div>
      </main>
    );
  }