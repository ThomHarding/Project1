import { ReimbursementInterface } from "../../interfaces/ReimbursementInterface"
import "./Reimbursement.css"

export const Reimbursement: React.FC<ReimbursementInterface> = (reimbursement:ReimbursementInterface) => {

    return(
        <div className="reimbursement-container">
                <h2>{reimbursement.amount}</h2>
                <h3>{reimbursement.description}</h3>
                <h4>{reimbursement.status}</h4>
                <h5>{reimbursement.user.firstName + " " + reimbursement.user.lastName}</h5>
        </div>
    )

}