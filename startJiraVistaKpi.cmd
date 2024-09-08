REM 
REM  Start the Jira Vista KPI application
REM

SET JAVA_HOME=C:\portableApps\java\jdk-11.0.2\
SET GROOVY_HOME=C:\Dateien\development\lib\groovy-4.0.6\

SET WORKSPACE=%cd%
SET INPATH=%WORKSPACE%\input\
SET INFILENAMEJSON=jira_-_restApiResponse.json
SET OUTPATH=%WORKSPACE%\output\
SET OUTFILENAMECSV=JiraKpi.csv

CD src
%GROOVY_HOME%bin/groovy Main.groovy %INPATH% %INFILENAMEJSON% %OUTPATH% %OUTFILENAMECSV%

CD ..
PAUSE