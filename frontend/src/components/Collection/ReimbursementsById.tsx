import axios from "axios";
import "./Collection.css"
import { ReimbursementInterface } from "../../interfaces/ReimbursementInterface";
import { useEffect, useState } from "react";
import { Reimbursement } from "../Reimbursement/Reimbursement";
import { state } from "../../globalData/store";
export const ReimbursementsById: React.FC<any> = (showPending) => {

    let userId = state.userSessionData.userId;
    const [pending, setPending] = useState<Boolean>(false);
    const [fallback, setFallback] = useState("");
    const [descriptionUpdate, setDescriptionUpdate] = useState("");
    const [reimbursements, setReimbursements] = useState<ReimbursementInterface[]>([])

    const getReimbursementsByUserId = async () => {
        document.getElementById("reimbPlace")!.hidden = false;
        setFallback("")
        const response = await axios.get("http://localhost:8080/reimbursements/" + userId, {withCredentials:true})
            .then(response => setReimbursements(response.data))
            .catch(noReimbursementsFound)
    }

    const getAllPendingReimbursementsById = async () => {
        document.getElementById("reimbPlace")!.hidden = false;
        setFallback("")
        setPending(!pending);
        if (pending) {
            const response = await axios.get("http://localhost:8080/reimbursements/" + userId + "/pending", {withCredentials:true})
            .then(response => setReimbursements(response.data))
            .catch(noReimbursementsFound)
        } else {
            getReimbursementsByUserId();
        }
    }

    const noReimbursementsFound = () => {
        document.getElementById("reimbPlace")!.hidden = true;
        setFallback("No reimbursements found.")
    }

    // const processReimbursement = async (reimb:ReimbursementInterface|undefined, approved:boolean) => {
    //     const response = await axios.patch("http://localhost:8080/reimbursements/"+reimb?.reimbId+"/" + (approved ?  "approve" : "deny"), reimb)
    //     alert((approved ? "Completed " : "Denied ")+ response.data.description)
    //     console.log(response.data)
    // }

    const updateReimbursement = async (reimb:ReimbursementInterface|undefined) => {
        let newReimb = {...reimb, description:descriptionUpdate}
        const response = await axios.patch("http://localhost:8080/reimbursements/"+reimb?.reimbId+"/update", newReimb)
        console.log(response.data)
        getAllPendingReimbursementsById()
    }

    const storeValues = (input:any) => {
        setDescriptionUpdate(input.target.value)
    }

    useEffect(() => {
        getReimbursementsByUserId();
    }, [])

    return(
        <div id="reimbursement-collection">
            <div id="fallback">{fallback}</div>
            <button id="togglePendingButton" onClick={getAllPendingReimbursementsById}>
                 Toggle View Pending
            </button>
            <h4>Current: {pending ? "Pending" : "All"}</h4>
            <div id="reimbPlace">
                {reimbursements.map((reimb, index) => 
                    <div className="reimbursement">
                        <Reimbursement {...reimb}></Reimbursement>
                        {reimb.status==="Pending" ? 
                        <div id="buttons-container">
                            {/* <button className="reimb-button" onClick={() => processReimbursement(reimb, true)}>Complete</button>
                            <button className="reimb-button" onClick={() => processReimbursement(reimb, false)}>Deny</button> */}
                        </div>
                        : ''}
                                                <div id="desc-change-container">
                            <input type="text" placeholder="change description" name="descriptionChange" onChange={storeValues}/>
                            <button className="reimb-button" onClick={() => updateReimbursement(reimb)}>Update</button>
                        </div>
                    </div>
            )}
            </div>


        </div>
    )

}