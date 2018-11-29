"""
     Boxever Kafka Error queue handler
"""
from kafka import KafkaConsumer
from kafka import KafkaProducer
import requests
import os
import time

KAFKA_BROKER = 'localhost:9092'
KAFKA_TOPIC = 'boxever-error'
KAFKA_GROUP = 'boxever-consume-error-group'


# Consume messsages from kafka boxever-error topic
def consumeMessages(f):
     
     consumer = KafkaConsumer(KAFKA_TOPIC,
                              group_id = KAFKA_GROUP,
                              bootstrap_servers = [KAFKA_BROKER])
     for message in consumer:
        print ("%s:%d:%d: key=%s value=%s" % (message.topic, message.partition,
                                             message.offset, message.key,
                                             message.value))
        f.write('ERROR: ' + str(time.time()) + ' - MSG: ' + str(message.value) + '\n') 
          

if __name__ == '__main__': # only run command like if it is called fromt he command line
    filename = 'kafka-error.log'

    if os.path.exists(filename):
        append_write = 'a' # append if already exists
    else:
        append_write = 'w' # make a new file if not
    f = open(filename,append_write)

    while True:
        consumeMessages(f)
    f.close()


