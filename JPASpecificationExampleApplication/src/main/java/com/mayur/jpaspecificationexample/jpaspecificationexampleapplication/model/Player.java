package com.mayur.jpaspecificationexample.jpaspecificationexampleapplication.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Schema(description = "Player Model Information")
@Getter
@Setter
@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private Long totalRuns;
    private Long totalMatchPlayed;
    private Long totalWickets;
    private Float average;
    private Float economy;
}
