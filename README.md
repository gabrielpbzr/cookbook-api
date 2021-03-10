# Cookbook API

This project contains the backend for a cookbook app.

## What do I need to run this project?

- PostgreSQL 9.5+
- Java 8
- Maven 3

### Building and running

1. Clone this repository on your machine.
2. Run on your terminal 
```shell 
$ mvn compile exec:java
```
3. If you didn't define an environment variable `PORT` with an HTTP port value, the app will be available at port `7000`;

## How to use

Send your requests to the REST API. All endpoints are under `/api/recipes`.



