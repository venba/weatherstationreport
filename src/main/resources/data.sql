
INSERT INTO STATION(id,name,province,date,mean_temp, highest_Monthly_Maxi_Temp,lowest_Monthly_Min_Temp)
  SELECT rownum(), Station_Name, Province, 
  CONVERT(PARSEDATETIME(Date,'dd/MM/yyyy'),timestamp), Mean_Temp, Highest_Monthly_Maxi_Temp, Lowest_Monthly_Min_Temp FROM CSVREAD('./src/main/resources/eng-climate-summary.csv');
