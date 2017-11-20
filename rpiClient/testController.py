import unittest
from stubSensor import stubSensor
from stubCamera import stubCamera
from controllerTest import controllerTest


class MyTest(unittest.TestCase):
    def testMotionSensed(self):        
        self.assertEqual(sensed, True)

    def testTakePic(self):
        self.assertEqual(pic, True)

c = controllerTest()

sensed = c.motionSensed()
signal = True
name = "test"
pic = c.takePic(name, sensed, signal)


if __name__ == '__main__':
    unittest.main()
