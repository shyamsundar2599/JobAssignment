############################################
#   @Project Name: Interview Assignment    #
#   @Author: Shyam Sundar S                #
############################################

Feature:Job_Interview_Assignemnt_Scenarios2

@test2
Scenario:Get_Price_of_product_from_Flipkart 
   Given Launch "https://www.flipkart.com/" in Chrome WebBrowser
   Then check Flipkart application is loaded successfully
   And search "APPLE iPhone 13 (Starlight, 256 GB)" product in Flipkart App
   Then get price of "APPLE iPhone 13 (Starlight, 256 GB)" product in FlipKart App


@test2
Scenario:Get_Price_of_product_from_Amazon_and_Compare
   Given Launch "https://www.amazon.in/" in Chrome WebBrowser
   Then check Amazon application is loaded successfully
   And search "Apple iPhone 13 (256GB) - Starlight" product in Amazon App
   Then get price of "Apple iPhone 13 (256GB) - Starlight" product in Amazon App
   Then compare the price between Amazon and Flipkart App
   #you can find report in target folder