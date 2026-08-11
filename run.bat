@echo off
REM InternMatch - Uygulamayi baslatma scripti
REM Bu script, proje yolundaki Turkce karakter (Masaustu) nedeniyle
REM 'mvn spring-boot:run' komutunun calismama sorununu cozmeye yardimci olur.
REM Kullanim: run.bat

echo [InternMatch] Proje derleniyor...
call mvn clean package -DskipTests -q
if %ERRORLEVEL% neq 0 (
    echo [HATA] Derleme basarisiz oldu!
    exit /b 1
)

echo [InternMatch] Uygulama baslatiliyor (port: 8081)...
java -jar target\internmatch-0.0.1-SNAPSHOT.jar
