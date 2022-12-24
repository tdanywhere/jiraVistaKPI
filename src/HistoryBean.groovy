/**
 * <p>jiraVistaKPI: </p>
 * <p>Usage: Generate and calculate Jira KPIs for VDM</p>
 * <p>Organisation: </p>
 * @author Th. Deter
 * @version 1.0
 */
public class HistoryBean {
  // History's properties
  def String  created

  def historyItemsMap = [:]

  public HistoryBean(created){
    this.created = created
  }
}
