#!/bin/bash
echo "kafka producer for topic :"$1
docker exec -it sparkstreaming-kafka bash /opt/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic $1