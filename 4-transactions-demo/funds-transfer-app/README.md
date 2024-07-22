Create topics:

```bash
$ kafka-topics --bootstrap-server localhost:29092 --create --topic transfers --partitions 1 --replication-factor 1
$ kafka-topics --bootstrap-server localhost:29092 --create --topic balances --partitions 1 --replication-factor 1
```

```bash
kafka-console-producer --bootstrap-server localhost:29092 --topic transfers \
--property parse.key=true \
--property key.separator="|" \
--property value.serializer=org.springframework.kafka.support.serializer.JsonSerializer
```

```bash
1|{"from":"Alice", "to":"Bob", "amount":"10"}
2|{"from":"Alice", "to":"Bob", "amount":"20"}
3|{"from":"Alice", "to":"Bob", "amount":"30"}
4|{"from":"Alice", "to":"Bob", "amount":"40"}
5|{"from":"Alice", "to":"Bob", "amount":"50"}
6|{"from":"Alice", "to":"Bob", "amount":"60"}
```

```bash
$ kafka-console-consumer --bootstrap-server localhost:29092 --topic balances \
--property value.deserializer=org.apache.kafka.connect.json.JsonDeserializer 

$ kafka-console-consumer --bootstrap-server localhost:29092 --topic transfers \
--property value.deserializer=org.apache.kafka.connect.json.JsonDeserializer
```

```bash
$ kafka-console-consumer --bootstrap-server localhost:29092 --topic  __consumer_offsets --from-beginning --formatter kafka.coordinator.group.GroupMetadataManager\$OffsetsMessageFormatter
```

```bash
$ kafka-consumer-groups --bootstrap-server localhost:29092 --describe --group transfers-group
```

1. verify if there are not exceptions
2. introduce error and verify
3. add transaction support