@echo off
echo =========================================
echo    CARRITO DE COMPRAS - TIENDA TECSUP    
echo =========================================
echo.
echo Compilando proyecto...
call gradlew.bat assembleDebug --quiet
echo Ejecutando...
echo.
for /f "delims=" %%i in ('dir /s /b "%USERPROFILE%\.gradle\caches\modules-2\files-2.1\org.jetbrains.kotlin\kotlin-stdlib\2.3.20\*\kotlin-stdlib-2.3.20.jar" 2^>nul ^| findstr /v sources ^| findstr /v javadoc') do set STDLIB=%%i
java -cp "app\build\intermediates\built_in_kotlinc\debug\compileDebugKotlin\classes;%STDLIB%" com.barzola.lab02carritokotlin.CarritoKt
pause
