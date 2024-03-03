import React, {useState, useRef, useEffect} from 'react'
import './Login.css'
import { Link , useNavigate} from 'react-router-dom';


export default function Registration() {
    const [registrationData, setRegistrationData] = useState({name:"",email:"",mobileNumber:"",password:""})
    const [registrationError, setRegistrationError] = useState(null)
    let navigate = useNavigate();
    const componentRef = useRef(null);

    useEffect(() => {
        removeAnimation()
    }, []);

    const removeAnimation = () =>{
        setTimeout(()=>{
            componentRef.current.classList.remove('animated-component'); // Remove after animation
        },500)
    }

    const onRegistrationSubmit = async (e) =>{
        e.preventDefault();
        const response = await fetch(process.env.HOST_URL+"/Client/Register", {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
              },
            body: JSON.stringify(registrationData)
        });
        const json = await response.json()
        console.log(json);
        if (json.success){ 
            setRegistrationData({name:"",email:"",mobileNumber:"",password:""})
            navigate("/");
        }
        else{
            setRegistrationError(json.userFriendlyMessage)
        }
      }

      const onRegisterChange = (e) =>{
        setRegistrationData({...registrationData,[e.target.name]:e.target.value})
      }
    
    
    return (
        <div className='Home'>
            <div ref={componentRef} className="container-fluid animated-component formComponent">
            <div className="row h-200 justify-content-center align-items-center">
            <div className="col-md-4 col-sm-6 col-12 card p-4 bg-white">
                <h4>Restaurant QR Menu</h4>
                <h3 className="mb-3">Register</h3>
                {registrationError && <span className='text-danger'>{registrationError}</span>}
                <form onSubmit={onRegistrationSubmit}>
                    <div className="mb-3">
                        <label htmlFor="name" className="form-label">Name</label>
                        <input type="text" className="form-control" name="name" id="name" onChange={onRegisterChange} style={{backgroundColor:"#F1F1F1"}} value={registrationData.name}/>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="email" className="form-label">Email</label>
                        <input type="email" className="form-control" name="email" id="email"  onChange={onRegisterChange} style={{backgroundColor:"#F1F1F1"}} value={registrationData.email}/>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="mobileNumber" className="form-label">Mobile Number</label>
                        <input type="number" className="form-control" name = "mobileNumber" id="mobileNumber" onChange={onRegisterChange} style={{backgroundColor:"#F1F1F1"}} value={registrationData.mobileNumber}/>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="password" className="form-label">Password</label>
                        <input type="password" className="form-control" name="password" id="password"  onChange={onRegisterChange} style={{backgroundColor:"#F1F1F1"}} value={registrationData.password}/>
                    </div>
                    <button type="submit" className="btn btn-danger rounded">Sign in</button>
                </form>
                <p className='my-4'>Existing User? <Link to='/Login' style={{color:"red"}}>Login</Link></p>
            </div>
            </div>
        </div>
        </div>
  )
}
