from picamera import PiCamera
from time import sleep
from gpiozero import MotionSensor
from datetime import datetime
import RPi.GPIO as GPIO

#instantiate the picamera and rotate the camera so
#the picture is straight while we view the picture
# depending on how the camera is postitioned
camera = PiCamera()
camera.rotation = 0

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.setup(3,GPIO.OUT)

pir = MotionSensor(4)

while True:
    pir.wait_for_motion()

    #turning on LED
    GPIO.output(3,1)
    
    #getting timestamp so name of the pic is unique
    # use ("%Y-%m-%d_%H:%M:%S") for verbose mode
    timestamp = datetime.now().strftime("%H:%M:%S")
    
    camera.start_preview(alpha=200)
    sleep(5)
    camera.capture('/home/pi/Desktop/camera/' + timestamp + '.jpg')

    #camera.start_recording('/home/pi/Desktop/camera/'+timestamp+'.h264')
    #sleep(5)
    #camera.stop_recording()

    camera.stop_preview()
    #turning off the LED
    GPIO.output(3,0)
