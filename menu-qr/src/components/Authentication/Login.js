import React,{useState, useEffect, useRef} from 'react'
import './Login.css'
import { Link , useNavigate} from 'react-router-dom';


function Login() {
  const [credentials, setCredentials] = useState({username: "", password: ""}) 
  const [loginError, setLoginError] = useState(null)
  let navigate = useNavigate();

    const componentRef = useRef(null);

    useEffect(() => {
    componentRef.current.classList.remove('animated-component'); // Remove after animation
    }, []);

  const onLoginSubmit = async (e) =>{
    console.log(process.env)
    e.preventDefault();
    const response = await fetch(process.env.HOST_URL+"/Client/Login", {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
          },
        body: JSON.stringify(credentials)
    });
    const json = await response.json()
    console.log(json);
    if (json.success){ 
        setCredentials({username: "", password: ""})
        //localStorage.setItem('token', json.authtoken); 
        console.log(document.cookie)
        //navigate('/Dashboard');        
    }
    else{
        setLoginError(json.userFriendlyMessage)
    }
  }

  const onLoginChange = (e) =>{
    setCredentials({...credentials,[e.target.name]:e.target.value})
  }
  
  return (
    <div className='Home'>
        <div ref={componentRef} className="container-fluid animated-component formComponent">
        <div className="row h-200 justify-content-center align-items-center">
        <div className="col-md-4 col-sm-6 col-12 card p-4 bg-white">
            <h4>Restaurant QR Menu</h4>
            <h3 className="mb-3">Login</h3>
            {loginError && <span className='text-danger'>{loginError}</span>}
            <form onSubmit={onLoginSubmit}>
                <div className="mb-3">
                    <label htmlFor="username" className="form-label">Username</label>
                    <input type="email" className="form-control" required name="username" id="username" onChange={onLoginChange} style={{backgroundColor:"#F1F1F1"}} value={credentials.username}/>
                </div>
                <div className="mb-3">
                    <label htmlFor="password" className="form-label">Password</label>
                    <input type="password" className="form-control" required name="password" id="password" onChange={onLoginChange} style={{backgroundColor:"#F1F1F1"}} value={credentials.password}/>
                </div>
                <button type="submit" className="btn btn-danger rounded">Login</button>
            </form> 
            <p className='my-4'>New User? <Link to='/Register' style={{color:"red"}}>Create Account</Link></p>
        </div>
    </div>
    </div>
    </div>

  )
}

export default Login