import serial
import time
import socket

ser = serial.Serial("/dev/ttyACM1", 9600)
pin = 1234
count = 0

s = socket.socket()
host = '192.168.43.37'
port = 5050

s.connect((host, port))

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

    print(count)

