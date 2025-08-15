# Legacy Java Pet Store.

El objetivo de este proyecto es proveer un ejemplo de una aplicación antigua implementada con Servlets.

La idea es analizar el funcionamiento de la aplicación y hacer una evaluación respecto a su Acoplamiento, Cohesion y Conascencia.

## Ejecutar la aplicación

Para ejecutar la aplicación debe contar con:

1. Docker
2. Java 11 (se recomienda utilizar sdk man)
3. Maven

Recuerde que todas las herramientas las proporciona Github Codespaces.

### 1. Compilación.

Una vez tenga instalada todas las dependencias compile la aplicación:

```sh
mvn clean package
```

### 2. Iniciar servidor web.

Para arrancar el servidor web puede ejecutar lo siguiente:

```sh
docker run -d --name tomcat  -p 8888:8080 tomcat:9.0
```

### 3. Instalar la aplicación Web.

Una vez que el servidor este corriendo, debe copiar el WAR generado en el paso 1.

```sh
docker cp target/web.war tomcat:/usr/local/tomcat/webapps
```

### 4. Acceder a la aplicación

Para acceder a la aplicación abra la siguiente URL: http://localhost:8888/web

Si esta utilizand Github Codespaces configure un puerto público de acuerdo a lo siguiente:  https://docs.github.com/en/codespaces/developing-in-a-codespace/forwarding-ports-in-your-codespace

## Luego de modificar codigo estos son los pasos

```sh
# Compilar el codigo
mvn clean package

# Desplegar la app
docker cp target/web.war tomcat:/usr/local/tomcat/webapps
```

## Tarea

1. Modificar la aplicación para que luego de que se agregue una mascota, se muestre un mensaje de "The pet has been added succsesfully !" y ademas desaparezca de la lista.
2. Implementar la opción "Cancel" en la pantalla de "Checkout", lo que haría que las mascotas vuelvan al listado de mascotas.