import { useEffect, useState } from "react"
import { UserInterface } from "../../interfaces/UserInterface"
import { useNavigate } from "react-router-dom"
import axios from "axios"

export const Register: React.FC = () => {

    const[user, setUser] = useState<UserInterface>({
        username:"",
        firstName:"",
        lastName:"",
        password:"",
        role:"Employee"
    })

    useEffect(() => {
        document.title = "Register"
     }, []);

    const navigate = useNavigate()

    const storeValues = (input:any) => {

        if(input.target.name === "username"){
            setUser((user) => ({...user, username:input.target.value}))
        } else if(input.target.name === "firstName"){
            setUser((user) => ({...user, firstName:input.target.value}))
        } else if(input.target.name === "lastName"){
            setUser((user) => ({...user, lastName:input.target.value}))
        } else if(input.target.name === "password"){
            setUser((user) => ({...user, password:input.target.value}))
        }

    }

    const register = async () => {

        const response = await axios.post("http://localhost:8080/users", user)

        alert(response.data.username + " created")

        navigate("/")

    }


    return(
        <div className="login">

        <div className="text-container">
            <h1>Createth thy account</h1>

            <div className="input-container">
                <input type="password" placeholder="password" name="password" onChange={storeValues}/>
            </div>
            <div className="input-container">
                <input type="text" placeholder="first name" name="firstName" onChange={storeValues}/>
            </div>
            <div className="input-container">
                <input type="text" placeholder="last name" name="lastName" onChange={storeValues}/>
            </div>
            <div className="input-container">
                <input type="text" placeholder="username" name="username" onChange={storeValues}/>
            </div>

            <button className="login-button" onClick={register}>Submit</button>
            <button className="login-button" onClick={() => navigate("/")}>Back</button>

        </div>

        </div>
    )
}