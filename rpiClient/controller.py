from motionSensor import motionSensor
from camera import camera
from led import led

class controller:
    def __init__(self):
        global ms
        global c
        global l
        ms = motionSensor()
        c = camera()
        l = led()
        
    """ Main """
    def motionSensed(self):
        return ms.detect();

    def takePic(self, name, detect, signal):
        if (detect and signal):
            c.capturePic(name)
            return True;
        return False;

    def light(self, switch):
        l.power(switch)
        if (switch):
            return True;
        return False;
