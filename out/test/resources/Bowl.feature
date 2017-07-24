Feature: The system should keep track of a game.

  Scenario: A bowler gets a perfect score
    Given A bowler is on frame "10"
    And "rolls a "strike"
    When the bowler gets a strike on their first bonus
    And the bowler gets a strike on their last bonus 
    Then the total score is "300"



  Scenario: A bowler gets a spare
    Given A bowler is on frame "1"
    And  and rolls a "9"
    When the bowler rolls a "1"
    Then  "9" and "\" is shown
    And   the total score is "_".

  Scenario:  a bowler rolls in next frame after getting a spare
    Given A bowler has rolled a "spare" in frame "1"
    When  the bowler rolls a "9"
    Then  "19" is the total score in frame "1"
    And   "9" is displayed in frame "2"

  Scenario: A bowler gets a strike


  Scenario: a bowler rolls the next frame after getting a strike
    Given A bowler has rolled a "strike" in frame "1"
    When  the bowler rolls a "7"
    And   then the bowler rolls a "1"
    Then  frame "1" displays "19" as the total score


  Scenario:  a bowler rolls a gutter ball
    Given  A bowler is starting frame "1"
    When   the bowler rolls a "gutter ball"
    Then   a "_" is displayed



  Scenario: A bowler rolls a gutter bowl

    Examples:
    |bowlers   |
    |1         |
    |2         |3