import React,{useState} from 'react'
import AddBusiness from '../Business/AddBusiness';

function Navbar() {
    const [show, setAddModalShow] = useState(false);

    const handleClose = () => {
        setAddModalShow(false);
    }
    const handleShow =() => setAddModalShow(true);
  return (
    <>
    <nav className="navbar navbar-expand-lg bg-danger">
    <div className="container-fluid">
        <a className="navbar-brand text-light" href='/Dashboard'>Restaurant QR Menu</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
        <div class="navbar-nav">
            <strong className="nav-link active text-light navText" aria-current="page" onClick={handleShow}>Add Business</strong>
        </div>
        </div>
        <form className="d-flex" role="search">
        <img className="rounded-circle shadow-4-strong" height={40} width={40} alt="avatar2" src="https://mdbcdn.b-cdn.net/img/new/avatars/1.webp" />
        </form>
    </div>
    </nav>
    <AddBusiness show={show} hideModal={handleClose}/>
    </>
  )
}

export default Navbar