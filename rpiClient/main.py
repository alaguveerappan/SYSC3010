from time import sleep
from datetime import datetime
import datetime
import os
import socket
from controller import controller

sock = socket.socket()
sock.bind(('', 5059))

""" Main """
def main():
    c = controller()

    timeOut = datetime.datetime.now() + datetime.timedelta(seconds = 6)
    placeholder = True

    while True:
        c.light(0)
        #wait for motion then turn on led and take a picture
        detect_ms = c.motionSensed()
        if (detect_ms):
            timeOut = datetime.datetime.now() + datetime.timedelta(seconds = 6)
            c.light(1)

        sig = signal(detect_ms)
        name = timestamp()
        pic = c.takePic(name, detect_ms, sig)
        sendPic(name, pic)

        if(datetime.datetime.now() > timeOut):
            c.light(0)
    return;

def signal(detect):
    if (detect):
        data_r = received_data()
        return (data_r == '1');
    return False;

def received_data():
    sock.listen(1)
    data, addr = sock.accept()
    received_data = data.recv(1024)
    return received_data;

""" Creating timestamp so name of the pic is unique use ("%Y-%m-%d_%H:%M:%S")"""
def timestamp():
    timestamp = datetime.datetime.now().strftime("%H:%M:%S")
    return timestamp;

def sendPic(filename, send):
    if (send):
        os.chdir("/home/pi/Documents/SYSC3010SYSC3010/rpiClient/")
        os.system("java tcpClient " + filename + ".jpg")
    return;

if __name__ == "__main__":
    main()
