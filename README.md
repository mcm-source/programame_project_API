## Cuestiones sobre el proyecto :seedling:

## Primer usuario con permisos de administrador

Este usuario se crea en la primera petición de login, nuestro plan era crearlo en la primera ejecución de la aplicación pero diversos problemas al crear el usuario con la
password y el encoding empleado nos lo han impedido.

Para cambiar el nombre y la password de este usuario debemos realizarmo desde el paquete de services en UserService:
![image](https://user-images.githubusercontent.com/68128045/162031027-c051bf93-3a7f-49ea-af7e-a1e57cc50272.png)

## Petición para datos de marcador

Para obtener los datos de sponsor y donaciones de un equipo en la API esta disponible la siguiente url:

http://domain/team/sponsorsdata/ {nombre del equipo}

Ejemplo de la información que obtenemos al realizar una petición:

![image](https://user-images.githubusercontent.com/68128045/162032938-b2cd649e-9990-4232-bda0-75605de48a9c.png)





