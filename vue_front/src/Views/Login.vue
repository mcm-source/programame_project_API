<template>
  <div class="login">

    <form action class="form" @submit.prevent="login">
        <img src="../assets/log.png" height ="85" width="85" />
        <h1 class="title">Login</h1>

      <label class="form-label" for="#email">Email:</label>
      <input
        v-model="usernameForm"
        class="form-input"
        type="text"
        id="email"
        required
        placeholder="Email"
      >
      <label class="form-label" for="#password">Password:</label>
      <input
        v-model="passwordForm"
        class="form-input"
        type="password"
        id="password"
        placeholder="Password"
      >
      <p v-if="error" class="error" v-text="mensajeError"></p>
      <input class="form-submit" type="submit" value="Login">
    </form>
  </div>
</template>

<script>
import auth from "@/logic/auth";
import {ApiUtils} from "../services/ApiUtils";
import {TokenUtils} from "../services/TokenUtils";
let username
let password
let token
export default {
  data: () => ({
    usernameForm: "",
    passwordForm: "",
    error: false,
    mensajeError: "Has introducido mal el email o la contraseña.",
    //token: null
  }),
  methods: {
    async login() {
      try {
        username=this.usernameForm
        password=this.passwordForm
        console.log("{\"username\":\""+username+"\",\"password\":\""+password+"\"}")
        token = await (


          //await fetch("http://localhost:8091/auth/login", {
          //ApiUtils.DOMAIN_URL_IN_USE+"/auth/login"
          await fetch(ApiUtils.DOMAIN_URL_IN_USE+"/auth/login", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
              username,
              password
              }),
          })
        ).json()
        TokenUtils.saveToken(token.jwt, username);
        console.log("From token utils: ", TokenUtils.getToken());
        console.log("From token utils user: ", TokenUtils.getUsername());
        // if (TokenUtils.getUsername()=="root"){
        //   this.$router.push("/register/createUser")
        // }else if(TokenUtils.getToken()!=="undefined"){
        //   console.log(TokenUtils.getToken())
        //   this.$router.push("/clients/newClient")
        // }
      } catch (error) {
        console.log(error);
        this.error = true;
      }
    }
  }
};



</script>

<style scoped>
.login {
  padding: 2rem;
}
.title {
  text-align: center;
}
.form {
  margin: 3rem auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 20%;
  min-width: 350px;
  max-width: 100%;
  background: rgba(19, 35, 47, 0.9);
  border-radius: 5px;
  padding: 40px;
  box-shadow: 0 4px 10px 4px rgba(0, 0, 0, 0.3);
}
.form-label {
  margin-top: 2rem;
  color: white;
  margin-bottom: 0.5rem;
}
.form-input {
  padding: 10px 15px;
  background: none;
  background-image: none;
  border: 1px solid white;
  color: white;
}
.form-submit {
  background: #1ab188;
  border: none;
  color: white;
  margin-top: 3rem;
  padding: 1rem 0;
  cursor: pointer;
  transition: background 0.2s;
}
.error {
  margin: 1rem 0 0;
  color: #ff4a96;
}
.title {
  text-align: center;
  color: #1ab188;
}
img{
  align-self: center;
}
.msg {
  margin-top: 3rem;
  text-align: center;
}
</style>
