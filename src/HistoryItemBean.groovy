/**
 * <p>jiraVistaKPI: </p>
 * <p>Usage: Generate and calculate Jira KPIs for VDM</p>
 * <p>Organisation: </p>
 * @author Th. Deter
 * @version 1.0
 */

public class HistoryItemBean {
  // Item's properties
  def String  field
  def String  fromString
  def String  toString
  def String  startDate

  public HistoryItemBean(field, fromString, toString, startDate){
    this.field = field
    this.fromString = fromString
    this.toString = toString
    this.startDate = startDate
  }
}
