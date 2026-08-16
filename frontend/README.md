# InternMatch - Frontend Statik Sunucu

Bu klasör InternMatch projesinin statik HTML/CSS arayüzünü içerir. Herhangi bir ek kütüphane, paket veya framework (React, Vite, Node.js vb.) gerektirmez.

## 🚀 Çalıştırma Adımları

### Yöntem 1: `run_frontend.bat` ile (Önerilen)

`frontend` klasöründeyken `run_frontend.bat` dosyasına çift tıklayın veya terminalde şu komutu çalıştırın:

```cmd
run_frontend.bat
```

Çıktıda şu bilgiyi göreceksiniz:
```text
Web site linki: http://localhost:5500
```

### Yöntem 2: Manuel Terminal Komutları ile

Eğer bat dosyasını kullanmak istemiyorsanız, `frontend` klasörü içindeyken terminalde aşağıdaki komutlardan birini çalıştırabilirsiniz:

#### Windows Launcher (`py`) ile:
```bash
py -m http.server 5500
```

#### Alternatif olarak Python (`python`) ile:
```bash
python -m http.server 5500
```

---

## 🌐 Tarayıcı Erişimi

Sunucu başladıktan sonra web tarayıcınızda şu adrese gidin:

👉 **[http://localhost:5500](http://localhost:5500)**

---

## 📁 Dosya Yapısı

```text
frontend/
├── index.html        # Statik HTML ana sayfası
├── style.css         # Tasarım ve responsive stiller
├── run_frontend.bat  # 5500 portunda sunucu başlatan bat dosyası
└── README.md         # Çalıştırma kılavuzu
```
