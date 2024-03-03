import './App.css';
import Home from './components/Home';
import Login from './components/Authentication/Login';
import {
  BrowserRouter as Router,
  Route,
  Routes,
} from "react-router-dom";
import Registration from './components/Authentication/Registration';
import Dashboard from './components/Dashboard/Dashboard';
import Menu from './components/Business/Menu';
import Navbar from './components/Dashboard/Navbar';

function App() {
  return (
    <Router>
      <Navbar/>
      <Routes>
        <Route key="Register" exact path="/Register" element={
            <Registration/>}/>
        <Route key="Login" exact path="/Login" element={
            <Login/>}/>
        <Route key="Home" exact path="/" element={
            <Home/>}/>
        <Route key="Dashboard" exact path="/Dashboard" element={
            <Dashboard/>}/>
        <Route key="EditMenu" exact path="/EditMenu" element={
            <Menu/>}/>
      </Routes>
    </Router>
  );
}

export default App;
