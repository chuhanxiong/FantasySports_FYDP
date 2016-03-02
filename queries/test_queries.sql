#select * from fantasysports.NBAPlayer
#delete from fantasysports.NBAPlayer where playerID > 0
#select teamID from NBATeam where teamName='Detroit'
#select * from fantasysports.NBATeam
#INSERT INTO fantasysports.NBATeam (teamID, teamName, seasonID, W, L, `WL%`, GB, PSG, PAG, SRS) values (1, "Toronto Raptors", 201516, 39, 19, 0.672, 0.0, 102.3, 97.9, 3.85);
#INSERT INTO fantasysports.NBAGame (gameID, homeTeamID, awayTeamID, date, season, homeScore, awayScore, winnerID) values (1, 12, 8, 0, 201516, 94, 106, 8);

Select * from fantasysports.NBAGame;
Select * from fantasysports.NBATeam;
Select playerName, playerID from fantasysports.NBAPlayer group by playerName;
Select teamID, teamName from fantasysports.NBATeam;
Select count(*) from fantasysports.NBAPlayerStats;

Select SUM(FT) from fantasysports.NBAPlayerStats where playerID=824;
Select SUM(FTA) from fantasysports.NBAPlayerStats where playerID=824;

#delete from fantasysports.NBAPlayerStats where rowID > 0

select * from fantasysports.NBAGame as a, fantasysports.NBAPlayerStats as b where `date`='2015-10-27' and a.gameID = b.gameID;