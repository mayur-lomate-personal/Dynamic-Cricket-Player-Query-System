package com.mayur.jpaspecificationexample.jpaspecificationexampleapplication.controller;

import com.google.common.base.Preconditions;
import com.mayur.jpaspecificationexample.jpaspecificationexampleapplication.model.Player;
import com.mayur.jpaspecificationexample.jpaspecificationexampleapplication.queryhelper.PlayerSpecification;
import com.mayur.jpaspecificationexample.jpaspecificationexampleapplication.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Tag(name = "Player", description = "Player management APIs")
@RestController
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    //search=firstName:*a*
    //searchfirstName!mayur
    //search=firstName:mayur,'totalRuns>499
    @Operation(
            summary = "Retrieve a Player with Normal Filter",
            description = "This will retrieve player according to normal query provided.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Player.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @RequestMapping(method = RequestMethod.GET, value = "/players")
    public ResponseEntity<List<Player>> search(@Parameter(description = "Normal Query to be executed", example = "firstName:mayur,'totalRuns>499") @RequestParam(value = "search") String search) {
        List<Player> players =  playerService.getPlayers(search);
        if(players.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    //search=( firstName:nikhil ) OR ( totalRuns>400 AND lastName:Lomate )
    @Operation(
            summary = "Retrieve a Player with Advanced Filter",
            description = "This will retrieve player according to advanced query provided.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Player.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/players/adv")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Player>> findAllByAdvPredicate(@Parameter(description = "Advanced Query to be executed", example = "( firstName:nikhil ) OR ( totalRuns>400 AND lastName:Lomate )") @RequestParam String search) {
        List<Player> players = playerService.findAllByAdvPredicate(search);
        if(players.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(players, HttpStatus.OK);
    }


    @Operation(
            summary = "Create a Player",
            description = "This will create a player with provided values")
    @RequestMapping(method = RequestMethod.POST, value = "/players")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Player resource) {
        Preconditions.checkNotNull(resource);
        playerService.addPlayer(resource);
    }
}
