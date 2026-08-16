@echo off
echo.
echo ===================================================
echo   InternMatch Frontend Statik Sunucusu Baslatildi
echo   Web site linki: http://localhost:5500
echo ===================================================
echo.
py -m http.server 5500 2>nul || python -m http.server 5500
