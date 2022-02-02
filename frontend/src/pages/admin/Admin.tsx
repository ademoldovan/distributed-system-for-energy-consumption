import {
  Autocomplete,
  Box,
  Button,
  Container,
  CssBaseline,
  FormControl,
  FormControlLabel,
  Grid,
  Radio,
  RadioGroup,
  TextField,
} from "@mui/material";
import { Label } from "@fluentui/react/lib/Label";
import "./admin.css";
import React, { useEffect, useState } from "react";
import LocalizationProvider from "@mui/lab/LocalizationProvider";
import DatePicker from "@mui/lab/DatePicker";
import AdapterDateFns from "@mui/lab/AdapterDateFns";
import axiosInstance from "../../configure/axiosInstance";
import { GridColDef } from "@mui/x-data-grid";
import { useHistory } from "react-router-dom";
import {
  AutocompleteClientOption,
  AutocompleteOption,
  Data,
  IClient,
  IDevice,
  ISensor,
} from "./admin.types";
import { DataGrid } from "@mui/x-data-grid";

export const Admin = (): JSX.Element => {
  const [value, setValue] = useState<string>("");
  const [crud, setCrud] = useState<string>("");
  const [clients, setClients] = useState<IClient[]>([]);
  const [devices, setDevices] = useState<IDevice[]>([]);
  const [sensors, setSensors] = useState<ISensor[]>([]);
  const [availableSensors, setAvailableSensors] = useState<ISensor[]>([]);
  const [rows, setRows] = useState<Data[]>([]);
  const [sensorRows, setSensorRows] = useState<ISensor[]>([]);
  const [deviceRows, setDeviceRows] = useState<IDevice[]>([]);
  const [sensorsDesc, setSensorsDesc] = useState<AutocompleteOption[]>([]);
  const [clientsList, setClientsList] = useState<AutocompleteClientOption[]>([]);
  const history = useHistory();

  const mapToRowClients = (client: IClient): Data => {
    return {
      id: client.id,
      firstName: client.firstName,
      lastName: client.lastName,
      address: client.address,
      emailAddress: client.email,
      birthDate: client.birthDate?.toString(),
    };
  };
  const mapToRowSensors = (sensor: ISensor): ISensor => {
    return {
      id: sensor.id,
      description: sensor.description,
      maximum_value: sensor.maximum_value,
    };
  };
  const mapToRowDevices = (device: IDevice): IDevice => {
    return {
      id: device.id,
      address: device.address,
      average: device.average,
      description: device.description,
      maximum_energy: device.maximum_energy,
      sensor_id: device.sensor_id,
      client_id: device.client_id,
    };
  };
  const mapToRowSensorsDesc = (sensor: ISensor): AutocompleteOption => {
    return {
      label: sensor.description,
      id: sensor.id,
    };
  };
  const mapToRowClientsList = (client: IClient): AutocompleteClientOption => {
    return {
      label: client.firstName + " " + client.lastName,
      id: client.id,
    };
  };
  useEffect(() => {
    const newRows: AutocompleteClientOption[] = clients.map((client: IClient) =>
      mapToRowClientsList(client)
    );
    setClientsList(newRows);
  }, [clients]);
  useEffect(() => {
    const newRows: AutocompleteOption[] = availableSensors.map((sensor: ISensor) =>
      mapToRowSensorsDesc(sensor)
    );
    setSensorsDesc(newRows);
  }, [sensors, availableSensors]);
  useEffect(() => {
    axiosInstance.get("/Device/GetAvailableSensors").then((response: any) => {
      setAvailableSensors(response.data);
    });
  }, []);
  useEffect(() => {
    axiosInstance.get("/Client/getClients").then((response: any) => {
      setClients(response.data);
    });
  }, []);
  useEffect(() => {
    axiosInstance.get("/Device/getDevices").then((response: any) => {
      setDevices(response.data);
    });
  }, []);
  useEffect(() => {
    axiosInstance.get("/Sensor/getSensors").then((response: any) => {
      setSensors(response.data);
    });
  }, []);
  useEffect(() => {
    const newRows: Data[] = clients.map((client: IClient) =>
      mapToRowClients(client)
    );
    setRows(newRows);
  }, [clients]);
  useEffect(() => {
    const newRows: ISensor[] = sensors.map((sensor: ISensor) =>
      mapToRowSensors(sensor)
    );
    setSensorRows(newRows);
  }, [sensors]);
  useEffect(() => {
    const newRows: IDevice[] = devices.map((device: IDevice) =>
      mapToRowDevices(device)
    );
    setDeviceRows(newRows);
  }, [devices]);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    sessionStorage.setItem("selectedRadioButton",(event.target as HTMLInputElement).value);
    setValue((event.target as HTMLInputElement).value);
  };
  const handleButton = (event: any) => {
    sessionStorage.setItem("selectedCrudOperation",(event.target as HTMLInputElement).value);
    setCrud((event.target as HTMLInputElement).value);
  };

  const refreshClients = () => {
    axiosInstance.get("/Client/getClients").then((response: any) => {
      setClients(response.data);
    });
    const newRows: Data[] = clients.map((client: IClient) =>
      mapToRowClients(client)
    );
    setRows(newRows);
  };
  const refreshDevices = () => {
    axiosInstance.get("/Device/getDevices").then((response: any) => {
      setDevices(response.data);
    });
    const newRows: IDevice[] = devices.map((device: IDevice) =>
      mapToRowDevices(device)
    );
    setDeviceRows(newRows);
  };
  const refreshSensors = () => {
    axiosInstance.get("/Sensor/getSensors").then((response: any) => {
      setSensors(response.data);
    });
    const newRows: ISensor[] = sensors.map((sensor: ISensor) =>
      mapToRowSensors(sensor)
    );
    setSensorRows(newRows);
  };

  const [id, setId] = useState<Number>();
  const [firstName, setFirstName] = useState<String>("");
  const [lastName, setLastName] = useState<String>("");
  const [address, setAddress] = useState<String>("");
  const [birthDate, setBirthDate] = useState<Date | null>(null);
  const [emailAddress, setEmailAddress] = useState<String>("");
  const [password, setPassword] = useState<String>("");

  const handleChangeOnCreateClient = (event: any) => {
    const name = event.target.name;
    const value = event.target.value;
    if (name === "firstName") {
      setFirstName(value);
    }
    if (name === "lastName") {
      setLastName(value);
    }
    if (name === "address") {
      setAddress(value);
    }
    if (name === "birthDate") {
      setBirthDate(value);
    }
    if (name === "email") {
      setEmailAddress(value);
    }
    if (name === "password") {
      setPassword(value);
    }
  };
  const handleCreateClient = (event: any) => {
    axiosInstance.post("/User/CreateUser", {
      lastName: lastName,
      firstName: firstName,
      birthDate: birthDate,
      address: address,
      email: emailAddress,
      password: password,
    });
    axiosInstance.post("/Client/CreateClient", {
      lastName: lastName,
      firstName: firstName,
      birthDate: birthDate,
      address: address,
      email: emailAddress,
      password: password,
    });
    refreshClients();
  };
  const createClient = (): JSX.Element => {
    return (
      <div style={{ position: "relative", left: "50px", bottom: "30px" }}>
        <Container component="main" maxWidth="xs">
          <CssBaseline />
          <Box
            sx={{
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
            }}
          >
            <Box component="form" noValidate sx={{ mt: 3 }}>
              <Grid container spacing={2}>
                <Grid item xs={12} sm={6}>
                  <TextField
                    autoComplete="given-name"
                    name="firstName"
                    required
                    fullWidth
                    id="firstName"
                    label="First Name"
                    autoFocus
                    onChange={handleChangeOnCreateClient}
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <TextField
                    required
                    fullWidth
                    id="lastName"
                    label="Last Name"
                    name="lastName"
                    autoComplete="family-name"
                    onChange={handleChangeOnCreateClient}
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    required
                    fullWidth
                    id="address"
                    label="Address"
                    name="address"
                    autoComplete="address"
                    onChange={handleChangeOnCreateClient}
                  />
                </Grid>
                <Grid item xs={12}>
                  <LocalizationProvider
                    name="birthDate"
                    dateAdapter={AdapterDateFns}
                  >
                    <DatePicker
                      label="Birth Date *"
                      value={birthDate}
                      onChange={(newValue) => {
                        setBirthDate(newValue);
                      }}
                      renderInput={(params) => <TextField {...params} />}
                    />
                  </LocalizationProvider>
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    required
                    fullWidth
                    id="email"
                    label="Email Address"
                    name="email"
                    autoComplete="email"
                    onChange={handleChangeOnCreateClient}
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    required
                    fullWidth
                    name="password"
                    label="Password"
                    type="password"
                    id="password"
                    autoComplete="new-password"
                    onChange={handleChangeOnCreateClient}
                  />
                </Grid>
              </Grid>
              <Button
                type="button"
                fullWidth
                variant="outlined"
                sx={{ mt: 3, mb: 2, color: "#4c5c68", borderColor: "#4c5c68" }}
                onClick={handleCreateClient}
              >
                Create Client
              </Button>
            </Box>
          </Box>
        </Container>
      </div>
    );
  };
  const readClient = (): JSX.Element => {
    const columns: GridColDef[] = [
      { field: "id", headerName: "ID", width: 100 },
      { field: "firstName", headerName: "First name", width: 140 },
      { field: "lastName", headerName: "Last name", width: 140 },
      {
        field: "birthDate",
        headerName: "Birth date",
        type: "date",
        width: 230,
      },
      { field: "address", headerName: "Address", width: 190 },
      { field: "emailAddress", headerName: "Email address", width: 210 },
    ];
    return (
      <div
        style={{
          height: 400,
          width: 1020,
          paddingLeft: "60px",
          paddingBottom: "40px",
        }}
      >
        <DataGrid
          rows={rows}
          columns={columns}
          pageSize={5}
          rowsPerPageOptions={[5]}
          checkboxSelection={false}
        />
      </div>
    );
  };
  const handleUpdateClient = (event: any) => {
    axiosInstance.post("/Client/UpdateClient", {
      id: id,
      lastName: lastName,
      firstName: firstName,
      birthDate: birthDate,
      address: address,
      // email: emailAddress,
      // password: password,
    });
    refreshClients();
  };
  const updateClient = (): JSX.Element => {
    const columns: GridColDef[] = [
      {
        field: "action",
        headerName: "Action",
        sortable: false,
        renderCell: (params) => {
          const onUpdate = () => {
            setId(params.row.id.valueOf());
            setFirstName(params.row.firstName.valueOf());
            setLastName(params.row.lastName.valueOf());
            setAddress(params.row.address.valueOf());
            setBirthDate(params.row.birthDate.valueOf());
            setEmailAddress(params.row.emailAddress.valueOf());
          };
          return <Button onClick={onUpdate}>Update</Button>;
        },
      },
      { field: "id", headerName: "ID", width: 40 },
      { field: "firstName", headerName: "First name", width: 120 },
      { field: "lastName", headerName: "Last name", width: 120 },
      {
        field: "birthDate",
        headerName: "Birth date",
        type: "date",
        width: 160,
      },
      { field: "address", headerName: "Address", width: 110 },
      { field: "emailAddress", headerName: "Email address", width: 190 },
    ];
    return (
      <div className="update">
        <div
          style={{
            height: 400,
            width: 800,
            paddingLeft: "50px",
            paddingBottom: "40px",
          }}
        >
          <DataGrid
            rows={rows}
            columns={columns}
            pageSize={5}
            rowsPerPageOptions={[5]}
            checkboxSelection={false}
          />
        </div>
        <div style={{ position: "relative", left: "50px", bottom: "30px" }}>
          <Container component="main" maxWidth="xs">
            <CssBaseline />
            <Box
              sx={{
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
              }}
            >
              <Box component="form" noValidate sx={{ mt: 3 }}>
                <Grid container spacing={2}>
                  <Grid item xs={12} sm={6}>
                    <TextField
                      autoComplete="given-name"
                      name="firstName"
                      required
                      fullWidth
                      id="firstName"
                      label="First Name"
                      value={firstName}
                      autoFocus
                      onChange={handleChangeOnCreateClient}
                    />
                  </Grid>
                  <Grid item xs={12} sm={6}>
                    <TextField
                      required
                      fullWidth
                      id="lastName"
                      label="Last Name"
                      value={lastName}
                      name="lastName"
                      autoComplete="family-name"
                      onChange={handleChangeOnCreateClient}
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <TextField
                      required
                      fullWidth
                      id="address"
                      label="Address"
                      value={address}
                      name="address"
                      autoComplete="address"
                      onChange={handleChangeOnCreateClient}
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <LocalizationProvider
                      name="birthDate"
                      dateAdapter={AdapterDateFns}
                    >
                      <DatePicker
                        label="Birth Date *"
                        value={birthDate}
                        onChange={(newValue) => {
                          setBirthDate(newValue);
                        }}
                        renderInput={(params) => <TextField {...params} />}
                      />
                    </LocalizationProvider>
                  </Grid>
                  {/* <Grid item xs={12}>
                    <TextField
                      required
                      fullWidth
                      id="email"
                      label="Email Address"
                      value={emailAddress}
                      name="email"
                      autoComplete="email"
                      onChange={handleChangeOnUpdateClient}
                    />
                  </Grid> */}
                </Grid>
                <Button
                  type="button"
                  fullWidth
                  variant="outlined"
                  sx={{
                    mt: 3,
                    mb: 2,
                    color: "#4c5c68",
                    borderColor: "#4c5c68",
                  }}
                  onClick={handleUpdateClient}
                >
                  Update Client
                </Button>
              </Box>
            </Box>
          </Container>
        </div>
      </div>
    );
  };
  const deleteClient = (): JSX.Element => {
    const columns: GridColDef[] = [
      { field: "id", headerName: "ID", width: 70 },
      { field: "firstName", headerName: "First name", width: 140 },
      { field: "lastName", headerName: "Last name", width: 140 },
      {
        field: "birthDate",
        headerName: "Birth date",
        type: "date",
        width: 190,
      },
      { field: "address", headerName: "Address", width: 140 },
      { field: "emailAddress", headerName: "Email address", width: 190 },
      {
        field: "action",
        headerName: "Action",
        sortable: false,
        renderCell: (params) => {
          const onDelete = () => {
            axiosInstance.post("/Client/DeleteClient", {
              id: params.row.id.valueOf(),
            });
            refreshClients();
          };
          return <Button onClick={onDelete}>Delete</Button>;
        },
      },
    ];
    return (
      <div
        style={{
          height: 400,
          width: 1000,
          paddingLeft: "50px",
          paddingBottom: "40px",
        }}
      >
        <DataGrid
          rows={rows}
          columns={columns}
          pageSize={5}
          rowsPerPageOptions={[5]}
          checkboxSelection={false}
        />
      </div>
    );
  };

  const [updateDeviceId, setUpdateDeviceId] = useState<Number>();
  const [deviceAddress, setDeviceAddress] = useState<String>("");
  const [deviceDescription, setDeviceDescription] = useState<String>("");
  const [maximumEnergy, setMaximumEnergy] = useState<Number>();
  const [averageEnergy, setAverageEnergy] = useState<Number>();
  const [sensorId, setSensorId] = useState<AutocompleteOption>();
  const [clientId, setClientId] = useState<AutocompleteClientOption>();

  const handleChangeOnCreateDevice = (event: any) => {
    const name = event.target.name;
    const value = event.target.value;
    if (name === "deviceAddress") {
      setDeviceAddress(value);
    }
    if (name === "deviceDescription") {
      setDeviceDescription(value);
    }
    if (name === "maximumEnergy") {
      setMaximumEnergy(value);
    }
    if (name === "averageEnergy") {
      setAverageEnergy(value);
    }
  };
  const handleCreateDevice = (event: any) => {
    axiosInstance.post("/Device/CreateDevice", {
      address: deviceAddress,
      description: deviceDescription,
      maximum_energy: maximumEnergy,
      average: averageEnergy,
      sensor: sensorId?.id,
      client: clientId?.id,
    });
    refreshDevices();
  };
  const createDevice = (): JSX.Element => {
    return (
      <div style={{ position: "relative", left: "50px", bottom: "30px" }}>
        <Container component="main" maxWidth="xs">
          <CssBaseline />
          <Box
            sx={{
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
            }}
          >
            <Box component="form" noValidate sx={{ mt: 3 }}>
              <Grid container spacing={2}>
                <Grid item xs={12}>
                  <TextField
                    name="deviceAddress"
                    required
                    fullWidth
                    onChange={handleChangeOnCreateDevice}
                    id="deviceAddress"
                    label="Address"
                    autoFocus
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    required
                    fullWidth
                    id="deviceDescription"
                    label="Description"
                    name="deviceDescription"
                    onChange={handleChangeOnCreateDevice}
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    required
                    fullWidth
                    id="maximumEnergy"
                    label="Maximum energy consumption"
                    name="maximumEnergy"
                    onChange={handleChangeOnCreateDevice}
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    required
                    fullWidth
                    id="averageEnergy"
                    label="Average energy consumption"
                    name="averageEnergy"
                    onChange={handleChangeOnCreateDevice}
                  />
                </Grid>
                <Grid item xs={12}>
                  <Autocomplete
                    disablePortal
                    id="sensorId"
                    options={sensorsDesc}
                    onChange={(event, value) => {
                      if (value != null) setSensorId(value);
                    }}
                    renderInput={(params) => (
                      <TextField {...params} label="Sensor" />
                    )}
                  />
                </Grid>
                <Grid item xs={12}>
                  <Autocomplete
                    disablePortal
                    id="clientId"
                    options={clientsList}
                    onChange={(event, value) => {
                      if (value != null) setClientId(value);
                    }}
                    renderInput={(params) => (
                      <TextField {...params} label="Client" />
                    )}
                  />
                </Grid>
              </Grid>
              <Button
                type="button"
                fullWidth
                variant="outlined"
                onClick={handleCreateDevice}
                sx={{ mt: 3, mb: 2, color: "#4c5c68", borderColor: "#4c5c68" }}
              >
                Create Device
              </Button>
            </Box>
          </Box>
        </Container>
      </div>
    );
  };
  const readDevice = (): JSX.Element => {
    const columns: GridColDef[] = [
      { field: "id", headerName: "ID", width: 100 },
      { field: "address", headerName: "Address", width: 130 },
      {
        field: "average",
        headerName: "Average energy consumption",
        width: 250,
      },
      { field: "description", headerName: "Device description", width: 260 },
      { field: "maximum_energy", headerName: "Maximum energy", width: 200 },
      { field: "sensor_id", headerName: "Sensor ID", width: 100 },
      { field: "client_id", headerName: "Client ID", width: 100 },
    ];
    return (
      <div
        style={{
          height: 400,
          width: 1150,
          paddingLeft: "60px",
          paddingBottom: "40px",
        }}
      >
        <DataGrid
          rows={deviceRows}
          columns={columns}
          pageSize={5}
          rowsPerPageOptions={[5]}
          checkboxSelection={false}
        />
      </div>
    );
  };
  const handleUpdateDevice = (event: any) => {
    axiosInstance.post("/Device/UpdateDevice", {
      id: updateDeviceId,
      address: deviceAddress,
      description: deviceDescription,
      maximum_energy: maximumEnergy,
      average: averageEnergy,
      sensor: sensorId?.id,
      client: clientId?.id,
    });
    refreshDevices();
  };
  const updateDevice = (): JSX.Element => {
    const columns: GridColDef[] = [
      {
        field: "action",
        headerName: "Action",
        sortable: false,
        renderCell: (params) => {
          const onUpdate = () => {
            setUpdateDeviceId(params.row.id.valueOf());
            setDeviceAddress(params.row.address.valueOf());
            setDeviceDescription(params.row.description.valueOf());
            setMaximumEnergy(params.row.maximum_energy.valueOf());
            setAverageEnergy(params.row.average.valueOf());
            setSensorId(params.row.sensor_id.valueOf());
            setClientId(params.row.client_id.valueOf());
          };
          return <Button onClick={onUpdate}>Update</Button>;
        },
      },
      { field: "id", headerName: "ID", width: 100 },
      { field: "address", headerName: "Address", width: 130 },
      {
        field: "average",
        headerName: "Average energy consumption",
        width: 250,
      },
      { field: "description", headerName: "Device description", width: 200 },
      { field: "maximum_energy", headerName: "Maximum energy", width: 200 },
      { field: "sensor_id", headerName: "Sensor ID", width: 100 },
      { field: "client_id", headerName: "Client ID", width: 100 },
    ];
    return (
      <div className="update">
        <div
          style={{
            height: 400,
            width: 820,
            paddingLeft: "60px",
            paddingBottom: "40px",
          }}
        >
          <DataGrid
            rows={deviceRows}
            columns={columns}
            pageSize={5}
            rowsPerPageOptions={[5]}
            checkboxSelection={false}
          />
        </div>
        <div style={{ position: "relative", left: "40px", bottom: "30px" }}>
          <Container component="main" maxWidth="xs">
            <CssBaseline />
            <Box
              sx={{
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
              }}
            >
              <Box component="form" noValidate sx={{ mt: 3 }}>
                <Grid container spacing={2}>
                  <Grid item xs={12}>
                    <TextField
                      required
                      fullWidth
                      id="deviceAddress"
                      label="Address"
                      name="deviceAddress"
                      value={deviceAddress}
                      onChange={handleChangeOnCreateDevice}
                      autoFocus
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <TextField
                      required
                      fullWidth
                      id="deviceDescription"
                      label="Description"
                      name="deviceDescription"
                      value={deviceDescription}
                      onChange={handleChangeOnCreateDevice}
                      autoFocus
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <TextField
                      required
                      fullWidth
                      id="maximumEnergy"
                      label="Maximum energy consumption"
                      name="maximumEnergy"
                      value={maximumEnergy}
                      onChange={handleChangeOnCreateDevice}
                      autoFocus
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <TextField
                      required
                      fullWidth
                      id="averageEnergy"
                      label="Average energy consumption"
                      name="averageEnergy"
                      value={averageEnergy}
                      onChange={handleChangeOnCreateDevice}
                      autoFocus
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <Autocomplete
                      disablePortal
                      id="sensorId"
                      options={sensorsDesc}
                      defaultValue={sensorId}
                      getOptionLabel={(option) => option.label}
                      onChange={(event, value) => {
                        if (value != null) setSensorId(value);
                      }}
                      renderInput={(params) => (
                        <TextField {...params} label="Sensor" />
                      )}
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <Autocomplete
                      disablePortal
                      id="clientId"
                      defaultValue={clientId}
                      options={clientsList}
                      onChange={(event, value) => {
                        if (value != null) setClientId(value);
                      }}
                      renderInput={(params) => (
                        <TextField {...params} label="Client" />
                      )}
                    />
                  </Grid>
                </Grid>
                <Button
                  type="button"
                  fullWidth
                  variant="outlined"
                  onClick={handleUpdateDevice}
                  sx={{
                    mt: 3,
                    mb: 2,
                    color: "#4c5c68",
                    borderColor: "#4c5c68",
                  }}
                >
                  Update Device
                </Button>
              </Box>
            </Box>
          </Container>
        </div>
      </div>
    );
  };
  const deleteDevice = (): JSX.Element => {
    const columns: GridColDef[] = [
      { field: "id", headerName: "ID", width: 100 },
      { field: "address", headerName: "Address", width: 130 },
      {
        field: "average",
        headerName: "Average energy consumption",
        width: 250,
      },
      { field: "description", headerName: "Device description", width: 210 },
      { field: "maximum_energy", headerName: "Maximum energy", width: 200 },
      { field: "sensor_id", headerName: "Sensor ID", width: 100 },
      { field: "device_id", headerName: "Device ID", width: 100 },
      {
        field: "action",
        headerName: "Action",
        sortable: false,
        renderCell: (params) => {
          const onDelete = () => {
            console.log(params.row.id.valueOf());
            axiosInstance.post("/Device/DeleteDevice", {
              id: params.row.id.valueOf(),
            });
            refreshDevices();
          };
          return <Button onClick={onDelete}>Delete</Button>;
        },
      },
    ];
    return (
      <div
        style={{
          height: 400,
          width: 1200,
          paddingLeft: "60px",
          paddingBottom: "40px",
        }}
      >
        <DataGrid
          rows={deviceRows}
          columns={columns}
          pageSize={5}
          rowsPerPageOptions={[5]}
          checkboxSelection={false}
        />
      </div>
    );
  };

  const [updateSensorId, setUpdateSensorId] = useState<Number>();
  const [sensorDescription, setSensorDescription] = useState<String>("");
  const [sensorMaximumValue, setSensorMaximumValue] = useState<Number>();
  const handleChangeOnCreateSensor = (event: any) => {
    const name = event.target.name;
    const value = event.target.value;
    if (name === "sensorDescription") {
      setSensorDescription(value);
    }
    if (name === "sensorMaximumValue") {
      setSensorMaximumValue(value);
    }
  };
  const handleCreateSensor = (event: any) => {
    axiosInstance.post("/Sensor/CreateSensor", {
      description: sensorDescription,
      maximum_value: sensorMaximumValue,
    });
    refreshSensors();
  };
  const createSensor = (): JSX.Element => {
    return (
      <div style={{ position: "relative", left: "50px", bottom: "30px" }}>
        <Container component="main" maxWidth="xs">
          <CssBaseline />
          <Box
            sx={{
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
            }}
          >
            <Box component="form" noValidate sx={{ mt: 3 }}>
              <Grid container spacing={2}>
                <Grid item xs={12}>
                  <TextField
                    name="sensorDescription"
                    required
                    fullWidth
                    id="sensorDescription"
                    label="Sensor description"
                    onChange={handleChangeOnCreateSensor}
                    autoFocus
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    required
                    fullWidth
                    id="sensorMaximumValue"
                    label="Maximum value"
                    name="sensorMaximumValue"
                    onChange={handleChangeOnCreateSensor}
                  />
                </Grid>
              </Grid>
              <Button
                type="button"
                onClick={handleCreateSensor}
                fullWidth
                variant="outlined"
                sx={{ mt: 3, mb: 2, color: "#4c5c68", borderColor: "#4c5c68" }}
              >
                Create Sensor
              </Button>
            </Box>
          </Box>
        </Container>
      </div>
    );
  };
  const readSensor = (): JSX.Element => {
    const columns: GridColDef[] = [
      { field: "id", headerName: "ID", width: 100 },
      { field: "description", headerName: "Sensor description", width: 260 },
      { field: "maximum_value", headerName: "Maximum Value", width: 260 },
    ];
    return (
      <div
        style={{
          height: 400,
          width: 630,
          paddingLeft: "60px",
          paddingBottom: "40px",
        }}
      >
        <DataGrid
          rows={sensorRows}
          columns={columns}
          pageSize={5}
          rowsPerPageOptions={[5]}
          checkboxSelection={false}
        />
      </div>
    );
  };
  const handleUpdatesensor = (event: any) => {
    axiosInstance.post("/Sensor/UpdateSensor", {
      id: updateSensorId,
      description: sensorDescription,
      maximum_value: sensorMaximumValue,
    });
    refreshSensors();
  };
  const updateSensor = (): JSX.Element => {
    const columns: GridColDef[] = [
      {
        field: "action",
        headerName: "Action",
        sortable: false,
        renderCell: (params) => {
          const onUpdate = () => {
            setUpdateSensorId(params.row.id.valueOf());
            setSensorDescription(params.row.description.valueOf());
            setSensorMaximumValue(params.row.maximum_value.valueOf());
          };
          return <Button onClick={onUpdate}>Update</Button>;
        },
      },
      { field: "id", headerName: "ID", width: 100 },
      { field: "description", headerName: "Sensor description", width: 260 },
      { field: "maximum_value", headerName: "Maximum Value", width: 200 },
    ];
    return (
      <div className="update">
        <div
          style={{
            height: 400,
            width: 730,
            paddingLeft: "60px",
            paddingBottom: "40px",
          }}
        >
          <DataGrid
            rows={sensorRows}
            columns={columns}
            pageSize={5}
            rowsPerPageOptions={[5]}
            checkboxSelection={false}
          />
        </div>
        <div style={{ position: "relative", left: "50px", bottom: "30px" }}>
          <Container component="main" maxWidth="xs">
            <CssBaseline />
            <Box
              sx={{
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
              }}
            >
              <Box component="form" noValidate sx={{ mt: 3 }}>
                <Grid container spacing={2}>
                  <Grid item xs={12}>
                    <TextField
                      required
                      fullWidth
                      id="sensorDescription"
                      label="Sensor description"
                      name="sensorDescription"
                      value={sensorDescription}
                      onChange={handleChangeOnCreateSensor}
                      autoFocus
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <TextField
                      required
                      fullWidth
                      id="sensorMaximumValue"
                      label="Maximum value"
                      name="sensorMaximumValue"
                      value={sensorMaximumValue}
                      onChange={handleChangeOnCreateSensor}
                      autoFocus
                    />
                  </Grid>
                </Grid>
                <Button
                  type="button"
                  onClick={handleUpdatesensor}
                  fullWidth
                  variant="outlined"
                  sx={{
                    mt: 3,
                    mb: 2,
                    color: "#4c5c68",
                    borderColor: "#4c5c68",
                  }}
                >
                  Update Sensor
                </Button>
              </Box>
            </Box>
          </Container>
        </div>
      </div>
    );
  };
  const deleteSensor = (): JSX.Element => {
    const columns: GridColDef[] = [
      { field: "id", headerName: "ID", width: 100 },
      { field: "description", headerName: "Sensor description", width: 260 },
      { field: "maximum_value", headerName: "Maximum Value", width: 260 },
      {
        field: "action",
        headerName: "Action",
        sortable: false,
        renderCell: (params) => {
          const onDelete = () => {
            axiosInstance.post("/Sensor/DeleteSensor", {
              id: params.row.id.valueOf(),
            });
            refreshSensors();
          };
          return <Button onClick={onDelete}>Delete</Button>;
        },
      },
    ];
    return (
      <div
        style={{
          height: 400,
          width: 730,
          paddingLeft: "60px",
          paddingBottom: "40px",
        }}
      >
        <DataGrid
          rows={sensorRows}
          columns={columns}
          pageSize={5}
          rowsPerPageOptions={[5]}
          checkboxSelection={false}
        />
      </div>
    );
  };
  const switchOperation = (): JSX.Element => {
    //const crud = sessionStorage.getItem("selectedCrudOperation");
    if (sessionStorage.getItem("selectedRadioButton") === "client") {
      if (sessionStorage.getItem("selectedCrudOperation") === "create") return createClient();
      if (sessionStorage.getItem("selectedCrudOperation") === "read") return readClient();
      if (sessionStorage.getItem("selectedCrudOperation") === "update") return updateClient();
      if (sessionStorage.getItem("selectedCrudOperation") === "delete") return deleteClient();
    }
    if (sessionStorage.getItem("selectedRadioButton") === "device") {
      if (sessionStorage.getItem("selectedCrudOperation") === "create") return createDevice();
      if (sessionStorage.getItem("selectedCrudOperation") === "read") return readDevice();
      if (sessionStorage.getItem("selectedCrudOperation") === "update") return updateDevice();
      if (sessionStorage.getItem("selectedCrudOperation") === "delete") return deleteDevice();
    }
    if (sessionStorage.getItem("selectedRadioButton") === "sensor") {
      if (sessionStorage.getItem("selectedCrudOperation") === "create") return createSensor();
      if (sessionStorage.getItem("selectedCrudOperation") === "read") return readSensor();
      if (sessionStorage.getItem("selectedCrudOperation") === "update") return updateSensor();
      if (sessionStorage.getItem("selectedCrudOperation") === "delete") return deleteSensor();
    }
    return <div></div>;
  };

  return (
    <div>
      <div className="head" style={{ backgroundColor: "#cccccc", height: "70px" }}>
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
                sessionStorage.removeItem('username');
                sessionStorage.removeItem('password');
                sessionStorage.removeItem('selectedRadioBuuton');
                sessionStorage.removeItem('selectedCrudOperation');
                history.push("/Home");
              }}
            >
              Log out
            </Button>
          </Grid>
        </div>
      </div>
      
      <div className="main">
        <Label style={{ paddingLeft: "10px", fontSize: "20px" }}>
          Administrator
        </Label>
        <div className="menu">
          <FormControl component="fieldset">
            <RadioGroup
              row
              name="row-radio-buttons-group"
              style={{ color: "#4c5c68" }}
              value={sessionStorage.getItem("selectedRadioButton")}
              onChange={handleChange}
            >
              <FormControlLabel
                value="client"
                name="Client"
                control={<Radio style={{ color: "#4c5c68" }} />}
                label="Client"
              />
              <FormControlLabel
                value="device"
                name="Device"
                control={<Radio style={{ color: "#4c5c68" }} />}
                label="Device"
              />
              <FormControlLabel
                value="sensor"
                name="Sensor"
                control={<Radio style={{ color: "#4c5c68" }} />}
                label="Sensor"
              />
            </RadioGroup>
          </FormControl>
        </div>
        <div>          
          <label>&nbsp;</label>
          <label>&nbsp;</label>
          <label>&nbsp;</label>
          <label>&nbsp;</label>
        </div>
        <div className="operations">
          <div className="crudOperations">
            <Button
              variant="outlined"
              value="create"
              onClick={handleButton}
              style={{ color: "#4c5c68", borderColor: "#4c5c68" }}
            >
              Create
            </Button>
            <label>&nbsp;</label>
            <Button
              variant="outlined"
              value="read"
              onClick={handleButton}
              style={{ color: "#4c5c68", borderColor: "#4c5c68" }}
            >
              Read
            </Button>
            <label>&nbsp;</label>
            <Button
              variant="outlined"
              value="update"
              onClick={handleButton}
              style={{ color: "#4c5c68", borderColor: "#4c5c68" }}
            >
              Update
            </Button>
            <label>&nbsp;</label>
            <Button
              variant="outlined"
              value="delete"
              onClick={handleButton}
              style={{ color: "#4c5c68", borderColor: "#4c5c68" }}
            > 
              Delete
            </Button>
          </div>
          <div>{switchOperation()}</div>
        </div>
      </div>
    </div>
  );
};
