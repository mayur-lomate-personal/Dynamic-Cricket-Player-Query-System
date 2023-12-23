package com.mayur.jpaspecificationexample.jpaspecificationexampleapplication;

import com.mayur.jpaspecificationexample.jpaspecificationexampleapplication.controller.PlayerController;
import com.mayur.jpaspecificationexample.jpaspecificationexampleapplication.model.Player;
import com.mayur.jpaspecificationexample.jpaspecificationexampleapplication.repository.PlayerRepository;
import com.mayur.jpaspecificationexample.jpaspecificationexampleapplication.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(value = PlayerController.class)
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PlayerRepository playerRepository;


    @MockBean
    PlayerService playerService;

    String examplePlayerJson = "{\"id\":1,\"firstName\":\"mayur\",\"lastName\":\"Lomate\",\"totalRuns\":475,\"totalMatchPlayed\":4,\"totalWickets\":1,\"average\":101.1,\"economy\":6.2}";

    @Test
    public void createPlayer() throws Exception {

        // playerService.addCourse to respond back with mockCourse
        Mockito.doNothing().when(playerService).addPlayer(Mockito.any(Player.class));

        // Send course as body to /students/Student1/courses
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/players")
                .accept(MediaType.APPLICATION_JSON).content(examplePlayerJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

    }
}
