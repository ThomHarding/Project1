import { UserInterface } from "../../interfaces/UserInterface"
import "./User.css"

export const User: React.FC<UserInterface> = (user:UserInterface) => {


    return(
        <div className="user-container">
                <h2>{user.firstName}</h2>
                <h3>{user.lastName}</h3>
                <h4>{user.role}</h4>
        </div>
    )

}