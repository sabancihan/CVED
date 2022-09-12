import { ActionFunction, json, LoaderFunction, redirect } from "@remix-run/node";
import { Form, Outlet, useLoaderData } from "@remix-run/react";
import { getServer } from "~/models/servers.server";
import invariant from "tiny-invariant";
import { userToken } from "~/cookies";
import { createSoftware, removeSoftware } from "~/models/software.server";
import { getRole } from "~/models/role.server";

type  LoaderData = {
    server:  Awaited<ReturnType<typeof getServer>>;
    admin :  Awaited<ReturnType<typeof getRole>>;
}

type ActionData =
  | {
        id: null | string;
    }
  | undefined;


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

    const admin = await getRole(cookie);

    invariant(server, "Server not found");

    

    return json<LoaderData>({server,admin});
  };

  export const action : ActionFunction = async ({ request }) => {



    const cookie = await userToken.parse(request.headers.get("Cookie"));

    
    if (!cookie) {
        return json({servers: []}, {status: 401});
    }

    const formData = await request.formData();




  

    const id = formData.get("id");


    const errors = {
        id: id ? null : "id is required",
      };

    invariant(typeof id === "string", "Server id must be a string");



    
    const hasErrors = Object.values(errors).some(
      (errorMessage) => errorMessage
    );
    if (hasErrors) {
      return json<ActionData>(errors);
    }        
    



    await removeSoftware(cookie, id);



    const serverId = request.url.split("/")[4];


    return redirect(`/servers/${serverId}`);


  
    
  };

export default function ServerDetail() {
    const {server,admin} = useLoaderData() as LoaderData;

    const softwares = server.software.map(({software,version,id}) => {
        const key = `${software.id.vendor_name} ${software.id.product_name} ${version}`
       return <li  key={key} className="capitalize">
        <div className="flex items-center justify-center space-x-5 ">
    
          <p>{key}</p>
          {id && admin && 
          <Form method="post">
            <input type="hidden" name="id" value={id} />

            <button type = "submit" className="flex items-center justify-center">
            <svg  xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-5 h-5 cursor-pointer">
                <path strokeLinecap="round" strokeLinejoin="round" d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0" />
              </svg>
            </button>
            
          </Form>
          }
        

        </div>
       </li>

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

                          <div className="border-t-2 ">
                            <Outlet context={admin}/>
                          </div>
                      </div>
          </div>
               
        </main>

        
      );
    
}