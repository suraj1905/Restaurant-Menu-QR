import React from 'react'
import { useLocation } from 'react-router-dom';
import Login from './Authentication/Login';
import Registration from './Authentication/Registration';

export default function Home() {
  let location = useLocation();

  return (
    <div className='Home'>
    {location.pathname === '/Register' ?
    <Registration/>:<Login/>}
    </div>
  )
}
