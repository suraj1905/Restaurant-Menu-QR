import React,{useState} from 'react'
import AddItem from './AddItem'
import ItemCategory from './ItemCategory';

export default function Menu() {
    const [ItemCategoryModalShow, setItemCategoryModalShow] = useState(false);
    const [AddModalShow, setAddModalShow] = useState(false);

    const handleClose = () => {
        setAddModalShow(false);
        setItemCategoryModalShow(false)
    }
    const ItemCategoryShow =() => setItemCategoryModalShow(true);
    const handleShow = () => setAddModalShow(true);
  return (
    <div className='container'>
        <div className='row'>
            <button onClick={handleShow} className='col-md-2 my-2  btn btn-danger'>Add Item</button>
            <h2 className='col-md-8 text-center mt-2'>Menu</h2>
            <button onClick={ItemCategoryShow} className='col-md-2 my-2  btn btn-danger'>Add Item Category</button>
        </div>
        <table className="table table-striped table-hover">
        <thead >
            <tr>
            <th scope="col">Image</th>
            <th scope="col">Name</th>
            <th scope="col">Category</th>
            <th scope="col">Price</th>
            <th scope="col">Quantity</th>
            <th scope="col">Status</th>
            <th scope="col">Action</th>
            </tr>
        </thead>
        <tbody className="table-group-divider">
            <tr>
            <td><img className='rounded' height={80} width={80} src='https://www.veganricha.com/wp-content/uploads/2022/03/Chili-Garlic-Noodles-0999-500x500.jpg'alt=''/></td>
            <td>Noodles</td>
            <td>Chinese</td>
            <td>₹230</td>
            <td>100gm</td>
            <td>Active</td>
            <td>
                <div onClick={handleShow}><i className="bi bi-pencil-square"></i></div>
                <div onClick={()=>{console.log("delete")}}><i className="bi bi-trash-fill"></i></div>
            </td>
            </tr>
        </tbody>
        </table>
        <AddItem show={AddModalShow} hideModal={handleClose}/>
        <ItemCategory show={ItemCategoryModalShow} hideModal={handleClose}/>
    </div>
  )
}
