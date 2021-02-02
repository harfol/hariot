#!/bin/bash


if [ "$1" == "stop" ]; then 
	sudo docker stop $( sudo docker ps -qa --filter name=hariot_slave )
	exit 0
fi

cd ../../device/ && make
cd -
cp ../../device/output/device-demo bin/slave

sudo docker rmi hariot/slave
sudo docker build -t hariot/slave .

for i in {0..9};
do
	pk=$(jq -r .Product.Key <../../my_secret.json)
	dn=$(jq -r .Devices[$i].Name <../../my_secret.json)
	ds=$(jq -r .Devices[$i].Secret <../../my_secret.json)
	echo -e "running hariot_slave_0$i $pk $dn $ds"
	sudo docker run --name hariot_slave_0$i -it --rm -d \
		-e PRODUCT_KEY=$pk \
		-e DEVICE_NAME=$dn \
		-e DEVICE_SECRET=$ds \
		hariot/slave
done
