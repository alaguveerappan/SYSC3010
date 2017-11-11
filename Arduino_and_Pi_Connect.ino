#include <Keypad.h>
#include <LiquidCrystal.h>
#include <Servo.h>

String password = "1234";
String tempPassword = "";
boolean unlocked = false;

Servo servo; 

LiquidCrystal lcd(13, 12, 11, 10, 9, 8);
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

void setup() {
  
    Serial.begin(9600);
  
    pinMode(14, INPUT);
    pinMode(15, INPUT);
    servo.attach(3);
    servo.write(68);
    lcd.begin(16,2);
    //lcd.print("Enter PIN:");
    lcd.setCursor(0,1);
}

void loop() {
  
  lcd.clear();
  enterPassword();
  verifyPassword();
      
  if (unlocked){
    lcd.setCursor(0,1);
    lcd.print("Unlocked");
    servo.write(178);
    delay(3000);
    servo.write(68);
    unlocked = false;
  }
  
  if (Serial.read() == '5'){
    timeOut();
  }
  
  keypressed = mykeypad.getKey();
 }


void enterPassword() {
  int k=5;
  int m=1;
  tempPassword = "";
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("Enter PIN:");
  
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
}

void verifyPassword() {
  
  /*
  - send tempPassword
  - receive 1 if correct PIN, 0 if incorrect
  */
  
  Serial.println(tempPassword);
  
  delay(2000);
  
  if(Serial.read() == '1') {
    lcd.clear();
    lcd.setCursor(0,0);
    lcd.print("Correct PIN");
    unlocked = true; 
  }
  else {
    lcd.setCursor(0,1);
    lcd.print("Wrong! Try Again");
    delay(2000);
    lcd.clear();
    lcd.setCursor(0,0);
    lcd.print("Enter PIN:");
  }
}

void timeOut() {
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("Wrong 5 times");
  lcd.setCursor(0,1);
  lcd.print("Timed out");
  delay(10000);
}