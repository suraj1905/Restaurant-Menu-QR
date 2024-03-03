import React from 'react'
import { Link } from 'react-router-dom'

function Business() {
  let Business = {
    id: 1,
    name:"Jashn-e-bengal",
    description: "An authentic bengali cuisine restaurant",
    addressLine: "Bandel More, Hooghly",
    pinCode:"712103",
    isOpen: "true",
    imageUrl:"https://i.ytimg.com/vi/WH4TUkPMIXI/sddefault.jpg",
  }

  return (
    <div className='m-2'>
        <div class="row row-cols-1 row-cols-md-4 g-2">
            <div class="col">
            <div className="card">
                <img src={Business.imageUrl} height={300} className="card-img-top" alt="..."/>
                <div className="card-body">
                    <h5 className="card-title">{Business.name}</h5>
                    <span className="card-text">{Business.description}</span><br/>
                    <small>{Business.addressLine}, {Business.pinCode}</small>
                    <button  class="btn btn-danger mt-2">View Menu</button>
                    <Link to='/EditMenu' class="btn btn-danger mt-2 ms-2">Edit Menu</Link>
                </div>
                </div>
            </div>
            </div>
    </div>
  )
}

export default Business