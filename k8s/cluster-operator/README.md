## RabbitMQ Cluster on K8s

### Create a k8s namespace
Create a namespace for development purpose only.
```shell
 kubectl create ns rabbitmq-cluster
```
### Deploy rabbitmq helm chart
Deploy bitnami/rabbitmq-cluster-operator helm chart
```shell
 helm upgrade --install rabbitmq-cluster bitnami/rabbitmq-cluster-operator -n rabbitmq-cluster
```
Create cluster pod configuration details in `rabbitmq-cluster.yaml`
```yaml
apiVersion: rabbitmq.com/v1beta1
kind: RabbitmqCluster
metadata:
  name: rabbitmq
spec:
  replicas: 3
  persistence:
    storageClassName: standard
    storage: 8Gi
  resources:
    requests:
      cpu: 100m
      memory: 300Mi
    limits:
      cpu: 1000m
      memory: 400Mi
  service:
    type: ClusterIP
```
Apply pod configuration details:

```shell
kubectl apply -f rabbitmq-cluster.yaml -n rabbitmq-cluster
```

### To access for outside the cluster, perform the following steps:

#### To Access the RabbitMQ AMQP port:
```shell
kubectl port-forward --namespace rabbitmq svc/rabbitmq 5672:5672
```
#### To Access the RabbitMQ Management interface:
```shell
kubectl port-forward --namespace rabbitmq svc/rabbitmq 15672:15672
```

    