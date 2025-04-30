@echo off
echo ===================================================
echo Running SonarQube Analysis for Proyecto-final-DISO-Juego-de-Combate
echo ===================================================

REM Check if SonarQube server is running
echo Checking if SonarQube server is running...
powershell -Command "try { $response = Invoke-WebRequest -Uri 'http://localhost:9000' -UseBasicParsing -TimeoutSec 5; if ($response.StatusCode -eq 200) { Write-Output 'SonarQube server is running.' } } catch { Write-Output 'SonarQube server is not running or not accessible.' }"

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo It seems that SonarQube server is not running.
    echo Please start the SonarQube server before running this script:
    echo 1. Navigate to your SonarQube installation directory
    echo 2. Run bin\windows-x86-64\StartSonar.bat
    echo 3. Wait for the server to start (this may take a few minutes)
    echo 4. Run this script again
    echo.
    pause
    exit /b 1
)

echo.
echo Running SonarQube Scanner...
echo.

REM Check if sonar-scanner is in PATH
where sonar-scanner >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo SonarQube Scanner is not found in your PATH.
    echo Please install SonarQube Scanner and add it to your PATH:
    echo 1. Download from https://docs.sonarqube.org/latest/analysis/scan/sonarscanner/
    echo 2. Extract the archive
    echo 3. Add the bin directory to your PATH
    echo 4. Run this script again
    echo.
    pause
    exit /b 1
)

REM Run SonarQube Scanner
sonar-scanner

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo SonarQube analysis failed. Please check the error messages above.
    echo.
    pause
    exit /b 1
)

echo.
echo SonarQube analysis completed successfully.
echo You can view the results at http://localhost:9000
echo.
pause