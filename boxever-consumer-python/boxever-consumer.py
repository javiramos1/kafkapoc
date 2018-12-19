"""
     Boxever Kafka Consumer that consumes member updates
      and updates Boxever using REST API
"""
from kafka import KafkaConsumer
from kafka import KafkaProducer
import requests
import os
import json
from time import sleep

from requests.auth import HTTPBasicAuth
 

API_KEY = os.environ.get('API_KEY', '') # IL UAT
API_SECRET =  os.environ.get('API_KEY', '')
BASE_URL =  os.environ.get('BASE_URL', 'https://api.boxever.com/v2')
BROWSER_CREATE = '/browser/create.json'
GUEST = '/guests'
KAFKA_BROKER =  os.environ.get('KAFKA_BROKER', 'localhost:9092')
KAFKA_TOPIC =  os.environ.get('KAFKA_TOPIC', 'boxever-consume')
KAFKA_GROUP =  'boxever-consume-group'

# set basic auth
session = requests.Session()
session.auth = (API_KEY, API_SECRET)

# Consume messsages from kafka boxever-consume topic
def consumeMessages():
     consumer = KafkaConsumer(KAFKA_TOPIC,
                              group_id = KAFKA_GROUP,
                              bootstrap_servers = [KAFKA_BROKER],
                              enable_auto_commit=False)
     for message in consumer:
          print ("%s:%d:%d: key=%s value=%s" % (message.topic, message.partition,
                                             message.offset, message.key,
                                             message.value))

                                             
          guest = json.loads(message.value)
          updateGuest(guest['href'], guest['guest']) # call API
          consumer.commit()
          sleep(1)

# Call Boxever API POST request
def updateGuest(href, guest):
     response = requests.post(href, auth=(API_KEY, API_SECRET), json=guest)
     response_json = response.json()

     print("Result Update: " + str(response_json))
if __name__ == '__main__': # only run command like if it is called fromt he command line

     try:
          while True:
               consumeMessages()
     except Exception as e:
          # send error message
          producer = KafkaProducer(bootstrap_servers=['localhost:9092'])
          print("An exception occurred: " + e.message)
          producer.send('boxever-error', b'error consumer')
          producer.flush()
          producer.close()

