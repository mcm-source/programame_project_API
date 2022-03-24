export class TokenUtils {
  static token;
  static username;

  static getToken() {
    if (!this.token) {
      this.token = sessionStorage.getItem("token");
      console.log(this.token);
    }
    return this.token;
  }

  static getUsername(){
    if (!this.username){
      this.username=sessionStorage.getItem("username");
      console.log(this.username)
    }
    return this.username
  }

  static saveToken(newToken) {
    this.token = newToken;
    // this.username=username;
    sessionStorage.setItem("token", newToken);
    // sessionStorage.setItem("username", username);
  }

  static logOut(){
    sessionStorage.clear()
    this.token=null;
    // this.username=null;
  }


}
