import type { LoaderFunction } from "@remix-run/node";
import { json } from "@remix-run/node";
import { Link, Outlet, useLoaderData } from "@remix-run/react";

import { getServers } from "~/models/servers.server";

type LoaderData = {
  servers: Awaited<ReturnType<typeof getServers>>;
};

export const loader: LoaderFunction = async () => {
  return json({ servers: await getServers() });
};

export default function PostAdmin() {
  const { servers } = useLoaderData() as LoaderData;
  return (
    <div className="mx-auto max-w-4xl">
      <h1 className="my-6 mb-2 border-b-2 text-center text-3xl">
        Server Admin
      </h1>
      <div className="grid grid-cols-4 gap-6">
        <nav className="col-span-4 md:col-span-1">
          <ul>
            {servers.map((server) => (
              <li key={server.id}>
                <Link
                  to={server.ipAddress}
                  className="text-blue-600 underline"
                >
                  {server.ipAddress}
                </Link>
              </li>
            ))}
          </ul>
        </nav>
        <main className="col-span-4 md:col-span-3">
          <Outlet/>
        </main>
      </div>
    </div>
  );
}