package com.dmacjam.airfighterjj_7.gameobjects;

/**
 * Created by Jakub on 7.4.2015.
 */
public class Score {
    private int points;

    public void addPoints(int add){
        points += add;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}
