#!/bin/bash
mvn spring-boot:build-image
k3d image import kubernetes-demo:0.0.1-SNAPSHOT -c mycluster
kubectl delete -f k8s/k8s.yaml
kubectl apply -f k8s/k8s.yaml
