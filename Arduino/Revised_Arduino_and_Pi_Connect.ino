#include <Keypad.h>
#include <LiquidCrystal.h>
#include <Servo.h>

String tempPassword = ""; // String variable to hold the PIN that will be entered
boolean unlocked = false; // lock is not yet unlocked
int failCount = 0; // initialize the number of times that the code was entered incorrectly to 0


// ======== MOTOR CLASS ========= // 
/* 
*  This class provides the angles that the motor would
*  spin to, as well as the methods necessary to get these
*  angles as well as open and close the "lock" by setting
*  the servo to move to these angles. Utilizes the Servo.h
*  library, which provides basic servo attach and write
*  methods. 
*/
Servo servo; // create an instance of Servo called servo

class Motor {
  // list of all the private variables
  private:
    const int openAngle = 178; // the angle at which the servo should be in order to open lock
    const int closeAngle = 68; // the angle at which the servo should be in order to close lock
    const int servoPin = 3; // the digital I/O pin to which the servo is connected 
  
  // list of all the public methods
  public: 
    Motor();
    int getOpenAngle();
    int getCloseAngle();
    void motorSetup();
    void motorOpenAndClose();
};

// Constructor 
Motor::Motor() {
}

// Getter for the angle that opens the lock
int Motor::getOpenAngle() {
  return this->openAngle;
}

// Getter for the angle that closes the lock
int Motor::getCloseAngle() {
  return this->closeAngle;
}

/* 
*  Initializes the servo motor to the right pin 
*  on the Arduino and sets the angle to the 
*  close angle
*/
void Motor::motorSetup() {
  servo.attach(servoPin); // initialize the servo to be connected to 
                          // the right digital I/O pin on the Arduino
  servo.write(closeAngle);
}

/* 
*  Opens the lock by turning the servo to the 
*  open angle and then closes the lock by turning
*  the servo to the close angle
*/ 
void Motor::motorOpenAndClose() {
  servo.write(openAngle);
  delay(3000); // wait for a few seconds to allow the user to enter through the entrance
  servo.write(closeAngle);
}

Motor* motor; // create an instance of motor to be used in the main loop 



// ========= LCD CLASS ========== // 
/* 
*  This class contains all the messages that are to be
*  displayed on the LCD screen, as well as the methods
*  that sets up, and writes messages to the LCD. Uses the LiquidCrystal.h
*  library, which provides basic LCD begin, clear, setCursor, and print
*  methods. 
*/

// create a LiquidCrystal.h LiquidCrystal with pins 8-13, where:
// pins 8-11 are data pins D4-D7
// pin 12 is the E (enable) pin
// pin 13 is the RS (register select) pin
LiquidCrystal lcd(13, 12, 11, 10, 9, 8); 

const String enterPinMessage = "Enter PIN:";
const String unlockedMessage = "Unlocked";
const String correctPinMessage = "Correct PIN";
const String incorrectPinMessage = "Wrong! Try again";
const String wrongFiveTimesMessage = "Wrong 5 times";
const String timedOutMessage = "Timed out";

/*
*  Setup the LCD display using the LiquidCrystal.h
*  begin method
*/
void lcdSetup() {
  lcd.begin(16,2);
}

// Method to display the Enter Pin message
void lcdEnterPinMessage() {
  lcd.clear();
  lcd.setCursor(0,0); // move cursor to top left corner of LCD display
  lcd.print(enterPinMessage);
}

// Method to display the Correct PIN and Unlocked message
void lcdUnlockedMessage() {
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print(correctPinMessage);
  lcd.setCursor(0,1); // move cursor to the second line, far left of LCD display
  lcd.print(unlockedMessage);
}

// Method to display the Incorrect PIN message
void lcdIncorrectPinMessage() {
  lcd.setCursor(0,1);
  lcd.print(incorrectPinMessage);
}

// Method to display the Wrong Five Times message and Timed Out message
void lcdTimedOutMessage() {
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print(wrongFiveTimesMessage);
  lcd.setCursor(0,1);
  lcd.print(timedOutMessage);
}



// ========= KEYPAD CLASS ========= //
/* 
*  The Keypad class contains variables that describe the keypad
*  structure, as well as methods that take in a PIN and store it 
*  in a string variable and sends this PIN to the server in order
*  to get it verified. Utilizes the Keypad.h library for basic
*  methods like getKey.
*/

char keys[4][3] = { //declare an array that describes the physical 4x3 keypad
  {'1','2','3'},
  {'4','5','6'},
  {'7','8','9'},
  {'*','0','#'}
};

byte rowPins[4] = {7, 6, 5, 4}; // the digital I/O pins that are used to control the rows of the keypad
byte colPins[3] = {14, 2, 15}; // the analog pins (14 and 15) and digital I/O pin that are used to 
                               // control the columns of the keypad

char keypressed; // char variable used to store the character that is pressed on the keypad

/* 
* Initialize an instance of class Keypad (from Keypad.h); requires the 4x3 array of keys, 
* the array of pins controlling the rows, the array of pins controlling the columns, 
* the number of rows, and the number of columns
*/
Keypad mykeypad = Keypad(makeKeymap(keys), rowPins, colPins, 4, 3);

/* 
*  Method used to continuously take in characters of the key pressed and 
*  display '*' to represent the numbers of the PIN until 
*  four characters have been taken in and the '*' is pressed. Sets the 
*  tempPassword to equal this taken-in PIN and returns the tempPassword
*/
String keypadEnterPin() {
  int placeCursor=5; // start the cursor at the center of the LCD display to center the entered PIN
  int numKeysPressed=1; // variable to keep track of how many keys have been pressed so far
  tempPassword = ""; // ensure that the variable that stores the password to be entered is blank first
  
  while(keypressed != '*') {
    keypressed = mykeypad.getKey(); // use a Keypad.h function to get key
    if (keypressed != NO_KEY && numKeysPressed <= 4){
      if (keypressed == '0' || keypressed == '1' || keypressed == '2' || keypressed == '3' ||
          keypressed == '4' || keypressed == '5' || keypressed == '6' || keypressed == '7' ||
          keypressed == '8' || keypressed == '9' ) {
        tempPassword += keypressed; // add the character pressed to the temporary password
        lcd.setCursor(placeCursor,1); 
        lcd.print("*"); 
        placeCursor++; // advance the cursor one spot to the right 
                       // in order to display the '*' that represents the PIN
        numKeysPressed++;
      }
    }
  }
  return tempPassword;
}

/*
*  Method that takes in the entered password, sends it to the 
*  server in order for it to be verified, and receives a '1' 
*  back if the PIN was correct and sets the unlocked variable
*  accordingly to be returned.
*/
boolean keypadVerifyPin(String tempPassword) {
  
  // verify that the PIN is of the correct length
  if(tempPassword.length() == 4) {
	  Serial.println(tempPassword); // send the PIN to the server through serial interface
	  
	  delay(2000); // wait for the server to validate PIN and send a signal back
	  
	  // receive signal sent from server, if '1' then PIN was correct, set unlocked to true
	  if(Serial.read() == '1') {
	    unlocked = true; 
	  }
	  // PIN was incorrect, set unlocked to false
	  else {
	    unlocked = false;
	  }
  }
  
  return unlocked;
}


/*
*  Sets up the Arduino and all the hardware components that are attached to it
*/
void setup() {
    // Initializes the serial baud rate to 9600 in order to communicate with the server
    Serial.begin(9600);
  
    // Sets up the analog pins 14 and 15 to be inputs in order to use with the keypad
    pinMode(14, INPUT);
    pinMode(15, INPUT);
    
    // Create an instance of the motor class and initializes it
    motor = new Motor();
    motor -> motorSetup();
    
    // Initializes the LCD
    lcdSetup();
}


/*
*  Continuous loop that allows user to enter a PIN, have the PIN 
*  sent to the server to be verified, and either open and close the 
*  lock according to the message received from the server, or 
*  keep the lock closed and inflict a time out if the PIN has been 
*  entered incorrectly five times.
*/ 
void loop() {
  
  lcdEnterPinMessage(); // display "Enter PIN: " on the LCD
  
  // if the PIN entered on the keypad is valid, display
  // unlocked message and make motor move to unlock door
  if (keypadVerifyPin(keypadEnterPin())){
    lcdUnlockedMessage();
    motor -> motorOpenAndClose();
    unlocked = false; // set unlocked back to false for the next cycle
  }
  else {
    lcdIncorrectPinMessage();
    delay(2000); // display the incorrect PIN message for two seconds
    failCount++; // increment the number of times that the PIN was entered incorrectly
  }
  
  // if the server sent back a '5' and the failCount reached 5, time out
  if (Serial.read() == '5' && failCount == 5) { 
    lcdTimedOutMessage();
    delay(10000); // time out for 10 seconds
    failCount = 0; // reset the failCount back to 0 for the next cycle
  }
  
  keypressed = mykeypad.getKey(); // keeps the keypad expectant for a key
 }