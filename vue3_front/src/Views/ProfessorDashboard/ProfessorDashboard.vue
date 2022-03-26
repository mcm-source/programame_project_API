<template lang="html">
  <div class="container">
    <div class="row">
      <div class="col text-left">

        <h2>Panel del profesor de {{professorLogedName}}
          <button type="button" class="btnAzul float-right" aria-label="Left Align" v-show="!limitedAdminButtons" v-on:click=openNewTeamForm()>
            Añadir Equipo
          </button>
        </h2>
        <confirm-dialogue ref="confirmDialogue"></confirm-dialogue>
        <!--        formulario de crear equipo-->
        <div class="div_equipo " v-show="addTeam">
          <div class="intern_form">
            <div>
              <button type="button" class="btn btn-default float-right" aria-label="Left Align"
                        v-on:click="addTeam=false">
                <span class="glyphicon glyphicon-remove icon-red" aria-hidden="true"></span>
              </button>
            </div>
            <label class="form-label">Nombre del Equipo:</label>
            <input
              v-model="teamFormModel[0]"
              class="margin_down"
              type="text"
              id="nombreEquipo"
              required
              placeholder="Nombre del Equipo"
            >
            <label class="form-label">Integrantes:</label>
            <input
              v-model="teamFormModel[1]"
              class="margin_down"
              type="text"
              id="integrantesEquipo"
              required
              placeholder="Integrantes"
            >
            <label class="form-label">Nombre del Centro:</label>
            <input
              v-model="teamFormModel[2]"
              class="margin_down"
              type="text"
              id="centroEquipo"
              required
              placeholder="Nombre del Centro"
            >
            <label class="form-label">Ubicación:</label>
            <select v-model="comunidad" class="margin_down">
              <option disabled value="">Seleccione un elemento</option>
              <option v-for="ccaa in comunidades">{{ ccaa.nombre }}</option>
            </select>

            <button type="button" class="btn btn-default" aria-label="Left Align" v-on:click="createTeam()">
              Añadir Equipo
            </button>
          </div>
        </div>
        <!--        bloque por cada equipo-->
        <div class="div_equipo" v-for="(equipo, index) in equipos">
          <h3>{{ equipo.name }}
            <button type="button" class="btn btn-default float-right" aria-label="Left Align"
                      v-on:click="deleteTeam(index)">
              <span class="glyphicon glyphicon-remove icon-red" aria-hidden="true"></span>
            </button>
            <button type="button" class="btn btn-default float-right" aria-label="Left Align"
                      v-on:click="newFormTriggerChange(index,0, null)">
              <span class="glyphicon glyphicon-pencil icon-brown" aria-hidden="true"></span>
            </button>
          </h3>
          <h4>{{ equipo.teamMembers }}</h4>
          <!--        formulario de modificar equipo-->
          <div class="div_equipo " v-show="booleanTriggers[index][0]">
            <div class="intern_form">
              <div>
                <button type="button" class="btn btn-default float-right" aria-label="Left Align"
                          v-on:click="newFormTriggerChange(index,0, null)">
                  <span class="glyphicon glyphicon-remove icon-red" aria-hidden="true"></span>
                </button>
              </div>
              <label class="form-label">Nombre del Equipo:</label>
              <input
                v-model="teamFormModel[0]"
                class="margin_down"
                type="text"
                required
                placeholder="Nombre del Equipo"
              >
              <label class="form-label">Integrantes:</label>
              <input
                class="margin_down"
                type="text"
                v-model="teamFormModel[1]"
                required
                placeholder="Integrantes"
              >
              <label class="form-label">Nombre del Centro:</label>
              <input

                class="margin_down"
                type="text"
                v-model="teamFormModel[2]"
                required
                placeholder="Nombre del Centro"
              >
              <label class="form-label">Ubicación:</label>
              <select v-model="comunidad" class="margin_down">
                <option disabled value="">Seleccione un elemento</option>
                <option v-for="ccaa in comunidades">{{ ccaa.nombre }}</option>
              </select>

              <button type="button" class="btn btn-default" aria-label="Left Align"
                      v-on:click=updateTeam(index)>
                Modificar Equipo
              </button>
            </div>
          </div>
          <!-- Tabla de benefactores-->
          <div>
            <table class="table table-bordered tabla_cli" id="tabla1">
              <thead>
              <tr>
                <th scope="col">Benefactor</th>
                <th scope="col">Cuantía</th>
                <th scope="col">Acciones</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="(patrocinio, index2) in equipo.listSponsors">
                <td>{{ patrocinio.name }}</td>
                <td scope="row" v-if="patrocinio.complexDonation!=null">
                  <span class="glyphicon glyphicon-asterisk icon-yellow"></span>
                  {{ patrocinio.complexDonation.amountForSimpleProblem }}
                  <span class="glyphicon glyphicon-asterisk icon-blue"></span>
                  {{ patrocinio.complexDonation.amountForMediumProblem }}
                  <span class="glyphicon glyphicon-asterisk"></span>
                  {{ patrocinio.complexDonation.amountForHardProblem }}
                </td>
                <td scope="row" v-else>
                  <span class="glyphicon glyphicon-asterisk icon-yellow"></span>
                  <span class="glyphicon glyphicon-asterisk icon-blue"></span>
                  <span class="glyphicon glyphicon-asterisk"></span>
                  {{ patrocinio.simpleDonation.amount }}
                </td>
                <td>
                  <button type="button" class="btn btn-default" aria-label="Left Align"
                            v-on:click="newFormTriggerChange(index,2,index2)">
                    <span class="glyphicon glyphicon-pencil icon-brown" aria-hidden="true"></span>
                  </button>
                  <button type="button" class="btn btn-default" aria-label="Left Align"
                            v-on:click=deleteSponsor(index,index2)>
                    <span class="glyphicon glyphicon-remove icon-red" aria-hidden="true"></span>
                  </button>
                </td>
              </tr>
              </tbody>
            </table>

            <!-- Formulario de modificar benefator -->
            <div class="div_equipo " v-show="booleanTriggers[index][2]">
              <div class="intern_form">
                <div>
                  <button type="button" class="btn btn-default float-right" aria-label="Left Align"
                            v-on:click="newFormTriggerChange(index,2, null)">
                    <span class="glyphicon glyphicon-remove icon-red" aria-hidden="true"></span>
                  </button>
                </div>
                <label class="form-label">Nombre del Benefactor:</label>
                <input
                  class="margin_down"
                  type="text"
                  v-model="sponsorFormModelName"
                  required
                  placeholder="Nombre del Benefactor"
                >
                <label class="form-label">Tipo de Cuantía:</label>
                <section>
                  <input type="radio" v-model="radioButtonsCuantia" value="0"> Cuantía por dificultad &nbsp;
                  <input type="radio" v-model="radioButtonsCuantia" value="1"> Cuantía única
                </section>
                <label class="form-label" v-show="radioButtonsCuantia==0">Cuantía de resolución Amarillo
                  <span class="glyphicon glyphicon-asterisk icon-yellow"></span> (Fácil):</label>
                <input
                  v-show="radioButtonsCuantia==0"
                  class="margin_down"
                  type="number"
                  v-model="sponsorFormModel[0]"
                  required
                  placeholder="Cuantía en €"
                ><!--                id="cuantiaFacil"-->
                <label class="form-label" v-show="radioButtonsCuantia==0">Cuantía de resolución Azul
                  <span class="glyphicon glyphicon-asterisk icon-blue"></span> (Medio):</label>
                <input
                  v-model="sponsorFormModel[1]"
                  v-show="radioButtonsCuantia==0"
                  class="margin_down"
                  type="number"
                  required
                  placeholder="Cuantía en €"
                >
                <label class="form-label" v-show="radioButtonsCuantia==0">Cuantía de resolución Negro
                  <span class="glyphicon glyphicon-asterisk "></span> (Difícil):</label>
                <input
                  v-model="sponsorFormModel[2]"
                  v-show="radioButtonsCuantia==0"
                  class="margin_down"
                  type="number"
                  required
                  placeholder="Cuantía en €"
                >
                <label class="form-label" v-show="radioButtonsCuantia==1">Cuantía de resolución general
                  <span class="glyphicon glyphicon-asterisk icon-yellow"></span>
                  <span class="glyphicon glyphicon-asterisk icon-blue"></span>
                  <span class="glyphicon glyphicon-asterisk "></span>
                  (Todas las dificultades):</label>
                <input
                  v-model="sponsorFormModel[3]"
                  v-show="radioButtonsCuantia==1"
                  class="margin_down"
                  type="number"
                  required
                  placeholder="Cuantía en €"
                >

                <button type="button" class="btn btn-default" aria-label="Left Align"
                        v-on:click=updateSponsor(index)>
                  Modificar Benefactor
                </button>
              </div>
            </div>

            <h4>
              <button type="button" class="btnAzulPequeño " aria-label="Left Align" v-show="!limitedAdminButtons"
                      v-on:click="newFormTriggerChange(index,1, null)">
                Añadir Patrocinador
              </button>
            </h4>
          </div>
          <!-- Form de añadir benefactor-->
          <div class="div_equipo " v-show="booleanTriggers[index][1]">
            <div class="intern_form">
              <div>
                <button type="button" class="btn btn-default float-right" aria-label="Left Align"
                          v-on:click="newFormTriggerChange(index,1, null)">
                  <span class="glyphicon glyphicon-remove icon-red" aria-hidden="true"></span>
                </button>
              </div>
              <label class="form-label">Nombre del Benefactor:</label>
              <input
                class="margin_down"
                type="text"
                v-model="sponsorFormModelName"
                required
                placeholder="Nombre del Benefactor"
              >
              <label class="form-label">Tipo de Cuantía:</label>
              <section>
                <input type="radio" v-model="radioButtonsCuantia" value="0"> Cuantía por dificultad &nbsp;
                <input type="radio" v-model="radioButtonsCuantia" value="1"> Cuantía única
              </section>
              <label class="form-label" v-show="radioButtonsCuantia==0">Cuantía de resolución Amarillo
                <span class="glyphicon glyphicon-asterisk icon-yellow"></span> (Fácil):</label>
              <input
                v-show="radioButtonsCuantia==0"
                class="margin_down"
                type="number"
                v-model="sponsorFormModel[0]"
                required
                placeholder="Cuantía en €"
              ><!--                id="cuantiaFacil"-->
              <label class="form-label" v-show="radioButtonsCuantia==0">Cuantía de resolución Azul
                <span class="glyphicon glyphicon-asterisk icon-blue"></span> (Medio):</label>
              <input
                v-model="sponsorFormModel[1]"
                v-show="radioButtonsCuantia==0"
                class="margin_down"
                type="number"
                required
                placeholder="Cuantía en €"
              >
              <label class="form-label" v-show="radioButtonsCuantia==0">Cuantía de resolución Negro
                <span class="glyphicon glyphicon-asterisk "></span> (Difícil):</label>
              <input
                v-model="sponsorFormModel[2]"
                v-show="radioButtonsCuantia==0"
                class="margin_down"
                type="number"
                required
                placeholder="Cuantía en €"
              >
              <label class="form-label" v-show="radioButtonsCuantia==1">Cuantía de resolución general
                <span class="glyphicon glyphicon-asterisk icon-yellow"></span>
                <span class="glyphicon glyphicon-asterisk icon-blue"></span>
                <span class="glyphicon glyphicon-asterisk "></span>
                (Todas las dificultades):</label>
              <input
                v-model="sponsorFormModel[3]"
                v-show="radioButtonsCuantia==1"
                class="margin_down"
                type="number"
                id="cuantiaGenerica"
                required
                placeholder="Cuantía en €"
              >

              <button type="button" class="btn btn-default" aria-label="Left Align"
                      v-on:click=createSponsor(index)>
                Añadir Benefactor
              </button>
            </div>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<script>

import {TokenUtils} from "../../services/TokenUtils";
import {ApiUtils} from "../../services/ApiUtils";
import ConfirmDialogue from "../../components/ConfirmDialogue";
// import "bootstrap";
// import "bootstrap/dist/css/bootstrap.min.css"

let response
export default {
  name: "ProfessorDashboard",
  components: { ConfirmDialogue },
  data() {
    return {
      professorLogedName:"",
      show: false,
      addTeam: false,
      currentSponsorEditing:0,
      limitedAdminButtons:false,
      radioButtonsCuantia: 0, //0:complexDonation, 1:simpleDonation
      comunidad: '', //Modelo de ubicación
      booleanTriggers: [[false, false, false], [false, false, false]], //editTeam, addSponsor, editSponsor x Team
      teamFormModel: ["", "", ""], //0: name, 1:teamMembers, 2:schoolName
      sponsorFormModelName:"",
      sponsorFormModel:[0,0,0,0], //0:amountForSimpleProblem, 1:amountForMediumProblem, 2:amountForHardProblem, 3:amount
      equipos: [],
      comunidades: [],
    }
  },
  methods: {
    openNewTeamForm() {
      let x, y
      //Se cierra el resto de formularios que puedan estar abiertos
      try {
        for (x in this.booleanTriggers) {
          for (y in this.booleanTriggers[x]) {
            // this.$set(this.booleanTriggers[x], y, false)
            this.booleanTriggers[x][y]=false
          }
        }
      } catch (error) {
        console.log(error)
      }
      //Se vacía el formulario y se muestra
      this.teamFormModel[0] = ""
      this.teamFormModel[1] = ""
      this.teamFormModel[2] = ""
      this.comunidad = ""
      this.addTeam = true
    },
    newFormTriggerChange(index, triggerIndex, sponsorIndex) {
      let x
      let y

      //Antes de abrir un formulario interno con triggers se cierra el resto para evitar que se pisen
      if (!this.booleanTriggers[index][triggerIndex]) {
        for (x in this.booleanTriggers) {
          for (y in this.booleanTriggers[x]) {
            // this.$set(this.booleanTriggers[x], y, false)
            this.booleanTriggers[x][y]= false
          }
        }
        this.addTeam=false
        //Si se va a editar equipo se cargan los datos en el formulario
        if (triggerIndex == 0) {
          this.teamFormModel[0] = this.equipos[index].name
          this.teamFormModel[1] = this.equipos[index].teamMembers
          this.teamFormModel[2] = this.equipos[index].schoolName
          this.comunidad = this.equipos[index].location
        }
        //Si se va a crear sponsor se vacía el modelo
        if (triggerIndex == 1) {
          this.radioButtonsCuantia = 0
          this.sponsorFormModel=[0,0,0,0]
          this.sponsorFormModelName=""
        }
        //Si se va a editar el sponsor se cargan sus datos
        if (triggerIndex == 2) {
            this.currentSponsorEditing=sponsorIndex
          this.sponsorFormModelName=this.sponsorFormModel[0]=this.equipos[index].listSponsors[sponsorIndex].name
          if(this.equipos[index].listSponsors[sponsorIndex].simpleDonation==null){
            this.radioButtonsCuantia=0
            this.sponsorFormModel[0]=this.equipos[index].listSponsors[sponsorIndex].complexDonation.amountForSimpleProblem
            this.sponsorFormModel[1]=this.equipos[index].listSponsors[sponsorIndex].complexDonation.amountForMediumProblem
            this.sponsorFormModel[2]=this.equipos[index].listSponsors[sponsorIndex].complexDonation.amountForHardProblem
            this.sponsorFormModel[3]=0
          }else if(this.equipos[index].listSponsors[sponsorIndex].complexDonation==null){
            this.radioButtonsCuantia=1
            this.sponsorFormModel[0]=0
            this.sponsorFormModel[1]=0
            this.sponsorFormModel[2]=0
            this.sponsorFormModel[3]=this.equipos[index].listSponsors[sponsorIndex].simpleDonation.amount
          }
        }
      }
      // this.$set(this.booleanTriggers[index], triggerIndex, !this.booleanTriggers[index][triggerIndex])
      this.booleanTriggers[index][triggerIndex]=!this.booleanTriggers[index][triggerIndex]

    },
    secureNotNullNumbers(){
      let i
      for(i in this.sponsorFormModel){
        if(this.sponsorFormModel[i].toString().length<1){
          this.sponsorFormModel[i]=0
        }
      }
    },
    secureNotEmptyTeamFields(){
      let empty=false
      let f
      for (f in this.teamFormModel){
        if(this.teamFormModel[f]==""){
          empty=true
        }
      }
      if (this.comunidad==""){
        empty=true
      }
      if (empty){
        alert("No se admiten campos vacíos.")
      }
      return !empty
    },
    secureNotEmptySponsorFields(){
      let empty=false
      let i
      if (this.sponsorFormModelName==""){
        empty=true
      }else{
        this.secureNotNullNumbers()
        //No se aceptan números negativos ni e
        if(this.radioButtonsCuantia==0){ //Cuantía compleja
          for(i=0; i<3; i++){
            if((this.sponsorFormModel[i].toString().includes("-"))||(this.sponsorFormModel[i].toString().includes("e"))){
              empty=true
            }
          }
        }else{//Cuantía simple
          if((this.sponsorFormModel[3].toString().includes("-"))||(this.sponsorFormModel[3].toString().includes("e"))){
            empty=true
          }
        }
      }
      if (empty){
        alert("No se admiten campos vacíos ni valores negativos.")
      }
      return !empty
    },
    async getTeamsFromDB(){
      this.equipos=[]
      let t, professorName
      try {
        //Si no es admin accediendo a otro listado
        if(TokenUtils.getTeacherControl().toString()=="null"){
          response = await ApiUtils.makeAuthrorizeGetData("/teacher/listDataForTeamsTable")
          professorName=await ApiUtils.makeAuthrorizeGetDataSimple("/teacher/getTeacherName")

        }else{ //Admin que viene de dashboard de administración
          response = await ApiUtils.makeAuthrorizeGetData("/team/getTeamData/"+TokenUtils.getTeacherControl())
          professorName= await ApiUtils.makeAuthrorizeGetDataSimple("/teacher/getTeacherNameWithId/"+TokenUtils.getTeacherControl())
        }
        this.professorLogedName=professorName
        this.equipos=response
        if(this.equipos==null){
          console.log('no hay equipos')
        }
        } catch (error) {
          console.log(error);
        }
        this.booleanTriggers.clear
        for(t in this.equipos){
          this.booleanTriggers.push([false, false, false])
        }
    },
    async createTeam(){
      if(this.secureNotEmptyTeamFields()){
        let con
        try {
          let name, teamMembers, schoolName, location
          name=this.teamFormModel[0]
          teamMembers=this.teamFormModel[1]
          schoolName=this.teamFormModel[2]
          location=this.comunidad
          response=await ApiUtils.makeAuthrorizePost("/team/createTeam", {
            name,
            teamMembers,
            schoolName,
            location
          });
        } catch (error) {
          console.log(error);
          //this.error = true;
        }
        try{
          con=await this.getTeamsFromDB()
        }catch (error){
          console.log(error)
        }
        this.addTeam=false
      }
    },
    async updateTeam(index){
      if(this.secureNotEmptyTeamFields()){
        let con
        try {
          let name, teamMembers, schoolName, location, idTeam
          name=this.teamFormModel[0]
          teamMembers=this.teamFormModel[1]
          schoolName=this.teamFormModel[2]
          location=this.comunidad
          idTeam=this.equipos[index].id
          console.log({
            idTeam,
            name,
            teamMembers,
            schoolName,
            location
          });
          response=await ApiUtils.makeAuthrorizePost("/team/updateTeam", {
            idTeam,
            name,
            teamMembers,
            schoolName,
            location
          });
        } catch (error) {
          console.log(error);
          //this.error = true;
        }
        this.newFormTriggerChange(index,0, null)
        try{
          con=await this.getTeamsFromDB()
        }catch (error){
          console.log(error)
        }
        this.addTeam=false
      }
    },
    async deleteTeam(index){
      let con
      try {

        const ok = await this.$refs.confirmDialogue.show({
          title: '¿Eliminar '+this.equipos[index].name+'?',
          message: 'Esta operación no se puede deshacer.',
          okButton: 'Eliminar',
          cancelButton: 'Cancelar'
        })

        if (ok) {
          let idTeam
          idTeam=this.equipos[index].id
          console.log({
            idTeam
          });
          response=await ApiUtils.makeAuthrorizeDeleteData("/team/deleteTeam/"+idTeam)
          console.log(response)
          // con=await this.getTeamsFromDB() no llega aquí por el error en json
        }


      } catch (error) {
        console.log(error);
        //this.error = true;
      }
      try{
        con=await this.getTeamsFromDB()
      }catch (error){
        console.log(error)
      }
    },
    async createSponsor(index){
      if(this.secureNotEmptySponsorFields()){
        let con
        try {
          this.secureNotNullNumbers()
          let isSimpleDonation, name, amountForSimpleProblem, amountForMediumProblem, amountForHardProblem, amount, idTeam
          idTeam=this.equipos[index].id
          name=this.sponsorFormModelName
          amountForSimpleProblem=this.sponsorFormModel[0].toString()
          amountForMediumProblem=this.sponsorFormModel[1].toString()
          amountForHardProblem=this.sponsorFormModel[2].toString()
          amount=this.sponsorFormModel[3].toString()
          if(this.radioButtonsCuantia==0){ //Cuantía compleja
            isSimpleDonation=false
            console.log({
                idTeam,
                name,
                isSimpleDonation,
                amountForSimpleProblem,
                amountForMediumProblem,
                amountForHardProblem
              }
            )
            response=await ApiUtils.makeAuthrorizePost("/sponsor/createSponsor", {
              idTeam,
              name,
              isSimpleDonation,
              amountForSimpleProblem,
              amountForMediumProblem,
              amountForHardProblem
            });
          }else if(this.radioButtonsCuantia==1){ //Cuantía simple
            isSimpleDonation=true
            console.log({
                idTeam,
                name,
                isSimpleDonation,
                amount
              }
            )
            response=await ApiUtils.makeAuthrorizePost("/sponsor/createSponsor", {
              idTeam,
              name,
              isSimpleDonation,
              amount
            });
          }
          console.log(response)
        } catch (error) {
          console.log(error);
          //this.error = true;
        }
        try{
          con=await this.getTeamsFromDB()
        }catch (error){
          console.log(error)
        }
        this.newFormTriggerChange(index,1,null)
      }
    },
    async updateSponsor(index){
      if(this.secureNotEmptySponsorFields()){
        let con
        try {
          let isSimpleDonation, name, amountForSimpleProblem, amountForMediumProblem, amountForHardProblem, amount, idTeam, idSponsor
          name=this.sponsorFormModelName
          this.secureNotNullNumbers()
          amountForSimpleProblem=this.sponsorFormModel[0].toString()
          amountForMediumProblem=this.sponsorFormModel[1].toString()
          amountForHardProblem=this.sponsorFormModel[2].toString()
          amount=this.sponsorFormModel[3].toString()
          idTeam=this.equipos[index].id
          idSponsor=this.equipos[index].listSponsors[this.currentSponsorEditing].id
          if(this.radioButtonsCuantia==0){ //Cuantía compleja
            isSimpleDonation=false
            console.log({
                idTeam,
                idSponsor,
                name,
                isSimpleDonation,
                amountForSimpleProblem,
                amountForMediumProblem,
                amountForHardProblem
              }
            )
            response=await ApiUtils.makeAuthrorizePost("/sponsor/updateSponsor", {
              idTeam,
              idSponsor,
              name,
              isSimpleDonation,
              amountForSimpleProblem,
              amountForMediumProblem,
              amountForHardProblem
            });
          }else if(this.radioButtonsCuantia==1){ //Cuantía simple
            isSimpleDonation=true
            console.log({
                idTeam,
                idSponsor,
                name,
                isSimpleDonation,
                amount
              }
            )
            response=await ApiUtils.makeAuthrorizePost("/sponsor/updateSponsor", {
              idTeam,
              name,
              idSponsor,
              isSimpleDonation,
              amount
            });
          }
          console.log(response)
          // con=await this.getTeamsFromDB() no llega aquí por el error en json
        } catch (error) {
          console.log(error);
          //this.error = true;
        }
        this.newFormTriggerChange(index,2, null)
        try{
          con=await this.getTeamsFromDB()
        }catch (error){
          console.log(error)
        }
      }

    },
    async deleteSponsor(index, index2){
      let con
      try {

        const ok = await this.$refs.confirmDialogue.show({
          title: '¿Eliminar '+this.equipos[index].listSponsors[index2].name+'?',
          message: 'Esta operación no se puede deshacer.',
          okButton: 'Eliminar',
          cancelButton: 'Cancelar'
        })

        if (ok) {
          let idSponsor
          idSponsor=this.equipos[index].listSponsors[index2].id
          console.log({
            idSponsor
          });
          response=await ApiUtils.makeAuthrorizeDeleteData("/sponsor/deleteSponsor/"+idSponsor)
          console.log(response)
          // con=await this.getTeamsFromDB() no llega aquí por el error en json
        }
      } catch (error) {
        console.log(error);
        //this.error = true;
      }
      try{
        con=await this.getTeamsFromDB()
      }catch (error){
        console.log(error)
      }
    },
  },
  async mounted() {
    let t
    try {
      //Este método devuelve a home si no hay sesión iniciada
      if(TokenUtils.getToken()==null){
        this.$router.push("/");
      }else{
        //Si es admin que no viene del dashboard de administración también lo echa
        const resAdmin= await ApiUtils.makeAuthrorizeGetDataSimple("/auth/isUserAdmin")
        if(resAdmin=="true"){
          if(TokenUtils.getTeacherControl().toString()=="null"){
            this.$router.push("/");
          }else{
            this.limitedAdminButtons=true
            const r=this.getTeamsFromDB()
          }
        }else{
          TokenUtils.setTeacherControl(null)
          const r=this.getTeamsFromDB()
        }

      }
    } catch (error) {
      console.log(error);
      this.$router.push("/");
    }
    const provincias=require('../../logic/provincias.json')
    this.comunidades=provincias
  }
};
</script>

<style lang="css" scoped>
.icon-success {
  color: #5CB85C;
}

.icon-yellow {
  color: #f9ff00;
}

.icon-blue {
  color: #0325e5;
}

.icon-red {
  color: #e50303;
}

.icon-brown {
  color: #b44f1b;
}

.row {
  padding-top: 4rem;
}

.table {
  font-size: medium;
}

.div_equipo {
  border-radius: 5px;
  box-shadow: 0 0 0 1px #387dfd;
  margin: 2rem auto;
  padding: 1rem;

}

.btnAzul {
  background: #387dfd;
  color: white;
  font-size: x-large;
}

.btnAzulPequeño {
  background: #387dfd;
  color: white;
  font-size: large;
}

.intern_form {
  width: 100%;
  display: grid;
  grid-template-columns: 1fr;
  font-size: medium;
  padding: 10px;
}

.margin_down {
  margin-bottom: 10px;
}

</style>

