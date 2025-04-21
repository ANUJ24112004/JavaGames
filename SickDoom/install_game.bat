@echo off
echo ========================================
echo    Shape RPG Arena Game - Installer
echo ========================================
echo.

echo This script will install Shape RPG Arena to your computer.

set /p INSTALL_DIR="Enter installation path (default: C:\ShapeRPGArena): "

if "%INSTALL_DIR%"=="" set INSTALL_DIR=C:\ShapeRPGArena

echo.
echo Installing to: %INSTALL_DIR%
echo.

if not exist "%INSTALL_DIR%" (
    mkdir "%INSTALL_DIR%"
    echo Created installation directory.
) else (
    echo Installation directory already exists.
)

echo.
echo Copying game files...
xcopy /E /I /Y "." "%INSTALL_DIR%"
if errorlevel 1 (
    echo ERROR: Failed to copy files.
    pause
    exit /b 1
)

echo.
echo Creating desktop shortcut...
echo Set oWS = WScript.CreateObject("WScript.Shell") > "%TEMP%\shortcut.vbs"
echo sLinkFile = oWS.SpecialFolders("Desktop") ^& "\Shape RPG Arena.lnk" >> "%TEMP%\shortcut.vbs"
echo Set oLink = oWS.CreateShortcut(sLinkFile) >> "%TEMP%\shortcut.vbs"
echo oLink.TargetPath = "%INSTALL_DIR%\run_game.bat" >> "%TEMP%\shortcut.vbs"
echo oLink.WorkingDirectory = "%INSTALL_DIR%" >> "%TEMP%\shortcut.vbs"
echo oLink.Description = "Shape RPG Arena Game" >> "%TEMP%\shortcut.vbs"
echo oLink.Save >> "%TEMP%\shortcut.vbs"
cscript /nologo "%TEMP%\shortcut.vbs"
del "%TEMP%\shortcut.vbs"

echo.
echo ========================================
echo Installation complete!
echo.
echo You can now run the game from:
echo 1. The desktop shortcut "Shape RPG Arena"
echo 2. By running run_game.bat in %INSTALL_DIR%
echo ========================================
echo.

pause 