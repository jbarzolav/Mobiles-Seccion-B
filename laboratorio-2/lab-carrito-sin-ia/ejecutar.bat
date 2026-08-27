@echo off
echo =========================================
echo    CARRITO DE COMPRAS - TIENDA TECSUP    
echo =========================================
echo.
echo Compilando proyecto...
call gradlew.bat assembleDebug --quiet
echo Ejecutando...
echo.
java -cp "app\build\intermediates\built_in_kotlinc\debug\compileDebugKotlin\classes;%USERPROFILE%\.gradle\caches\modules-2\files-2.1\org.jetbrains.kotlin\kotlin-stdlib\2.3.20\ed866de74ad3d49086a27bbd75952cd186479436\kotlin-stdlib-2.3.20.jar" com.barzola.lab02carritokotlin.CarritoKt
pause
