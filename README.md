# SYSC3010 Project
Entrance Security System with RPI's and Arduino's

## Group Members
- Alagu Paramesh Veerappan
- Carmen Ho
- Kien Ha
- Keith Ko

## Development
- [x] Continuous File Transfer
- [x] Server chooses when to take the picture
- [x] Server
- [x] Client
- [] Integrating the database with existing components
- [] Android App
- [] Comments :P

## Prerequisites

* Linux: Install `mongodb`
* Java: Install `java-sdk8`

## Useful Command

* `javac -classpath .:../external\ libraries/java-json.jar:..\external\
  libraries/gson-2.8.2.jar:../external\
  libraries/mongo-java-driver-3.5.0.jar Database.java`
* `java -classpath .:../external\ libraries/java-json.jar:..\external\ 
  libraries/gson-2.8.2.jar:../external\
  libraries/mongo-java-driver-3.5.0.jar Database`


## Troubleshooting
### Raspberry Pi Server
`run main.py from rpiClient first`
`run main.py from rpiServer afterwards`

### Raspberry Pi Camera

* To test taking a picture
`raspistill -o name.jpg`

* To view picture from terminal
`gpicview name.jpg`

* To play video from terminal
`omxplayer name.h264`

### Arduino
* To use the Arduino every time it is connected to the RPi
`run /dev/tty*`
`change the x in ACMx in main.py of rpiServer`
