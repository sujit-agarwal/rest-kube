#!/bin/sh
sudo usermod -a -G microk8s knoldus;
sudo chown -f -R knoldus ~/.kube;
newgrp microk8s;
