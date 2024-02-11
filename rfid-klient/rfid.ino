#include <SPI.h>
#include <MFRC522.h>
#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
// Piny dla modułu MFRC522
constexpr uint8_t RST_PIN = D3;
constexpr uint8_t SS_PIN = D4;
MFRC522 rfid(SS_PIN, RST_PIN); // Instancja klasy
MFRC522::MIFARE_Key key;
String tag;
// Dane sieci Wi-Fi
const char* ssid = "####"; // podstawić ssid sieci
const char* password = "####"; // podstawić hasło sieci
// Adres serwera HTTP
const char* serverAddress = "http://192.168.0.8:8080/rfid"; // domyślny end point serwera
void setup() {
 Serial.begin(115200);
 WiFi.begin(ssid, password);
 while (WiFi.status() != WL_CONNECTED) {
 delay(1000);
 Serial.println("Connecting to WiFi...");
 }
 SPI.begin(); // Inicjalizacja magistrali SPI
 rfid.PCD_Init(); // Inicjalizacja modułu MFRC522
}
void loop() {
 if (WiFi.status() == WL_CONNECTED) {
 if (!rfid.PICC_IsNewCardPresent())
 return;
 if (rfid.PICC_ReadCardSerial()) {
 for (byte i = 0; i < 4; i++) {
 tag += rfid.uid.uidByte[i];
 }
 Serial.println(tag);
 sendTagToServer(tag);
 tag = "";
 rfid.PICC_HaltA();
 rfid.PCD_StopCrypto1();
 }
 }
}
void sendTagToServer(String tag) {
 HTTPClient http;
 WiFiClient client; // Używamy klienta WiFi jako argumentu w metodzie begin
 http.begin(client, serverAddress);
 http.addHeader("Content-Type", "text/plain");
 int httpResponseCode = http.POST(tag);
 if (httpResponseCode > 0) {
 String response = http.getString();
 Serial.println("Response code: " + String(httpResponseCode));
 Serial.println("Response: " + response);
 } else {
 Serial.println("Error sending POST request");
 }
 http.end();
}
