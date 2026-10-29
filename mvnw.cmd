@echo off
setlocal
rem Project-local Maven bootstrapper. Does not require Maven installed or added to PATH.
set "MAVEN_VERSION=3.9.11"
set "MAVEN_CACHE=%USERPROFILE%\.m2\wrapper\dists"
set "MAVEN_HOME=%MAVEN_CACHE%\apache-maven-%MAVEN_VERSION%"
set "MAVEN_CMD=%MAVEN_HOME%\bin\mvn.cmd"
set "MAVEN_ZIP=%MAVEN_CACHE%\apache-maven-%MAVEN_VERSION%-bin.zip"

if exist "%MAVEN_CMD%" goto run_maven

echo.
echo [Maven Wrapper] Maven %MAVEN_VERSION% is not cached yet.
echo [Maven Wrapper] Downloading Maven into your user profile cache. No system-wide installation is performed.
if not exist "%MAVEN_CACHE%" mkdir "%MAVEN_CACHE%"
if errorlevel 1 goto failed

powershell -NoProfile -ExecutionPolicy Bypass -Command "$ErrorActionPreference='Stop'; try { Invoke-WebRequest -Uri 'https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/%MAVEN_VERSION%/apache-maven-%MAVEN_VERSION%-bin.zip' -OutFile '%MAVEN_ZIP%'; Expand-Archive -LiteralPath '%MAVEN_ZIP%' -DestinationPath '%MAVEN_CACHE%' -Force; Remove-Item -LiteralPath '%MAVEN_ZIP%' -Force; exit 0 } catch { Write-Error $_; exit 1 }"
if errorlevel 1 goto failed
if not exist "%MAVEN_CMD%" goto failed

:run_maven
call "%MAVEN_CMD%" %*
exit /b %ERRORLEVEL%

:failed
echo.
echo [Maven Wrapper] Could not download or prepare Maven.
echo Check your internet connection and try again.
echo Maven distribution URL: https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/%MAVEN_VERSION%/apache-maven-%MAVEN_VERSION%-bin.zip
exit /b 1
