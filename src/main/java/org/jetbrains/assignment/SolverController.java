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

@RestController
public class SolverController {
    @PostMapping("/moves")
    public ResponseEntity<String> processMoves(@RequestBody List<Coordinates> coordinatesList) {
        final var robot = Robot.robot;
        final var moves = new ArrayList<Movement>();

        for (int i = 0; i < coordinatesList.size() - 1; i++) {
            Coordinates currentPos = coordinatesList.get(i);
            Coordinates nextPos = coordinatesList.get(i + 1);

            final int dx = nextPos.x() - currentPos.x();
            final int dy = nextPos.y() - currentPos.y();

            if (dx != 0) {
                var intermediatePos = new Coordinates(nextPos.x(), currentPos.y());
                int steps = Util.calculateSteps(currentPos, intermediatePos);
                var direction = Util.getDirection(currentPos, intermediatePos);
                robot.move(direction, steps);
                moves.add(new Movement(direction.name(), steps));
            }

            if (dy != 0) {
                var intermediatePos = new Coordinates(nextPos.x(), currentPos.y());
                int steps = Util.calculateSteps(intermediatePos, nextPos);
                var direction = Util.getDirection(intermediatePos, nextPos);
                robot.move(direction, steps);
                moves.add(new Movement(direction.name(), steps));
            }
        }

        String result;
        try {
            result = new ObjectMapper().writeValueAsString(moves);
        } catch (JsonProcessingException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
            result = "Serialization failed!";
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
