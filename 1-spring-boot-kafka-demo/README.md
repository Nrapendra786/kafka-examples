1. Start up a single instance Zookeeper and single instance Kafka

```bash
$ docker-compose up -d
```


```bash
$ kafka-topics --bootstrap-server localhost:29092 --list | grep my-topic
$ kafka-topics --bootstrap-server localhost:29092 --describe --topic my-topic-java
$ kafka-topics --bootstrap-server localhost:29092 --delete --topic my-topic-java
$ kafka-topics --bootstrap-server localhost:29092 --delete --topic my-topic-dlt

$ kafka-topics --bootstrap-server localhost:29092 --create --topic my-topic-java --partitions 6 --replication-factor 1
$ kafka-topics --bootstrap-server localhost:29092 --create --topic my-topic-dlt --partitions 1 --replication-factor 1
```

2. Start up the consumer: `simple-consumer` with 1 then with 2 and then with 3 instances
3. 
Check the consumer groups:

```bash
$ kafka-consumer-groups --bootstrap-server localhost:29092 --list | grep my-group
$ kafka-consumer-groups --bootstrap-server localhost:29092 --describe --group my-group-java
$ kafka-consumer-groups --bootstrap-server localhost:29092 --describe --group my-group-dlt
```

Both Kafka clients interact with a topic which is created by the first client which starts up.

3. `simple-producer` exposes a REST endpoints where we can POST messages

```bash
$ echo "hello" | http post :8080/messages
$ echo 100 | http post :8080/many-messages
$ echo 5 | http post :8080/partition
```

4. Consumer Reset offset

What to do when there is no initial offset in Kafka or if the current offset no longer exists (for example the consumer group was deleted)
on the server we set it to `earliest` with. (default is `latest`)

```yaml
spring:
  kafka:
    consumer:
      auto-offset-reset: earliest
``` 

Reset offset (to-earliest, to-latest, to-offset <Long>) when the group is 'inactive' and only printing out what will be the change (--dry-run)

Assignments can only be reset if the group 'my-group-java' is inactive (meaning the consumer is not running)

```bash
$ kafka-consumer-groups --bootstrap-server localhost:29092 --reset-offsets --group my-group-java --to-earliest --topic my-topic-java --dry-run
$ kafka-consumer-groups --bootstrap-server localhost:29092 --reset-offsets --group my-group-java --to-earliest --topic my-topic-java --execute
$ kafka-consumer-groups --bootstrap-server localhost:29092 --group my-group-java --describe
```


View `__consumer_offsets` topic

```bash
$ kafka-console-consumer --bootstrap-server localhost:29092 --topic  __consumer_offsets --from-beginning --formatter kafka.coordinator.group.GroupMetadataManager\$OffsetsMessageFormatter
```

### Poison pill

```bash
$ echo "poison-pill" | http :8080/messages
````

Producer logs:

```bash
2023-11-09T11:44:48.767+01:00  INFO 60969 --- [nio-8080-exec-5] c.e.s.SimpleProducerApplication          : Sending payload poison-pill
2023-11-09T11:44:48.769+01:00  INFO 60969 --- [ad | producer-1] c.e.s.SimpleProducerApplication          : success, topic: my-topic, partition: 4, offset: 12
```

Consumer logs:
```bash
Caused by: java.lang.RuntimeException: failed processing message:poison-pill - 10 times
2023-11-09T11:46:37.671+01:00 DEBUG 61778 --- [ntainer#0-2-C-1] o.s.kafka.listener.DefaultErrorHandler   : Skipping seek of: my-topic-4@12
```

This is the default logic to `DefaultErrorHandler`. More details here: https://docs.spring.io/spring-kafka/reference/kafka/annotation-error-handling.html

### Cleanup

Delete a consumer group

```bash
$ kafka-consumer-groups --bootstrap-server localhost:9092 --group group-id --delete
```

Shut down cluster

```bash
$ docker-compose down
```


### Create Docker images and deploy to Kubernetes

```bash
$ cd spring-boot-kafka-producer
$ mvn spring-boot:build-image

...
Successfully built image 'docker.io/library/spring-boot-kafka-producer:0.0.1-SNAPSHOT'

$ cd spring-boot-kafka-consumer
$ mvn spring-boot:build-image

Successfully built image 'docker.io/library/spring-boot-kafka-consumer:0.0.1-SNAPSHOT'
```

### Deploy to K8S

```bash
$ kubectl apply -f ./k8s
```

https://spring.io/guides/topicals/spring-on-kubernetes/

------------------------------------------------------------------------------------------------------------------------

