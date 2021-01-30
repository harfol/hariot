!/bin/bash

echo 'step 1: start mysql.'
service mysql start

echo 'step 2: setup admin database. /sql/admin.sql'
mysql </sql/admin.sql
sleep 1

echo 'step 3: setup user="docker" pass="123456"'
mysql </sql/admin-secret.sql

echo 'step 3: remain...'
tail -f /dev/null
