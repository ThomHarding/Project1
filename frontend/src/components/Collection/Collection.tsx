import { useEffect, useState } from "react"
import "./Collection.css"
import axios from "axios"
import { ReimbursementInterface } from "../../interfaces/ReimbursementInterface"
import { Reimbursement } from "../Reimbursement/Reimbursement"
import { UserInterface } from "../../interfaces/UserInterface"
import { User } from "../User/User"

export const Collection: React.FC = () => {

    const [reimbursements, setReimbursements] = useState<ReimbursementInterface[]>([]) //start with empty array
    const [users, setUsers] = useState<UserInterface[]>([]) //start with empty array
    const [userToPost, setUserToPost] = useState<UserInterface>({
        firstName: "John",
        lastName: "Testman",
        password: "eggstraiordinary",
        role: "Employee",
        username: "jtestman"
    })

    useEffect(() => {
        getAllUsers()
        getAllReimbursements()
    }, [])

    const getAllReimbursements = async () => {

        const response = await axios.get("http://localhost:8080/reimbursements", {withCredentials:true})

        //populate the reimbursement state  
        setReimbursements(response.data)

        console.log(response.data)

    }

    const getAllUsers = async () => {

        const response = await axios.get("http://localhost:8080/users", {withCredentials:true})

        //populate the reimbursement state  
        setUsers(response.data)

        console.log(response.data)

    }

    const postUser = async () => {
        const response = await axios.post("http://localhost:8080/users", 
        userToPost,
        {withCredentials:true})
        .then((response) => {
            alert('posted ' + response.data) 

        })

        console.log(response)

    }

    const deleteUser = async (user:UserInterface) => {

        const response = await axios.delete("http://localhost:8080/users/" + user.userId, {withCredentials:true})

        alert("Deleted" + response.data.firstName + " " + response.data.lastName)

        console.log(response.data)

    }

    const promoteUser = async (user:UserInterface|undefined) => {
        const response = await axios.patch("http://localhost:8080/users/" + user?.userId + "/promote",
        {...user })

        alert("Promoted " + response.data.firstName + " " + response.data.lastName)

        console.log(response.data)

    }

    //works to complete or deny a reimbursement
    const processReimbursement = async (reimb:ReimbursementInterface|undefined, approved:boolean) => {
        const response = await axios.patch("http://localhost:8080/reimbursements/"+reimb?.reimbId+"/" + (approved ?  "approve" : "deny"), reimb)

        alert((approved ? "Completed " : "Denied ")+ response.data.description)

        console.log(response.data)

    }

    return(
        <div className="collection-container">
            <button onClick={postUser}>insert a user</button>
            {users.map((user, index) => 
                <div className="user">
                    <User {...user}></User>
                    <button className="user-button" onClick={() => promoteUser(user)}>Promote</button>
                    <button className="user-button" onClick={() => deleteUser(user)}>Delete</button>
                </div>
           )}
           <p />
            {reimbursements.map((reimb, index) => 
                <div className="reimbursement">
                    <Reimbursement {...reimb}></Reimbursement>
                    <button className="reimb-button" onClick={() => processReimbursement(reimb, true)}>Complete</button>
                    <button className="reimb-button" onClick={() => processReimbursement(reimb, false)}>Deny</button>
                </div>
           )}

            {/* If you need to render multiple things in map(), they need to be in a <div> */}

        </div>
    )
}