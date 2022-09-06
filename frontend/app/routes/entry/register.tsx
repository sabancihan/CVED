import { ActionFunction, json, redirect } from "@remix-run/node";
import { Link } from "@remix-run/react";
import invariant from "tiny-invariant";
import { register } from "~/models/login.server";
import login from "../login";

  
type ActionData =
| {
      username: null | string;
      password: null | string;
      name : null | string;
      surname : null | string;
      email : null | string;
      _csrf: null | string;
  }
| undefined;

export const action : ActionFunction = async ({ request }) => {

    
    const formData = await request.formData();

    console.log(formData,"form")

    const username = formData.get("username");
    const password = formData.get("password");
    const name = formData.get("name");
    const surname = formData.get("surname");
    const email = formData.get("email");
    const _csrf = formData.get("_csrf");


    const errors: ActionData = {
        username: username ? null : "Kullanıcı adı gerekli",
        password: password  ? null : "Şifre gereklidir",
        name: name  ? null : "İsim gereklidir",
        surname: surname  ? null : "Soyisim gereklidir",
        email: email  ? null : "Email gereklidir",
        _csrf: _csrf ? null : "CSRF tokeni gereklidir",
      };


      

        invariant(typeof username === "string", "Kullanıcı adı string olmalıdır");
        invariant(typeof password === "string", "Şifre string olmalıdır");
        invariant(typeof _csrf === "string", "Csrf string olmalıdır");
        invariant(typeof name === "string", "İsim string olmalıdır");
        invariant(typeof surname === "string", "Soyisim string olmalıdır");
        invariant(typeof email === "string", "Email string olmalıdır");

      const hasErrors = Object.values(errors).some(
        (errorMessage) => errorMessage
      );
      if (hasErrors) {
        return json<ActionData>(errors);
      }        



      const headers = await register({
        username: username,
        password: password,
        _csrf: _csrf,
        name : name,
        surname : surname,
        email : email
      });

    

      

     return redirect("/entry");
      

  

  };


export default function Register() {

    return (
        <>
        <div className="mb-4">
            <label htmlFor="name" className="block mb-2 text-sm font-medium text-gray-600">
                Adınız
            </label>
            <input required type="text" name="name" id="name" className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent" />
        </div>

        <div className="mb-4">
            <label htmlFor="surname"  className="block mb-2 text-sm font-medium text-gray-600">
                Soyadınız
            </label>
            <input required type="text" name="surname" id="surname" className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent" />
        </div>

        
        <div className="mb-4">
            <label htmlFor="email" className="block mb-2 text-sm font-medium text-gray-600">
                Email
            </label>
            <input required type="email" name="email" id="email" className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent" />
        </div>

        <div className="mb-4 flex space-x-2">
                    <button type="submit" className="w-full px-4 py-2 text-white bg-blue-600 rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-600 focus:ring-opacity-50">
                        Kayıt Ol
                    </button>

                    <Link className="w-full px-4 py-2 text-center text-white bg-blue-600 rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-600 focus:ring-opacity-50"  to="..">
                           Giriş Yap
                    </Link>
       
                </div>

    </>
        

    )
    }