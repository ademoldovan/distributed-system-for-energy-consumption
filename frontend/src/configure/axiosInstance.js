import axios from 'axios'

const axiosInstance = axios.create(
    {
        baseURL: "http://localhost:8080",
        headers:{
            post:
                {
                    "Content-Type": "aplication/json",
                    "Access-Control-Allow-Origin":"*",
                    "Access-Control-Allow-Headers":
                        "Content-Type, Access-Control-Allow-Headers, Authorization ,X-",
                }
        }
    }

);

export default axiosInstance
