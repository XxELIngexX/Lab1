# TALLER 1: APLICACIONES DISTRIBUIDAS (HTTP, SOCKETS, HTML, JS,MAVEN, GIT)
Se desarrolará una aplicación eficiente y multiusuario que utiliza la API gratuita de " https://www.omdbapi.com/ " para consultar información de películas. La arquitectura incluye un cliente web asíncrono en el navegador, un servidor que actúa como gateway y una fachada de servicios con caché para evitar consultas repetidas al API. El proyecto se gestiona con Maven, Git y GitHub. El backend está desarrollado exclusivamente en Java, sin utilizar frameworks como SPRING. La interfaz web, implementada en HTML y JS, es simple y no depende de librerías complejas.

## Requyisitos previos
es importante contar con las siguientes herramientas:
- Maven
- JDK 17 (java)

nota: si por algun motivo el projecto no lo puede ejecutar, prube agregando la siguiente propiedad (antes de las dependencias) en el pom:
~~~
<properties>
<maven.compiler.source>1.8</maven.compiler.source>
<maven.compiler.target>1.8</maven.compiler.target>
</properties>
~~~
## Empezar
### Instalación de Maven:
como se explico anteriormente el proyecto usa la herramienta Maven, para la instalacion en el equipo local ouede seguir el instructivo de instalacion en el seguiente link https://maven.apache.org/install.html

### descargar el proyecto
Proporciona el comando para clonar el repositorio desde GitHub. Por ejemplo:
