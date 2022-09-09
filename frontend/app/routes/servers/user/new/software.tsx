import { ActionFunction, redirect } from "@remix-run/node";
import { Form, Link, useLocation, useNavigate, useOutletContext } from "@remix-run/react";

export const action : ActionFunction = async ({ request }) => {
    const formData = await request.formData();


  

    const ipAddress = formData.get("version");
    const port = formData.get("product_name");
    const cpu = formData.get("vendor_name");



    


  
    return redirect("/servers/user/new?index");
  };



 

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

    const inputClassName = `w-full rounded border border-gray-500 px-2 py-1 text-lg text-black`;

    return (
        <form onSubmit={newSoftware} className="mx-10">
          <h1 className="text-white text-xl font-bold">Uygulama Ekle</h1>
       <p>
            <label className="text-white">
              Üretici:{" "}
              <input 
              required
                type="text"
                name="vendor_name"
                className={inputClassName}
              />
            </label>
          </p>
          <p>
            <label className="text-white">
              Ad:{" "}
              <input
              required
                type="text"
                name="product_name"
                className={inputClassName}
              />
            </label>
          </p>
  
          
          <p>
            <label className="text-white">
              Versiyon:{" "}
              <input
              required
                type="text"
                name="version"
                className={inputClassName}
              />
            </label>
          </p>
  
   
          
  
          <p className="text-right">
          <button
            type="submit"
            className="rounded bg-blue-500 my-2 py-2 px-4 text-white hover:bg-blue-600 focus:bg-blue-400 disabled:bg-blue-300"
          >
            Oluştur
          </button>
        </p>
  
        </form>
      );
}