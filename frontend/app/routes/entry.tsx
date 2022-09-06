import { ActionFunction, json, LoaderFunction } from "@remix-run/node";
import { Link, Outlet, useLoaderData, useLocation } from "@remix-run/react";

import { getCsrf } from "~/models/login.server";

type LoaderData = {
    _csrf: Awaited<ReturnType<typeof getCsrf>>;
  
  }

  export const action : ActionFunction = async ({ request }) => {
    return null;
  }

  export const loader: LoaderFunction = async () => {
    const _csrf = await getCsrf();
    if (!_csrf) {
      throw new Error("CSRF token bulunamadı");
    }
    return json({ _csrf: _csrf });
   
  }
  
export default function Index() {
    const data = useLoaderData<LoaderData>();

    const location = useLocation();

    const locationPath = location.pathname == "/entry" ? "/entry?index" : location.pathname;



    return (
        <main className="mx-auto max-w-4xl">
            <h1 className="my-6 border-b-2 text-center text-3xl">
                CVED Doğrulama
            </h1>

            <form method="post" action={locationPath}>
              <input type="hidden" name="_csrf" value={data._csrf} />
                <div className="mb-4">
                    <label  className="block mb-2 text-sm font-medium text-gray-600">
                        Kullanıcı Adı
                    </label>
                    <input required type="text" name="username" id="username" className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent" />
                </div>
                <div className="mb-4">
                    <label htmlFor="password" className="block mb-2 text-sm font-medium text-gray-600">
                        Şifre
                    </label>
                    <input required type="password" name="password" id="password" className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent" />
                </div>

                <Outlet/>
            
            </form>
        </main>
    )
  }
  