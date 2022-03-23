<template lang="html">
  <div class="container">
    <div class="row">
      <div class="col text-left">

        <h2>Página Home

        </h2>
      </div>
    </div>
  </div>
</template>

<script>
import Navigation from "../components/Navigation";
import auth from "@/logic/auth";
import {TokenUtils} from "../services/TokenUtils";
import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css"
let total_equipo1 =0;
let total_equipo2=0;
export default {
  name: "Home",

  data() {
    return {
      show: false,
      addTeam: false,
      addBenefactor1: false,
    }
  },
  methods: {
    handleClose () {
      this.show = false;
    },
    getTotal1(){
      return total_equipo1
    },
    getTotal2(){
      return total_equipo2
    },
    add: function () {
      this.myData.push({
        name: this.username,
        age: this.age
      });
      this.username = '';
      this.age = '';
    },
  }
};
</script>

<style lang="css" scoped>
.icon-success {
  color: #5CB85C;
}
.icon-yellow{
  color: #f9ff00;
}
.icon-blue{
  color: #0325e5;
}
.icon-red{
  color: #e50303;
}
.icon-brown{
  color: #b44f1b;
}
.row{
  padding-top: 4rem;
}
.table{
  font-size: medium;
}

.div_equipo{
  border-radius: 5px;
  box-shadow: 0 0 0 1px #387dfd;
  margin: 2rem auto;
  padding: 1rem ;

}
.btnAzul{
  background: #387dfd;
  color: white;
  font-size: x-large;
}
.btnAzulPequeño{
  background: #387dfd;
  color: white;
  font-size: large;
}
.intern_form{
  /*float: bottom;*/
  width:100%;
  /*height: 80px;*/
  display: grid;
  grid-template-columns: 1fr;
  font-size: medium;
  /*border-radius: 4px;*/
  /*box-shadow: 0 1px 6px rgba(0,0,0,.2);*/
  padding: 10px;
}
.margin_down{
  margin-bottom: 10px;
}

</style>
