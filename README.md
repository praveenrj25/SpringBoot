# SpringBoot
RESTful webservices using spring boot and hibernate framework.

This is a simple [Event](https://github.com/praveenrj25/SpringBoot/tree/master/spring-boot-rest/src/main/java/com/rest/springboot/model) API, exposes to **create**, **update** and **delete** events. Also have the functionalities to *getAllEvents*, *getEventByID* and *search*.

## Run Configuration

Run As SpringBootApplication through IDE. And in browser hit **_http://localhost:8080_**

`create` POST http://localhost:8080/api/events and pass Event Resquent Body

`update` PUT http://localhost:8080/api/events/update/{id} and pass Event Resquent Body

`delete` DELETE http://localhost:8080/api/events/delete/{id}

`getAllEvents` GET http://localhost:8080/api/events

`getEventById` GET http://localhost:8080/api/events/{id}

`search` GET http://localhost:8080/api/events/serach?q=
