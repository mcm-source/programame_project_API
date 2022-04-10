
import { TokenUtils } from "./TokenUtils";

export class ApiUtils {
  static DOMAIN_REMOTE="http://3.232.204.125:8086";  // Esto no se usa para nada, ¿no? Si se usa,
  static DOMAIN_URL = "http://localhost:8096";       // habría que permitir su configuración externa
  static DOMAIN_URL_IN_USE=process.env.VUE_APP_SERVER_API_URL || "http://localhost:8092";

  /*
  static makePost(endPoint, object) {
    TokenUtils.getToken();
    let response = fetch(this.DOMAIN_URL + endPoint, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        username,
        password,
      }),
    });
    if (response.status === 401) {
      goto();
    } else {
      r = response.json();
      return r;
    }
  }
  */

  static async makeAuthrorizePost(endPoint, object) {
    console.log(endPoint);
    TokenUtils.getToken();
    let aut = "Bearer " + TokenUtils.getToken();
    console.log(this.DOMAIN_URL_IN_USE+endPoint);
    const response = await( fetch(this.DOMAIN_URL_IN_USE+endPoint, {
      method: "POST",
      headers: {
        Authorization: aut,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(object),
    }))
    return response.status;
  }

  static async makeGeneralGetData(endPoint) {
    console.log(endPoint);
    console.log(this.DOMAIN_URL_IN_USE+endPoint);
    const response = await fetch(this.DOMAIN_URL_IN_USE+endPoint, {
      method: "GET",
      headers: {

      },
    });
    return await response.json();
  }

  static async makeAuthrorizeGetData(endPoint) {
    console.log(endPoint);
    let aut = "Bearer " + TokenUtils.getToken();
    console.log(this.DOMAIN_URL_IN_USE+endPoint);
    const response = await fetch(this.DOMAIN_URL_IN_USE+endPoint, {
      method: "GET",
      headers: {
        Authorization: aut
      },
    });
    return await response.json();
  }

  static async makeAuthrorizeGetDataSimple(endPoint) {
    console.log(endPoint);
    let aut = "Bearer " + TokenUtils.getToken();
    console.log(this.DOMAIN_URL_IN_USE+endPoint);
    const response = await fetch(this.DOMAIN_URL_IN_USE+endPoint, {
      method: "GET",
      headers: {
        Authorization: aut
      },
    });
    return response.text();
  }

  static async makeAuthrorizeDeleteData(endPoint) {
    console.log(endPoint);
    let aut = "Bearer " + TokenUtils.getToken();
    console.log(this.DOMAIN_URL_IN_USE+endPoint);
    const response = await fetch(this.DOMAIN_URL_IN_USE+endPoint, {
      method: "DELETE",
      headers: {
        Authorization: aut
      },
    });
    return response.status;
  }
  static async makeAuthrorizeDeleteRoot(endPoint, object) {
    console.log(endPoint);
    TokenUtils.getToken();
    let aut = "Bearer " + TokenUtils.getToken();
    console.log(this.DOMAIN_URL_IN_USE+endPoint);
    const response = await(fetch(this.DOMAIN_URL_IN_USE+endPoint, {
      method: "DELETE",
      headers: {
        Authorization: aut,
        "Content-Type": "application/json",
      },
      body: (object.email),
    }))
    return response.status;
  }


}
