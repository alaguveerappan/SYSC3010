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
		# if motion is sensed, turn the light on 
        if (detect_ms):
            timeOut = datetime.datetime.now() + datetime.timedelta(seconds = 6)
            c.light(1)
            
        # if pin was entered wrong 5 times, proceed to take picture if required
        sig = signal(detect_ms) # receive signal from server indicating whether 
								# or not a picture should be taken
        name = timestamp()
		# if server tells client to take pic, and motion is detected, take picture
        pic = c.takePic(name, detect_ms, sig) 
        sendPic(name, pic) # send the picture to the server
		
		# turn off the light after the time out time
        if(datetime.datetime.now() > timeOut):
            c.light(0)
    return;

""" Receive data from server whether or not to take picture"""
def signal(detect):
    if (detect):
        data_r = received_data()
        return (data_r == '1');
    return False;

""" Listen to the port for data from server and return that data """
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
		# navigate to the directory in preparation to send the picture file
        os.chdir("/home/pi/Documents/SYSC3010/SYSC3010/rpiClient/")
		# send the picture file using tcpClient with the appropriate filename
        os.system("java tcpClient " + filename + ".jpg")
    return;

# required for python files with main() 
if __name__ == "__main__":
    main()
