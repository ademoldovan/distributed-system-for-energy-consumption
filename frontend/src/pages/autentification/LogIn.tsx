import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import Link from "@mui/material/Link";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { useState } from "react";
import { useHistory } from "react-router-dom";
import axiosInstance from "../../configure/axiosInstance";

const theme = createTheme();

export const LogIn = (): JSX.Element => {
  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const history = useHistory();

  const handleChange = (event: any): void => {
    const name = event.target.name;
    const value = event.target.value;
    name === "Username" ? setUsername(value) : setPassword(value);
  };

  const handleSubmit = (): void => {
    sessionStorage.setItem("username", username);
    sessionStorage.setItem("password", password);
    sessionStorage.setItem("selectedRadioButton", "client");
    sessionStorage.setItem("selectedCrudOperation", "create");
    axiosInstance.post("/User/LogIn", {
      username: username,
      password: password,
    });
    axiosInstance.get("/User/LogInResponse").then((response) => {
      if (response.data === "admin") {
        history.push("/Admin");
      }
      if (response.data === "client") {
        history.push("/Client");
      }
      if (response.data === "null") {
        history.push("/");
      }     
      // if (response.data === "admin") {
      //   Auth.login(() => {
      //     history.push("/Admin");
      //   })
      // }
      // if (response.data === "client") {
      //   Auth.login(() => {
      //     history.push("/Client");
      // })
      // }
      // if (response.data === "null") {
      //   Auth.login(() => {
      //     history.push("/");
      // })
      // }
    });
  };

  return (
    <ThemeProvider theme={theme}>
      <div className="head" style={{ backgroundColor: "#cccccc", height: "75px" }}>
        <div className="titlu" style={{ paddingTop: "30px" }}>
          <text>Energy Utility Platform</text> 
        </div>
        <div
          className="menu"
          style={{ paddingRight: "20px", paddingTop: "30px" }}
        >
          <Grid item>
            <Button
              variant="text"
              style={{ color: "black" }}
              onClick={() => {
                history.push("/Home");
              }}
            >
              Home
            </Button>
          </Grid>
          <Grid item>
            <Button
              variant="text"
              style={{ color: "black" }}
              onClick={() => {
                history.push("/LogIn");
              }}
            >
              Log in
            </Button>
          </Grid>
          <Grid item>
            <Button
              variant="text"
              style={{ color: "black" }}
              onClick={() => {
                history.push("/SignUp");
              }}
            >
              Sign up
            </Button>
          </Grid>
          <Grid item>
            <Button variant="text" style={{ color: "black" }}>
              Contact
            </Button>
          </Grid>
        </div>
      </div>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Sign in
          </Typography>
          <Box
            component="form"
            noValidate
            sx={{ mt: 1 }}
          >
            <TextField
              margin="normal"
              required
              fullWidth
              id="email"
              label="Email Address"
              name="Username"
              onChange={handleChange}
              autoComplete="email"
              autoFocus
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="Password"
              onChange={handleChange}
              label="Password"
              type="password"
              id="password"
              autoComplete="current-password"
            />
            <Button
              type="button"
              fullWidth
              variant="contained"
              onClick={handleSubmit}
              sx={{ mt: 3, mb: 2 }}
            >
              Sign In
            </Button>
            <Grid container>
              <Grid item>
                <Link href="/SignUp" variant="body2">
                  {"Don't have an account? Sign Up"}
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
};