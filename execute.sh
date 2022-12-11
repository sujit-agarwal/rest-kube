#!/bin/sh
mvn clean install
docker build . -t rest-kube:local

docker save rest-kube > rest-kube.tar

microk8s ctr image import rest-kube.tar

helm install knoldus ./knoldus-app
