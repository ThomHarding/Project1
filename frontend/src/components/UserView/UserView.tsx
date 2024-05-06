import { useEffect, useState } from "react";
import "./UserView.css"
import { ReimbursementsById } from "../Collection/ReimbursementsById";
import { state } from "../../globalData/store";
import axios from "axios";
import { LogoutButton } from "../Login/LogoutButton";

export const UserView: React.FC = () => {

    useEffect(() => {
        document.title = "User View"
     }, []);

    const [reimb, setReimb] = useState(
        {
            description: "",
            amount: 0,
            status: "Pending",
            user: {}
        }
    )

    const storeValues = (input:any) => { 
        if(input.target.name === "description"){
            setReimb((reimb) => ({...reimb, description:input.target.value}))
        } else {
            setReimb((reimb) => ({...reimb, amount:parseInt(input.target.value)}))
        }
 
    }

    const postReimbursement = async () => {
        const user = await axios.get("http://localhost:8080/users/" + state.userSessionData.userId)
        reimb.user = user.data;
        const response = await axios.post("http://localhost:8080/reimbursements", 
        reimb,
        {withCredentials:true})
        .then((response) => {
            alert('posted ' + reimb.description) 

        })

        console.log(response)

    }

    return(
        <div className="user-view">
                <div>
                    <LogoutButton/>
                    <ReimbursementsById />
                    <button id="createReimbursementButton" onClick={postReimbursement}>Create Reimbursement</button>
                </div>
                <div>
                    <div id="reimbursement-form">
                        <div className="input-container">
                            <input type="text" placeholder="Description" name="description" onChange={storeValues}/>
                        </div>
                        <div className="input-container"> 
                            <input type="number" placeholder='Amount' name="amount" onChange={storeValues}/>
                        </div>
                    </div>
                </div>
        </div>
    )

}