package com.github.reubuisnessgame.gamebank.adminservice.repository;

import com.github.reubuisnessgame.gamebank.adminservice.model.BlockScoreModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BlockScoreRepository extends CrudRepository<BlockScoreModel, Long> {
    Optional<BlockScoreModel> findByTeamId(long teamId);
}
