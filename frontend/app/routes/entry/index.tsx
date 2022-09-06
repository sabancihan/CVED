import { ActionFunction, json, LoaderFunction, redirect } from "@remix-run/node"
import { Link, useActionData, useLoaderData } from "@remix-run/react";
import invariant from "tiny-invariant";
import { getCsrf, login } from "~/models/login.server"


  export const action : ActionFunction = async ({ request }) => {

    
    const formData = await request.formData();

    console.log(formData,"form")

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



      

     return redirect("/servers");
      

  

  };


  
  
  type ActionData =
    | {
          username: null | string;
          password: null | string;
          _csrf: null | string;
      }
    | undefined;



export default function Index() {


    
    const errors  = useActionData() as ActionData;







    return (
 
                <div className="mb-4 flex space-x-2">
                    <button type="submit" className="w-full px-4 py-2 text-white bg-blue-600 rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-600 focus:ring-opacity-50">
                        Giriş Yap
                    </button>

                    <Link className="w-full px-4 py-2 text-center text-white bg-blue-600 rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-600 focus:ring-opacity-50"  to="register">
                            Kayıt Ol
                    </Link>
       
                </div>
   
    );
}
