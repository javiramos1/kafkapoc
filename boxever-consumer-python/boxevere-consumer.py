from kafka import KafkaConsumer
from kafka import KafkaProducer
import requests
import json
from time import sleep
from requests.auth import HTTPBasicAuth
 

API_KEY = 'iluatejwwu4e1qw9cfd6ulzqiqcei6yy'  # IL UAT
API_SECRET = ''
BASE_URL = 'https://api.boxever.com/v2'
BROWSER_CREATE = '/browser/create.json'
GUEST = '/guests'

session = requests.Session()
session.auth = (API_KEY, API_SECRET)


def consumeMessages():
     consumer = KafkaConsumer('boxever-consume',
                              group_id='boxever-consume-group',
                              bootstrap_servers=['localhost:9092'])
     for message in consumer:
          print ("%s:%d:%d: key=%s value=%s" % (message.topic, message.partition,
                                             message.offset, message.key,
                                             message.value))
          guest = json.loads(message.value)
          updateGuest(guest['href'], guest['guest'])

def updateGuest(href, guest):
     response = requests.post(href, auth=(API_KEY, API_SECRET), json=guest)
     response_json = response.json()

     print("Result Update: " + response_json)
if __name__ == '__main__': # only run command like if it is called fromt he command line

     try:
          consumeMessages()
     except:
          producer = KafkaProducer(bootstrap_servers=['localhost:9092'])
          print("An exception occurred")
          producer.send('boxever-error', b'error consumer')
          producer.flush()

