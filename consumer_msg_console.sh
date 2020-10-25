#!/bin/bash
echo "kafka consumer for topic :"$1
docker exec -it sparkstreaming-kafka bash /opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic $1