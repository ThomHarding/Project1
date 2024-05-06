import { useNavigate } from "react-router-dom"
import { state } from "../../globalData/store"

export const LogoutButton: React.FC = () => {

    const navigate = useNavigate()


    const logout = async () => {

        alert("bye")
        state.userId = 0;
        state.username = '';
        state.role = "Employee";
        navigate("/")

    }


    return(
        <button onClick={logout}>
            Logout
        </button>
    )
}