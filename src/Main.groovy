import groovy.json.JsonSlurper

/**
 * <p>jiraVistaKPI: </p>
 * <p>Usage: Generate and calculate Jira KPIs for VDM</p>
 * <p>Organisation: </p>
 * @author Th. Deter
 * @version 1.12
 */

public class Main {

	static void main (String[] args){
	  println("== Main ============================")
	  
	  def inPath = args[0]
	  def inFileNameJson = args[1]
	  def outPath = args[2]
	  def outFileNameCsv = args[3]
	  println("== inPath: " + inPath)
	  println("== inFileNameJson: " + inFileNameJson)
	  println("== outPath: " + outPath)
	  println("== outFileNameCsv: " + outFileNameCsv)
	  println("")
	  
	  /**
	   *  Create a map consisting of counter and Issue Beans
	   */
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
	  def createFile = new CreateFile()
	  createFile.createFileKpiCsv(outPath, outFileNameCsv, mapIssues)
	
	  println("== FINISH ============================")
	  
	}
}