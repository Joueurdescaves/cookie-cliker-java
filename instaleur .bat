@echo off 
set user_profile=%USERPROFILE%
set dossier_cible=%user_profile%
echo                                                ##############################
echo                                                #                            #
echo                                                #   installeur by Jstudio    #
echo                                                #                            #
echo                                                ##############################

set /p y/n= cet installeur va ecrire sur votre disk éte vous sur (y/n) :
if %y/n% == y goto verif
if %y/n% == n goto exit

:verif 
echo cet instalation va prendre 644 mo
set /p espace= avez vous lespace sufisant sur votre disk (y/n) :
if %espace% == y goto installeur

:installeur
echo creation du dossier racine 
mkdir "%user_profile%" Jstudio
mkdir "%user_profile%/Jstudio/app


:exit 
pause
