import RPi.GPIO as GPIO

class motionSensor:

    def __init__(self):
        GPIO.setwarnings(False)
        GPIO.setmode(GPIO.BCM)

        """ set Pin 4 to the Motion Sensor """
        GPIO.setup(4,GPIO.IN)
        sensed = False

    def getSensed(self):
        global sensed
        return sensed;

    def detect(self):
        global sensed
        sensed = GPIO.input(4)
        return sensed;

    
