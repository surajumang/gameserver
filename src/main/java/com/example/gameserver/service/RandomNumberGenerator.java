package com.example.gameserver.service;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;

@Component
public class RandomNumberGenerator  {

    private Random random;

    @PostConstruct
    void init() {
        random = new Random();
    }

    public int nextInt(int to) {
        return random.nextInt(to);
    }
}
