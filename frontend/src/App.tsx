import React from 'react';
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { UserView } from './components/UserView/UserView';
import { ManagerView } from './components/ManagerView/ManagerView';
import { Register } from './components/Login/Register';
import { Login } from './components/Login/Login';


function App() {
  return (
    <div className="App">
      <BrowserRouter>
          <Routes>
              <Route path="" element={<Login/>}/>
              <Route path="/users" element={<UserView/>}/>
              <Route path="/managers" element={<ManagerView/>}/>
              <Route path="/register" element={<Register/>}/>
          </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
