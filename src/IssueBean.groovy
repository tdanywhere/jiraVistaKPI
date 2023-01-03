import java.text.SimpleDateFormat

/**
 * <p>jiraVistaKPI: </p>
 * <p>Usage: Generate and calculate Jira KPIs for VDM</p>
 * <p>Organisation: </p>
 * @author Th. Deter
 * @version 1.0
 */

public class IssueBean {
  final ITEM_STATUS_BACKLOG = "Backlog"
  final ITEM_STATUS_INPROGRESS = "In Progress"
  final ITEM_STATUS_PAUSED = "PAUSED"
  final ITEM_STATUS_INCLARIFICATION = "In Clarification"
  final ITEM_STATUS_INCODEREVIEW = "IN CODE REVIEW"
  final ITEM_STATUS_TESTABLE = "Testable"
  final ITEM_STATUS_TEST_NOT_OK = "Test not OK"
  final ITEM_STATUS_TEST_OK = "Test OK"
  final ITEM_STATUS_DELIVERED = "DELIVERED"
  final ITEM_STATUS_DELIVERED_INST = "DELIVERED INSTALLED"
  final ITEM_STATUS_DELIVERED_OK = "Delivered OK"
  final ITEM_STATUS_INPRODUCTION = "IN PRODUCTION"

  String datePattern = "yyyy-MM-dd'T'HH:mm:ss";
  SimpleDateFormat df = new SimpleDateFormat(datePattern,Locale.GERMANY)

  // Issue's properties
  def String  key
  def String  summary
  def String  issueType
  def String  status
  def String  assigneeName
  def String  componentName
  def String  teamName
  def String  priorityName
  def String  creatorName
  def String  created
  def String  vistaModule
  def String  estimateDays
  def Integer changelogsTotal
  def Integer statusChanges = 0

  def historiesMap  = [:]

  // Values derived from Maps.
  def String  fixVersion

  // Values derived from child Maps.
  def String dateStartBacklog
  def String dateStartProgress
  def String dateStartPaused
  def String dateStartInClarification
  def String dateStartInCodeReview
  def String dateStartTestable
  def String dateStartTestNotOK
  def String dateStartTestOK
  def String dateStartDelivered
  def String dateStartDeliveredInstalled
  def String dateStartDeliveredOK
  def String dateStartInProduction

  def BigDecimal durationCreated = 0
  def BigDecimal durationBacklog = 0
  def BigDecimal durationInProgress = 0
  def BigDecimal durationPaused = 0
  def BigDecimal durationInClarification = 0
  def BigDecimal durationInCodeReview = 0
  def BigDecimal durationTestable = 0
  def BigDecimal durationTestNotOK = 0
  def BigDecimal durationTestOK = 0
  def BigDecimal durationDelivered = 0

  def String  previousHistoryDate

  /**
   * Construct the Issue.
   */
  public IssueBean(key, summary, issueType, status, assigneeName, componentName
                  ,teamName, priorityName, creatorName, created, vistaModule
                  ,changelogsTotal, estimateDays ){
    this.key = key
    this.summary = summary
    this.issueType = issueType
    this.status = status
    this.assigneeName = assigneeName
    this.componentName = componentName
    this.teamName = teamName
    this.priorityName = priorityName
    this.creatorName = creatorName
    this.created = created
    this.vistaModule = vistaModule
    this.changelogsTotal = changelogsTotal
    this.estimateDays = estimateDays
  }

  /**
   * Add a new HistoryMap to the Issue.
   */
  void setHistoriesMap(key, history) {
    this.historiesMap.put(key, history)

    // Number of Status changes.
    history.historyItemsMap.each() {keyItems, historyItem ->
      if (historyItem.field == "status"){
        this.statusChanges ++
      }
    }

    // StartDate and Duration in status.
    history.historyItemsMap.each() {keyItems, historyItem ->
      if (historyItem.field == "status") {

        // Derive date of status change from Map.
        if (historyItem.toString == ITEM_STATUS_BACKLOG){
          this.dateStartBacklog = historyItem.startDate
        }else if(historyItem.toString == ITEM_STATUS_INPROGRESS){
          this.dateStartProgress = historyItem.startDate
        }else if(historyItem.toString == ITEM_STATUS_PAUSED){
          this.dateStartPaused = historyItem.startDate
        }else if(historyItem.toString == ITEM_STATUS_INCLARIFICATION){
          this.dateStartInClarification = historyItem.startDate
        }else if(historyItem.toString == ITEM_STATUS_INCODEREVIEW){
          this.dateStartInCodeReview = historyItem.startDate
        }else if(historyItem.toString == ITEM_STATUS_TESTABLE){
          this.dateStartTestable = historyItem.startDate
        }else if(historyItem.toString == ITEM_STATUS_TEST_OK){
          this.dateStartTestOK = historyItem.startDate
        }else if(historyItem.toString == ITEM_STATUS_TEST_NOT_OK){
          this.dateStartTestNotOK = historyItem.startDate
        }else if(historyItem.toString == ITEM_STATUS_DELIVERED){
          this.dateStartDelivered = historyItem.startDate
        }else if(historyItem.toString == ITEM_STATUS_DELIVERED_INST){
          this.dateStartDeliveredInstalled = historyItem.startDate
        }else if(historyItem.toString == ITEM_STATUS_DELIVERED_OK){
          this.dateStartDeliveredOK = historyItem.startDate
        }else if(historyItem.toString == ITEM_STATUS_INPRODUCTION){
          this.dateStartInProduction = historyItem.startDate
        }

        // Calculate duration in Status CREATED.
        if (historyItem.toString == this.ITEM_STATUS_BACKLOG) {
          this.dateStartBacklog = this.dateStartBacklog ?: history.created
          this.durationCreated = calcDuration(this.created, this.dateStartBacklog)
        }

        // Calculate duration in Status BACKLOG.
        if (historyItem.fromString == this.ITEM_STATUS_BACKLOG) {
          //this.durationBacklog += calcDuration(this.dateChangedBacklog ?: this.previousHistoryDate ?: history.created, history.created)
          this.durationBacklog = calcDuration(this.dateStartBacklog ?: history.created, history.created)

          // Save History Date for next call of setHistoriesMap()
          this.previousHistoryDate = history.created
        }

        // Calculate duration in Status IN PROGRESS.
        if (historyItem.fromString == this.ITEM_STATUS_INPROGRESS) {
          this.durationInProgress += calcDuration(this.dateStartProgress ?: history.created, history.created)

          // Save History Date for next call of setHistoriesMap()
          this.previousHistoryDate = history.created
        }

        // Calculate duration in Status PAUSED.
        if (historyItem.fromString == this.ITEM_STATUS_PAUSED) {
          this.durationPaused += calcDuration(this.dateStartPaused ?: history.created, history.created)

          // Save History Date for next call of setHistoriesMap()
          this.previousHistoryDate = history.created
        }

        // Calculate duration in Status IN CLARIFICATION.
        if (historyItem.fromString == this.ITEM_STATUS_INCLARIFICATION) {
          this.durationInClarification += calcDuration(this.dateStartInClarification ?: history.created, history.created)

          // Save History Date for next call of setHistoriesMap()
          this.previousHistoryDate = history.created
        }

        // Calculate duration in Status IN CODEREVIEW.
        if (historyItem.fromString == this.ITEM_STATUS_INCODEREVIEW) {
          this.durationInCodeReview += calcDuration(this.dateStartInCodeReview ?: history.created, history.created)

          // Save History Date for next call of setHistoriesMap()
          this.previousHistoryDate = history.created
        }

        // Calculate duration in Status TESTABLE.
        if (historyItem.fromString == this.ITEM_STATUS_TESTABLE) {
          this.durationTestable += calcDuration(this.dateStartTestable ?: history.created, history.created)

          // Save History Date for next call of setHistoriesMap()
          this.previousHistoryDate = history.created
        }

        // Calculate duration in Status TEST NOT OK.
        if (historyItem.fromString == this.ITEM_STATUS_TEST_NOT_OK) {
          this.durationTestNotOK += calcDuration(this.dateStartTestNotOK ?: history.created, history.created)

          // Save History Date for next call of setHistoriesMap()
          this.previousHistoryDate = history.created
        }

        // Calculate duration in Status TEST OK.
        if (historyItem.fromString == this.ITEM_STATUS_TEST_OK) {
          this.durationTestOK += calcDuration(this.dateStartTestOK ?: history.created, history.created)

          // Save History Date for next call of setHistoriesMap()
          this.previousHistoryDate = history.created
        }

        // Calculate duration in Status DELIVERED.
        if (historyItem.fromString == this.ITEM_STATUS_DELIVERED) {
          this.durationDelivered += calcDuration(this.dateStartDelivered ?: history.created, history.created)

          // Save History Date for next call of setHistoriesMap()
          this.previousHistoryDate = history.created
        }
        if (historyItem.fromString == this.ITEM_STATUS_DELIVERED_INST) {
          this.durationDelivered += calcDuration(this.dateStartDeliveredInstalled ?: history.created, history.created)

          // Save History Date for next call of setHistoriesMap()
          this.previousHistoryDate = history.created
        }
        if (historyItem.fromString == this.ITEM_STATUS_DELIVERED_OK) {
          this.durationDelivered += calcDuration(this.dateStartDeliveredOK ?: history.created, history.created)

          // Save History Date for next call of setHistoriesMap()
          this.previousHistoryDate = history.created
        }
      }
    }
  }

  /**
   * Function to calculate durations.
   */
  def BigDecimal calcDuration (startDate, endDate){
    def cdStartDate = df.parse(startDate)
    def cdEndDate = df.parse(endDate ?: '2022-01-01T00:00:00')
    def BigDecimal duration

    use(groovy.time.TimeCategory) {
      duration = (((cdEndDate - cdStartDate).days *24 ) + (cdEndDate - cdStartDate).hours)
    }

    return duration
  }

}
