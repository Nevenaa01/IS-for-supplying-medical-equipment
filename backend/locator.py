# import pika
# import json
# import random
# import time

# def generate_coordinates():
#     return {
#         'latitude': random.uniform(-90, 90),
#         'longitude': random.uniform(-180, 180)
#     }

# def send_coordinates(channel):
#     coordinates = generate_coordinates()
#     message = json.dumps(coordinates)

#     channel.basic_publish(
#         exchange='mapExchange',
#         routing_key='mapQueue',
#         body=message
#     )
#     print(f" [x] Sent coordinates: {message}")

# def main():
#     connection = pika.BlockingConnection(pika.ConnectionParameters('localhost', port=5672))
#     channel = connection.channel()

#     while True:
#         send_coordinates(channel)
#         time.sleep(5)

#     connection.close()

# if __name__ == '__main__':
#     main()


# import pika
# import json

# # Your JSON data
# json_data = {
#     'key1': 'value1',
#     'key2': 'value2',
#     'key3': 42
# }

# # Convert the JSON data to a JSON-formatted string
# json_message = json.dumps(json_data)

# parameters = pika.URLParameters('amqp://guest:guest@localhost:5672/%2F')
# connection = pika.BlockingConnection(parameters)
# channel = connection.channel()

# # Declare 'mapExchange' as a durable direct exchange
# channel.exchange_declare(exchange='mapExchange', exchange_type='direct', durable=True)

# # Declare 'mapQueue' as a durable queue
# channel.queue_declare(queue='mapQueue', durable=True)

# # Bind the 'mapQueue' queue to the 'mapExchange' exchange with the routing key 'mapQueue'
# channel.queue_bind(exchange='mapExchange', queue='mapQueue', routing_key='mapQueue')

# # Publish the JSON message to the 'mapExchange' exchange with the routing key 'mapQueue'
# channel.basic_publish(exchange='mapExchange', routing_key='mapQueue', body=json_message)

# connection.close()

import pika
import json
import random
import time
import ast
import sys
import re

def generate_coordinates(coordinates_string):
    # Split the string into individual coordinate pairs
    coordinate_pairs = coordinates_string.split(',')
    lat=coordinate_pairs[0].split(':')[1]
    lang=coordinate_pairs[1].split(':')[1]
    return coordinate_pairs

def send_coordinates(channel, coordinates_string):
    coordinates = coordinates_string
    json_message = json.dumps(coordinates)

    channel.basic_publish(
        exchange='mapExchange',
        routing_key='mapQueue',
        body=json_message
    )
    #print(f" [x] Sent coordinates: {generate_coordinates(coordinates_string)}")

def main(arg):
    parameters = pika.URLParameters('amqp://guest:guest@localhost:5672/%2F')
    connection = pika.BlockingConnection(parameters)
    channel = connection.channel()

    # Declare 'mapExchange' as a durable direct exchange
    channel.exchange_declare(exchange='mapExchange', exchange_type='direct', durable=True)

    # Declare 'mapQueue' as a durable queue
    channel.queue_declare(queue='mapQueue', durable=True)

    # Bind the 'mapQueue' queue to the 'mapExchange' exchange with the routing key 'mapQueue'
    channel.queue_bind(exchange='mapExchange', queue='mapQueue', routing_key='mapRoutingKey')
    send_coordinates(channel,arg[0])
    # while True:
        
    #     time.sleep(50)

    connection.close()

if __name__ == '__main__':
     main(sys.argv[1:])