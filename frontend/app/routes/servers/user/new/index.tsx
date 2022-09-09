import { json, LoaderFunction } from "@remix-run/node";
import { Link, useOutletContext } from "@remix-run/react";
import { ContextType } from "./software";





export default function SoftwareIndex() {
    const context = useOutletContext() as ContextType;
    const softwares = context.software;

    console.log(softwares,"index");
  return (
    <div className="flex flex-col">
    <div className="flex items-center">



        <div className="flex flex-col">
        <h1 className="text-xl text-white">Uygulamalar</h1>

            {softwares && softwares.map((software) => (

                <div key={`${software.vendor_name}:${software.product_name}:${software.version}}`} className="flex flex-row space-x-2 text-white">
                    <p>{software.product_name}</p>
                    <p>{software.vendor_name}</p>
                    <p>{software.version}</p>
                </div>
                ))}
        </div>
     

        
            <p>

            <Link to="software" title="Yeni Uygulama Oluştur">
            <svg  xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-6 mx-1 h-6 text-white">
                <path strokeLinecap="round" strokeLinejoin="round" d="M12 9v6m3-3H9m12 0a9 9 0 11-18 0 9 9 0 0118 0z" />
            </svg>

            </Link>
            </p>
    </div>
    </div>

  );
}