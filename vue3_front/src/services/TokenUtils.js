export class TokenUtils {
  static token;
  static teacherControl;


  static getToken() {
    if (!this.token) {
      this.token = sessionStorage.getItem("token");
      console.log(this.token);
    }
    return this.token;
  }
  static getTeacherControl(){
    return sessionStorage.getItem("teacherControl")
  }

  static setTeacherControl(value){
    this.teacherControl=value
    sessionStorage.setItem("teacherControl", value);
  }

  static saveToken(newToken) {
    this.token = newToken;
    this.teacherControl=null;
    sessionStorage.setItem("token", newToken);
    sessionStorage.setItem("teacherControl", null);
  }

  static logOut(){
    sessionStorage.clear()
    this.token=null;
    this.teacherControll=null;
  }


}
