!/bin/bash

step_no=0
function step_info()
{
	let step_no++
	echo -e "step $step_no : $*"
}

step_info 'start mysql.'
service mysql start

step_info 'setup database.'
mysql </schema/db.sql
sleep 1

step_info 'setup user="admin" pass="123456"'
mysql </schema/db-secret.sql >/dev/null

step_info 'remain...'
tail -f /dev/null
