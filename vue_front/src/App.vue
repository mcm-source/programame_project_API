<template>
  <div>
  <div id="barra-superior">
    <ul class="elementos">
    <li>
      <router-link to="/">Listado General</router-link>
    </li>
    </ul>


    <div class="float-right" >
      <button @click="show = !show" class="userButton">
        <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
        <label v-if="userLoged==null" >Acceder</label>
        <label v-else >{{userLoged}}</label>
        <span class="glyphicon glyphicon-chevron-down" aria-hidden="true" v-show="!show"></span>
        <span class="glyphicon glyphicon-chevron-up" aria-hidden="true" v-show="show"></span>
        </button>

      <div class="dropdown" v-show="show">
          <label class="form-label" for="#email" v-show="userLoged==null">Email:</label>
          <input
            v-show="userLoged==null"
            v-model="usernameForm"
            class="form-input"
            type="text"
            id="email"
            required
            placeholder="Email"
          >
          <label class="form-label" for="#password" v-show="userLoged==null">Password:</label>
          <input
            v-show="userLoged==null"
            v-model="passwordForm"
            class="form-input"
            type="password"
            id="password"
            required
            placeholder="Password"
          >
          <button type="button" class="btn btn-default" aria-label="Left Align" v-on:click="login" v-show="userLoged==null">
            <span class="glyphicon glyphicon-user" aria-hidden="true"> Entrar</span>
          </button>
        <button type="button" class="btn btn-default" aria-label="Left Align" v-on:click="goToTeams"v-show="userLoged!=null && userAdmin==false">
          Mis Equipos
        </button>
        <button type="button" class="btn btn-default" aria-label="Left Align" v-on:click="goToAdministrator"v-show="userLoged!=null && userAdmin==true">
          Profesores y control
        </button>
          <button type="button" class="btn btn-default" aria-label="Left Align" v-on:click="salir"v-show="userLoged!=null">
            <span class="glyphicon glyphicon-user" aria-hidden="true"> Salir</span>
          </button>
      </div>
    </div>

<!--    <button v-on:click="Register" class="RegButton">Gestion de usuarios</button>-->

  </div>
  <div id="app">
    <link rel="stylesheet" href="https://apps.bdimg.com/libs/bootstrap/3.2.0/css/bootstrap.min.css">
    <router-view></router-view>
  </div>
  </div>
</template>

<script>

import {ApiUtils} from "./services/ApiUtils";
import {TokenUtils} from "./services/TokenUtils"

let response
let username
let password
let  token
export default {
  name: "App",
  components: {
  },
  data: () => ({
    show:false,
    usernameForm: "",
    passwordForm: "",
    userLoged: null,
    userAdmin:false
  }),
  methods: {
    async login(){
      try{
        username=this.usernameForm
        password=this.passwordForm
        token = await (
          await fetch(ApiUtils.DOMAIN_URL_IN_USE+"/auth/login", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
              username,
              password
            }),
          })
        ).json()
        if(token ==undefined){
          console.log('Login mal')
        }
        TokenUtils.saveToken(token.jwt);
        console.log("From token utils: ", TokenUtils.getToken());
        // console.log("From token utils user: ", TokenUtils.getUsername());
        this.userLoged=this.usernameForm
        const resAdmin= await ApiUtils.makeAuthrorizeGetDataSimple("/auth/isUserAdmin")
        this.show=false
        if(resAdmin=="true"){
          this.userAdmin=true
          this.goToAdministrator()
        }else{
          this.userAdmin=false
          this.goToTeams()
        }
      } catch (error) {
        console.log(error);
        this.error = true;
      }

    },
    async salir(){
      try {
        TokenUtils.logOut()
        this.userLoged=null
        this.userAdmin=false
        this.usernameForm=""
        this.passwordForm=""
        this.show=false
        this.$router.push("/").catch(()=>{})
      } catch (error) {
        console.log(error);
      }
    },
    goToTeams(){
      TokenUtils.setTeacherControl(null)
      this.show=false

        this.$router.push("/professorDashboard").catch(()=>{
          window.location.reload()})

      // this.$router.push("/professorDashboard");
    },
    goToAdministrator(){
      this.show=false
      this.$router.push("/administratorDashboard").catch(()=>{
        window.location.reload()});
    },
  }, async mounted(){
    if (TokenUtils.getToken()!==null){
      try{
        const res = await ApiUtils.makeAuthrorizeGetDataSimple("/teacher/getTeacherName")
        this.userLoged=res
        const resAdmin= await ApiUtils.makeAuthrorizeGetDataSimple("/auth/isUserAdmin")
        if(resAdmin=="true"){
          this.userAdmin=true
        }else{
          this.userAdmin=false
        }
      }catch(error){
        console.log(error)
        this.userLoged=null
        this.userAdmin=false
      }

    }
  }
};
</script>

<style>

body{
  margin: 0;
  background-repeat: no-repeat;
  background-attachment: fixed;
}
html {
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
  background-color:#fff;
  /*background-image: url("https://wallpaperaccess.com/full/4743110.jpg");*/
  background-size: 100%;
  background-repeat: no-repeat;
  background-attachment: fixed;
}
.dropdown{
  /*float: bottom;*/
  width:100%;
  /*height: 80px;*/
  display: grid;
  grid-template-columns: 1fr;
  margin-top: 4rem;
  font-size: 12px;
  background-color: #fff;
  border-radius: 4px;
  box-shadow: 0 1px 6px rgba(0,0,0,.2);
  padding: 10px;
}
.dropdown label{
  /*padding-top: 10px;*/

}
.dropdown input{
  margin-bottom: 10px;
}
.dropdown b-button{
  margin: 10px;
}

b-button{
  margin: 10px;
}

@font-face {
  font-family: newFont;
  src:url("fonts/LinLibertine_aDRS.ttf");

}
#app {
  /*font-family: "Avenir", Helvetica, Arial, sans-serif;*/
  font-family: newFont;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;

  width: 100%;
  height: 100%

}
#barra-superior{
  margin:0;
  padding: 0;
  width:100%;
  background: #387dfd;
  height: 50px;
  position: fixed;
  z-index: 101;
}

ul{
  list-style: none;
}

.elementos > li{
  float:left;
}
.userButton{
  float:right;
  color: white;
  /*font: normal normal 20px Arial;*/
  /*font-family: "Avenir", Helvetica, Arial, sans-serif;*/
  font-family: newFont;
  font-size: 22px;
  text-decoration: none;
  border:none;
  background: #387dfd;
  margin-right: 20px;
  margin-left: 10px;
}

.float-right{
  float: right;
}


li{
  margin-right: 20px;
}
li a{
  color: white;
  /*font: normal normal 20px Arial;*/
  /*font-family: "Avenir", Helvetica, Arial, sans-serif;*/
  font-family: newFont;
  font-size: 22px;
  text-decoration: none;
}
</style>
