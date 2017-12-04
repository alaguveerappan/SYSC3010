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
- [x] Comments

## Prerequisites

* Linux: Install `mongodb`
* Java: Install `java-sdk8`

* Make folder path to store images
`mkdir /home/pi/Desktop/camera/`

## Useful Command
Compiling database java programs from command line
* `javac -classpath .:../external\ libraries/java-json.jar:..\external\ libraries/gson-2.8.2.jar:../external\ libraries/mongo-java-driver-3.5.0.jar addLogDatabase.java`
* `java -classpath .:../external\ libraries/java-json.jar:..\external\ libraries/gson-2.8.2.jar:../external\ libraries/mongo-java-driver-3.5.0.jar addLogDatabase`

## Running programs
* For Client Main
`cd Documents/SYSC3010/SYSC3010/rpiClient`
`python main.py port`

Example:
`python main.py 3060`

* For Server Main
`cd Documents/SYSC3010/SYSC3010/rpiServer`
`python main.py arduinoSerial Baudrate client_ip_addr port db_port`

Example:
`python main.py 0 9600 192.168.43.37 3060 27017`

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

### Database
* If running `mongod` from the command line does not work, run `sudo
  systemctl stop mongodb` or `sudo systemctl restart mongodb`, then run
  `mongod` again
