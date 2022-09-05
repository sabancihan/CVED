import { createCookie} from "@remix-run/node";


export const userToken = createCookie("userToken", {
    path: "/",
    httpOnly: true,
    secure: true,
    expires:  new Date(Date.now() + 1000 * 60 * 60 * 10),  // 10 hours,
    sameSite: "lax",
    secrets: ["my-secret"],
    

});  