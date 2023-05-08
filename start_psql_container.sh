#!/bin/bash
docker run -p 5442:5432 --name karate_postgres \
-e POSTGRES_DB=karatedb \
-e POSTGRES_USER=karateuser \
-e POSTGRES_PASSWORD=karateuser \
-d postgres:alpine