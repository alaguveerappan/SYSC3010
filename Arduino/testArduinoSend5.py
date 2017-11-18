import serial
import time
import socket
import os

ser = serial.Serial("/dev/ttyACM0", 9600)
pin = 1234
count = 0

s = socket.socket()
s.connect(('192.168.43.37', 5054))

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
            
            s.send(b'1')
            os.chdir("/home/pi/Desktop/tcp")
            os.system("java Server localhost")

    print(count)

