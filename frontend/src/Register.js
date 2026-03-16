import axios from "axios";
import { useForm } from "react-hook-form"
import { useNavigate } from "react-router-dom";

export const Register=()=> {
    const {register, handleSubmit, formState:{errors}} = useForm();
    const navigate = useNavigate();

    const submit=(data)=> {
        axios.post("http://localhost:8082/user/add").then(response=> {
            console.log("Register Success", response.data);
            navigate('/login')
        }).catch(error=> {
            console.error("Login failed")
        });
    }

    return (
        <div>
            
        </div>
    );
}