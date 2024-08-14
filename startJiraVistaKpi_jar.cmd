SET JAVA_HOME=C:\development\programme\Zulu\zulu-11

SET INPATH="C:/development/workspaces/jiraVistaKPI/input/"
SET INFILENAMEJSON="jira_-_restApiResponse.json"
SET OUTPATH="C:/development/workspaces/jiraVistaKPI/output/"
SET OUTFILENAMECSV="JiraKpi.csv"

cd dist
%JAVA_HOME%\bin\java -jar JiraVistaKPILauncher.jar %INPATH% %INFILENAMEJSON% %OUTPATH% %OUTFILENAMECSV%

pause