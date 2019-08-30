package com.github.reubuisnessgame.gamebank.adminservice.repository;

import com.github.reubuisnessgame.gamebank.adminservice.model.BlockScoreModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlockScoreRepository extends CrudRepository<BlockScoreModel, Long> {
    Optional<BlockScoreModel> findByTeamId(long teamId);
    void deleteAllByTeamId(long teamId);
}
