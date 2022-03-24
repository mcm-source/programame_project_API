<template>
  <div class="container">
    <div class="row">
      <div class="col text-left">

        <h2>Panel de administración</h2>

        <div class="div_tabla ">
          <table class="table table-bordered tabla_cli" id="tabla1">
            <thead>
            <tr>
              <th scope="col">Profesor</th>
              <th scope="col">Email</th>
              <th scope="col">Acceder a sus equipos</th>
              <th scope="col">Bloquear, editar o eliminar cuenta</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="teacher in dbRows">
              <td>{{ teacher.name }}</td>
              <td>{{ teacher.email }}</td>
              <td>
                <b-button type="button" class="btn btn-default" aria-label="Left Align"
                          v-on:click="goToProfessorDashboard(teacher.id)">

                  <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
                </b-button>
              </td>
              <td>Aún por implementar</td>
            </tr>
            </tbody>
          </table>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {TokenUtils} from "../../services/TokenUtils";
import {ApiUtils} from "../../services/ApiUtils";
import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css"

export default {
  name: "AdministratorDashboard",
  data: () => ({
    show:false,
    dbRows: [],
  }),
  methods: {
    goToProfessorDashboard(teacherId){
      TokenUtils.setTeacherControl(teacherId)
      this.$router.push("/professorDashboard");
    }
  }, async mounted(){
    try {
      let response
      //Este método devuelve a home si no hay sesión iniciada
      if(TokenUtils.getToken()==null){
        this.$router.push("/");
      }else{
        this.equipos=[]
        try {
          response = await ApiUtils.makeAuthrorizeGetData("/teacher/listTeacherData")
          this.dbRows=response
          if(this.dbRows==null){
            console.log('no hay profesores')
          }
        } catch (error) {
          console.log(error);
        }
      }
    } catch (error) {
      console.log(error);
      this.$router.push("/");
    }
  }
}

</script>

<style scoped>

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

</style>
