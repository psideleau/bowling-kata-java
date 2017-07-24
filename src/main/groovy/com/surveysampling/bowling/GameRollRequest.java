package com.surveysampling.bowling;

import lombok.*;

/**
 * Created by SSI.
 */
@Builder
@Value
public class GameRollRequest {
    private String gameID;
    private int pins;
}
