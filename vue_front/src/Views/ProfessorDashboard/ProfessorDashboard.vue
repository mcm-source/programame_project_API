<template lang="html">
  <div class="container">
    <div class="row">
      <div class="col text-left">

        <h2>Bienvenido/a profesor/a
          <button type="button" class="btnAzul float-right" aria-label="Left Align" v-on:click=openNewTeamForm()>
            Añadir Equipo
          </button>
        </h2>
        <!--        formulario de crear equipo-->
        <div class="div_equipo " v-show="addTeam">
          <div class="intern_form">
            <div>
              <b-button type="button" class="btn btn-default float-right" aria-label="Left Align"
                        v-on:click="addTeam=false">
                <span class="glyphicon glyphicon-remove icon-red" aria-hidden="true"></span>
              </b-button>
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

            <button type="button" class="btn btn-default" aria-label="Left Align" v-on:click="openNewTeamForm()">
              Añadir Equipo
            </button>
          </div>
        </div>
        <!--        bloque por cada equipo-->
        <div class="div_equipo" v-for="(equipo, index) in equipos">
          <h3>{{ equipo.name }}
            <b-button type="button" class="btn btn-default float-right" aria-label="Left Align">
              <span class="glyphicon glyphicon-remove icon-red" aria-hidden="true"></span>
            </b-button>
            <b-button type="button" class="btn btn-default float-right" aria-label="Left Align"
                      v-on:click="newFormTriggerChange(index,0, null)">
              <span class="glyphicon glyphicon-pencil icon-brown" aria-hidden="true"></span>
            </b-button>
          </h3>
          <h4>{{ equipo.teamMembers }}</h4>
          <!--        formulario de modificar equipo-->
          <div class="div_equipo " v-show="booleanTriggers[index][0]">
            <div class="intern_form">
              <div>
                <b-button type="button" class="btn btn-default float-right" aria-label="Left Align"
                          v-on:click="newFormTriggerChange(index,0, null)">
                  <span class="glyphicon glyphicon-remove icon-red" aria-hidden="true"></span>
                </b-button>
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
                      v-on:click="newFormTriggerChange(index,0, null)">
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
                  <b-button type="button" class="btn btn-default" aria-label="Left Align"
                            v-on:click="newFormTriggerChange(index,2,index2)">
                    <span class="glyphicon glyphicon-pencil icon-brown" aria-hidden="true"></span>
                  </b-button>
                  <b-button type="button" class="btn btn-default" aria-label="Left Align"
                            v-on:click="tabla(booleanTriggers[index][1])">
                    <span class="glyphicon glyphicon-remove icon-red" aria-hidden="true"></span>
                  </b-button>
                </td>
              </tr>
              </tbody>
            </table>

            <!-- Formulario de modificar benefator -->
            <div class="div_equipo " v-show="booleanTriggers[index][2]">
              <div class="intern_form">
                <div>
                  <b-button type="button" class="btn btn-default float-right" aria-label="Left Align"
                            v-on:click="newFormTriggerChange(index,2, null)">
                    <span class="glyphicon glyphicon-remove icon-red" aria-hidden="true"></span>
                  </b-button>
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
                        v-on:click="newFormTriggerChange(index,2,null)">
                  Modificar Benefactor
                </button>
              </div>
            </div>

            <h4>
              <button type="button" class="btnAzulPequeño " aria-label="Left Align"
                      v-on:click="newFormTriggerChange(index,1, null)">
                Añadir Patrocinador
              </button>
            </h4>
          </div>
          <!-- Form de añadir benefactor-->
          <div class="div_equipo " v-show="booleanTriggers[index][1]">
            <div class="intern_form">
              <div>
                <b-button type="button" class="btn btn-default float-right" aria-label="Left Align"
                          v-on:click="newFormTriggerChange(index,1, null)">
                  <span class="glyphicon glyphicon-remove icon-red" aria-hidden="true"></span>
                </b-button>
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
                      v-on:click="newFormTriggerChange(index,1,null)">
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
import Navigation from "../../components/Navigation";
import auth from "../../logic/auth";
import {TokenUtils} from "../../services/TokenUtils";
import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css"

let provincias
export default {
  name: "ProfessorDashboard",
  data() {
    return {
      show: false,
      addTeam: false,
      radioButtonsCuantia: 0, //0:complexDonation, 1:simpleDonation
      comunidad: '',
      booleanTriggers: [[false, false, false], [false, false, false]], //editTeam, addSponsor, editSponsor x Team
      teamFormModel: ["", "", "", ""], //0: name, 1:teamMembers, 2:schoolName, 3:location
      sponsorFormModelName:"",
      sponsorFormModel:[0,0,0,0], //0:amountForSimpleProblem, 1:amountForMediumProblem, 2:amountForHardProblem, 3:amount
      equipos: [{
        "id": 2,
        "name": "nombre1",
        "teamMembers": "a, b y super c",
        "schoolname": "escuela1",
        "location": "Pontevedra",
        "teacher": null,
        "listSponsors": [{
          "id": 34,
          "name": "ssponsor",
          "team": null,
          "simpleDonation": {
            "id": 56,
            "amount": 20.0,
            "sponsor": null
          },
          "complexDonation": null
        }, {
          "id": 36,
          "name": "ssponsor222",
          "team": null,
          "simpleDonation": null,
          "complexDonation": {
            "id": 55,
            "amountForSimpleProblem": 4.0,
            "amountForMediumProblem": 8.0,
            "amountForHardProblem": 12.0,
            "sponsor": null
          }
        }]
      },
        {
          "id": 98,
          "name": "nombre2",
          "teamMembers": "Pin y pon",
          "schoolname": "escuela1",
          "location": "Albacete",
          "teacher": null,
          "listSponsors": [{
            "id": 40,
            "name": "spunsor",
            "team": null,
            "simpleDonation": null,
            "complexDonation": {
              "id": 85,
              "amountForSimpleProblem": 2.0,
              "amountForMediumProblem": 6.0,
              "amountForHardProblem": 15.0,
              "sponsor": null
            }
          }
          ]
        }
      ],

      comunidades: [{
        'nombre': 'Galicia',

      },
        {
          'nombre': 'Madrid',

        },
        {
          'nombre': 'Andalucía',

        }],
    }
  },
  methods: {
    handleClose() {
      this.show = false;
    },
    openNewTeamForm() {
      let x, y
      //Se cierra el resto de formularios que puedan estar abiertos
      try {
        for (x in this.booleanTriggers) {
          for (y in this.booleanTriggers[x]) {
            this.$set(this.booleanTriggers[x], y, false)
          }
        }
      } catch (exception) {

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
            this.$set(this.booleanTriggers[x], y, false)
          }
        }
        this.addTeam=false
        //Si se va a editar equipo se cargan los datos en el formulario
        if (triggerIndex == 0) {
          this.teamFormModel[0] = this.equipos[index].name
          this.teamFormModel[1] = this.equipos[index].teamMembers
          this.teamFormModel[2] = this.equipos[index].schoolname
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
      this.$set(this.booleanTriggers[index], triggerIndex, !this.booleanTriggers[index][triggerIndex])

    },
    tabla(frase) {
      console.log(frase)
    }
  },
  mounted() {
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
  /*float: bottom;*/
  width: 100%;
  /*height: 80px;*/
  display: grid;
  grid-template-columns: 1fr;
  font-size: medium;
  /*border-radius: 4px;*/
  /*box-shadow: 0 1px 6px rgba(0,0,0,.2);*/
  padding: 10px;
}

.margin_down {
  margin-bottom: 10px;
}

</style>

