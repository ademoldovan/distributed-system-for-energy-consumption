import "./home.css";
import Grid from '@mui/material/Grid';
import { Button } from "@mui/material";
import { BsFacebook, BsInstagram, BsLinkedin, BsTwitter } from 'react-icons/bs';
import { useHistory } from "react-router";

export const Home = (): JSX.Element => {
  const history = useHistory();
  return (
    <div>
      <div className="head">       
        <div className="titlu" style={{paddingTop: "30px"}}>
          <text>Energy Utility Platform</text>
        </div>
        <div className="menu" style={{paddingRight: "20px", paddingTop: "30px"}}>
          <Grid item><Button variant="text" style={{color: 'black'}} onClick={() => {
              history.push("/Home")
              }}>Home</Button></Grid>
          <Grid item><Button variant="text" style={{color: 'black'}} onClick={() => {
              history.push("/LogIn")
              }}>Log in</Button></Grid>
          <Grid item><Button variant="text" style={{color: 'black'}} onClick={() => {
              history.push("/SignUp")
              }}>Sign up</Button></Grid>
          <Grid item><Button variant="text" style={{color: 'black'}}>Contact</Button></Grid>
        </div>
      </div>
      <div className="body">
        <h3 className="useEnergy">USE ENERGY EFFICIENTLY</h3>
      </div>
      <div className="footer">
        <div style={{fontSize: "30px"}}><BsFacebook/></div>
        <div style={{fontSize: "30px"}}><BsInstagram/></div>
        <div style={{fontSize: "30px"}}><BsTwitter/></div>
        <div style={{fontSize: "30px"}}><BsLinkedin/></div>
      </div>
    </div>
  );
};