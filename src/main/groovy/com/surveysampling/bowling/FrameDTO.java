package com.surveysampling.bowling;

import lombok.*;

/**
 * Created by SSI.
 */
@Value
@Builder
public class FrameDTO {
    private final int roll1;
    private final int roll2;
    private final int score;
}
