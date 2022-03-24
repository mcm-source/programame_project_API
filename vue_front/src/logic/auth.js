import axios from "axios";
import Cookies from "js-cookie";
import {ApiUtils} from "../services/ApiUtils";

const ENDPOINT_PATH = "https://reqres.in/api/";
let clientes
let servicios
let dispositivos
let usuarios
export default {

  register(email, password) {
    const user = { email, password };
    return axios.post(ENDPOINT_PATH + "regiser", user);
  },

  actualizarClientList(listaClientes){
    console.log(listaClientes)
    clientes=listaClientes
  },
  actualizarDeviceList(listaDispositivos){
    console.log(listaDispositivos)
    dispositivos=listaDispositivos
  },
  actualizarUserList(listaUsuarios){
    console.log(listaUsuarios)
    usuarios=listaUsuarios
  },
  getClientList(){
    return clientes
  },
  actualizarServiceList(listaServicios){
    console.log(listaServicios)
    servicios=listaServicios
  },
  getServiceList(){
    return servicios
  },
  getDeviceList(){
    return dispositivos
  },
  getUserList(){
    return usuarios
  },

};
