#!/bin/bash

docker run --name my-app -p 3306:3306 -e MYSQL_ROOT_PASSWORD=foobar -d mysql
