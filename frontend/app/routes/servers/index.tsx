import { json, LoaderFunction } from "@remix-run/node";
import {Link, Outlet, useLoaderData } from "@remix-run/react";
import { userToken } from "~/cookies";
import { getServers } from "~/models/servers.server";

export const loader  : LoaderFunction= async ({request}) => {

  console.log(request.headers.get("Cookie"),"cookie");

  
  const cookie = await userToken.parse(request.headers.get("Cookie"));

  if (!cookie) {
    return json({servers: []}, {status: 401});
}

    return json<LoaderData>({
        servers: await getServers(cookie)
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