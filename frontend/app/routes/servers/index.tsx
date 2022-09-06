import { json } from "@remix-run/node";
import {Link, Outlet, useLoaderData } from "@remix-run/react";
import { getServers } from "~/models/servers.server";

export const loader = async () => {
    return json<LoaderData>({
        servers: await getServers()
    })
}



type LoaderData = {
  servers : Awaited<ReturnType<typeof getServers>>;
}

export default function Servers() {
    const { servers } = useLoaderData() as LoaderData;
    console.log(servers);
    return (
      <main>
        <div className="flex flex-col items-center h-full">

          <div className="bg-gray-300 w-full text-center">
            <h1 className="text-5xl text-white">Sunucular</h1>
          </div>
        
          <Outlet />

        

        </div>
      </main>
    );
  }