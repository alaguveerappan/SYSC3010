# formerly "testArduinoSend5.py"

import serial
import time
import socket
import os

ser = serial.Serial("/dev/ttyACM0", 9600)
pin = 1234
count = 0

def main():
    while True:
        read_ser =  int(ser.readline())
        print(read_ser)

        if read_ser == pin:
            ser.write('1'.encode())
            count = 0
            
        else:
            ser.write('0'.encode())
            count += 1
            
            if(count >= 1):
                ser.write('5'.encode())
                count = 0
                send_data('192.168.43.37', 5055)
                receive_pic()

        print(count)

def send_data(ip, port):
    s = socket.socket()
    s.connect((ip, port))
    s.send(b'1')
    return;

def receive_pic():
    os.chdir("/home/pi/Desktop/tcp")
    os.system("java Server localhost")
    return;

if "__name__" == "__main__":
    main()

