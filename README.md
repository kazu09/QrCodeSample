# QrCodeSample

## 画面説明
### トップ画面
「zxing readQR」と「mlkit readQR」ボタンがあります。  
どちらのボタンをタップしてもカメラを起動する画面に遷移します。  
遷移先の違いとしては以下になります。  
「zxing readQR」 → Zxingを使用したQR解析を行う画面  
「mlkit readQR」 → Ml Kitを使用したQR解析を行う画面  
<img alt="top画面" width="200" src="https://github.com/kazu09/QrCodeSample/assets/64839248/d9f4cf39-6c11-4d1e-b5b2-532933a1f1cb">
### QR読み取り画面
* zxingでの読み取り画面とmlkitでの読み取り画面はレイアウトも処理も同じになります。
* QR読み取り成功
  * 以下画像のように表示エリアにテキストが表示されます
<img alt="読み取り成功" width="200" src="https://github.com/kazu09/QrCodeSample/assets/64839248/d9c1ef2f-8c64-407a-8f7e-5c0b6642b724">

* QR読み取り失敗またはQR未読み取り
  * 以下画像のように表示エリアにテキストが未表示になります
<img alt="未読み取り" width="200" src="https://github.com/kazu09/QrCodeSample/assets/64839248/393416b3-7249-434a-85d4-d5affafd79c1">

---

## Screen Description
### Top Screen
There are two buttons: "zxing readQR" and "mlkit readQR".  
Tapping either button will navigate to a screen that activates the camera.  
The differences in the screens they navigate to are as follows:  
"zxing readQR" → Screen for QR analysis using Zxing  
"mlkit readQR" → Screen for QR analysis using Ml Kit  
<img alt="top画面" width="200" src="https://github.com/kazu09/QrCodeSample/assets/64839248/d9f4cf39-6c11-4d1e-b5b2-532933a1f1cb">
### QR Code Scanning Screen
* The layout and processes for the zxing scanning screen and the mlkit scanning screen are the same.
* QR Code Scanning Success:
  * Text will be displayed in the display area as shown in the image below.
<img alt="読み取り成功" width="200" src="https://github.com/kazu09/QrCodeSample/assets/64839248/d9c1ef2f-8c64-407a-8f7e-5c0b6642b724">

* QR Code Scanning Failure or No QR Code Scanned:
  * The display area will not show any text, as depicted in the image below.
<img alt="未読み取り" width="200" src="https://github.com/kazu09/QrCodeSample/assets/64839248/393416b3-7249-434a-85d4-d5affafd79c1">
