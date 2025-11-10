@echo off
setlocal enableextensions

echo ========================================
echo   Creando JAR ejecutable del proyecto
echo ========================================
echo.

REM ---------------------------------------------------------
REM 1) COMPILAR ARCHIVOS JAVA
REM ---------------------------------------------------------
echo [1/4] Compilando archivos Java...
if exist out rmdir /s /q out >nul 2>&1
mkdir out
javac -encoding UTF-8 -d out *.java

if %errorlevel% neq 0 (
    echo ERROR: Fallo la compilacion. Verifica tus clases.
    pause
    exit /b 1
)

REM ---------------------------------------------------------
REM 2) CREAR MANIFEST.MF CORRECTO
REM ---------------------------------------------------------
echo [2/4] Generando archivo MANIFEST.MF...

if not exist META-INF mkdir META-INF

(
  echo Manifest-Version: 1.0
  echo Main-Class: ClientGUI
  echo.
) > META-INF\MANIFEST.MF

REM ---------------------------------------------------------
REM 3) BUSCAR jar.exe
REM ---------------------------------------------------------
echo [3/4] Buscando comando jar...

set JAR_CMD=

REM Buscar en PATH
where jar >nul 2>nul
if %errorlevel% equ 0 (
    set JAR_CMD=jar
)

REM Buscar en JAVA_HOME
if not defined JAR_CMD if defined JAVA_HOME (
    if exist "%JAVA_HOME%\bin\jar.exe" (
        set JAR_CMD="%JAVA_HOME%\bin\jar.exe"
    )
)

REM Buscar en Program Files (64 bits)
if not defined JAR_CMD (
    for /d %%i in ("C:\Program Files\Java\*") do (
        if exist "%%i\bin\jar.exe" (
            set JAR_CMD="%%i\bin\jar.exe"
        )
    )
)

REM Buscar en Program Files (32 bits)
if not defined JAR_CMD (
    for /d %%i in ("C:\Program Files (x86)\Java\*") do (
        if exist "%%i\bin\jar.exe" (
            set JAR_CMD="%%i\bin\jar.exe"
        )
    )
)

if not defined JAR_CMD (
    echo ERROR: No se encontro jar.exe
    echo.
    echo Solucion:
    echo  - Instala el JDK o
    echo  - Asegura que jar.exe este en el PATH
    pause
    exit /b 1
)


REM ---------------------------------------------------------
REM 4) CREAR JAR EJECUTABLE
REM ---------------------------------------------------------
echo [4/4] Empaquetando JAR ejecutable...
%JAR_CMD% cfm AprobacionGastos.jar META-INF\MANIFEST.MF -C out .

if %errorlevel% neq 0 (
    echo ERROR: Fallo la creacion del JAR
    pause
    exit /b 1
)

echo.
echo ========================================
echo   JAR creado exitosamente
echo ========================================
echo Archivo generado: AprobacionGastos.jar
echo.
echo Para ejecutarlo:
echo     java -jar AprobacionGastos.jar
echo.
pause
