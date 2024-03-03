import React, { useState } from 'react'
import BusinessContext from './BusinessContext';

export default function BusinessState(props) {
    const [data, setData] = useState([])

    // Get all Notes
  const getBusiness = async () => {
    // API Call 
    const response = await fetch(process.env.HOST_URL+'/Business/GetClientBusiness', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        "auth-token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoiNjEzMWRjNWUzZTQwMzdjZDQ3MzRhMDY2In0sImlhdCI6MTYzMDY2OTU5Nn0.hJS0hx6I7ROugkqjL2CjrJuefA3pJi-IU5yGUbRHI4Q"
      }
    });
    const json = await response.json() 
    if(json.success){
        setData(json)
    }
  }

  return (
    <BusinessContext.Provider value={{ data , getBusiness }}>
      {props.children}
    </BusinessContext.Provider>
  )
}
