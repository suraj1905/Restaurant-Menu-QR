import React from 'react'
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';

export default function AddItem(props) {
    const {show, hideModal} = props
    return (
      <>
        <Modal show={show} onHide={hideModal}>
          <Modal.Header closeButton>
            <Modal.Title>Add Item</Modal.Title>
          </Modal.Header>
          <Modal.Body>
           <form>
           <div className="mb-3">
            <label for="exampleFormControlInput1" className="form-label">Name</label>
            <input type="text" className="form-control" id="exampleFormControlInput1" placeholder=""/>
          </div>
          <div className="mb-3">
            <label for="exampleFormControlInput1" className="form-label">Description</label>
            <input type="text" className="form-control" id="exampleFormControlInput1" />
          </div>
          <div className="mb-3">
            <label for="exampleFormControlInput1" className="form-label">Price</label>
            <input type="number" className="form-control" id="exampleFormControlInput1" />
          </div>
          <div className="mb-3">
            <label for="exampleFormControlInput1" className="form-label">Quantity</label>
            <input type="text" className="form-control" id="exampleFormControlInput1" />
          </div>
          <div className="mb-3">
            <label for="exampleFormControlInput1" className="form-label">Image</label>
            <input type="file" className="form-control" id="exampleFormControlInput1" />
          </div>
           </form>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={hideModal}>
              Close
            </Button>
            <Button variant="danger" onClick={hideModal}>
              Save Changes
            </Button>
          </Modal.Footer>
        </Modal>
      </>
    )
}
