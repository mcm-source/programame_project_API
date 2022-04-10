# Frontend de la aplicación con Vue

El frontend es una _single page application_ hecha en Vue que se conecta a los endpoints del backend.

Para el desplegado final, se proporciona un Dockerfile que permite crear un contenedor que sirve, usando Apache, el contenido estático.

En el momento de crear la imagen es necesario especificar la URL raíz donde se encuentran los endpoints del backend. Esa variable de entorno se denomina ```VUE_APP_SERVER_API_URL```. El valor que se asigne no debe terminar en ```/``` porque la aplicación la añadirá automáticamente junto con el resto de la ruta de cada endpoint. Es posible especificar una URL completa (en cuyo caso el servidor correspondiente tendrá que tener activado CORS) o una ruta relativa al lugar desde donde se sirva el frontend. En ese caso será necesario configurar un proxy inverso que se encargue de entrar en el contenedor del backend y el del frontend.

Por ejemplo, si un proxy inverso se va a encargar de conectarse con el contenedor del frontend en ```$URL``` y de conectar el contenedor del backend en ```$URL/api```, la creación de la imagen puede hacerse con el siguiente comando:

``` bash
docker build -t programame/ucrania2022/frontend \
             --build-arg VUE_APP_SERVER_API_URL=api .
```

Aunque lo normal será lanzar la imagen con ```docker-compose```, un ejemplo de lanzamiento independiente es:

``` bash
docker run --rm \
        --name ucrania2022-frontend \
        -p 80:80 \
        programame/ucrania2022/frontend
```


# vue3_front

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Lints and fixes files
```
npm run lint
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).
