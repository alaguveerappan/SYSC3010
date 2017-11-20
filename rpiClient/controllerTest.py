from stubSensor import stubSensor
from stubCamera import stubCamera

class controllerTest:
    def __init__(self):
        global ms
        global c
        ms = stubSensor()
        c = stubCamera()
        
    """ Main """
    def motionSensed(self):
        return ms.detect();

    def takePic(self, name, detect, signal):
        if (detect and signal):
            return (c.capturePic(name))
        return False;
