package com.example.gameserver.model;

public enum GameChoice {
    ROCK("Rock"), PAPER("Paper"), SCISSOR("Scissors");

    private final String prettyName;

    GameChoice(String prettyName) {
        this.prettyName = prettyName;
    }

    public String getPrettyName() {
        return prettyName;
    }

    public GameChoice getWinningChoice() {
        switch (this) {
            case ROCK:
                return PAPER;
            case PAPER:
                return SCISSOR;
            case SCISSOR:
                return ROCK;
        }
        throw new RuntimeException("Invalid GameChoice received");
    }

    public static GameChoice fromString(String gameChoice) {
        if (ROCK.name().equalsIgnoreCase(gameChoice)){
            return ROCK;
        }
        if (PAPER.name().equalsIgnoreCase(gameChoice)){
            return PAPER;
        }
        if (SCISSOR.name().equalsIgnoreCase(gameChoice)){
            return SCISSOR;
        }
        throw new RuntimeException("Invalid name provided for game choice" + gameChoice);
    }

    public boolean canWinAgainst(GameChoice other) {
        switch (this) {
            case ROCK:
                return other == SCISSOR;
            case PAPER:
                return other == ROCK;
            case SCISSOR:
                return other == PAPER;
        }
        throw new RuntimeException("Invalid GameChoice received");
    }

    public static GameChoice getFromInt(int num) {
        switch (num) {
            case 0: return ROCK;
            case 1: return PAPER;
            case 2: return SCISSOR;
        }
        throw new RuntimeException("Invalid number received for Game choice");
    }

}
