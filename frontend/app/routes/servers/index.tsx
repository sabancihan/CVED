import { json } from "@remix-run/node";
import {Link, useLoaderData } from "@remix-run/react";
import { getServers } from "~/models/servers.server";

export const loader = async () => {
    return json({
        servers: [
          {
            ipAddress: "157.21.12.13",
            port : 25565,
          },
          {
            ipAddress: "157.29.12.13",
            port : 1283,
          },
        ],
      });
}

type Server = {
    ipAddress: string;
    port: number;
}

type LoaderData = {
  servers : Awaited<ReturnType<typeof getServers>>;
}

export default function Servers() {
    const { servers } = useLoaderData() as LoaderData;
    console.log(servers);
    return (
      <main>
        <ul>
          {servers.map((server) => (
            <li key={server.ipAddress}>
              <Link to={`/servers/${server.ipAddress}`}>
                {server.port}
              </Link>
            </li>
          ))}
        </ul>
      </main>
    );
  }