import { ActionFunction, isCookie, json, LoaderFunction, redirect } from "@remix-run/node"
import { useActionData, useLoaderData } from "@remix-run/react";
import axios from "axios";
import invariant from "tiny-invariant";
import { getCsrf, login } from "~/models/login.server"
import {userToken} from "../cookies"



type LoaderData = {
  _csrf: Awaited<ReturnType<typeof getCsrf>>;

}


type ActionData =
  | {
        username: null | string;
        password: null | string;
        _csrf: null | string;
    }
  | undefined;

export const action : ActionFunction = async ({ request }) => {
    const formData = await request.formData();

    const username = formData.get("username");
    const password = formData.get("password");
    const _csrf = formData.get("_csrf");


    const errors: ActionData = {
        username: username ? null : "Kullanıcı adı gerekli",
        password: password  ? null : "Şifre gereklidir",
        _csrf: _csrf ? null : "CSRF tokeni gereklidir",
      };


      

        invariant(typeof username === "string", "Kullanıcı adı string olmalıdır");
        invariant(typeof password === "string", "Şifre string olmalıdır");
        invariant(typeof _csrf === "string", "Csrf string olmalıdır");

      const hasErrors = Object.values(errors).some(
        (errorMessage) => errorMessage
      );
      if (hasErrors) {
        return json<ActionData>(errors);
      }        



      const headers = await login({
        username: username,
        password: password,
        _csrf: _csrf,
      });



      

      if (!headers) {
        return json<ActionData>({
            username: "Giriş başarısız kullanıcı adı hatalı olabilir",
            password: "Giriş başarısız şifre hatalı olabilir",
            _csrf: "Giriş başarısız csrf tokeni hatalı olabilir",
        });
        }

        console.log(await userToken.serialize(headers))

        //create cookie with token
        return redirect("/servers/user", {
          headers: {
            "Set-Cookie": await userToken.serialize(headers)
          },
        });
    

      

  

  };

  export const loader: LoaderFunction = async () => {
    const _csrf = await getCsrf();
    if (!_csrf) {
      throw new Error("CSRF token bulunamadı");
    }
    return json({ _csrf: _csrf });
   
  }





export default function Login() {



    const data = useLoaderData<LoaderData>();
    
    const errors  = useActionData() as ActionData;







    return (
        <main className="mx-auto max-w-4xl">
            <h1 className="my-6 border-b-2 text-center text-3xl text-white">
                Giriş
            </h1>
            <form method="post">
              <input type="hidden" name="_csrf" value={data._csrf} />
                <div className="mb-4">
                    <label  className="block mb-2 text-sm font-medium text-white">
                        Kullanıcı Adı
                    </label>
                    <input type="text" name="username" id="username" className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent" />
                </div>
                <div className="mb-4">
                    <label htmlFor="password" className="block mb-2 text-sm font-medium text-white">
                        Şifre
                    </label>
                    <input type="password" name="password" id="password" className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent" />
                </div>
                <div className="mb-4">
                    <button type="submit" className="w-full px-4 py-2 text-white bg-blue-600 rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-600 focus:ring-opacity-50">
                        Giriş Yap
                    </button>
                </div>
            </form>
        </main>
    );



}