import serial
import time

ser = serial.Serial("/dev/ttyACM2", 9600)
pin = 1234
count = 0

while True:
    read_ser =  int(ser.readline())
    print(read_ser)

    if read_ser == pin:
        ser.write('1'.encode())
        count = 0
        
    else:
        ser.write('0'.encode())
        count += 1
        
        if(count >= 2):
            ser.write('5'.encode())
            count = 0

    print(count)
