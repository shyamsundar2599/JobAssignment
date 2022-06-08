############################################
#   @Project Name: Interview Assignment    #
#   @Author: Shyam Sundar S                #
############################################

Feature:Job_Interview_Assignemnt_Scenarios
  
@test1
Scenario:Application_data_verification_and_report_creation
   Given Launch "https://generic-ui.com/demo" in Chrome WebBrowser
   Then check application is loaded successfully
   And select "100 Rows" in the row size dropdown in webpage
   Then verify GUI Grid displays "100" rows in UI
   And generate report with "Project ID # Project Name # Progress" as columns for project with "Status := Active" in webpage
   #you can find report in resources folder
   
