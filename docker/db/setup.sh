#!/bin/bash

if [ "$1" == "build" ]; then
	sudo docker rmi hariot/db
	sudo docker build -t hariot/db .
fi

if [ "$1" == "run" ]; then

	sudo docker run --name hariot_db -it -p 3306:3306 --rm -d hariot/db
fi
if [ "$1" == "stop" ]; then
	sudo docker stop hariot_db
fi
