Hideqlib
========

Hideqlib adalah library android untuk menggunakan proxy di webview android, dan menggunakan vpn untuk keseluruhan aplikasi android.
[![](https://jitpack.io/v/ryanbekhen/Hideqlib.svg)](https://jitpack.io/#ryanbekhen/Hideqlib)

Cara Menambahkan Repository
---------------------------
Langkah 1. Tambahkan repositori JitPack ke file build Anda\
Tambahkan di build.gradle root Anda di akhir repositori:
```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

Langkah 2. Tambahkan dependensi
```gradle
dependencies {
    implementation 'com.github.ryanbekhen:Hideqlib:1.0.3'
}
```

Penggunaan
----------
```
// Cek apakah telah menggunakan proxy
WebViewProxy.isUsingProxy(getApplicationContext());

// Mengaktifkan proxy
WebViewProxy.setEnabled(getApplicationContext(), "16.10.23.129", 3121);

// Menonaktifkan proxy
WebViewProxy.setDisabled(getApplicationContext())
```

Lisensi
-------
Lisensi Hideqlib adalah [Apache License 2.0](https://github.com/ryanbekhen/Hideqlib/blob/master/LICENSE).

Hubngi Saya
-----------
[Telegram](https://t.me/ryanbekhen), 
[Whatsapp](http://wa.me/6282395984045), 
[Instagram](https://www.instagram.com/ryanbekhen/)
