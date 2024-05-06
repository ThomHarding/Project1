import { useNavigate } from "react-router-dom"
import "./Login.css"
import { useEffect, useState } from "react"
import axios from "axios"
import { state } from "../../globalData/store"
import { LoginUserInterface } from "../../interfaces/LoginUserInterface"

export const Login: React.FC = () => {

    //defining a state object for our user data
    const[user, setUser] = useState<LoginUserInterface>({
        username:"",
        password:"",
        role:"Employee"
    })

    const navigate = useNavigate()

    const storeValues = (input:any) => {

        if(input.target.name === "username"){
            setUser((user) => ({...user, username:input.target.value}))
        } else {
            setUser((user) => ({...user, password:input.target.value}))
        }
 
    }

    useEffect(() => {
        document.title = "Login"
     }, []);

    const login = async () => {
        const response = await axios.post("http://localhost:8080/users/login", 
        { username:user.username, password:user.password}, {withCredentials:true})
        .then((response) => {

            //if the login was successful, log the user in and store their info in global state
            state.userSessionData = response.data
            
            console.log(state.userSessionData)

            alert("Welcome, " + state.userSessionData.username)

            if (response.data.role === 'Employee') {
                navigate("/users")
            } else {
                navigate("/managers")
            }

        })
        .catch((error) => {alert("Login Failed!")}) //If login fails, tell the user that

    }


    return(
        <div className="login">
            <div className="text-container">
                <h1>Welcome to the Reimbursement Realm</h1>
                <h3>Sign in to access the webpage</h3>

                <div className="input-container">
                    <input type="text" placeholder="username" name="username" onChange={storeValues}/>
                </div>

                <div className="input-container">
                    <input type="password" placeholder="password" name="password" onChange={storeValues}/>
                </div>
                <button className="login-button" onClick={login}>Login</button>
                <button className="login-button" onClick={() => navigate("/register")}>Create Account</button>
            </div>

        </div>
 
    )

}