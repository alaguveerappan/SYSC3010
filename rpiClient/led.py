import RPi.GPIO as GPIO

class led:

    def __init__(self):    
        GPIO.setwarnings(False)
        GPIO.setmode(GPIO.BCM)
        """ set GPIO 3 as a output pin so the led can be connected to that pin and could
        be set On an Off """
        GPIO.setup(3,GPIO.OUT)
        on = False

    def power(self, high_low):
        global on
        GPIO.output(3 , high_low)
        if(high_low == 1):
            on = True
        else:
            on = False
        return;

    def getOn(self):
        global on
        return on;
