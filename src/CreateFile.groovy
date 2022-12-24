import java.text.SimpleDateFormat

/**
 * <p>jiraVistaKPI: </p>
 * <p>Usage: Generate and calculate Jira KPIs for VDM</p>
 * <p>Organisation: </p>
 * @author Th. Deter
 * @version 1.0
 */

public class CreateFile {

  /**
   * Creates a file and writes a list of Issues.
   */
  static void createFileKpiCsv(String filePath, String fileName, Map mapIssues) {
    def out = new File(filePath + fileName)
    def writer = new FileWriter(out)

    String datePattern = "yyyy-MM-dd'T'HH:mm:ss";
    SimpleDateFormat df = new SimpleDateFormat(datePattern,Locale.GERMANY)

    println("== Create file Jira KPI ==============")

    writer.write("ISSUE;SUMMARY;ISSUE_TYPE;STATUS;ASSIGNEE;COMPONENT;TEAM;PRIORITY;CREATOR;CREATED;VISTAMODULE;" +
                 "FIXVERSION;ESTIMATEDAYS;CHANGES;STATUS_CHANGES;DURATION_CREATED;DURATION_BACKLOG;DURATION_INPROGRESS;DURATION_PAUSED;" +
                 "DURATION_INCLARIFICATION;DURATION_INCODEREVIEW;DURATION_TESTABLE;DURATION_TESTOK;DURATION_DELIVERED \n")

    // Issue.
    mapIssues.each() {keyIssue, issue -> writer.write(
      issue.key + ";" +
      issue.summary + ";" +
      issue.issueType + ";" +
      issue.status + ";" +
      issue.assigneeName + ";" +
      issue.componentName + ";" +
      issue.teamName + ";" +
      issue.priorityName + ";" +
      issue.creatorName + ";")
   
      Date date = df.parse(issue?.created?:'2022-01-01T00:00:00'.substring(0,19))
      writer.write(date.toLocaleString().replace(',','') + ";")

      writer.write(issue.vistaModule + ";")

      issue.fixVersion ? writer.write(issue.fixVersion) : null
      writer.write(";")
      issue.estimateDays ? writer.write(issue.estimateDays) : null
      writer.write(";")

      writer.write(issue.changelogsTotal + ";" + issue.statusChanges + ";" )
      writer.write(issue.durationCreated + ";")
      writer.write(issue.durationBacklog + ";")
      writer.write(issue.durationInProgress + ";")
      writer.write(issue.durationPaused + ";")
      writer.write(issue.durationInClarification + ";")
      writer.write(issue.durationInCodeReview + ";")
      writer.write(issue.durationTestable + ";")
      writer.write(issue.durationTestOK + ";")
      writer.write(issue.durationDelivered + ";")
/*
      // Changelog.
      issue.dateChangedBacklog ? writer.write(df.parse(issue.dateChangedBacklog.substring(0,19)).format(outputDateFormat) + ";") : writer.write("" + ";")
      issue.dateChangedInProgress ? writer.write(df.parse(issue.dateChangedInProgress.substring(0,19)).format(outputDateFormat) + ";") : writer.write("" + ";")
      issue.dateChangedPaused ? writer.write(df.parse(issue.dateChangedPaused.substring(0,19)).format(outputDateFormat) + ";") : writer.write("" + ";")
      issue.dateChangedInClarification ? writer.write(df.parse(issue.dateChangedInClarification.substring(0,19)).format(outputDateFormat) + ";") : writer.write("" + ";")
      issue.dateChangedInCodeReview ? writer.write(df.parse(issue.dateChangedInCodeReview.substring(0,19)).format(outputDateFormat) + ";") : writer.write("" + ";")
      issue.dateChangedTestable ? writer.write(df.parse(issue.dateChangedTestable.substring(0,19)).format(outputDateFormat) + ";") : writer.write("" + ";")
      issue.dateChangedTestOK ? writer.write(df.parse(issue.dateChangedTestOK.substring(0,19)).format(outputDateFormat) + ";") : writer.write("" + ";")
      issue.dateChangedDelivered ? writer.write(df.parse(issue.dateChangedDelivered.substring(0,19)).format(outputDateFormat) + ";") : writer.write("" + ";")
      issue.dateChangedInProduction ? writer.write(df.parse(issue.dateChangedInProduction.substring(0,19)).format(outputDateFormat) + ";") : writer.write("" + ";")
*/

      // Addendum: History of all changes.
      issue.historiesMap.each() {
        keyHistMap, historyMap -> historyMap.historyItemsMap.each() {
          keyHistItemsMap, historyItem -> historyItem.field == "status" ? writer.write(historyItem.toString + ";" + df.parse(historyItem.startDate.substring(0,19)).toLocaleString().replace(',','') + ";") : null}
      }
      writer.write("\n")
    }
    writer.close()
  }
}
