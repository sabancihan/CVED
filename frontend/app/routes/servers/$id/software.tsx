import { ActionFunction, json, redirect } from "@remix-run/node";
import { Form, Link, useActionData, useLocation, useNavigate, useOutletContext } from "@remix-run/react";
import { FormEvent } from "react";
import invariant from "tiny-invariant";
import { SoftwareAddInputForm } from "~/components/SoftwareAddInputForm";
import { userToken } from "~/cookies";
import { createSoftware } from "~/models/software.server";

type ActionData =
  | {
        version: null | string;
        server: null | string;
        product_name: null | string;
        vendor_name: null | string;
    }
  | undefined;

export const action : ActionFunction = async ({ request }) => {
    const cookie = await userToken.parse(request.headers.get("Cookie"));

    
    if (!cookie) {
        return json({servers: []}, {status: 401});
    }

    const formData = await request.formData();


  

    const version = formData.get("version");
    const product_name = formData.get("product_name");
    const vendor_name = formData.get("vendor_name");

    const id = request.url.split("/")[4];


    
    const errors: ActionData = {
        vendor_name: vendor_name ? null : "Vendor name is required",
        product_name: product_name  ? null : "Product name is required",
        version: version ? null : "Version is required",
        server: id ? null : "Server id is required",
      };









      invariant(typeof id === "string", "Server id must be a string");
      invariant(typeof vendor_name === "string", "Vendor name must be a string");
      invariant(typeof product_name === "string", "Product name must be a string");
      invariant(typeof version === "string", "version must be a string");


      
      const hasErrors = Object.values(errors).some(
        (errorMessage) => errorMessage
      );
      if (hasErrors) {
        return json<ActionData>(errors);
      }        


      await createSoftware(cookie, {
        software : {
                vendor_name,
                product_name,
            
        },
        version,
        server: id,
      })






    return redirect(`/servers/${id}`);


  
    
  };



 





export default function SoftwareIndex() {


    const errors  = useActionData() as ActionData;





    return (
        <Form  method="post"  className="px-10 mb-2 flex flex-col">
            <SoftwareAddInputForm />

            <button
          type="submit"
          className="rounded bg-blue-500 my-2 py-2 px-4 text-white hover:bg-blue-600 focus:bg-blue-400 disabled:bg-blue-300"
        >
          Oluştur
        </button>

        </Form>
    )
}