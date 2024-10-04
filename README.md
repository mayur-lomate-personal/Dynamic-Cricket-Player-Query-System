# Dynamic Cricket Player Query System

The Dynamic Cricket Player Query System is designed to facilitate flexible and efficient querying of cricket player data. This project utilizes Spring Boot and JPA Specification to enable dynamic queries, allowing users to construct complex search criteria at runtime. By leveraging the power of JPA Specification, the system provides a type-safe and efficient way to interact with the database, ensuring optimal performance while retrieving cricket player information. This project aims to enhance data accessibility and user experience through customizable querying capabilities.

## Installation

To run this project, you will need to create a JAR file and deploy it on an external Tomcat server.

The Tomcat architecture for reference is as follows.

![Screenshot](./help-images/TomcatArchitecture.png)

## API Reference

#### Dynamic Advanced Query

```http
  GET http://localhost:8090/JPASpecificationExample/players/adv?search=( firstName:nikhil ) OR ( totalRuns>400 AND lastName:Lomate ) :- returns player according to provided filters
```

#### Dynamic Normal Query

```http
  GET http://localhost:8090/JPASpecificationExample/players?search=firstName:mayur,'totalRuns>499,lastName:Lomate :- returns player according to provided filters

  Here, ',' stands for AND, ''' in predicate stands for OR, ':' stands for equality, '!' stands for negation, '>' stands for greater than, '<' stands for less than, and '~' stands for like.
```

#### To create a Player

```http
  POST http://localhost:8090/JPASpecificationExample/players :- Creates a player according to the given information
```

You can also refer to the Swagger documentation.