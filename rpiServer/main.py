from pymongo import MongoClient
import pprint
import serial
import time
import socket
import os

ser = serial.Serial("/dev/ttyACM0", 9600)


def main():
    wrongPin = True
    count = 0
    
    while True:
        read_ser =  int(ser.readline())
        print(read_ser)

        client = MongoClient('localhost', 27017)
        db = client['security']
        collection = db['pin']

        getPin = collection.find({})
        
        for pin in getPin:
            if read_ser == int(pin['pin']):
                wrongPin = False
                break
            
            else:
                wrongPin = True
                
        if((wrongPin) and count < 5):
            ser.write('0'.encode())
            count += 1
        else:
            ser.write('1'.encode())
            count = 0
            wrongPin = True
            
        if(count >= 5):
            ser.write('5'.encode())
            count = 0
            send_data('192.168.43.37', 5059)
            receive_pic()

        print(count)
    return;

def send_data(ip, port):
    s = socket.socket()
    s.connect((ip, port))
    s.send(b'1')
    return;

def receive_pic():
    os.chdir("/home/pi/Documents/SYSC3010/SYSC3010/rpiServer/")
    os.system("java tcpServer ")
    return;

if __name__ == "__main__":
    main()

