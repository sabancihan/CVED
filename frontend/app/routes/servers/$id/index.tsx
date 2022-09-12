import { Link, useOutletContext } from "@remix-run/react";


export default function ServerDetailIndex() {

    const {admin} = useOutletContext() as {admin : boolean};


    return (

        admin &&  <div className="flex space-x-2 items-center justify-center border-t-2">
      

        <Link to="software" title="Yeni Uygulama Oluştur">

            <div className="flex items-center justify-center">
                    <h1 className="text-xl text-black border-t-2s">Yeni Uygulama Ekle</h1>

                    <svg  xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-6 mx-1 h-6 text-white">
                        <path strokeLinecap="round" strokeLinejoin="round" d="M12 9v6m3-3H9m12 0a9 9 0 11-18 0 9 9 0 0118 0z" />
                    </svg>
            </div>
         

        </Link>

        </div>
    );

      
        
 

   
    
  
}