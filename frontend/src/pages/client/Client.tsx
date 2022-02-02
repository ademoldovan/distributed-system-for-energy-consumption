import { Label } from "@fluentui/react";
import { Button, Grid } from "@mui/material";
import { DataGrid, GridColDef } from "@mui/x-data-grid";
import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import axiosInstance from "../../configure/axiosInstance";
import { IConsumption, IDevice } from "../admin/admin.types";
import "./client.css";
import SockJS from 'sockjs-client';
import { Stomp } from "@stomp/stompjs";

export const Client = (): JSX.Element => {
  const history = useHistory();

  const [devices, setDevices] = useState<IDevice[]>([]);
  const [deviceRows, setDeviceRows] = useState<IDevice[]>([]);

  const [consumptionsRows, setConsumptionsRows] = useState<IConsumption[]>([]);

  const connect = () => {
    const URL = "https://m-adelina-assignment-1-ds2021.herokuapp.com/socket";
    const websocket = new SockJS(URL);
    const stompClient = Stomp.over(websocket);
    stompClient.connect({}, () => {
      stompClient.subscribe('/topic/socket/notification/', notification=>{
        let message = notification.body;
        let userObj = JSON.parse(message);
        if (sessionStorage.getItem("username") === userObj.username) {
          alert("Sensor with id: " + userObj.sensorId + " exceeded maximum value!! ");
        }
      })
    })
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
  const mapToRowConsumptions = (consumption: IConsumption): IConsumption => {
    return {
      idSensor: consumption.idSensor,
      id: consumption.id,
      energy_consumption: consumption.energy_consumption,
      timestamp: consumption.timestamp,
    };
  };

  useEffect(() => {
    axiosInstance.get("/Device/getDevicesByUser/" + sessionStorage.getItem("username") ).then((response: any) => {
      setDevices(response.data);
    });
  }, []);
  useEffect(() => {
    const newRows: IDevice[] = devices.map((device: IDevice) =>
      mapToRowDevices(device)
    );
    setDeviceRows(newRows);
  }, [devices]);
  useEffect(() => {
    //connect();
  }, []);

  const columns: GridColDef[] = [
    { field: "id", headerName: "ID", width: 80 },
    { field: "address", headerName: "Address", width: 170 },
    {
      field: "average",
      headerName: "Average energy consumption",
      width: 220,
    },
    { field: "description", headerName: "Device description", width: 210 },
    { field: "maximum_energy", headerName: "Maximum energy", width: 170 },
    { field: "sensor_id", headerName: "Sensor ID", width: 100 },
    {
      field: "action",
      headerName: "Sensor Consumption",
      width: 170,
      sortable: false,
      renderCell: (params) => {
        const onView = () => {
          axiosInstance.get("/Consumption/getSensorConsumption/" + params.row.sensor_id.valueOf()).then((response: any) =>{
            const newRows: IConsumption[] = response.data.map((consumption: IConsumption) =>
            mapToRowConsumptions(consumption)
          );
          setConsumptionsRows(newRows);
          });
        };
        return <Button onClick={onView}>View</Button>;
      },
    },
  ];

  const columnsConsumption: GridColDef[] = [
    { field: "idSensor", headerName: "Sensor ID", width: 120 },
    { field: "id", headerName: "ID", width: 120 },
    { field: "energy_consumption", headerName: "Energy Consumption", width: 170 },
    { field: "timestamp", headerName: "Timestamp", width: 210 },
  ];

  return (
    <div> 
      <div
        className="head"
        style={{ backgroundColor: "#cccccc", height: "75px" }}
      >
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
                sessionStorage.removeItem("username");
                sessionStorage.removeItem("password");
                history.push("/Home");
              }}
            >
              Log out
            </Button>
          </Grid>
        </div>
      </div>
      <div className="main">
        <Label style={{ paddingLeft: "10px", fontSize: "20px" }}>Client</Label>
        <label>&nbsp;</label>
        <label>&nbsp;</label>
        <div
          style={{
            height: 350,
            width: 1130,
            paddingLeft: "50px",
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
        <div
          style={{
            height: 350,
            width: 630,
            paddingLeft: "50px",
            paddingBottom: "40px",
          }}
        >
          <DataGrid
            rows={consumptionsRows}
            columns={columnsConsumption}
            pageSize={5}
            rowsPerPageOptions={[5]}
            checkboxSelection={false}
          />
        </div>
      </div>
    </div>
  );
};
