@ECHO OFF
SETLOCAL

SET JAVA_EXEC=java
IF DEFINED JAVA_HOME SET JAVA_EXEC=%JAVA_HOME%\bin\java

SET WRAPPER_JAR=%~dp0.mvn\wrapper\maven-wrapper.jar
IF NOT EXIST "%WRAPPER_JAR%" (
    ECHO Downloading Maven Wrapper JAR...
    powershell -Command "(New-Object Net.WebClient).DownloadFile('https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar', '%WRAPPER_JAR%')"
)

"%JAVA_EXEC%" -classpath "%WRAPPER_JAR%" "-Dmaven.multiModuleProjectDirectory=%~dp0" org.apache.maven.wrapper.MavenWrapperMain %*
