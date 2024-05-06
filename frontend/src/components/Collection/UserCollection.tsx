import { useEffect, useState } from "react"
import "./Collection.css"
import axios from "axios"

import { UserInterface } from "../../interfaces/UserInterface"
import { User } from "../User/User"
import { state } from "../../globalData/store"

export const UserCollection: React.FC = () => {

    const [users, setUsers] = useState<UserInterface[]>([])

    useEffect(() => {
        getAllUsers()
    }, [])

    const getAllUsers = async () => {

        const response = await axios.get("http://localhost:8080/users", {withCredentials:true})
  
        setUsers(response.data)

        console.log(response.data)

    }

    const deleteUser = async (user:UserInterface) => {

        const response = await axios.delete("http://localhost:8080/users/" + user.userId, {withCredentials:true})
        alert("Deleted " + response.data.firstName + " " + response.data.lastName + " and all associated reimbursements")
        getAllUsers()

        console.log(response.data)

    }

    const promoteUser = async (user:UserInterface|undefined) => {
        const response = await axios.patch("http://localhost:8080/users/" + user?.userId + "/promote",
        {...user })

        alert("Promoted " + response.data.firstName + " " + response.data.lastName)
        getAllUsers()

        console.log(response.data)

    }

    return(
        <div className="collection-container">
            {users.map((user, index) => 
            <div className="all-users-container">
                    <div className="user">
                        <User {...user}></User>
                        {user.role === "Employee" ? //can only promote people who aren't already a manager
                            <button className="user-button" onClick={() => promoteUser(user)}>Promote</button>
                            : ""
                        }
                        <button className="user-button" onClick={() => deleteUser(user)}>Delete</button>
                    </div>

            </div>

           )}

        </div>
    )
}