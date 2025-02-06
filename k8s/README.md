## RabbitMQ on Kubernetes

### Install kubernetes cluster
On windows, start powershell with elevated privileges and run the following command
```shell
choco install minikube
```
### Start kubernetes cluster
Start minikube with the required resources
```shell
minikube start --memory=4096 --cpus=2
```
### Deploy RabbitMQ on the cluster
[How to configurer rabbitmq on k8s](./rabbitmq/README.md)