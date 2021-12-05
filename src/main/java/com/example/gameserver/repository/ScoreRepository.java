package com.example.gameserver.repository;

import com.example.gameserver.model.Score;
import org.springframework.data.repository.CrudRepository;

public interface ScoreRepository extends CrudRepository<Score, Integer> {
}
