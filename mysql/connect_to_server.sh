#!/bin/bash

docker run -it --link my-app:mysql --rm mysql sh -c 'exec mysql -h"mysql" -P"3306" -uroot -p"foobar"'
