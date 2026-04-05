@echo off
chcp 65001 >nul
setlocal

set PROJECT_ROOT=%~dp0
set BACKEND=%PROJECT_ROOT%backend
set FRONTEND_CLIENT=%PROJECT_ROOT%serveme-client
set FRONTEND_ADM=%PROJECT_ROOT%serveme-admin

echo.
echo ========================================
echo   SERVE-ME — Inicializando ambiente
echo ========================================
echo.

:: ── 1. Testes unitários ──────────────────
echo [1/4] Rodando testes do backend...
echo.
cd /d "%BACKEND%"
call gradlew.bat test
if %errorlevel% neq 0 (
    echo.
    echo [ERRO] Testes falharam. Corrija antes de continuar.
    pause
    exit /b 1
)
echo.
echo [OK] Todos os testes passaram.
echo.

:: ── 2. Build do backend ──────────────────
echo [2/4] Compilando backend...
echo.
call gradlew.bat bootJar
if %errorlevel% neq 0 (
    echo.
    echo [ERRO] Build falhou.
    pause
    exit /b 1
)
echo.
echo [OK] Build concluido.
echo.

:: ── 3. Inicia backend em nova janela ─────
echo [3/4] Iniciando backend...
start "Serve-me Backend" cmd /k "cd /d "%BACKEND%" && java -jar build\libs\serveme-0.0.1-SNAPSHOT.jar"

:: ── Aguarda backend via health check ─────
echo Aguardando backend ficar pronto...
set MAX_TENTATIVAS=30
set TENTATIVA=0

:HEALTH_LOOP
set /a TENTATIVA+=1
if %TENTATIVA% gtr %MAX_TENTATIVAS% (
    echo.
    echo [ERRO] Backend nao respondeu apos %MAX_TENTATIVAS% tentativas. Verifique a janela do backend.
    pause
    exit /b 1
)

:: curl retorna 0 se a requisicao teve resposta, independente do status HTTP
curl -s -o nul -w "%%{http_code}" http://localhost:8080/actuator/health 2>nul | findstr /r "200" >nul
if %errorlevel% neq 0 (
    echo   Tentativa %TENTATIVA%/%MAX_TENTATIVAS% — aguardando...
    timeout /t 2 /nobreak >nul
    goto HEALTH_LOOP
)

echo.
echo [OK] Backend pronto.
echo.

:: ── 4. Inicia os dois frontends ──────────
echo [4/4] Iniciando frontends...
start "Serve-me Frontend Client" cmd /k "cd /d "%FRONTEND_CLIENT%" && npm run dev"
start "Serve-me Frontend ADM"    cmd /k "cd /d "%FRONTEND_ADM%" && npm run dev"

echo.
echo ========================================
echo   Ambiente iniciado com sucesso!
echo   Backend:         http://localhost:8080
echo   Frontend Client: http://localhost:22800
echo   Frontend ADM:    http://localhost:22801
echo ========================================
echo.
pause