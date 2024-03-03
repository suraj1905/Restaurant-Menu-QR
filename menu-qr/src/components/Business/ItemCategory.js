import React from 'react'
import Modal from 'react-bootstrap/Modal';

export default function ItemCategory(props) {
    const {show, hideModal} = props
  return (
    <>
        <Modal show={show} onHide={hideModal}>
          <Modal.Header closeButton>
            <Modal.Title>Item Category</Modal.Title>
          </Modal.Header>
          <Modal.Body>
          <form className="row g-3">
            <div className="col-auto">
                <input type="text" className="form-control" id="itemCategoryName" name='itemCategoryName' placeholder="Item Category name"/>
            </div>
            <div className="col-auto">
                <button type="submit" className="btn btn-danger mb-3">Add</button>
            </div>
          </form>
          <table className="table table-striped table-hover">
        <thead >
            <tr>
            <th scope="col">Name</th>
            <th scope="col">Action</th>
            </tr>
        </thead>
        <tbody className="table-group-divider">
            <tr>
            <td>Noodles</td>
            <td>
                <i className="bi bi-pencil-square"></i>
                <i className="bi bi-trash-fill ms-2"></i>
            </td>
            </tr>
        </tbody>
        </table>
          </Modal.Body>
        </Modal>
      </>
  )
}
