from pymongo import MongoClient
from datetime import datetime
import pprint
import serial
import time
import socket
import os
import sys

# taking in port number and baudrate for arduino from the main arguements
ser = serial.Serial("/dev/ttyACM"+str(sys.argv[1]), int(sys.argv[2]))

def main():
    wrongPin = True
    count = 0
    
    while True:
        # retrieves pin entered and checks if pin exists in the database
        read_ser =  int(ser.readline())
        wrongPin = validate_pin(read_ser)

        # gets name if available and logs the informtion to database
        name = get_name(read_ser)
        log(name, timestamp(), wrongPin)

        #if wrong pin is entered and count less than 5 tell user to return pin
        #     else open door by sending arduino signals
        if((wrongPin) and count < 5):
            ser.write('0'.encode())
            count += 1
        else:
            ser.write('1'.encode())
            count = 0
            wrongPin = True

        #if count is 5 then send client signal to tke pic
        if(count >= 5):
            ser.write('5'.encode())
            count = 0
            # ip address and port of client are taken in from main arguements
            take_pic(str(sys.argv[3]), int(sys.argv[4]))
            
        print(read_ser)
        print(count)
    return;

""" Takes the pin entered and cross checks it with pin from database """
def validate_pin(enteredPin):
    # port for the database is taken from main arguements
    client = MongoClient('localhost', int(sys.argv[5]))
    db = client['security']
    collection = db['pin']

    # retrieve all collection from database
    getPin = collection.find({})

    #loop through all pins to check if pin exist in database
    for PIN in getPin:
        if enteredPin == int(PIN['pin']):
            return False;
        else:
            return True;

""" Takes the pin entered and if it matches with database then returns the
         corresponding name or returns null"""
def get_name(pin):
    # port for the database is taken from main arguements
    client = MongoClient('localhost', sys.argv[5])
    db = client['security']
    collection = db['pin']
    # retrieve all collection from database
    getPin = collection.find({})
    
    #loop through all pins to check if pin exist in database and return name
    for PIN in getPin:
        if enteredPin == int(PIN['pin']):
            return PIN['name'];
    return "null";

""" Get time and date to log the information """
def timestamp():
    timestamp = datetime.datetime.now().strftime("%Y-%m-%d_%H:%M:%S")
    return timestamp;

""" Logs the entry to a database """
def log(name, datetime, valid):
    os.chdir("/home/pi/Documents/SYSC3010/SYSC3010/EntranceSecurity/src/")
    os.system("java addLogDatabase " + name + " " + datetime + " "+ valid)
    return;

""" Send Signal to client and receives pic"""
def take_pic(ip, port):
    send_data(ip, port)
    receive_pic()
    return;

""" Send data to client indicating if client should take a picture """
def send_data(ip, port):
    s = socket.socket()
    s.connect((ip, port))
    s.send(b'1')
    return;

""" Receives the pic taken from the client """
def receive_pic():
    os.chdir("/home/pi/Documents/SYSC3010/SYSC3010/rpiServer/")
    os.system("java tcpServer localhost")
    return;

if __name__ == "__main__":
    main()
