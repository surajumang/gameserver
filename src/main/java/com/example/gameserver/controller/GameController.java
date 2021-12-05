package com.example.gameserver.controller;

import com.example.gameserver.model.GameChoice;
import com.example.gameserver.model.Score;
import com.example.gameserver.repository.ScoreRepository;
import com.example.gameserver.service.RandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
public class GameController {
    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private RandomNumberGenerator randomNumberGenerator;

    @GetMapping(path = "/start")
    public @ResponseBody ResponseEntity start(){
        Score score = new Score();
        score.setUserScore(0);
        score.setServerScore(0);
        Score savedScore = scoreRepository.save(score);
        System.out.printf("Generated token is: %s with\n", savedScore.getId());
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "READY");
        responseBody.put("token", savedScore.getId());
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping(path = "/v1/{token}/{choice}")
    public ResponseEntity playV1(@PathVariable Integer token, @PathVariable String choice) {
        Optional<Score> savedScore = scoreRepository.findById(token);
        if (savedScore.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        if (savedScore.get().getUserScore() >= 3 || savedScore.get().getServerScore() >= 3) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "ENDED");
            return ResponseEntity.ok(responseBody);
        }
        Score dbScore = savedScore.get();
        GameChoice userChoice = GameChoice.fromString(choice);
        GameChoice serverChoice = GameChoice.getFromInt(randomNumberGenerator.nextInt(3));
        if (userChoice != serverChoice) {
            if(userChoice.canWinAgainst(serverChoice)) {
                dbScore.setUserScore(dbScore.getUserScore() + 1);
            }else {
                dbScore.setServerScore(dbScore.getServerScore() + 1);
            }
        }
        scoreRepository.save(dbScore);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("serverChoice", serverChoice.getPrettyName());
        responseBody.put("userScore", dbScore.getUserScore());
        responseBody.put("serverScore", dbScore.getServerScore());
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping(path = "/v2/{token}/{choice}")
    public ResponseEntity playV2(@PathVariable Integer token, @PathVariable String choice) {
        Optional<Score> savedScore = scoreRepository.findById(token);
        if (savedScore.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        if (savedScore.get().getUserScore() >= 3 || savedScore.get().getServerScore() >= 3) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "ENDED");
            return ResponseEntity.ok(responseBody);
        }
        Score dbScore = savedScore.get();
        GameChoice userChoice = GameChoice.fromString(choice);
        GameChoice serverChoice = userChoice.getWinningChoice();

        dbScore.setServerScore(dbScore.getServerScore() + 1);
        scoreRepository.save(dbScore);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("serverChoice", serverChoice.getPrettyName());
        responseBody.put("userScore", dbScore.getUserScore());
        responseBody.put("serverScore", dbScore.getServerScore());
        return ResponseEntity.ok(responseBody);
    }


}
