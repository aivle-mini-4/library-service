#!/bin/bash

docker run -d an990906/userinfomanagement:v1

docker run -d an990906/userhistorymanagement:v1

docker run -d an990906/scriptmanagement:v2

docker run -d an990906/pointmanagement:v1

docker run -d an990906/monthlysubscriptionmanagement:v1

docker run -d an990906/booksubscriptionmanagement:v1

docker run -d an990906/booksmanagement:v1

docker run -d an990906/authidentity:v1

docker run -d an990906/aiservice:v1

docker run -d an990906/admintask:v1


cd gateway
mvn spring-boot:run