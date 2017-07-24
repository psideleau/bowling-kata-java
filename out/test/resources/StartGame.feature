Feature: Start a ten-pin bowling game
  As an owner of a bowling alley, we want to have an automated system score games

  Scenario: T559 playing on one lane
    Given <players> want to bowl
    When the front-desk opens a <lane> to the bowler(s)
    Then <players> are displayed on the lane's display system

    Examples:
    |players   | lane
    |1         |1
    |2         |3