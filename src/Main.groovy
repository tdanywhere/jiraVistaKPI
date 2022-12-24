import groovy.json.JsonSlurper

/**
 * <p>jiraVistaKPI: </p>
 * <p>Usage: Generate and calculate Jira KPIs for VDM</p>
 * <p>Organisation: </p>
 * @author Th. Deter
 * @version 1.12
 */
static void main(String[] args) {
  /*
  def inPath = args[0]
  def inFileNameJson = args[1]
  def outPath = args[2]
  def outFileNameCsv = args[3]
  */


  /**
   *  Create a map consisting of counter and Issue Beans
   */
  def inPath = 'C:\\Dateien\\development\\workspace\\jiraVistaKPI\\input\\'
  def inFileNameJson = 'jira_-_restApiResponse.json'

  def jsonSlurper = new JsonSlurper()
  def fileIncoming = jsonSlurper.parse(new File(inPath + inFileNameJson))

  println("== HEADER ============================")
  println "Total: " + fileIncoming.total
  println("")

  /*
   * Create the Issues.
   */
  def mapIssues = [:]
  mapIssues = IssueFactory.createIssuesMap(fileIncoming)

  /*
   * Create output file.
   */
  def outPath = 'C:\\Dateien\\development\\workspace\\jiraVistaKPI\\output\\'
  def outFileNameCsv = 'JiraKpi.csv'
  CreateFile.createFileKpiCsv(outPath, outFileNameCsv, mapIssues)



/**
//  String apiString = 'https://jsonplaceholder.typicode.com/todos/1'
//  String apiString = 'https://jira-hoyer.apps.teamworkx.cloud/rest/api/2/search?filter=12602&expand=issues'
//  String apiString = 'https://jira-hoyer.apps.teamworkx.cloud/rest/api/2/search?filter=12602' // current sprint
//  String apiString = 'https://jira-hoyer.apps.teamworkx.cloud/rest/api/2/search?jql=issueKey=HV-1809&expand=changelog'
//  URL apiUrl = new URL(apiString)
//  def object = new JsonSlurper().parseText(apiUrl.text)
//  println "Object: $object"
 */

}