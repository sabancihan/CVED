import { json, LoaderFunction } from "@remix-run/node";
import { Link, Links, Meta, Outlet, useLoaderData } from "@remix-run/react";
import { getServers } from "~/models/servers.server";
import { userToken } from "~/cookies";
import { EmblaCarousel } from "~/components/EmblaCarousel";


export const loader: LoaderFunction = async ({request}) => {




    const cookie = await userToken.parse(request.headers.get("Cookie"));



    if (!cookie) {
        return json({servers: []}, {status: 401});
    }

    const result = await getServers(cookie);

    return json({ servers: result});
  };

  type LoaderData = {
    servers: Awaited<ReturnType<typeof getServers>>;
    token: string;
  };

  export type ContextType = {
    token : String
  }





export default function UserServers() {
    const { servers ,token} = useLoaderData() as LoaderData;
    const context : ContextType = {token : token}

    const slides =   servers.map((server) => (
      <div key={server.id} className="w-full text-center rounded-md bg-gray-300">
          <Link className="text-3xl"  to={`/servers/${server.id}`}>
              {server.ipAddress}:{server.port}
          </Link>
          <h1>{server.cpu}</h1>
          <h1>{server.ram}</h1>
          <h1>{server.disk}</h1>
      </div>
  ))

    return (
      
      <main className="h-screen">
   

        
        <div className="flex flex-col items-center h-full">

          

          <div className="bg-gray-300 w-full text-center">
            <h1 className="text-5xl text-white">Sunucularım</h1>
          </div>





          <div className="flex  items-center h-full justify-center">

          <div>
              <EmblaCarousel  slides={slides}  options={
                {
                  skipSnaps: false,
                  slidesToScroll: 6,
                  align: "start",
                }
              }/>
            </div>

            <Outlet />
          </div>

       
        

        

        </div>
      </main>
    );
  }