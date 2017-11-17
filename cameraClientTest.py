from picamera import PiCamera
from time import sleep
from gpiozero import MotionSensor
from datetime import datetime
import datetime
import RPi.GPIO as GPIO
import os

"""instantiate the picamera and rotate the camera in the angle needed so the
picture is straight while the camera takes a picture depending on how the
camera is postitioned when installed"""
camera = PiCamera()
camera.rotation = 270

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
""" set GPIO 3 as a output pin so the led can be connected to that pin and could
be set On an Off """
GPIO.setup(3,GPIO.OUT)

""" set Pin 4 to the Motion Sensor """
GPIO.setup(4,GPIO.IN)
#pir = MotionSensor(4)

""" Main """
def main():
    timeOut = datetime.datetime.now() + datetime.timedelta(seconds = 10)
    placeholder = True
    while True:
        """ wait for motion then turn on led and take a picture """
        if (GPIO.input(4)):
            timeOut = datetime.datetime.now() + datetime.timedelta(seconds = 10)
            led(1)
            if (placeholder):
                capturePic()
        if(datetime.datetime.now() > timeOut):
            led(0)
        
        
    return;


""" Creating timestamp so name of the pic is unique use ("%Y-%m-%d_%H:%M:%S")"""
def timestamp():
    timestamp = datetime.datetime.now().strftime("%H:%M:%S")
    return timestamp;

""" Turn Led On or Off depending on the input high_low"""
def led(high_low):
    GPIO.output(3 , high_low)
    return;

""" Take a Picture after 5 sec. (camera needs atleast 2 sec before taking
    a picture to get a clear photo)"""
def capturePic():
    """ Alpha is set to 200 so you can preview the picture on the monitor
    before capturing and saving the picture on the sdCard."""
    camera.start_preview(alpha=200)
    sleep(5)
    name = timestamp()
    camera.capture('/home/pi/Desktop/camera/' + name + '.jpg')
    savePic(name + '.jpg')
    camera.stop_preview()
    return;

""" Take a Picture after 5 sec. (camera needs atleast 2 sec before taking
    a picture to get a clear photo)"""
def recordVid():
    camera.start_recording('/home/pi/Desktop/camera/' + timestamp() + '.h264')
    sleep(5)
    camera.stop_recording()
    camera.stop_preview()
    return;

def savePic(filename):
    os.chdir("/home/pi/Desktop/TCP/")
    os.system("java ClientCameraTest " + filename)
    return;

if __name__ == "__main__":
    main()
