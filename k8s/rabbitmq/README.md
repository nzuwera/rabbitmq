## RabbitMQ on Kubernetes as a single instance

### Create a k8s namespace
Create a namespace for development purpose only.
```shell
 kubectl create ns rabbitmq
```
### Deploy rabbitmq helm chart
Deploy bitnami/rabbitmq helm chart
```shell
 helm upgrade --install rabbitmq bitnami/rabbitmq --version 15.0.3 -f .\k8s\rabbitmq\rabbitmq-talisman-all-dev-values.yaml -n rabbitmq 
```
RabbitMQ can be accessed within the cluster on port 5672 at rabbitmq.rabbitmq.svc.cluster.local

### To access for outside the cluster, perform the following steps:

#### To Access the RabbitMQ AMQP port:
```shell
kubectl port-forward --namespace rabbitmq svc/rabbitmq 5672:5672
```
#### To Access the RabbitMQ Management interface:
```shell
kubectl port-forward --namespace rabbitmq svc/rabbitmq 15672:15672
```

    