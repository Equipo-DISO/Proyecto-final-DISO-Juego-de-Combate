#!/bin/bash

echo "==================================================="
echo "Running SonarQube Analysis for Proyecto-final-DISO-Juego-de-Combate"
echo "==================================================="

# Check if SonarQube server is running
echo "Checking if SonarQube server is running..."
if curl -s -f http://localhost:9000 > /dev/null; then
    echo "SonarQube server is running."
else
    echo
    echo "It seems that SonarQube server is not running."
    echo "Please start the SonarQube server before running this script:"
    echo "1. Navigate to your SonarQube installation directory"
    echo "2. Run bin/[OS]/sonar.sh start"
    echo "3. Wait for the server to start (this may take a few minutes)"
    echo "4. Run this script again"
    echo
    exit 1
fi

echo
echo "Running SonarQube Scanner..."
echo

# Check if sonar-scanner is in PATH
if ! command -v sonar-scanner &> /dev/null; then
    echo "SonarQube Scanner is not found in your PATH."
    echo "Please install SonarQube Scanner and add it to your PATH:"
    echo "1. Download from https://docs.sonarqube.org/latest/analysis/scan/sonarscanner/"
    echo "2. Extract the archive"
    echo "3. Add the bin directory to your PATH"
    echo "4. Run this script again"
    echo
    exit 1
fi

# Run SonarQube Scanner
sonar-scanner

if [ $? -ne 0 ]; then
    echo
    echo "SonarQube analysis failed. Please check the error messages above."
    echo
    exit 1
fi

echo
echo "SonarQube analysis completed successfully."
echo "You can view the results at http://localhost:9000"
echo