# Docker para crear la imagen de la aplicación web del frontend.
#
# Se utiliza un Dockerfile multietapa. La imagen final tiene un
# apache básico con los ficheros estáticos de la aplicación compilada
# y minimizada.
#
# La creación de la imagen requiere configuración a través de una
# variable de entorno, VUE_APP_SERVER_API_URL, que indica la URL
# del backend. No debe acabar en '/' y puede ser una ruta relativa
# del punto donde se sirve el frontend.
#
# Ejemplo de build:
#
# docker build -t programame/ucrania2022/frontend --build-arg VUE_APP_SERVER_API_URL=api .
#
# Ejemplo de lanzamiento:
#
# docker run --rm \
#        --name ucrania2022-frontend \
#        -p 80:80 \
#        programame/ucrania2022/frontend


###################################################
#
#         Imagen donde se hace la build
#
###################################################
FROM node:16-alpine AS builder-frontend
LABEL autodelete-programame="true"
# Para borrarla luego (tiene que ser a mano)
# list=$(docker images -q -f "dangling=true" -f "label=autodelete-programame=true")
# if [ -n "$list" ]; then
#      docker rmi $list
# fi

WORKDIR /app

# Copiamos solo el package.json y hacemos el install, porque
# esto cambiará poco y así reaprovechamos la cache de Docker
# para la descarga de paquetes de node.

COPY ./package*.json ./
RUN npm install

# Enviamos la variable de entorno con la URL del backend
# (URL a la que se conectará el frontend)
ARG VUE_APP_SERVER_API_URL

# Copiamos todo lo demás.
COPY ./jsconfig.json ./babel.config.js ./vue.config.js ./
COPY ./public public
COPY ./src src

# Hacemos la  build de producción.
RUN npm run build
# El resultado se queda en ./dist/

###################################################
#
#   Imagen final
#
###################################################

# La imagen final es una web estática. Usamos Apache.
FROM httpd:alpine

# Copiamos el resultado de la build de la imagen anterior en el directorio
# que será servido por Apache

COPY --from=builder-frontend /app/dist/favicon.ico /app/dist/index.html /usr/local/apache2/htdocs/
COPY --from=builder-frontend /app/dist/css /usr/local/apache2/htdocs/css
COPY --from=builder-frontend /app/dist/fonts /usr/local/apache2/htdocs/fonts
COPY --from=builder-frontend /app/dist/js /usr/local/apache2/htdocs/js
