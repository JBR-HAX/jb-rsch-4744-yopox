package org.jetbrains.assignment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

record Movement(String direction, int steps) {}
record Coordinates(int x, int y) {}

@RestController
public class MovementController {
    @PostMapping("/locations")
    public ResponseEntity<String> processMovements(@RequestBody List<Movement> movements) {
        List<Coordinates> coordinatesList = new ArrayList<>();
        coordinatesList.add(Robot.robot.getCoordinates());

        for (Movement movement : movements) {
            Robot.robot.move(Util.Direction.valueOf(movement.direction()), movement.steps());
            coordinatesList.add(Robot.robot.getCoordinates());
        }

        String result;
        try {
            result = new ObjectMapper().writeValueAsString(coordinatesList);
        } catch (JsonProcessingException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
            result = "Serialization failed!";
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}