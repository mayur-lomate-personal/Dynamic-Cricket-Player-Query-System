package com.mayur.jpaspecificationexample.jpaspecificationexampleapplication.service;

import com.mayur.jpaspecificationexample.jpaspecificationexampleapplication.model.Player;
import com.mayur.jpaspecificationexample.jpaspecificationexampleapplication.queryhelper.GenericSpecificationsBuilder;
import com.mayur.jpaspecificationexample.jpaspecificationexampleapplication.queryhelper.PlayerSpecification;
import com.mayur.jpaspecificationexample.jpaspecificationexampleapplication.queryhelper.PlayerSpecificationsBuilder;
import com.mayur.jpaspecificationexample.jpaspecificationexampleapplication.repository.PlayerRepository;
import com.mayur.jpaspecificationexample.jpaspecificationexampleapplication.util.CriteriaParser;
import com.mayur.jpaspecificationexample.jpaspecificationexampleapplication.util.SearchOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.common.base.Joiner;

@Log4j2
@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepo;

    public List<Player> getPlayers(String searchParameters) {
        PlayerSpecificationsBuilder builder = new PlayerSpecificationsBuilder();
        String operationSetExper = Joiner.on("|").join(SearchOperation.SIMPLE_OPERATION_SET);
        Pattern pattern = Pattern.compile("(\\p{Punct}?)(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
        //"(\\p{Punct}?)(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),"
        //"(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),"
        Matcher matcher = pattern.matcher(searchParameters + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(5), matcher.group(4), matcher.group(6));
        }

        Specification<Player> spec = builder.build();
        return playerRepo.findAll(spec);
    }

    public List<Player> findAllByAdvPredicate(String searchParameters) {
        Specification<Player> spec = resolveSpecificationFromInfixExpr(searchParameters);
        return playerRepo.findAll(spec);
    }

    protected Specification<Player> resolveSpecificationFromInfixExpr(String searchParameters) {
        CriteriaParser parser = new CriteriaParser();
        GenericSpecificationsBuilder<Player> specBuilder = new GenericSpecificationsBuilder<>();
        return specBuilder.build(parser.parse(searchParameters), PlayerSpecification::new);
    }

    public void addPlayer(Player resource) {
        playerRepo.save(resource);
    }
}
