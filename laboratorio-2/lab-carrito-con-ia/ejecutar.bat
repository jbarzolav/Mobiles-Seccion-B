@echo off
title Carrito de Compras - Con IA (OOP)
set JAVA="C:\Program Files\Eclipse Adoptium\jdk-25.0.1.8-hotspot\bin\java.exe"
set JAR=%~dp0console\build\libs\console.jar
%JAVA% -jar "%JAR%"
pause
