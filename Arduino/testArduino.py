import serial
import time
ser = serial.Serial('/dev/ttyACM0', 9600)
ser.readline()
print(ser)
time.sleep(10)
print(ser)
print('hello')
