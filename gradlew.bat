batch
@echo off
setlocal

set GRADLE_BAT_PARENT=%~dp0
set GRADLE_HOME=%GRADLE_BAT_PARENT%gradle\wrapper

if "%DEBUG%" == "" (
  set DEBUG=false
)

if "%GRADLE_USER_HOME%" == "" (
  set GRADLE_USER_HOME=%USERPROFILE%\.gradle
)

if "%JAVA_HOME%" == "" (
  echo ERROR: JAVA_HOME is not set.
  goto error
)

if exist "%GRADLE_HOME%\gradle-wrapper.jar" (
  goto execute
) else (
  echo ERROR: %GRADLE_HOME%\gradle-wrapper.jar does not exist.
  goto error
)

:execute
"%JAVA_HOME%\bin\java" -classpath "%GRADLE_HOME%\gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain %*
goto end

:error
echo An error occurred. Check the environment variables and ensure that the gradlew.bat script is in the correct location.
exit /b 1

:end
endlocal
exit /b 0