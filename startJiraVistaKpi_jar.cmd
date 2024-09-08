SET JAVA_HOME=C:\development\programme\Zulu\zulu-11

SET INPATH="./input/"
SET INFILENAMEJSON="jira_-_restApiResponse.json"
SET OUTPATH="./output/"
SET OUTFILENAMECSV="JiraKpi.csv"

cd dist
%JAVA_HOME%\bin\java -jar JiraVistaKPILauncher.jar %INPATH% %INFILENAMEJSON% %OUTPATH% %OUTFILENAMECSV%

pause