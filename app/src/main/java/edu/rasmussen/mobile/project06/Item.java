package edu.rasmussen.mobile.project06;

import java.io.Serializable;

/**
 * Created by nicho_000 on 9/7/2015.
 */
public class Item implements Serializable{

    String desc;
    String clue1;
    String clue2;
    String clue3;
    String answer1;
    String answer2;
    String lat="0";
    String lon;
    String price;
    String name;
    double distance;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    int guess=0;

    public String getClue3() {
        return clue3;
    }

    public void setClue3(String clue3) {
        this.clue3 = clue3;
    }

    public int getGuess() {
        return guess;
    }

    public void setGuess(int guess) {
        this.guess = guess;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getClue1() {
        return clue1;
    }

    public void setClue1(String clue1) {
        this.clue1 = clue1;
    }

    public String getClue2() {
        return clue2;
    }

    public void setClue2(String clue2) {
        this.clue2 = clue2;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
