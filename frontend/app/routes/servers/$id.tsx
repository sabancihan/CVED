import { json, LoaderFunction } from "@remix-run/node";
import { useLoaderData } from "@remix-run/react";
import { getServer } from "~/models/servers.server";
import invariant from "tiny-invariant";

type  LoaderData = {
    server:  Awaited<ReturnType<typeof getServer>>;
}

export const loader: LoaderFunction = async ({
    params ,
  }) => {
    console.log(params);
    invariant(params.id, "id is required");

    const id = Number(params.id);

    invariant(!isNaN(id), "id must be a number");

    const server = await getServer(id);

    invariant(server, "Server not found");

    return json<LoaderData>({server});
  };

export default function ServerDetail() {
    const {server} = useLoaderData() as LoaderData;
    
    return (
        <main className="mx-auto max-w-4xl">
          <h1 className="my-6 border-b-2 text-center text-3xl">
            Some Server {server.ipAddress}
          </h1>
        </main>
      );
    
}