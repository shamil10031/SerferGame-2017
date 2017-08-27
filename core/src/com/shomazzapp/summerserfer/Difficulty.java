package com.shomazzapp.summerserfer;

import com.badlogic.gdx.graphics.Color;

import static com.shomazzapp.summerserfer.Constants.SPRUT_VELOCITY_EASY;
import static com.shomazzapp.summerserfer.Constants.SPRUT_VELOCITY_HARD;
import static com.shomazzapp.summerserfer.Constants.SPRUT_VELOCITY_NORMAL;

public enum Difficulty {
    EASY(SPRUT_VELOCITY_EASY, 40, new Color(0x00ff00ff)),
    NORMAL(SPRUT_VELOCITY_NORMAL, 70, new Color(0xffff22ff)),
    HARD(SPRUT_VELOCITY_HARD, 100,new Color(0xff0000ff));

    Color color;
    float velocity;
    int scoreValue;

    Difficulty(float velocity, int scoreValue,Color color) {
        this.color = color;
        this.velocity = velocity;
        this.scoreValue = scoreValue;
    }

    public Color getColor(){return color;}
    public float getVelocity(){return velocity;}
    public int getScoreValue(){return scoreValue;}
}
