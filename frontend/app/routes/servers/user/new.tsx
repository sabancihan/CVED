import {json, redirect } from "@remix-run/node";
import { Form ,useActionData} from "@remix-run/react";
import { createServer } from "~/models/servers.server";
import type { ActionFunction } from "@remix-run/node";
import invariant from "tiny-invariant";

const inputClassName = `w-full rounded border border-gray-500 px-2 py-1 text-lg`;

type ActionData =
  | {
        ipAddress: null | string;
        port: null | string;
        cpu: null | string;
        ram: null | string;
        disk: null | string;
    }
  | undefined;

export const action : ActionFunction = async ({ request }) => {
    const formData = await request.formData();
  

    const ipAddress = formData.get("ipAddress");
    const port = formData.get("port");
    const cpu = formData.get("cpu");
    const ram = formData.get("ram");
    const disk = formData.get("disk");

    const errors: ActionData = {
        ipAddress: ipAddress ? null : "ipAddress is required",
        port: port  ? null : "port is required",
        cpu: cpu ? null : "cpu is required",
        ram: ram ? null : "ram is required",
        disk: disk ? null : "disk is required",
      };

      const portNumber = Number(port);

      

        invariant(typeof ipAddress === "string", "ipAddress must be a string");
        invariant(!isNaN(portNumber), "id must be a number");
        invariant(typeof cpu === "string", "cpu must be a string");
        invariant(typeof ram === "string", "ram must be a string");
        invariant(typeof disk === "string", "disk must be a string");

      const hasErrors = Object.values(errors).some(
        (errorMessage) => errorMessage
      );
      if (hasErrors) {
        return json<ActionData>(errors);
      }        
  
    await createServer({
            port: portNumber,
            disk: disk,
            ram: ram,
            cpu: cpu,
            ipAddress: ipAddress,
    });
  
    return redirect("/servers/admin");
  };


export default function NewServer() {
    const errors  = useActionData();
    return (
      <Form method="post">
        <p>
          <label className="text-white">
            Server Ip Address:{" "}
            <input
              type="text"
              name="ipAddress"
              className={inputClassName}
            />
          </label>
        </p>
        <p>
          <label className="text-white">
            Server Port:{" "}
            <input
              type="text"
              name="port"
              className={inputClassName}
            />
          </label>
        </p>

        <p>
          <label className="text-white">
            Server Ram:{" "}
            <input
              type="text"
              name="ram"
              className={inputClassName}
            />
          </label>
        </p>

        <p className="text-right">
        <button
          type="submit"
          className="rounded bg-blue-500 my-2 py-2 px-4 text-white hover:bg-blue-600 focus:bg-blue-400 disabled:bg-blue-300"
        >
          Create Server
        </button>
      </p>

      </Form>
    );
  }