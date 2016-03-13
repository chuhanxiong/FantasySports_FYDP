package com.fantasysports.Model;

import java.util.LinkedHashMap;

/**
 * Created by hamy on 3/13/2016.
 */
public class NHLQueryObject {

    private String playerName = "";
    private String teamName = "";
    private int G = 0;
    private int A = 0;
    private int PTS = 0;
    private double shotPercent = 0.0;
    private String startDate = "";
    private String endDate = "";
    private LinkedHashMap<String, String> map;

    public NHLQueryObject() {
        map = new LinkedHashMap<String, String>();
    }

    public LinkedHashMap<String, String> getMap() {
        return map;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getG() {
        return G;
    }

    public void setG(int g) {
        G = g;
        map.put("G", String.valueOf(g));
    }

    public int getA() {
        return A;
    }

    public void setA(int a) {
        A = a;
        map.put("A", String.valueOf(a));
    }

    public int getPTS() {
        return PTS;
    }

    public void setPTS(int PTS) {
        this.PTS = PTS;
        map.put("PTS", String.valueOf(PTS));
    }

    public double getShotPercent() {
        return shotPercent;
    }

    public void setShotPercent(double shotPercent) {
        this.shotPercent = shotPercent;
        map.put("S%", String.valueOf(shotPercent));
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
