export interface IUser {
  username: string;
  password: string;
  role: string;
}

export interface Data {
  id: number;
  firstName: string;
  lastName: string;
  address: string;
  birthDate: string;
  emailAddress: string;
}

export interface IClient {
    id: number;
    firstName: string;
    lastName: string;
    address: string;
    birthDate: Date;
    email: string;
}

export interface IConsumption {
  idSensor: number;
  id: number;
  energy_consumption: number;
  timestamp: Date;
}

export interface AutocompleteOption {
  label: string;
  id: number;
}

export interface AutocompleteClientOption {
  label: string;
  id: number;
}

export interface IDevice {
    id: number;
    address: string;
    average: number;
    description: string;
    maximum_energy: number;
    sensor_id: number;   
    client_id: number;              
}

export interface ISensor {
    id: number;
    description: string;
    maximum_value: string;
}

export type Order = "asc" | "desc";