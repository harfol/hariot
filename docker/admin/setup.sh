#!?bin/bash

ADMIN_ROOT_DIR=${ADMIN_ROOT_DIR:=$PWD../../admin}
DOCKER_ADMIN_DIR=${DOCKER_ADMIN_DIR:=$PWD}

set -e

if [ "$1" == "build" ]; then
	rm -rf $DOCKER_ADMIN_DIR/bin
	mkdir -p $DOCKER_ADMIN_DIR/bin

	cd $ADMIN_ROOT_DIR
	mvn package
	cd $DOCKER_ADMIN_DIR && cp -f $ADMIN_ROOT_DIR/target/admin-latest-SNAPSHOT.jar $DOCKER_ADMIN_DIR/bin/

	sudo docker rmi hariot/admin
	sudo docker build -t hariot/admin .
fi

if [ "$1" == "run" ]; then
	sudo docker run --name hariot_admin -it --rm \
		-e PRODUCT_KEY=$PRODUCT_KEY \
		-e ACCESS_KEY=$ACCESS_KEY \
		-e ACCESS_SECRET=$ACCESS_SECRET \
    	-e ACCESS_CLIENT_ID=$ACCESS_CLIENT_ID \
		-e ACCESS_UID=$ACCESS_UID \
		--link hariot_db \
		-d hariot/admin 
fi

if [ "$1" == "stop" ]; then
	sudo docker stop hariot_admin
fi
	

