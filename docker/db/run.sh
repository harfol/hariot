!/bin/bash

sudo docker rmi hariot/db
sudo docker build -t hariot/db .
sudo docker run --name hariot_db -it -p 3306:3306 --rm -d hariot/db
