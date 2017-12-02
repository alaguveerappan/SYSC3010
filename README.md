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
- [x] Integrating the database with existing components
- [x] Android App
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

## Running programs
* For Client Main
` cd Documents/SYSC3010/SYSC3010/rpiClient
` python main.py port
` python main.py 3060
* For Server Main
` cd Documents/SYSC3010/SYSC3010/rpiServer
` python main.py arduinoSerial Baudrate ip_addr port db_port
` python main.py 0 9600 192.168.43.37 3060 27017

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

### Database
* If running `mongod` from the command line does not work, run `sudo
  systemctl stop mongodb`, then run `mongod` again
