#!/bin/bash


pk=$(jq .Product.Key <../my_secret.json)
mkdir -p $PWD/output/logs
for i in {0..9}
do
	dn=$(jq .Devices[$i].Name <../my_secret.json)
	ds=$(jq .Devices[$i].Secret <../my_secret.json)
	echo -e "$1 device-demo ${pk//\"/ } ${dn//\"/ } ${ds//\"/ }"
	${PWD}/output/device-demo ${pk//\"/ } ${dn//\"/ } ${ds//\"/ } &>${PWD}/output/logs/device-demo-$i.txt &
done
