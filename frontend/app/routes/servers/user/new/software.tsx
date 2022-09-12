import { ActionFunction, redirect } from "@remix-run/node";
import { Form, Link, useLocation, useNavigate, useOutletContext } from "@remix-run/react";
import { FormEvent } from "react";
import { SoftwareAddInputForm } from "~/components/SoftwareAddInputForm";





 

export type SoftwareInput = {
    version: string;
    product_name: string;
    vendor_name: string;
    };

 export type ContextType = {
    software: SoftwareInput[];
    setSoftware: React.Dispatch<React.SetStateAction<SoftwareInput[]>>;

  }



export default function SoftwareIndex() {

  const navigate = useNavigate();


  function newSoftware(e : React.FormEvent<HTMLFormElement>) {
    e.preventDefault();

    

    const vendor_name = e.currentTarget.vendor_name.value;
    const product_name = e.currentTarget.product_name.value;
    const version = e.currentTarget.version.value;

    setSoftware([...software, {vendor_name, product_name, version}]);

    navigate("/servers/user/new");




    


  }

    const context = useOutletContext() as ContextType;
    const { software, setSoftware } = context;

    return (
      <form onSubmit={newSoftware}>
        <h1>Uygulama Ekle</h1>
        <SoftwareAddInputForm/>
        <div className="flex justify-end">
        <button
          type="submit"
          className="rounded bg-blue-500 my-2 py-2 px-4 text-white hover:bg-blue-600 focus:bg-blue-400 disabled:bg-blue-300"
        >
          Oluştur
        </button>  

        </div>

            </form>
    )
}