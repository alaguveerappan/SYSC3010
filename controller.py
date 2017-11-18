from time import sleep
from datetime import datetime
import datetime
import RPi.GPIO as GPIO
import os
import socket
from motionSensor import motionSensor
from camera import camera
from led import led

sock = socket.socket()
sock.bind(('', 5055))
    
""" Main """
def main():
    ms = motionSensor()
    c = camera()
    l = led()
        
    l.power(0)
    timeOut = datetime.datetime.now() + datetime.timedelta(seconds = 6)
    placeholder = True
    while True:
        #wait for motion then turn on led and take a picture
        if (ms.detect()):
            timeOut = datetime.datetime.now() + datetime.timedelta(seconds = 6)
            l.power(1)
            print("in")
            pics = picpic()
            if (pics):
                name = timestamp()
                c.capturePic(name)
                sendPic(name + '.jpg')
        if(datetime.datetime.now() > timeOut):
            l.power(0)
    return;

def picpic():
    sock.listen(1)
    data, addr = sock.accept()
    received_data = data.recv(1024)
    print(received_data)
    return(received_data == '1');
    
""" Creating timestamp so name of the pic is unique use ("%Y-%m-%d_%H:%M:%S")"""
def timestamp():
    timestamp = datetime.datetime.now().strftime("%H:%M:%S")
    return timestamp;

def sendPic(filename):
    os.chdir("/home/pi/Desktop/TCP/")
    os.system("java ClientCameraTest " + filename)
    return;

if __name__ == "__main__":
    main()
