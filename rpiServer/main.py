from pymongo import MongoClient
from datetime import datetime
import datetime
import pprint
import serial
import time
import socket
import os
import sys

ser = serial.Serial("/dev/ttyACM"+str(sys.argv[1]), int(sys.argv[2]))

""" acm 0 or 1, baudrate, ip, port, dbport"""
def main():
    wrongPin = True
    count = 0
    
    while True:
        read_ser =  int(ser.readline())
        
        wrongPin = validate_pin(read_ser)

        name = get_name(read_ser)
        
        if((wrongPin) and count < 5):
            ser.write('0'.encode())
            count += 1
        else:
            ser.write('1'.encode())
            count = 0
            
        log(name, timestamp(), wrongPin)
        
        if(count >= 5):
            ser.write('5'.encode())
            count = 0
            take_pic(str(sys.argv[3]), int(sys.argv[4]))
            
        print(read_ser)
        print(count)
    return;

def validate_pin(enteredPin):
    client = MongoClient('localhost', int(sys.argv[5]))
    db = client['security']
    collection = db['pin']

    getPin = collection.find({})
        
    for PIN in getPin:
        if enteredPin == int(PIN['pin']):
            return False;
        else:
            return True;

def get_name(enteredPin):
    client = MongoClient('localhost', int(sys.argv[5]))
    db = client['security']
    collection = db['pin']

    getPin = collection.find({})

    for PIN in getPin:
        if enteredPin == int(PIN['pin']):
            return str(PIN['name']);
    return "null";

def log(name, datetime, valid):
    os.chdir("/home/pi/Documents/SYSC3010/SYSC3010/EntranceSecurity/src/")
    classpath = "-classpath .:../external\ libraries/java-json.jar:..\external\ libraries/gson-2.8.2.jar:../external\ libraries/mongo-java-driver-3.5.0.jar"
    os.system("java " + classpath + " addLogDatabase " + name + " " + datetime + " " + str(not valid))
    return;

def take_pic(ip, port):
    send_data(ip, port)
    receive_pic()
    return;

def send_data(ip, port):
    s = socket.socket()
    s.connect((ip, port))
    s.send(b'1')
    return;

def receive_pic():
    os.chdir("/home/pi/Documents/SYSC3010/SYSC3010/rpiServer/")
    os.system("java tcpServer localhost")
    return;

def timestamp():
    timestamp = datetime.datetime.now().strftime("%H:%M:%S")
    return timestamp;

if __name__ == "__main__":
    main()
