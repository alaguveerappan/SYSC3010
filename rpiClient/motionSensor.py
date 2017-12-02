import RPi.GPIO as GPIO

class motionSensor:

    def __init__(self):
        GPIO.setwarnings(False)
        GPIO.setmode(GPIO.BCM)
        # set GPIO 4 as a input pin so the motion sensor can be connected
        #     to that pin and could be used to get signals on whether motion
        #     is being detected or not
        GPIO.setup(4,GPIO.IN)
        sensed = False

    """ Detecting motion using input of GPIO pin 4"""
    def detect(self):
        global sensed
        sensed = GPIO.input(4)
        return sensed;

    """ Getter for if motion is sensed """
    def getSensed(self):
        global sensed
        return sensed;
