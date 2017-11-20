from picamera import PiCamera
from time import sleep

class camera:
    
    def __init__(self):
        global cc
        cc = PiCamera()
        cc.rotation = 270

    """ Take a Picture after 5 sec. (camera needs atleast 2 sec before taking
        a picture to get a clear photo)"""
    def capturePic(self, name):
        """ Alpha is set to 200 so you can preview the picture on the monitor
        before capturing and saving the picture on the sdCard."""
        cc.start_preview(alpha=200)
        sleep(5)
        cc.capture('/home/pi/Desktop/camera/' + name + '.jpg')
        cc.stop_preview()
        return True;
    
    """ Take a Picture after 5 sec. (camera needs atleast 2 sec before taking
        a picture to get a clear photo)"""
    def recordVid(self, name):
        cc.start_recording('/home/pi/Desktop/camera/' + name + '.h264')
        sleep(5)
        cc.stop_recording()
        cc.stop_preview()
        return;
