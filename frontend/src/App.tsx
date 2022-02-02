import { BrowserRouter,Redirect,Route,Switch } from 'react-router-dom';
import { Admin } from './pages/admin/Admin';
import { Home } from './pages/home/Home';
import { Client } from './pages/client/Client';
import { LogIn } from './pages/autentification/LogIn';
import { SignUp } from './pages/autentification/SignUp';
import './App.css';

function App() {
  return (
      <BrowserRouter>
        <Switch>
          <Route path="/Home"><Home/></Route>
          <Route path="/SignUp"><SignUp/></Route>
          <Route path="/LogIn"><LogIn/></Route>
          <Route path="/Client"><Client/></Route>
          <Route path="/Admin"><Admin/></Route>
          <Route><Redirect to="/Home"/></Route>
        </Switch>
      </BrowserRouter>
  );
}

export default App;
