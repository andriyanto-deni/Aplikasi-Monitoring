#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
#include "DHT.h"

#define FIREBASE_HOST "latihan-XXXXXXXXXXXXXXXXXXX-default-rtdb.firebaseio.com"
#define FIREBASE_AUTH "aMVGX0HcMXXXXXXXXXXXXXXXXXXXXX8fu9mp9oFvu"
#define WIFI_SSID "Alfamart"
#define WIFI_PASSWORD "00000000"
//#define WIFI_SSID "Testing"
//#define WIFI_PASSWORD "11012345678"

#define DHTPIN 4 
#define DHTTYPE DHT11
DHT dht(DHTPIN, DHTTYPE);  
boolean data=false;

void setup() {
  pinMode(0,OUTPUT);
  pinMode(LED_BUILTIN, OUTPUT); 
  Serial.begin(9600);

  // connect to wifi.
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status() != WL_CONNECTED) {
    digitalWrite(LED_BUILTIN, LOW);
    Serial.print(".");
    delay(500);
    digitalWrite(LED_BUILTIN, HIGH);
    delay(500);
  }
  Serial.println();
  Serial.print("connected: ");
  Serial.println(WiFi.localIP());

  dht.begin();
  
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);

}

void loop() {
  
  
  data = (Firebase.getBool("Data/Led"));
  Serial.print("data: ");
  Serial.println(data);
  if (data == false ){
    digitalWrite(0, LOW);
  }else if(data == true){
    digitalWrite(0, HIGH);
  }
  delay(1000);
//  digitalWrite(0, HIGH); // turn the LED on
//  delay(2000); // wait for a second
//  digitalWrite(0, LOW); // turn the LED off
//  delay(1000); // wait for a second

  int h = dht.readHumidity();
  int t = dht.readTemperature();
//
//  if (isnan(h) || isnan(t)) {
//    Serial.println(F("Failed to read from DHT sensor!"));
//    return;
//  }
//  Serial.print(F("Humidity: "));
//  Serial.print(h);
//  Serial.print(F("%  Temperature: "));
//  Serial.print(t);
//  Serial.println(F("°C "));


  String suhu = String(t);
  Firebase.setString("Data/Suhu",suhu);
  if (Firebase.failed()) {
      
      Serial.print("setting failed:");
      Serial.println(Firebase.error());
      digitalWrite(LED_BUILTIN, HIGH);
      digitalWrite(0, LOW);
      return; 
  }
  delay(1000);
  String kelembapan = String(h);
  Firebase.setString("Data/Kelembapan",kelembapan);
  if (Firebase.failed()) {
      
      Serial.print("setting failed:");
      Serial.println(Firebase.error());
      digitalWrite(LED_BUILTIN, HIGH);
      digitalWrite(0, LOW);
      return; 
  }
  delay(1000);


}
