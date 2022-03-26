import { createApp,h } from 'vue'
import {createRouter, createWebHashHistory} from 'vue-router'

import App from './App.vue'
import Home from "./Views/Home";
import AdministratorDashboard from "./Views/AdministratorDashboard/AdministratorDashboard";
import ProfessorDashboard from "./Views/ProfessorDashboard/ProfessorDashboard";

const routes = [
    { path: "/", component: Home },
    { path: "/administratorDashboard", component: AdministratorDashboard },
    { path: "/professorDashboard", component: ProfessorDashboard }
];

const router=new createRouter({
    history: createWebHashHistory(),
    routes})
const app = createApp({render: ()=>h(App)})
app.use(router)

app.mount('#app')
