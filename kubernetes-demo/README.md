```bash
$ k3d cluster create mycluster 
```

```bash
$ kubectl apply -f k8s/privileges.yaml
```

```bash
$ mvn spring-boot:build-image
$ k3d image import kubernetes-demo:0.0.1-SNAPSHOT -c mycluster
# view images
$ docker exec -it  k3d-mycluster-server-0 crictl images
$ kubectl apply -f k8s/k8s.yaml
$ kubectl port-forward svc/kubernetes-demo 8080:8080
```

```bash
$ http :8080
```