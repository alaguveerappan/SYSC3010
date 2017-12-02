from time import sleep
from datetime import datetime
from controller import controller
import datetime
import os
import socket
import sys

sock = socket.socket()
# taking in port numberas a main argument
sock.bind(('', int(sys.argv[1])))

""" Main """
def main():
    c = controller()
    # using timeout to turn off light
    timeOut = datetime.datetime.now() + datetime.timedelta(seconds = 6)
    placeholder = True

    while True:
        # turn off led at the start and turn on when motion is detected
        c.light(0)
        detect_ms = c.motionSensed()
        if (detect_ms):
            timeOut = datetime.datetime.now() + datetime.timedelta(seconds = 6)
            c.light(1)
            
        # if pin was entered wrong 5 times, proceed to take picture if required
        sig = signal(detect_ms)
        name = timestamp()
        pic = c.takePic(name, detect_ms, sig)
        sendPic(name, pic)

        if(datetime.datetime.now() > timeOut):
            c.light(0)
    return;

""" Receive data from server whether or not to take picture"""
def signal(detect):
    if (detect):
        data_r = received_data()
        return (data_r == '1');
    return False;

""" Listen to the port and return data """
def received_data():
    sock.listen(1)
    data, addr = sock.accept()
    received_data = data.recv(1024)
    return received_data;

""" Creating timestamp so name of the pic is unique ("%Y-%m-%d_%H:%M:%S") """
def timestamp():
    timestamp = datetime.datetime.now().strftime("%H:%M:%S")
    return timestamp;

""" Send the picture taken to the server """
def sendPic(filename, send):
    if (send):
        os.chdir("/home/pi/Documents/SYSC3010/SYSC3010/rpiClient/")
        os.system("java tcpClient " + filename + ".jpg")
    return;

if __name__ == "__main__":
    main()
