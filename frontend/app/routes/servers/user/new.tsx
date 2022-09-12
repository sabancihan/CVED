import {json, redirect } from "@remix-run/node";
import { Form ,Outlet,useActionData, useOutletContext} from "@remix-run/react";
import { createServer } from "~/models/servers.server";
import type { ActionFunction } from "@remix-run/node";
import invariant from "tiny-invariant";
import type { ContextType } from "../user";
import { userToken } from "~/cookies";
import { SoftwareVersioned } from "~/models/servers.server";
import { SoftwareInput } from "./new/software";
import React from "react";
import qs from "qs";

const inputClassName = `w-full rounded border border-gray-500 px-2 py-1 text-lg text-black`;

type ActionData =
  | {
        ipAddress: null | string;
        port: null | string;
        cpu: null | string;
        ram: null | string;
        disk: null | string;
    }
  | undefined;

  function isSoftwareVersionedArray(software: any): software is SoftwareVersioned[] {
    return Array.isArray(software) && software.every((item) => isSoftwareVersioned(item));
  }

  function isSoftwareVersioned(software: any): software is SoftwareVersioned {
    return software.version !== undefined && software.software !== undefined;
  }

export const action : ActionFunction = async ({ request }) => {



    let {ipAddress,port,cpu,ram,disk,software} = await qs.parse(await request.text());

    software = software ?? []




    const cookie = await userToken.parse(request.headers.get("Cookie"));




    if (!cookie) {
        return json({servers: []}, {status: 401});
    }


  



    const errors: ActionData = {
        ipAddress: ipAddress ? null : "ipAddress is required",
        port: port  ? null : "port is required",
        cpu: cpu ? null : "cpu is required",
        ram: ram ? null : "ram is required",
        disk: disk ? null : "disk is required",
      };

      const portNumber = Number(port);

      

        invariant(typeof ipAddress === "string", "ipAddress must be a string");
        invariant(!isNaN(portNumber), "port must be a number");
        invariant(typeof cpu === "string", "cpu must be a string");
        invariant(typeof ram === "string", "ram must be a string");
        invariant(typeof disk === "string", "disk must be a string");
        invariant(isSoftwareVersionedArray(software), "software must be an array of SoftwareVersioned");


      const hasErrors = Object.values(errors).some(
        (errorMessage) => errorMessage
      );
      if (hasErrors) {
        return json<ActionData>(errors);
      }        


      console.log(JSON.stringify(software),"software");



  
   await createServer(cookie, {
        ipAddress,
        port: portNumber,
        cpu,
        ram,
        disk,
        software
   })
  
    return redirect("/servers/user");
  };


export default function NewServer() {
    const token = useOutletContext<ContextType>();
    const errors  = useActionData();
    const [software,setSoftware] = React.useState<SoftwareInput[]>([]);


    return (
      <>
      <Form method="post" className="mx-10">
        <h1 className="text-white text-xl font-bold">Sunucu Ekle</h1>
     <p>
          <label className="text-white">
            Ip Addresi:{" "}
            <input
              type="text"
              name="ipAddress"
              className={inputClassName}
            />
          </label>
        </p>
        <p>
          <label className="text-white">
            Port:{" "}
            <input
              type="number"
              name="port"
              className={inputClassName}
            />
          </label>
        </p>

        
        <p>
          <label className="text-white">
            İşlemci:{" "}
            <input
              type="text"
              name="cpu"
              className={inputClassName}
            />
          </label>
        </p>

        <p>
          <label className="text-white">
            Ram:{" "}
            <input
              type="text"
              name="ram"
              className={inputClassName}
            />
          </label>
        </p>

        
        <p>
          <label className="text-white">
            Disk:{" "}
            <input
              type="text"
              name="disk"
              className={inputClassName}
            />
          </label>
        </p>


        {software.map((item,index) => (
         
         <div key={`${item.vendor_name}:${item.product_name}:${item.version}`}>
            <input type="hidden" name={`software[${index}][version]`} value={item.version} />
  

            <input type="hidden" name={`software[${index}][software][id][product_name]`} value={item.product_name} />
            <input type="hidden" name={`software[${index}][software][id][vendor_name]`} value={item.vendor_name} />
         </div>
      
        ))}
        

        <p className="text-right">
        <button
          type="submit"
          className="rounded bg-blue-500 my-2 py-2 px-4 text-white hover:bg-blue-600 focus:bg-blue-400 disabled:bg-blue-300"
        >
          Oluştur
        </button>
      </p>

      </Form>

      <Outlet context={{
        setSoftware: setSoftware,
        software: software,
      }}/>
      </>
    );
  }