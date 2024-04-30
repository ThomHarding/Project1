import { useNavigate } from "react-router-dom";
import "./Login.css";
import React from "react"

export const Login: React.FC = () => {

    const navigate = useNavigate();

    const login = async () => {
        navigate("/catch")
    }

    return(
        <div className="login">
            <div className="text-container">
                <h1>page header</h1>
                <h3>sign in please</h3>
    
                <div className="input-container">
                    <input type="text" placeholder="username" name="username"/>
                </div>
    
                <div className="input-container">
                    <input type="password" placeholder="password" name="password"/>
                </div>
    
                <button className="login-button">login</button>
                <button className="login-button">creatue account</button>
            </div>
        </div>
    )
}

