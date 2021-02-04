#!/bin/bash

set -e
cd ..
ROOT_DIR=$PWD
cd docker
ADMIN_ROOT_DIR=$ROOT_DIR/admin
SLAVE_ROOT_DIR=$ROOT_DIR/device

DOCKER_ROOT_DIR=$ROOT_DIR/docker
DOCKER_ADMIN_DIR=$ROOT_DIR/docker/admin
DOCKER_SLAVE_DIR=$ROOT_DIR/docker/slave

SECRET_FILE=$ROOT_DIR/my_secret.json

PRODUCT_KEY=$(jq -r .Product.Key <$SECRET_FILE )
ACCESS_KEY=$(jq -r .Access.Key <$SECRET_FILE )
ACCESS_SECRET=$(jq -r .Access.Secret <$SECRET_FILE )
ACCESS_CLIENT_ID=$(jq -r .Access.ClientId <$SECRET_FILE)
ACCESS_UID=$(jq -r .Access.UId <$SECRET_FILE)


if [ "$1" == "slave" -a "$2" == "run" ]; then
	if [ "$3" != "" ] && [ $3 -ge 0 ] && [ $3 -le 9 ]; then
		DEVICE_NAME=$(jq -r .Devices[$3].Name <$SECRET_FILE)
		DEVICE_SECRET=$(jq -r .Devices[$3].Secret <$SECRET_FILE)
		source $DOCKER_ROOT_DIR/$1/setup.sh $2 $3
	else 
		for i in {0..9}
		do
			DEVICE_NAME=$(jq -r .Devices[$i].Name <$SECRET_FILE)
			DEVICE_SECRET=$(jq -r .Devices[$i].Secret <$SECRET_FILE)
			source $DOCKER_ROOT_DIR/$1/setup.sh $2 $i
		done
	fi
	exit 0
fi

source $DOCKER_ROOT_DIR/$1/setup.sh $2

