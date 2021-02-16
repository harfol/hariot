#!/bin/bash


ACCESS_FILE=aliyun-access.env
DEVICE_FILE=aliyun-device.env
SECRET_FILE=../my_secret.json
echo -e "ALIYUN_ACCESS_KEY=$(jq -r .Access.Key <$SECRET_FILE )" >$ACCESS_FILE
echo -e "ALIYUN_ACCESS_SECRET=$(jq -r .Access.Secret <$SECRET_FILE )" >>$ACCESS_FILE
echo -e "ALIYUN_ACCESS_CLIENT_ID=$(jq -r .Access.ClientId <$SECRET_FILE)" >>$ACCESS_FILE
echo -e "ALIYUN_ACCESS_UID=$(jq -r .Access.UId <$SECRET_FILE)" >>$ACCESS_FILE
echo -e "ALIYUN_PRODUCT_KEY=$(jq -r .Product.Key <$SECRET_FILE )" >$DEVICE_FILE

DOCKER_DIR=$PWD
PRO_ACTION=
ACTION=
POST_ACTION=
BUILD_ARGS=""
BUILD_OBJ=""
function compile_admin()
{
	if [ -d admin/bin ] ; then
		rm -rf admin/bin/*
	else
		mkdir -p admin/bin
	fi
	cd ../admin/
	mvn clean && mvn package -Dadmin.version=latest-SNAPSHOT
	cd $DOCKER_DIR && cp -f ../admin/target/admin-latest-SNAPSHOT.jar admin/bin/
}
function compile_slave()
{
	if [ -d slave/bin ] ; then
		rm -rf slave/bin/*
	else
		mkdir -p slave/bin
	fi
	cd ../device/
	make clean && make
	cd $DOCKER_DIR && cp -f ../device/output/device-demo slave/bin/slave
}

case "$1" in
	compile)
		while [ -n "$2" ]; 
		do
			case "$2" in
				admin) compile_admin ;;
				slave) compile_slave ;;
				*) ;;
			esac
			shift
		done
		;;
	setenv)
		if [ "$2" == "slave" ] && [ -n "$3" ] && [ $3 -ge 0 ] && [ $3 -le 9 ]; then
			echo -e "ALIYUN_DEVICE_NAME=$(jq -r .Devices[$3].Name <$SECRET_FILE)" >>$DEVICE_FILE
			echo -e "ALIYUN_DEVICE_SECRET=$(jq -r .Devices[$3].Secret <$SECRET_FILE)" >>$DEVICE_FILE
		fi
		;;
	rmi)
		sudo docker rmi $(sudo docker images | grep 'hariot' | tr -s ' ' | cut -d' ' -f3)
		;;
	*)
		;;
esac
			

















