package com.github.reubuisnessgame.gamebank.adminservice.repository;

import com.github.reubuisnessgame.gamebank.adminservice.model.AdminModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends CrudRepository<AdminModel, Long> {

    Optional<AdminModel> findAllByUsername(String username);
    Optional<AdminModel> findByUserId(long userId);
}
