package com.surveysampling.bowling;

import lombok.*;

import java.util.*;

/**
 * Created by SSI.
 */
@Value
@Builder
public class GameResponse {
    private final String gameId;
    private final List<FrameDTO> frames;
    private final int totalScore;
    private final boolean finished;
}
