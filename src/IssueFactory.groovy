import groovy.swing.binding.JComponentProperties

/**
 * <p>jiraVistaKPI: </p>
 * <p>Usage: Generate and calculate Jira KPIs for VDM</p>
 * <p>Organisation: </p>
 * @author Th. Deter
 * @version 1.0
 */

public class IssueFactory {

  public static Map createIssuesMap(fileIncoming) {

    def IssueBean issueBean
    def HistoryBean historyBean
    def HistoryItemBean historyItemBean
    def mapIssues = [:]

    println("== Issue factory =====================")
    for(int i in 0..fileIncoming.total-1) {
      if(fileIncoming?.issues[i]){
        // use null safe navigation with "?"
        issueBean = new IssueBean(fileIncoming.issues[i]?.key,
                                  fileIncoming.issues[i]?.fields?.summary,
                                  fileIncoming.issues[i]?.fields?.issuetype?.name,
                                  fileIncoming.issues[i]?.fields?.status?.name,
                                  fileIncoming.issues[i]?.fields?.assignee?.name,
                                  fileIncoming.issues[i]?.fields?.components?.getAt(0)?.name,
                                  fileIncoming.issues[i]?.fields?.customfield_10910?.value, //TeamName
                                  fileIncoming.issues[i]?.fields?.priority?.name,
                                  fileIncoming.issues[i]?.fields?.creator?.name,
                                  fileIncoming.issues[i]?.fields?.created,
                                  fileIncoming.issues[i]?.fields?.customfield_11600?.value, // VistaModule
                                  fileIncoming.issues[i]?.changelog?.total,
                                  fileIncoming.issues[i]?.fields?.customfield_10708 // Estimate Days
        )

        if(issueBean.changelogsTotal > 0){
          for (int j in 0..issueBean.changelogsTotal -1) {
            // use null safe navigation with "?"
            historyBean = new HistoryBean(fileIncoming.issues[i].changelog.histories[j]?.created
            )

            int k = 0
            while (fileIncoming.issues[i].changelog.histories[j]?.items[k]?.field) {
              historyItemBean = new HistoryItemBean(fileIncoming.issues[i].changelog.histories[j].items[k].field,
                                                    fileIncoming.issues[i].changelog.histories[j].items[k].fromString,
                                                    fileIncoming.issues[i].changelog.histories[j].items[k].toString,
                                                    historyBean.created)

              // Derive fixVersion from Map.
              if(historyItemBean.field == "Fix Version"){
                issueBean.fixVersion = historyItemBean.toString
              }

              // Store History Items.
              historyBean.historyItemsMap.put(k, historyItemBean)
              k++
            }

            // Store History of an Issue.
            // issueBean.historiesMap.put(j, historyBean)
            issueBean.setHistoriesMap(j, historyBean)
          }
        }

      // Store complete Issue.
      mapIssues.put(i, issueBean)
      }
    }

    // Return the result.
    return mapIssues
  }
}
