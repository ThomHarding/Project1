import { UserInterface } from "../interfaces/UserInterface";

export const state:any = {

    //we typically want to store user session info on the front end
    //for personalization as well as role-based security control
    userSessionData: {
        userId:5,
        username:"",
        role:"Employee"
    } as UserInterface,

}
