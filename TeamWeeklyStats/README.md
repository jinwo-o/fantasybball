README

TeamWeeklyStats outputs a team's projected statistics for that week, based on amount of games played that week * the
player's averages.

Input:

Go to src/main/java/ca.fantasybasketball.teamweeklystats/TeamWeeklyStats.java and enter the file path on line 16
Ex: /users/jin/work/fantasybball/FantasyCSV.csv

FantasyCSV.csv:

Player Name,Team,Position,Season,Games Left,MIN,PTS,3PM,REB,AST,STL,BLK,TO,DD
Malcolm Brogdon,MIL,PG,2019,5,30.2,15.5,1.8,4.5,3.4,0.6,0.2,1.7,0
JJ Redick,PHI,SG,2019,1,30.8,18.4,2.8,2.3,3,0.2,0.2,1.5,0
Bogdan Bogdanovic,SAC,SG,2019,1,26.5,15.1,2.1,3.3,3.5,0.9,0.1,1.1,0
Serge Ibaka,TOR,PF,2019,1,27.3,16.7,0.8,7.4,1.4,0.5,1.4,1.8,0.1
Karl-Anthony Towns,MIN,C,2019,1,33.6,21.6,1.9,11.9,2.4,0.9,1.7,3.4,0.6
Jordan Clarkson,CLE,PG,2019,1,25.1,16,1.5,3.3,2.1,0.6,0.1,1.5,0
John Collins,ATL,PF,2019,0,28,18.1,0.6,8.3,2.6,0.1,0.5,2.1,0.5
Enes Kanter,NYK,C,2019,0,26.9,14.9,0.1,11.5,2,0.4,0.5,2,0.6
Dario Saric,PHI,PF,2019,1,26.7,10.7,1.5,6.1,1.6,0.4,0.2,1.4,0.1
Jusuf Nurkic,POR,C,2019,1,26.6,15,0.1,10.6,2.4,1,1.3,2.3,0.5
Tobias Harris,LAC,PF,2019,2,34.8,21,1.9,8.2,2.2,0.6,0.5,2,0.3
T.J. Warren,PHO,SF,2019,2,28.9,17.4,1.7,4,1.3,0.9,0.8,1.1,0
Myles Turner,IND,C,2019,1,27.8,12,0.4,6.7,1.6,0.4,2.8,2.5,0
Troy Daniels,PHO,SG,2019,2,16.3,12,1.9,1.5,0.6,0.7,0.1,1.2,0

Output:

Minutes: 535.4000000000001
Threes: 31.1
Rebounds: 101.49999999999999
Assists: 43.2
Steals: 12.300000000000002
Blocks: 11.599999999999998
Turnovers: 32.6
DD: 1.9
Points: 303.8

Next Step:
Create an SQL Database