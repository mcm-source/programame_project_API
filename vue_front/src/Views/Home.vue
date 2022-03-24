<template lang="html">
  <div class="container">
    <div class="row">
      <div class="col text-left">

        <h2>Tabla general de benefactores</h2>

        <div class="div_tabla ">
          <table class="table table-bordered tabla_cli" id="tabla1">
            <thead>
            <tr>
              <th scope="col">Equipo</th>
              <th scope="col">Centro</th>
              <th scope="col">Benefactor</th>
              <th scope="col">Cuantía</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="dbRow in tableRows">
              <td>{{ dbRow.teamName }}</td>
              <td>{{ dbRow.schoolName }}</td>
              <td>{{ dbRow.sponsorName }}</td>
              <td scope="row" v-if="dbRow.complexDonation!=null">
                <span class="glyphicon glyphicon-asterisk icon-yellow"></span>
                {{ dbRow.complexDonation.amountForSimpleProblem }}
                <span class="glyphicon glyphicon-asterisk icon-blue"></span>
                {{ dbRow.complexDonation.amountForMediumProblem }}
                <span class="glyphicon glyphicon-asterisk"></span>
                {{ dbRow.complexDonation.amountForHardProblem }}
              </td>
              <td scope="row" v-else>
                <span class="glyphicon glyphicon-asterisk icon-yellow"></span>
                <span class="glyphicon glyphicon-asterisk icon-blue"></span>
                <span class="glyphicon glyphicon-asterisk"></span>
                {{ dbRow.simpleDonation.amount }}
              </td>
            </tr>
            </tbody>
          </table>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css"
import {ApiUtils} from "../services/ApiUtils";
let a='nada'
export default {
  name: "Home",

  data() {
    return {
      show: false,
      addTeam: false,
      addBenefactor1: false,
      tableRows:[]
    }
  },
  methods: {

  },
  async mounted() {
    try{
      const res = await ApiUtils.makeAuthrorizeGetData("/teacher/listDataForOverallTable")
      this.tableRows=res
      console.log(res)
      console.log(res.data())
    }catch(error){
      console.log(error)
    }

  }
};
</script>

<style lang="css" scoped>
.icon-yellow {
  color: #f9ff00;
}

.icon-blue {
  color: #0325e5;
}
.row{
  padding-top: 4rem;
}
.table{
  font-size: medium;
}

.div_tabla{
  border-radius: 5px;
  box-shadow: 0 0 0 1px #387dfd;
  margin: 2rem auto;
  padding: 1rem ;

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
