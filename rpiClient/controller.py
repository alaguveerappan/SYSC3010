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
        
    """" Check for motion """
    def motionSensed(self):
        return ms.detect();

    """ Take pic if motion is detected and if pin entered wrong 5 times """
    def takePic(self, name, detect, signal):
        if (detect and signal):
            return (c.capturePic(name))
        return False;
    
    """ To turn on and turn off the led """
    def light(self, switch):
        l.power(switch)
        if (switch):
            return True;
        return False;
