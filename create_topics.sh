#!/bin/bash
echo "creating kafka topic :"$1
docker exec -it sparkstreaming-kafka bash /opt/kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic $1