package com.surveysampling.bowling

import com.surveysampling.bowling.spring.App
import io.restassured.path.json.JsonPath
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import spock.lang.Specification

import static io.restassured.RestAssured.given
/**
 * Created by SSI.
 */
@SpringBootTest(classes = App, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BowlingAcceptanceTest extends Specification {
    @LocalServerPort
    int port

    String url;

    def setup() {
        url = "http://localhost:$port"
    }

    def "should start a new game"() {
        when:
        JsonPath path = startGame()

        then:
        path.getString("gameId") != null
        path.getInt("totalScore") == 0

        assertFramesHaveDefaultValues(path)
    }

    private void assertFramesHaveDefaultValues(JsonPath path) {
        assert path.getList("frames").size() == Game.FRAMES_PLUS_BONUS_FRAMES
        List firstRolls = path.get("frames.roll1")
        List secondRolls = path.get("frames.roll2")
        List scores = path.get('frames.score')
        
        assert firstRolls.every { it == -1 }
        assert secondRolls.every { it == -1 }
        assert scores.every { it == -1 }
    }

    def "should update game"() {
        given:
        JsonPath startResponse = startGame()
        String gameId = startResponse.getString("gameId");
        def pins = 5
        when:
        JsonPath updateResponse = given()
                .header("Accept", "application/json")
                .queryParam("pins", pins)
                .when()
                .post("$url/games/$gameId")
                .then()
                .contentType('application/json')
                .statusCode(200)
                .extract()
                .jsonPath()

        then:
        updateResponse.getString("gameId") != null
        updateResponse.getInt("totalScore") == pins
        updateResponse.getList("frames").size() == Game.FRAMES_PLUS_BONUS_FRAMES
        updateResponse.getInt("frames[0].roll1") == pins
    }

    private JsonPath startGame() {
        return given()
                .header("Accept", "application/json")
                .when()
                .put("$url/games")
                .then()
                .contentType('application/json')
                .statusCode(200)
                .extract()
                .jsonPath()
    }
}
