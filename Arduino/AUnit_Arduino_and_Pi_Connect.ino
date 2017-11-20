// arduinounit-2.2.0 - Version: Latest 
#include <ArduinoUnit.h>

#include <Keypad.h>
#include <LiquidCrystal.h>
#include <Servo.h>


String password = "1234";
String tempPassword = "";
boolean unlocked = false;
int failCount = 0;

Servo servo;



// ======== MOTOR CLASS ========= // 
class Motor {
  private:
    int openAngle;
    int closeAngle;
    
  public: 
    Motor();
    int getOpenAngle();
    int getCloseAngle();
    void motorSetup();
    void motorOpenAndClose();
};

Motor::Motor() {
  this->openAngle = 178;
  this->closeAngle = 68;
}

int Motor::getOpenAngle() {
  return this->openAngle;
}

int Motor::getCloseAngle() {
  return this->closeAngle;
}

void Motor::motorSetup() {
  servo.attach(3);
  servo.write(closeAngle);
}

void Motor::motorOpenAndClose() {
  servo.write(openAngle);
  delay(3000);
  servo.write(closeAngle);
  unlocked = false;
}

Motor* motor;



// ========= LCD CLASS ========== // 
LiquidCrystal lcd(13, 12, 11, 10, 9, 8);

String enterPinMessage = "Enter PIN:";
String unlockedMessage = "Unlocked";
String correctPinMessage = "Correct PIN";
String incorrectPinMessage = "Wrong! Try again";
String wrong5TimesMessage = "Wrong 5 times";
String timedOutMessage = "Timed out";

void lcdSetup() {
  lcd.begin(16,2);
}

void lcdEnterPinMessage() {
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print(enterPinMessage);
}

void lcdUnlockedMessage() {
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print(correctPinMessage);
  lcd.setCursor(0,1);
  lcd.print(unlockedMessage);
}

void lcdIncorrectPinMessage() {
  lcd.setCursor(0,1);
  lcd.print(incorrectPinMessage);
}

void lcdTimedOutMessage() {
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print(wrong5TimesMessage);
  lcd.setCursor(0,1);
  lcd.print(timedOutMessage);
}



// ========= KEYPAD CLASS ========= // 
char keys[4][3] = { //declare an array
  {'1','2','3'},
  {'4','5','6'},
  {'7','8','9'},
  {'*','0','#'}
};
byte rowPins[4] = {7, 6, 5, 4}; 
byte colPins[3] = {14, 2, 15};
//initialize an instance of class Keypad
char keypressed;
Keypad mykeypad = Keypad(makeKeymap(keys), rowPins, colPins, 4, 3);

String keypadEnterPin() {
  int k=5;
  int m=1;
  tempPassword = "";
  
  while(keypressed != '*') {
    keypressed = mykeypad.getKey();
    if (keypressed != NO_KEY && m <= 4){
      if (keypressed == '0' || keypressed == '1' || keypressed == '2' || keypressed == '3' ||
          keypressed == '4' || keypressed == '5' || keypressed == '6' || keypressed == '7' ||
          keypressed == '8' || keypressed == '9' ) {
        tempPassword += keypressed;
        lcd.setCursor(k,1);
        lcd.print("*");
        k++; 
        m++;
      }
    }
  }
  return tempPassword;
}

boolean keypadVerifyPin(String tempPassword) {
  
  /*
  - send tempPassword
  - receive 1 if correct PIN, 0 if incorrect
  */
  
   if (Serial.read() == '5' && failCount == 2) { 
    lcdTimedOutMessage();
    delay(10000);
    failCount = 0; 
  }
  
  Serial.println(tempPassword);
  
  delay(2000);
  
  if(Serial.read() == '1') {
    unlocked = true; 
    failCount = 0;
  }
  else {
    unlocked = false;
    failCount++;
  }
  
  return unlocked;
}



void setup() {
  
    Serial.begin(9600);
  
    pinMode(14, INPUT);
    pinMode(15, INPUT);
    
    motor = new Motor();
    
    motor -> motorSetup();
    lcdSetup();
}


void loop() {
  
  lcdEnterPinMessage();
      
  if (keypadVerifyPin(keypadEnterPin())){
    lcdUnlockedMessage();
    motor -> motorOpenAndClose();
  }
  else {
    lcdIncorrectPinMessage();
    delay(2000);
  }
  
  keypressed = mykeypad.getKey();
 }