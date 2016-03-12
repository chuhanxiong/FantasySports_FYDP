package com.fantasysports.Model;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by hamy on 3/1/2016.
 */
public class NBAQueryObject {

    private String[] playerName;
    private String teamName;
    private double MP = 0.0;
    private int FG = 0;
    private int FGA = 0;
    private double FGPercent = 0.0;;
    private int threeP = 0;
    private int threePA = 0;
    private double threePPercent = 0.0;;
    private int FT = 0;
    private int FTA = 0;
    private double FTPercent = 0.0;;
    private int ORB = 0;
    private int DRB = 0;
    private int TRB = 0;
    private int AST = 0;
    private int STL = 0;
    private int BLK = 0;
    private int TOV = 0;
    private int PF = 0;
    private int PTS = 0;
    private String startDate = "";
    private String endDate = "";

    public String[] getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String[] playerName) {
        this.playerName = playerName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public double getMP() {
        return MP;
    }

    public void setMP(double MP) {
        this.MP = MP;
    }

    public int getFG() {
        return FG;
    }

    public void setFG(int FG) {
        this.FG = FG;
    }

    public int getFGA() {
        return FGA;
    }

    public void setFGA(int FGA) {
        this.FGA = FGA;
    }

    public double getFGPercent() {
        return FGPercent;
    }

    public void setFGPercent(double FGPercent) {
        this.FGPercent = FGPercent;
    }

    public int getThreeP() {
        return threeP;
    }

    public void setThreeP(int threeP) {
        this.threeP = threeP;
    }

    public int getThreePA() {
        return threePA;
    }

    public void setThreePA(int threePA) {
        this.threePA = threePA;
    }

    public double getThreePPercent() {
        return threePPercent;
    }

    public void setThreePPercent(double threePPercent) {
        this.threePPercent = threePPercent;
    }

    public int getFT() {
        return FT;
    }

    public void setFT(int FT) {
        this.FT = FT;
    }

    public int getFTA() {
        return FTA;
    }

    public void setFTA(int FTA) {
        this.FTA = FTA;
    }

    public double getFTPercent() {
        return FTPercent;
    }

    public void setFTPercent(double FTPercent) {
        this.FTPercent = FTPercent;
    }

    public int getORB() {
        return ORB;
    }

    public void setORB(int ORB) {
        this.ORB = ORB;
    }

    public int getDRB() {
        return DRB;
    }

    public void setDRB(int DRB) {
        this.DRB = DRB;
    }

    public int getTRB() {
        return TRB;
    }

    public void setTRB(int TRB) {
        this.TRB = TRB;
    }

    public int getAST() {
        return AST;
    }

    public void setAST(int AST) {
        this.AST = AST;
    }

    public int getSTL() {
        return STL;
    }

    public void setSTL(int STL) {
        this.STL = STL;
    }

    public int getBLK() {
        return BLK;
    }

    public void setBLK(int BLK) {
        this.BLK = BLK;
    }

    public int getTOV() {
        return TOV;
    }

    public void setTOV(int TOV) {
        this.TOV = TOV;
    }

    public int getPF() {
        return PF;
    }

    public void setPF(int PF) {
        this.PF = PF;
    }

    public int getPTS() {
        return PTS;
    }

    public void setPTS(int PTS) {
        this.PTS = PTS;
    }
    
    public static NBAQueryObject convertFileToNBAQueryObject(MultipartFile file) throws IOException{
    	System.out.println(file.getInputStream().toString());
    	return null;
    }
}

