#!/bin/sh
docker save rest-kube > rest-kube.tar
microk8s ctr image import rest-kube.tar


docker save openzipkin/zipkin > zipkin.tar
microk8s ctr image import zipkin.tar