import '@babel/polyfill'
import 'mutationobserver-shim'
// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import './plugins/bootstrap-vue'
import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css"


import Router from "vue-router";
import App from './App'
import Home from "./views/Home";
import AdministratorDashboard from "./Views/AdministratorDashboard/AdministratorDashboard";
import ProfessorDashboard from "./Views/ProfessorDashboard/ProfessorDashboard";

import tab from "bootstrap/js/src/tab";
//import router from './router'

/*--------------------REGISTER BOOTSTRAP---------------------------------*/
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
// Importar los archivos CSS de Bootstrap y BootstrapVue (el orden es importante)
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
// Haz que BootstrapVue esté disponible en todo tu proyecto
Vue.use(BootstrapVue);
// Opcionalmente, instale el plugin de componentes de iconos BootstrapVue
Vue.use(IconsPlugin);
/*-----------------------------------------------------------------------*/



Vue.use(Router);

const routes = [
  { path: "/", component: Home },
  { path: "/administratorDashboard", component: AdministratorDashboard },
  { path: "/professorDashboard", component: ProfessorDashboard }

];

const router = new Router({
  routes
});

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  router,
  render: h=>h(App)
}).$mount("#app");
