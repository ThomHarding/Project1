import axios from "axios";
import "./Collection.css"
import { ReimbursementInterface } from "../../interfaces/ReimbursementInterface";
import { useEffect, useState } from "react";
import { Reimbursement } from "../Reimbursement/Reimbursement";
export const AllReimbursements: React.FC<any> = ({showPending}) => {

    const [reimbursements, setReimbursements] = useState<ReimbursementInterface[]>([]) //start with empty array
    const [pending, setPending] = useState<Boolean>(true);
    const [fallback, setFallback] = useState("");
    const [descriptionUpdate, setDescriptionUpdate] = useState("");

    const getAllReimbursements = async () => {
        setFallback("")
        const response = await axios.get("http://localhost:8080/reimbursements", {withCredentials:true})
        .then(response => setReimbursements(response.data))
        .catch(noReimbursementsFound)
    }

    const getAllPendingReimbursements = async () => {
        setFallback("")
        if (pending) {
            console.log('true');
            const response = await axios.get("http://localhost:8080/reimbursements/pending", {withCredentials:true})
            .then(response => setReimbursements(response.data))
            .catch(noReimbursementsFound)
        } else {
            console.log('false');
            getAllReimbursements();
        }
        setPending(!pending);
    }
    
    const processReimbursement = async (reimb:ReimbursementInterface|undefined, approved:boolean) => {
        const response = await axios.patch("http://localhost:8080/reimbursements/"+reimb?.reimbId+"/" + (approved ?  "approve" : "deny"), reimb)
        alert((approved ? "Approved " : "Denied ")+ response.data.description)
        getAllPendingReimbursements()
        console.log(response.data)

    }

    const noReimbursementsFound = () => {
        setFallback("No reimbursements found.")
    }

    const updateReimbursement = async (reimb:ReimbursementInterface|undefined) => {
        let newReimb = {...reimb, description:descriptionUpdate}
        const response = await axios.patch("http://localhost:8080/reimbursements/"+reimb?.reimbId+"/update", newReimb)
        console.log(response.data)

        getAllReimbursements();
    }

    const storeValues = (input:any) => {
        setDescriptionUpdate(input.target.value)
    }

    useEffect(() => {
        getAllReimbursements();
    }, [])

    

    return(
        <div className="reimbursement-collection">
            <div id="fallback">{fallback}</div>
            <button id="togglePendingButton" onClick={getAllPendingReimbursements}>
                 Toggle View Pending
            </button>
            <h4>Current: {pending ? "All" : "Pending"}</h4>
            {reimbursements.map((reimb, index) => 
                <div className="reimbursement">
                    <Reimbursement {...reimb}></Reimbursement>
                    {reimb.status==="Pending" ? 
                    <div>
                        <button className="reimb-button" onClick={() => processReimbursement(reimb, true)}>Complete</button>
                        <button className="reimb-button" onClick={() => processReimbursement(reimb, false)}>Deny</button>
                    </div>
                     : 
                    ''}
                    <div>
                        <input type="text" placeholder="change description" name="descriptionChange" onChange={storeValues}/>
                        <button className="reimb-button" onClick={() => updateReimbursement(reimb)}>Update</button>
                    </div>
                </div>
           )}

        </div>
    )

}