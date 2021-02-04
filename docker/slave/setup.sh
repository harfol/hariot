#!/bin/bash

SLAVE_ROOT_DIR=${SLAVE_ROOT_DIR:=$PWD../../device}
DOCKER_SLAVE_DIR=${DOCKER_SLAVE_DIR:=$PWD}

if [ "$1" == "stop" ]; then 
	sudo docker stop $( sudo docker ps -qa --filter name=hariot_slave )
	exit 0
fi

if [ "$1" == "build" ]; then 

	cd $SLAVE_ROOT_DIR
	make
	cd $DOCKER_SLAVE_DIR
	cp $SLAVE_ROOT_DIR/output/device-demo $DOCKER_SLAVE_DIR/bin/slave
	
	sudo docker rmi hariot/slave
	sudo docker build -t hariot/slave .
fi

if [ "$1" == "run" ]; then
	if [ $2 -ge 0 ] && [ $2 -le 9 ]; then
		sudo docker run --name hariot_slave_0$2 -it --rm -d \
			-e PRODUCT_KEY=$PRODUCT_KEY \
			-e DEVICE_NAME=$DEVICE_NAME \
			-e DEVICE_SECRET=$DEVICE_SECRET \
			hariot/slave
	fi
fi
