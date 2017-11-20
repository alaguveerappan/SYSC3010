from stubCamera import stubCamera
from stubSensor import stubSensor

c = stubCamera()
mS = stubSensor()

def main():
    testMotionSensed()
    testMotionNotSensed()
    return

def testMotionSensed():
    """ Test sensor with motion """
    print("Motion is sensed and a picture is taken")
    assert(c.capturePic(mS.motionSensed()));
    print("Test passed")
    return True;

def testMotionNotSense():
    """ Test sensor with no motion """
    print("Motion is not sensed and a picture is not taken")
    assert(c.capturePic(mS.motionNotSensed()) == False);
    print("Test passed")
    return True;

if __name__ == "__main__":
    main()
