@echo off
title Shape RPG Arena - Advanced Launcher
color 0A
echo ==========================================
echo   SHAPE RPG ARENA - ADVANCED LAUNCHER
echo ==========================================
echo.

REM Get the directory where this batch file is located
set GAME_DIR=%~dp0
cd /d "%GAME_DIR%"

echo Testing Java installation...
java -version >nul 2>&1
if errorlevel 1 (
    color 0C
    echo ERROR: Java is not installed or not in the PATH.
    echo Please install Java from https://www.java.com/
    echo.
    pause
    exit /b 1
)

echo Java found. Trying to launch game...
echo.

REM Try method 1 (simplest)
echo METHOD 1: Simple class execution
java Main
if not errorlevel 1 goto SUCCESS

REM Try method 2 (with explicit classpath)
echo.
echo METHOD 1 failed. Trying METHOD 2...
echo.
java -cp "%GAME_DIR%" Main
if not errorlevel 1 goto SUCCESS

REM Try method 3 (with current directory in classpath)
echo.
echo METHOD 2 failed. Trying METHOD 3...
echo.
java -cp ".;%GAME_DIR%" Main
if not errorlevel 1 goto SUCCESS

REM Try method 4 (with wildcard classpath)
echo.
echo METHOD 3 failed. Trying METHOD 4...
echo.
java -cp ".;%GAME_DIR%;*" Main
if not errorlevel 1 goto SUCCESS

REM If all methods failed
color 0C
echo.
echo ==========================================
echo All methods failed to run the game.
echo.
echo Please ensure:
echo 1. Java is properly installed
echo 2. All game files are in the same folder
echo 3. No antivirus is blocking the execution
echo ==========================================
echo.
pause
exit /b 1

:SUCCESS
color 0A
echo.
echo Game completed successfully!
echo.
pause 