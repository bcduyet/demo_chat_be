bat
@echo off
REM Check if the server is already running
tasklist | find "java.exe" | find "xeex_chat.jar" > nul
if %errorlevel% == 0 (
    echo "XEEX Chat Server is already running..."
    exit
)
REM Start the JAR file
call setenv.bat
java -jar xeex_chat.jar --spring.config.location=./resources/

echo "XEEX Chat Server are running..."