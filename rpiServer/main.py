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
    wrongPin = True # initialize the wrongPin variable to True, until the correct pin is entered
    count = 0 # initialize number of times the pin was entered incorrectly to 0 
    
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
            ser.write('0'.encode()) # send signal to Arduino to keep lock locked and display Incorrect Pin message
            count += 1 # increment the number of times the pin was entered incorrectly
        else:
            ser.write('1'.encode()) # send signal to Arduino to open lock
            count = 0 # reset the count of incorrectly entered pins to 0 
            wrongPin = True

        #if count is 5 then send client signal to take pic
        if(count >= 5):
            ser.write('5'.encode()) # send signal to Arduino to time out
            count = 0 # reset the count of incorrectly entered pins to 0 
            # ip address and port of client are taken in from main arguments
            take_pic(str(sys.argv[3]), int(sys.argv[4]))
            
        print(read_ser) # print the entered pin
        print(count) # print the number of times that the pin was entered incorrectly
    return;

""" Takes the pin entered and cross checks it with pin from database """
def validate_pin(enteredPin):
    # port for the database is taken from main arguements
    client = MongoClient('localhost', int(sys.argv[5]))
    db = client['security']
    collection = db['pin']

    # retrieve all collection from database
    getPin = collection.find({})

    #loop through all pins to check if pin exists in database
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
    db = client['security'] # get the database named "security" 
    collection = db['pin'] # get the collection that contains the pins and users
    # retrieve all documents from collection
    getPin = collection.find({})
    
    #loop through all pins to check if pin exists in database and returns name
    #     associated with that particular pin or returns "null"
    for PIN in getPin:
        if enteredPin == int(PIN['pin']):
            return PIN['name'];
    return "null";

""" Get date and time to log the information """
def timestamp():
    timestamp = datetime.datetime.now().strftime("%Y-%m-%d_%H:%M:%S")
    return timestamp;

""" Logs the entry to a database """
def log(name, datetime, valid):
    # navigate to the appropriate directory to prepare for logging the PIN entry
    os.chdir("/home/pi/Documents/SYSC3010/SYSC3010/EntranceSecurity/src/")
    # add a log to the database containing the name associated with 
    #     the entered PIN, the date and time, and whether or not the PIN
    #     was valid
    os.system("java addLogDatabase " + name + " " + datetime + " "+ valid)
    return;

""" Send Signal to client and receives pic by calling the send_data and receive_pic defs"""
def take_pic(ip, port):
    send_data(ip, port)
    receive_pic()
    return;

""" Send data to client indicating if client should take a picture """
def send_data(ip, port):
    s = socket.socket() 
    try: # try to connect and send the msg if not close application
        s.connect((ip, port)) # connect to the IP address of the client at the correct port
        s.send(b'1') # send a '1' to the client to indicate that the client should take a picture
    except socket.error, msg:
        print " Connection refused"
        sys.exit(0)
    return;

""" Receives the pic taken from the client """
def receive_pic():
    # navigate to the directory that contains the tcpServer file to prepare
    #     to receive picture file
    os.chdir("/home/pi/Documents/SYSC3010/SYSC3010/rpiServer/") 
    # run the tcpServer file on the same network as the client in order to
    #     receive the file that the client sends
    os.system("java tcpServer localhost")
    return;

# required for python files with main() 
if __name__ == "__main__":
    main()
