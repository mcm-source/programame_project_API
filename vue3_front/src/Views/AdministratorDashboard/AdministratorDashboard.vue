<template>
  <div class="container">
    <div class="row">
      <div class="col text-left">

        <h2>Panel de administración
          <button type="button" class="btnAzul float-right" aria-label="Left Align" v-on:click=openNewUserForm()>
            Crear Cuenta
          </button>
        </h2>
        <confirm-dialogue ref="confirmDialogue"></confirm-dialogue>

        <!--        formulario de crear user-->
        <div class="div_tabla " v-show="addUserFormVision">
          <div class="intern_form">
            <div>
              <button type="button" class="btn btn-default float-right" aria-label="Left Align"
                        v-on:click="addUserFormVision=false">
                <span class="glyphicon glyphicon-remove icon-red" aria-hidden="true"></span>
              </button>
            </div>
            <label class="form-label">Derechos de administrador (Check para conceder): <input type="checkbox" id="checkbox" v-model="userFormModelRole"> </label>
            <label class="form-label icon-red">ATENCIÓN: Un usuario con derechos de administración deja de ser editable
              y tiene acceso a este mismo panel.</label>
            <label class="form-label">Nombre del Usuario:</label>
            <input
              v-model="userFormModel[0]"
              class="margin_down"
              type="text"
              required
              placeholder="Nombre del Usuario"
            >
            <label class="form-label">Correo electrónico:</label>
            <input
              v-model="userFormModel[1]"
              class="margin_down"
              type="text"
              required
              placeholder="Correo electrónico"
            >
            <label class="form-label">Contraseña:</label>
            <input
              v-model="userFormModel[2]"
              class="margin_down"
              type="password"
              required
              placeholder="Contraseña"
            >
            <label class="form-label">Repetir Contraseña:</label>
            <input
              v-model="userFormModel[3]"
              class="margin_down"
              type="password"
              required
              placeholder="Repetir Contraseña"
            >
            <button type="button" class="btn btn-default" aria-label="Left Align" v-on:click="createUser">
              Añadir User
            </button>
          </div>
        </div>


        <!--Tabla de usuarios-->
        <div class="div_tabla ">
          <table class="table table-bordered tabla_cli" id="tabla1">
            <thead>
            <tr>
              <th scope="col">Profesor</th>
              <th scope="col">Email</th>
              <th scope="col">Acceder a sus equipos</th>
              <th scope="col">Editar o eliminar cuenta</th>
            </tr>
            </thead>
            <tbody v-for="(teacher, index) in dbRows" :key=teacher.id>
            <tr>
              <td>{{ teacher.name }}</td>
              <td>{{ teacher.email }}</td>
              <td>
                <button type="button" class="btn btn-default" aria-label="Left Align"
                          v-on:click="goToProfessorDashboard(teacher.id)">

                  <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
                </button>
              </td>
              <td>
                <button type="button" class="btn btn-default" aria-label="Left Align"
                          v-on:click="openEditUserForm(index)">
                  <span class="glyphicon glyphicon-pencil icon-brown" aria-hidden="true"></span>
                </button>
                <button type="button" class="btn btn-default" aria-label="Left Align"
                          v-on:click=deleteUser(teacher.email,teacher.name)>
                  <span class="glyphicon glyphicon-remove icon-red" aria-hidden="true"></span>
                </button>
              </td>
            </tr>
            <!--        formulario de modificar usuario-->
            <tr class="" v-show="editionFormVisionTrigger[index]">
              <td colspan="4">
                <div class="intern_form">
                  <div>
                    <button type="button" class="btn btn-default float-right" aria-label="Left Align"
                              v-on:click="setTriggersToFalse">
                      <span class="glyphicon glyphicon-remove icon-red" aria-hidden="true"></span>
                    </button>
                  </div>
                  <label class="form-label">Nombre del Usuario:</label>
                  <input
                    v-model="userFormModel[0]"
                    class="margin_down"
                    type="text"
                    required
                    placeholder="Nombre del Usuario"
                  >
                  <label class="form-label">Correo electrónico:</label>
                  <input
                    v-model="userFormModel[1]"
                    class="margin_down"
                    type="text"
                    required
                    placeholder="Correo electrónico"
                  >
                  <label class="form-label">¿Desea cambiar su contraseña? (Check para editar): <input type="checkbox" v-model="editingPassword"> </label>
                  <label class="form-label" v-show="editingPassword">Nueva Contraseña:</label>
                  <input
                    v-show="editingPassword"
                    v-model="userFormModel[2]"
                    class="margin_down"
                    type="password"
                    required
                    placeholder="Nueva Contraseña"
                  >
                  <label class="form-label" v-show="editingPassword">Repetir Contraseña:</label>
                  <input
                    v-show="editingPassword"
                    v-model="userFormModel[3]"
                    class="margin_down"
                    type="password"
                    required
                    placeholder="Repetir Contraseña"
                  >
                  <button type="button" class="btn btn-default" aria-label="Left Align" v-on:click=editUser(index)>
                    Modificar User
                  </button>
                </div>
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
import {TokenUtils} from "../../services/TokenUtils";
import {ApiUtils} from "../../services/ApiUtils";
import ConfirmDialogue from "../../components/ConfirmDialogue";
// import "bootstrap";
// import "bootstrap/dist/css/bootstrap.min.css"

export default {
  name: "AdministratorDashboard",
  components: { ConfirmDialogue },
  data: () => ({
    show:false,
    addUserFormVision:false,
    dbRows: [],
    editionFormVisionTrigger:[],
    userFormModel:["","","",""],
    userFormModelRole:false,
    editingPassword:false,
    editing:false
  }),
  methods: {
    goToProfessorDashboard(teacherId){
      TokenUtils.setTeacherControl(teacherId)
      this.$router.push("/professorDashboard");
    },
    async getUsersFromDB(){
      let response, t
      this.dbRows=[]
      try {
        response = await ApiUtils.makeAuthrorizeGetData("/teacher/listTeacherData")
        this.dbRows=response
        if(this.dbRows==null){
          console.log('no hay profesores')
        }
        console.log(response)
      } catch (error) {
        console.log(error);
      }
      this.editionFormVisionTrigger.clear
      for(t in this.dbRows){
        this.editionFormVisionTrigger.push(false)
      }
    },
    openNewUserForm(){
      let i
      this.setTriggersToFalse()
      this.userFormModelRole=false
      for (i in this.userFormModel){
        this.userFormModel[i]=""
      }
      this.addUserFormVision=true
    },
    openEditUserForm(index){
      let i
      this.setTriggersToFalse()
      this.userFormModelRole=false
      this.editingPassword=false
      for (i in this.userFormModel){
        this.userFormModel[i]=""
      }
      //falta obtener rol
      this.userFormModel[0]=this.dbRows[index].name
      this.userFormModel[1]=this.dbRows[index].email
      this.$set(this.editionFormVisionTrigger, index, true)
    },
    secureNotEmptyFields(){
      let empty=false
      if((this.userFormModel[0]=="")||(this.userFormModel[1]=="")){
        empty=true
      }
      if(empty){
        alert("No se admiten campos vacíos.")
      }
      return !empty

    },
    async createUser(){
      if(this.secureNotEmptyFields()){
        let con, response
        try {
          if((this.userFormModel[2].length>3)&&(this.userFormModel[2]==this.userFormModel[3])){
            let name, email, password, passwordRepeat, hasUserAdminRole
            name=this.userFormModel[0]
            email=this.userFormModel[1]
            password=this.userFormModel[2]
            passwordRepeat=this.userFormModel[3]
            hasUserAdminRole=this.userFormModelRole

            response=await ApiUtils.makeAuthrorizePost("/auth/createUser", {
              hasUserAdminRole,
              email,
              password,
              passwordRepeat,
              name
            });
            console.log(response)
            if(response==201){
              console.log("Insertado correctamente")
              this.addUserFormVision=false
            }
          }else{
            alert('Las contraseñas no coinciden o tienen menos de 4 caracteres.')
          }
        } catch (error) {
          console.log(error);
          //this.error = true;
        }
        try{
          con=await this.getUsersFromDB()
        }catch (error){
          console.log(error)
        }
      }

    },
    async editUser(index){
      if(this.secureNotEmptyFields()){
        try{
          let continuar=false
          let response,name, email, password, passwordRepeat, isPasswordChange, id
          name=this.userFormModel[0]
          email=this.userFormModel[1]
          password=this.userFormModel[2]
          passwordRepeat=this.userFormModel[3]
          isPasswordChange=this.editingPassword
          id=this.dbRows[index].id
          console.log(id)
          if(!this.editingPassword){
            continuar=true
          }else{
            if((this.userFormModel[2].length>3)&&(this.userFormModel[2]==this.userFormModel[3])){
              continuar=true
            }else{
              alert('Las contraseñas no coinciden o tienen menos de 4 caracteres.')
              continuar=false
            }
          }
          if (continuar){
            console.log({
              id,
              isPasswordChange,
              email,
              password,
              passwordRepeat,
              name
            })
            response=await ApiUtils.makeAuthrorizePost("/auth/updateUser", {
              id,
              isPasswordChange,
              email,
              password,
              passwordRepeat,
              name
            });
            console.log(response)
            if(response==200){
              console.log("Modificado correctamente")
              this.setTriggersToFalse()
            }
          }
        }catch(error){
          console.log(error)
        }
        try{
          const con=await this.getUsersFromDB()
        }catch (error){
          console.log(error)
        }
      }

    },
    async deleteUser(correo, nombre){
      try{
        const ok = await this.$refs.confirmDialogue.show({
          title: '¿Eliminar usuario '+nombre+'?',
          message: 'ATENCIÓN: Se perderán también sus equipos.',
          okButton: 'Eliminar',
          cancelButton: 'Cancelar'
        })
        if(ok){
          let response
          let email=correo
          response=await ApiUtils.makeAuthrorizeDeleteRoot("/auth/deleteUser", {
            email
          });
          console.log(response)
        }
        const cons=this.getUsersFromDB()
      }catch (error){
        console.log(error)
      }

    },
    setTriggersToFalse(){
      let t
      for (t in this.editionFormVisionTrigger){
        this.$set(this.editionFormVisionTrigger, t, false)
      }
      this.addUserFormVision=false
    }
  }, async mounted(){
    try {
      let response
      //Este método devuelve a home si no hay sesión iniciada
      if(TokenUtils.getToken()==null){
        this.$router.push("/");
      }else{ //Si no es admin lo devuelve a home también
        const resAdmin= await ApiUtils.makeAuthrorizeGetDataSimple("/auth/isUserAdmin")
        if(resAdmin=="true"){
          response=this.getUsersFromDB()
        }else{
          this.$router.push("/");
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
.btnAzul {
  background: #387dfd;
  color: white;
  font-size: x-large;
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

.icon-blue {
  color: #0325e5;
}

.icon-red {
  color: #e50303;
}

.icon-brown {
  color: #b44f1b;
}



</style>
