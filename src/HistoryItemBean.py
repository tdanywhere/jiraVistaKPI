'''
<p>jiraVistaKPI: </p>
<p>Usage: Generate and calculate Jira KPIs for VDM</p>
<p>Organisation: </p>
@author Th. Deter
@version 1.0
'''
class HistoryItemBean:
    def __init__(self, field: str, from_string: str, to_string: str, start_date: str):
        self.field = field
        self.from_string = from_string
        self.to_string = to_string
        self.start_date = start_date