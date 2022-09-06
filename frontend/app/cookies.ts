import { createCookie} from "@remix-run/node";


export const userToken = createCookie("userToken", {
    path: "/",
    httpOnly: true,
    secure: false,
    sameSite: "strict",
    secrets: ["my-secret"],
    maxAge: 60 * 60 * 10 // 10 hours
    

});  