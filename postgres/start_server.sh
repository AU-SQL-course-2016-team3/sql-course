#!/bin/bash

docker run --name app -p 5432:5432 -e POSTGRES_PASSWORD=foobar -d postgres
