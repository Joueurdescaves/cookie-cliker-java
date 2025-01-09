@echo off
setlocal enabledelayedexpansion

:: Définir l'URL de l'API GitHub (remplacer avec votre dépôt GitHub)
set GITHUB_API_URL=https://api.github.com/repos/username/repository/releases/latest

:: Nom du fichier .jar et du fichier version.txt
set JAR_FILE=monApplication.jar
set VERSION_FILE=version.txt
set TEMP_JAR=latest_version.jar

:: Vérifier si curl est installé (utilisé pour récupérer les informations de l'API GitHub)
where curl >nul 2>nul
if errorlevel 1 (
    echo Curl n'est pas installé. Téléchargez-le et ajoutez-le à votre PATH.
    exit /b 1
)

:: Vérifier si le fichier version.txt existe
if not exist %VERSION_FILE% (
    echo Le fichier version.txt est introuvable.
    exit /b 1
)

:: Lire la version locale à partir du fichier version.txt
set /p LOCAL_VERSION=<%VERSION_FILE%
echo Version locale : %LOCAL_VERSION%

:: Obtenir la version distante depuis GitHub (l'API retourne un JSON)
echo Vérification de la dernière version disponible sur GitHub...
curl -s %GITHUB_API_URL% > response.json

:: Extraire la version distante du JSON
for /f "tokens=2 delims=:," %%a in ('findstr /i "tag_name" response.json') do (
    set REMOTE_VERSION=%%a
)

:: Nettoyer les guillemets autour de la version distante
set REMOTE_VERSION=%REMOTE_VERSION:"=%

echo Version distante : %REMOTE_VERSION%

:: Comparer les versions
if "%LOCAL_VERSION%"=="%REMOTE_VERSION%" (
    echo L'application est déjà à jour.
    del response.json
    exit /b 0
)

:: Demander à l'utilisateur s'il veut mettre à jour (Y/N)
set /p CONFIRM_UPDATE=Êtes-vous sûr de vouloir mettre à jour vers la version %REMOTE_VERSION% ? (Y/N) : 
if /i not "%CONFIRM_UPDATE%"=="Y" (
    echo Mise à jour annulée.
    del response.json
    exit /b 0
)

:: Télécharger la nouvelle version .jar si une mise à jour est disponible
echo Nouvelle version disponible : %REMOTE_VERSION%
echo Téléchargement du fichier .jar...

:: Extraire l'URL du fichier .jar à partir de la réponse JSON
for /f "tokens=2 delims=:," %%b in ('findstr /i ".jar" response.json') do (
    set JAR_URL=%%b
)

:: Nettoyer les guillemets autour de l'URL du fichier .jar
set JAR_URL=%JAR_URL:"=%

echo URL du fichier .jar : %JAR_URL%

:: Supprimer l'ancienne version de l'application (fichier .jar)
if exist %JAR_FILE% (
    echo Suppression de l'ancienne version...
    del %JAR_FILE%
)

:: Télécharger le fichier .jar
curl -L %JAR_URL% -o %TEMP_JAR%

:: Vérifier si le fichier .jar a été téléchargé avec succès
if exist %TEMP_JAR% (
    echo Mise à jour téléchargée avec succès.
    echo %REMOTE_VERSION% > %VERSION_FILE%
    echo Remplacement de l'ancienne version...
    rename %TEMP_JAR% %JAR_FILE%
    echo L'application a été mise à jour vers la version %REMOTE_VERSION%.
) else (
    echo Erreur de téléchargement du fichier .jar.
)

:: Nettoyer les fichiers temporaires
del response.json
pause
