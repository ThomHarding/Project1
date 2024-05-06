import "./ManagerView.css"
import { AllReimbursements } from "../Collection/AllReimbursements";
import { UserCollection } from "../Collection/UserCollection";
import { LogoutButton } from "../Login/LogoutButton";
import { useEffect } from "react";

export const ManagerView: React.FC = () => {

    useEffect(() => {
        document.title = "Manager View"
     }, []);

    return(
        <div className="manager-view">
                    <LogoutButton/>
                    <AllReimbursements />
                    <div id="allUsersView">
                        <UserCollection/>
                    </div>
        </div>
    )

}