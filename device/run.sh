#!/bin/bash



dm_name=mqtt-basic-sp-demo
sp_name=mqtt-basic-sp-demo
id=(${1//-/ })
action=$2



for i in $id
do
    case $action in
    dm)
        ${PWD}/output/$dm_name  >device_$i.txt 2>&1 &
        ;;
    sp)
        ${PWD}/output/$sp_name  >device_$i.txt 2>&1 &
        ;;
    *)
        ;;
    esac
done
job_pids=$(ps -a | grep mqtt-basic | cut -d ' ' -f1)

