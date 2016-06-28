#!/bin/bash 
COUNTER=0
while [  $COUNTER -lt 1000 ]; do
  /home/clove/gpmr/pet-race-devops/gce/kubernetes/cluster/kubectl.sh exec -it cassandra-data-${COUNTER} -- nodetool repair gpmr
  /home/clove/gpmr/pet-race-devops/gce/kubernetes/cluster/kubectl.sh exec -it cassandra-analytics-${COUNTER} -- nodetool repair gpmr
  let COUNTER=COUNTER+1 
done
