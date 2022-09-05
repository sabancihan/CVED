import { ActionFunction, json, LoaderFunction, redirect } from "@remix-run/node"
import { useActionData, useLoaderData } from "@remix-run/react";
import invariant from "tiny-invariant";
import { login } from "~/models/login.server"






type ActionData =
  | {
        username: null | string;
        password: null | string;
    }
  | undefined;

export const action : ActionFunction = async ({ request }) => {
    const formData = await request.formData();

    const username = formData.get("username");
    const password = formData.get("password");


    const errors: ActionData = {
        username: username ? null : "Kullanıcı adı gerekli",
        password: password  ? null : "Şifre gereklidir",
      };


      

        invariant(typeof username === "string", "Kullanıcı adı string olmalıdır");
        invariant(typeof password === "string", "Şifre string olmalıdır");

      const hasErrors = Object.values(errors).some(
        (errorMessage) => errorMessage
      );
      if (hasErrors) {
        return json<ActionData>(errors);
      }        

      const headers = await login({
        username: username,
        password: password,
      });

      

      if (!headers) {
        return json<ActionData>({
            username: "Giriş başarısız kullanıcı adı hatalı olabilir",
            password: "Giriş başarısız şifre hatalı olabilir",
        });
        }


      
    console.log(headers);

      //redirect("/servers");
      

  

  };





export default function Login() {
    const errors  = useActionData() as ActionData;


    return (
        <main className="mx-auto max-w-4xl">
            <h1 className="my-6 border-b-2 text-center text-3xl">
                Giriş
            </h1>
            <form method="post">
                <div className="mb-4">
                    <label  className="block mb-2 text-sm font-medium text-gray-600">
                        Kullanıcı Adı
                    </label>
                    <input type="text" name="username" id="username" className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent" />
                </div>
                <div className="mb-4">
                    <label htmlFor="password" className="block mb-2 text-sm font-medium text-gray-600">
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