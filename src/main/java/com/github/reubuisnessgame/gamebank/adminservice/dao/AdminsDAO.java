package com.github.reubuisnessgame.gamebank.adminservice.dao;

import com.github.reubuisnessgame.gamebank.adminservice.form.ChangingUserDataForm;
import com.github.reubuisnessgame.gamebank.adminservice.form.StartGameForm;
import com.github.reubuisnessgame.gamebank.adminservice.model.AdminModel;
import com.github.reubuisnessgame.gamebank.adminservice.model.BlockScoreModel;
import com.github.reubuisnessgame.gamebank.adminservice.model.TeamModel;
import com.github.reubuisnessgame.gamebank.adminservice.model.UserModel;
import com.github.reubuisnessgame.gamebank.adminservice.repository.BlockScoreRepository;
import com.github.reubuisnessgame.gamebank.adminservice.repository.TeamsRepository;
import com.github.reubuisnessgame.gamebank.adminservice.repository.UserRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Component
public class AdminsDAO {
    private final RepositoryComponent repositoryComponent;

    private final UserRepository userRepository;

    private final TeamsRepository teamsRepository;

    private static final String TEAM_BASE_URL = "http://127.0.0.1:9993/" + "team" ;
    private static final String SHARES_BASE_URL  = "http://127.0.0.1:9994/" + "stock";
    private static final String PROBLEMS_BASE_URL  = "http://127.0.0.1:9995/" + "problems";

    private final BlockScoreRepository blockScoreRepository;
    private boolean isGameStarted;

    private Logger LOGGER = LoggerFactory.getLogger(AdminsDAO.class.getSimpleName());


    public AdminsDAO(RepositoryComponent repositoryComponent, TeamsRepository teamsRepository, UserRepository userRepository, BlockScoreRepository blockScoreRepository) {
        this.repositoryComponent = repositoryComponent;
        this.teamsRepository = teamsRepository;
        this.userRepository = userRepository;
        this.blockScoreRepository = blockScoreRepository;
        isGameStarted = false;
    }


    public AdminModel createNewAdmin(String username, String password, String userRole, Double maxScore, Double coefficient) {
        return repositoryComponent.saveNewAdmin(username, password, userRole, maxScore, coefficient);
    }

    public ChangingUserDataForm changeAdmin(String username, String newUsername, String newPassword, String newRole, Double newMaxScore, Double newCoefficient) {
        return repositoryComponent.changeAdminData(username, newUsername, newPassword, newRole, newMaxScore, newCoefficient);
    }

    public void startGame(String token, boolean isGameStarted) {
        this.isGameStarted = isGameStarted;

        LOGGER.info("Game Started");

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        StartGameForm form = new StartGameForm(isGameStarted, System.currentTimeMillis());

        HttpEntity<StartGameForm> request = new HttpEntity<>(form, headers);

        ResponseEntity<Void> response;

        try {
            response = restTemplate.postForEntity(TEAM_BASE_URL+"/game", request, Void.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new HttpServerErrorException(response.getStatusCode());
            }
        } catch (Exception e){
            LOGGER.warn("Teams service not available", e);

        }

        try {
            response = restTemplate.postForEntity(SHARES_BASE_URL + "/game", request, Void.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new HttpServerErrorException(response.getStatusCode());
            }
        } catch (Exception e){
            LOGGER.warn("StockExchange service not available", e);
        }

        try {
            response = restTemplate.postForEntity(PROBLEMS_BASE_URL + "/game", request, Void.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new HttpServerErrorException(response.getStatusCode());
            }
        } catch (Exception e){
            LOGGER.warn("Problems service not available", e);
        }

    }

    public TeamModel createNewTeam(Long number) {
        return repositoryComponent.saveNewTeam(number);
    }


    public TeamModel getTeam(Long number) {
        return repositoryComponent.getTeamByNumber(number);
    }

    public UserModel lockUnlockUserByUsername(String username, boolean nonLocked) {
        UserModel userModel = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username: " + username + " not found"));
        userModel.setNonLocked(nonLocked);
        return userRepository.save(userModel);
    }

    public Iterable<TeamModel> getAllTeams() throws NotFoundException {
        Iterable<TeamModel> userModels = teamsRepository.findAll();
        List<TeamModel> teamModels = new ArrayList<>();

        userModels.forEach(teamModels::add);
        teamModels.sort((t, t1) -> (int) (t.getFullScore() - t1.getFullScore()));
        throw new NotFoundException("Teams not found");
    }

    public Iterable<AdminModel> getAllAdmins() throws NotFoundException {
        return repositoryComponent.getAllAdmins();
    }

    public AdminModel getMyInfo(String token){
        return repositoryComponent.getAdminByToken(token);
    }

    public TeamModel addScore(String token, Long teamNumber, boolean isWin) throws IllegalAccessException {
        if(isGameStarted) {

            AdminModel leading = repositoryComponent.getAdminByToken(token);
            TeamModel teamModel = repositoryComponent.getTeamByNumber(teamNumber);
            BlockScoreModel model = blockScoreRepository.findByTeamId(teamModel.getUserId()).orElseThrow(() -> new IllegalArgumentException("Incorrect team data"));
            double rate = model.getRate();
            if (isWin) {
                rate *= leading.getCoefficient();
                teamModel.setScore(teamModel.getScore() + rate);
            }
            return teamsRepository.save(teamModel);
        }
        throw new IllegalAccessException("The game has not started yet");

    }

    public TeamModel blockScore(String token, Double rate, Long teamNumber) throws IllegalAccessException {
        if(isGameStarted) {
            AdminModel leading = repositoryComponent.getAdminByToken(token);
            TeamModel teamModel = repositoryComponent.getTeamByNumber(teamNumber);
            if (rate > leading.getMaxScore()) {
                rate = leading.getMaxScore();
            }
            teamModel.setScore(teamModel.getScore() - rate);
            blockScoreRepository.save(new BlockScoreModel(teamModel.getUserId(), rate));
            return teamsRepository.save(teamModel);
        }
        throw new IllegalAccessException("The game has not started yet");


    }

    public void clearAll(String token) throws IllegalAccessException {
        if(!isGameStarted) {
            repositoryComponent.clearAll();

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);


            HttpEntity<Void> request = new HttpEntity<>(headers);
            ResponseEntity<Void> response;

            /*try {
                response = restTemplate.postForEntity(TEAM_BASE_URL + "/clear", request, Void.class);

                if (response.getStatusCode() != HttpStatus.OK) {
                    throw new HttpServerErrorException(response.getStatusCode());
                }
            } catch (Exception ignore) {
                LOGGER.warn("Teams service not available");
            }*/

            try {
                response = restTemplate.postForEntity(SHARES_BASE_URL + "/clear", request, Void.class);

                if (response.getStatusCode() != HttpStatus.OK) {
                    throw new HttpServerErrorException(response.getStatusCode());
                }
            } catch (Exception e) {
                LOGGER.warn("StockExchange service not available", e);
            }

            try {
                response = restTemplate.postForEntity(PROBLEMS_BASE_URL + "/clear", request, Void.class);

                if (response.getStatusCode() != HttpStatus.OK) {
                    throw new HttpServerErrorException(response.getStatusCode());
                }

            } catch (Exception e){
                LOGGER.warn("Problems service not available", e);
            }
            throw new IllegalAccessException("The game has not started yet");
        }
    }
}
