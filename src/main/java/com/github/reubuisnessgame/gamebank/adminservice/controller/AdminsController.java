package com.github.reubuisnessgame.gamebank.adminservice.controller;

import com.github.reubuisnessgame.gamebank.adminservice.dao.AdminsDAO;
import com.github.reubuisnessgame.gamebank.adminservice.form.*;
import com.github.reubuisnessgame.gamebank.adminservice.model.ExceptionModel;
import com.github.reubuisnessgame.gamebank.adminservice.model.UserModel;
import javassist.NotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin")
public class AdminsController {

    private final AdminsDAO adminsDAO;

    private Logger LOGGER = LoggerFactory.getLogger(AdminsController.class.getSimpleName());


    public AdminsController(AdminsDAO adminsDAO) {
        this.adminsDAO = adminsDAO;
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @RequestMapping(value = "/create_adm", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity createNewAdmin(@RequestBody CreateAdminForm form) {
        try {
            return ResponseEntity.ok(adminsDAO.createNewAdmin(form.getUsername(), form.getPassword(), form.getRole(),
                    form.getMaxScore(), form.getCoefficient()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ExceptionModel(400, "Bad Request", e.getMessage(), "/admin/create_adm"));
        } catch (Throwable t) {
            return ResponseEntity.status(500).body(new ExceptionModel(500, "Internal Error", t.getMessage(), "/admin/create_adm"));
        }
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @RequestMapping(value = "/change_adm", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity changeAdmin(@RequestBody ChangeAdminForm form) {
        try {
            return ResponseEntity.ok(adminsDAO.changeAdmin(form.getLastUsername(), form.getNewUsername(), form.getNewPassword(),
                    form.getNewRole(), form.getNewMaxScore(), form.getNewCoefficient()));
        } catch (IllegalArgumentException | UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body(new ExceptionModel(400, "Bad Request", e.getMessage(), "/admin/change_adm"));
        } catch (Throwable t) {
            return ResponseEntity.status(500).body(new ExceptionModel(500, "Internal Error", t.getMessage(), "/admin/change_adm"));
        }
    }

    @PreAuthorize("hasAuthority('MODERATOR') or hasAuthority('LEADING') or hasAuthority('EXCHANGE_WORKER')")
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity aboutMe(@RequestHeader(value = "Authorization") String token) {
        try {
            return ResponseEntity.ok(adminsDAO.getMyInfo(token));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ExceptionModel(400, "Bad Request", e.getMessage(), "/admin/me"));
        } catch (Throwable t) {
            return ResponseEntity.status(500).body(new ExceptionModel(500, "Internal Error", t.getMessage(), "/admin/me"));
        }
    }


    @PreAuthorize("hasAuthority('MODERATOR')")
    @RequestMapping(value = "/game", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity startGame(@RequestHeader(value = "Authorization") String token, @RequestParam boolean start) {
        try {
            LOGGER.info("Start or stop game");
            adminsDAO.startGame(token, start);
            Map<String, Object> map = new HashMap<>();
            map.put("status", "ok");
            map.put("isGameStated", start);
            return ResponseEntity.ok(map);
        } catch (Throwable t) {
            return ResponseEntity.status(500).body(new ExceptionModel(500, "Internal Error", t.getMessage(), "/admin/start"));
        }
    }

    @PreAuthorize("hasAuthority('MODERATOR') or hasAuthority('LEADING') or hasAuthority('EXCHANGE_WORKER')")
    @RequestMapping(value = "/create_team/{number}", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity createTeam(@PathVariable Long number) {
        try {
            return ResponseEntity.ok(adminsDAO.createNewTeam(number));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ExceptionModel(400, "Bad Request", e.getMessage(), "/admin/create_team/" + number));
        } catch (Throwable t) {
            return ResponseEntity.status(500).body(new ExceptionModel(500, "Internal Error", t.getMessage() + " Type: " + t.getClass().getSimpleName(), "/admin/create_team/" + number));
        }
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @RequestMapping(value = "/teams", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity getAllTeams() {
        try {
            return ResponseEntity.ok(new AllTeamsForm(adminsDAO.getAllTeams()));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(new ExceptionModel(404, "Not Found", e.getMessage(), "/admin/teams"));
        } catch (Throwable t) {
            return ResponseEntity.status(500).body(new ExceptionModel(500, "Internal Error", t.getMessage(), "/admin/teams"));
        }
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity getAllUsers() {
        try {
            AllUsersForm form = new AllUsersForm();
            form.setTeams(adminsDAO.getAllTeams());
            form.setAdmins(adminsDAO.getAllAdmins());
            return ResponseEntity.ok(form);
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(new ExceptionModel(404, "Not Found", e.getMessage(), "/admin/users"));
        } catch (Throwable t) {
            return ResponseEntity.status(500).body(new ExceptionModel(500, "Internal Error", t.getMessage(), "/admin/users"));
        }
    }

    @PreAuthorize("hasAuthority('MODERATOR') or hasAuthority('LEADING') or hasAuthority('EXCHANGE_WORKER')")
    @RequestMapping(value = "/team/{number}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity getTeam(@PathVariable Long number) {
        try {
            return ResponseEntity.ok(adminsDAO.getTeam(number));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(404).body(new ExceptionModel(404, "Not Found", e.getMessage(), "/admin/team/" + number));
        } catch (Throwable t) {
            return ResponseEntity.status(500).body(new ExceptionModel(500, "Internal Error", t.getMessage(), "/admin/team/" + number));
        }
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @RequestMapping(value = "/lock/{username}", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity lockUser(@PathVariable(value = "username") String username, @RequestParam boolean lock) {
        try {
            UserModel model = adminsDAO.lockUnlockUserByUsername(username, lock);
            Map <String,Object> map = new HashMap<>();
            map.put("username", model.getUsername());
            map.put("role", model.getRole());
            map.put("non_locked", model.isAccountNonLocked());
            return ResponseEntity.ok(map);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(404).body(new ExceptionModel(404, "Not Found", e.getMessage(), "/admin/lock/" + username));
        } catch (Throwable t) {
            return ResponseEntity.status(500).body(new ExceptionModel(500, "Internal Error", t.getMessage(), "/admin/lock/" + username));
        }
    }

    @PreAuthorize("hasAuthority('MODERATOR') or hasAuthority('LEADING')")
    @RequestMapping(value = "/block_scr", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity blockScore(@RequestHeader(value = "Authorization") String token, @RequestBody AddScoreForm form) {
        try {
            LOGGER.info("Blocking score " + form.getTeamNumber());
            return ResponseEntity.ok(adminsDAO.blockScore(token, form.getRate(), form.getTeamNumber()));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(400).body(new ExceptionModel(400, "Bad Request", e.getMessage(), "/team/block_scr"));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(404).body(new ExceptionModel(404, "Not Found", e.getMessage(), "/admin/block_scr"));
        } catch (Throwable t) {
            return ResponseEntity.status(500).body(new ExceptionModel(500, "Internal Error", t.getMessage(), "/admin/block_scr"));
        }
    }

    @PreAuthorize("hasAuthority('MODERATOR') or hasAuthority('LEADING')")
    @RequestMapping(value = "/add_scr", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity addScore(@RequestHeader(value = "Authorization") String token, @RequestBody AddScoreForm form) {
        try {
            return ResponseEntity.ok(adminsDAO.addScore(token, form.getTeamNumber(), form.isWin()));
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(403).body(new ExceptionModel(403, "Forbidden", e.getMessage(), "/team/add_scr"));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(404).body(new ExceptionModel(404, "Not Found", e.getMessage(), "/admin/add_scr"));
        } catch (Throwable t) {
            return ResponseEntity.status(500).body(new ExceptionModel(500, "Internal Error", t.getClass().getSimpleName(), "/admin/add_scr"));
        }
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @RequestMapping(value = "/clear", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity clearAll(@RequestHeader(value = "Authorization") String token) {
        try {
            adminsDAO.clearAll(token);
            Map<String, Object> map = new HashMap<>();
            map.put("status", "ok");
            return ResponseEntity.ok(map);
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(403).body(new ExceptionModel(403, "Forbidden", e.getMessage(), "/admin/clear"));
        } catch (Throwable t) {
            return ResponseEntity.status(500).body(new ExceptionModel(500, "Internal Error", t.getMessage(), "/admin/clear"));
        }
    }


}
