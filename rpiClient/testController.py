import unittest
from controllerTest import controllerTest


class MyTest(unittest.TestCase):
    def testMotionSensed(self):        
        self.assertEqual(sensed, True)

    def testTakePicMotionSignal(self):
        self.assertEqual(pic, True)
        
    def testTakePicMotionNotSignal(self):
        self.assertEqual(pic2, False)
        
    def testTakePicNotMotionNotSignal(self):
        self.assertEqual(pic3, False)
        

c = controllerTest()

name = "test"

sensed = c.motionSensed()
signal = True
pic = c.takePic(name, sensed, signal)

sensed2 = c.motionSensed()
signal2 = False
pic2 = c.takePic(name, sensed2, signal2)

sensed3 = False
signal3 = False
pic3 = c.takePic(name, sensed3, signal3)

if __name__ == '__main__':
    unittest.main()
