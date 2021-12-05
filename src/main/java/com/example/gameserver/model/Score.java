package com.example.gameserver.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Score {
    @Id
    @GeneratedValue
    private int id;

    private int userScore;

    private int serverScore;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public int getServerScore() {
        return serverScore;
    }

    public void setServerScore(int serverScore) {
        this.serverScore = serverScore;
    }
}
