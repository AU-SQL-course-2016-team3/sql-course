#!/bin/bash

./stop_server.sh
docker run --name app -p 5432:5432 -e POSTGRES_PASSWORD=foobar -d postgres
