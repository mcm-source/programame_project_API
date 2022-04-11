# Docker para crear la imagen del backend.
#
# Se utiliza un Dockerfile multietapa. La imagen final tiene un
# JRE 11 básico junto con la build compilada del frontend con
# un tomcat incrustado y minimizado.
#
# El lanzamiento de la imagen requiere proporcionar variables
# de entorno para indicar el acceso a la base de datos, el usuario
# y contraseña del administrador que se creará en el primer
# arranque de la aplicación y el puerto donde escuchará la
# aplicación.
#
# Para crear la imagen se puede usar algo como:
#
# docker build -t programame/ucrania2022/api .
#
# Ejemplo de lanzamiento:
#
# docker run --rm \
#       --name ucrania2022-api \
#       -e SPRINT_DATASOURCE_URL=jdbc:mysql://<urlDB>:3306/nombreDb \
#       -e SPRINT_DATASOURCE_USERNAME=usuario \
#       -e SPRINT_DATASOURCE_PASSWORD=passwd \
#       -e USERS_FIRSTADMIN_USER=test \
#       -e USERS_FIRSTADMIN_PASSWORD=12341234 \
#       -e SERVER_PORT=8080 \
#       -p 8080:8080 \
#       programame/ucrania2022/api



###################################################
#
#         Imagen donde se hace la build
#
###################################################
FROM gradle:jdk11 AS builder
LABEL autodelete-programame="true"
# Para borrarla luego (tiene que ser a mano)
# list=$(docker images -q -f "dangling=true" -f "label=autodelete-programame=true")
# if [ -n "$list" ]; then
#      docker rmi $list
# fi

WORKDIR /app

# Copiamos el código fuente, empezando por las herramientas
# para, idealmente, mejorar el uso de la cache imágenes de docker
COPY gradlew settings.gradle build.gradle ./
COPY ./gradle/ ./gradle/
COPY ./src/ ./src/

# Hacemos la build
RUN ./gradlew build

# El resultado se queda en ./build/libs
# Son varios .jar autocontenidos. El que hay que lanzar
# es programame_project_API-0.0.1-SNAPSHOT.jar

###################################################
#
#   Imagen final
#
###################################################


# La imagen final solo necesita el JRE. Usamos una imagen
# reducida.
FROM openjdk:11-jre-slim
LABEL autodelete-programame="true"
# Para borrarla luego (tiene que ser a mano)
# list=$(docker images -q -f "dangling=true" -f "label=autodelete-programame=true")
# if [ -n "$list" ]; then
#      docker rmi $list
# fi

WORKDIR /app

# Copiamos el resultado de la compilación
COPY --from=builder /app/build/libs/*.jar /app/

# Configuramos el punto de entrada
ENTRYPOINT ["java","-jar","/app/programame_project_API-0.0.1-SNAPSHOT.jar"]
