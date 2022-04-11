# Desplegado de la aplicación

En este directorio se incluyen ficheros .yml para el lanzamiento de la aplicación completa (frontend, backend y base de datos) usando Docker Compose.

Se utiliza una configuración "modular" en varios ficheros .yml de modo que dependiendo de las necesidades se pueden incorporar unas funcionalidades u otras. Además, se espera que la aplicación se configure a través de variables de entorno.

Para poner en marcha la aplicación, el primer paso es copiar el fichero ```env.template``` a un fichero nuevo ```.env``` y modificarlo para ajustar la configuración convenientemente. Una vez hecho, bastará lanzar todos los contenedores con Docker Compose:

```bash
docker-compose up -d
```

El fichero ```env.template``` tiene comentarios explicativos sobre las diferentes variables de configuración que hay que establecer, por lo que no se repetirá aquí.

Si quieres evitar el uso de tantos ficheros diferentes, puedes compactar todos en un ```docker-compose.yml``` único y autocontenido que puedes mover donde te parezca con

```bash
docker-compose config > docker-compose.yml
```

Para que el lanzamiento funcione, es necesario haber creado con antelación las imágenes del backend y del frontend. Se esperan dos nombres concretos en las imágenes. Si estás en el directorio raíz del repositorio (el padre a éste) puedes crear las imágenes con:

```bash
docker build -t programame/ucrania2022/api .
cd vue3_front
docker build -t programame/ucrania2022/frontend \
             --build-arg VUE_APP_SERVER_API_URL=api .
```

La creación de las imágenes puede llevar algo de tiempo, especialmente la primera vez.

Ten en cuenta que los contenedores lanzados _no serán autónomos_. Es necesario lanzar simultáneamente un proxy inverso que conecte con el frontend y el backend. En particular, si se quiere colocar la aplicación en ```$URL```, el proxy inverso deberá conectar esa URL con el puerto 80 del frontend, y ```$URL/api``` con el puerto 8080 (configurable) del backend.

La forma de montar el proxy inverso queda fuera de este README ```:-)```