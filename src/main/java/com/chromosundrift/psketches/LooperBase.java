package com.chromosundrift.psketches;

import processing.core.PApplet;

abstract public class LooperBase extends PApplet {

    public static final int MARGIN = 20;
    public static int DEFAULT_FPS = 60;
    public static int DEFAULT_DURATION_S = 14;
    public static int TIKTOK_WIDTH = 720;
    public static int TIKTOK_HEIGHT = 1280;

    private float delta;

    private int frameNum = 0;
    private float elapsedSeconds = 0;
    private float theta = 0;
    private int durationSeconds;
    private int frameRate;
    private boolean drawDebug = false;

    @Override
    public void settings() {
        size(TIKTOK_WIDTH, TIKTOK_HEIGHT);
        smooth();
        init();
    }

    public void init(int durationSeconds, int frameRate) {
        this.durationSeconds = durationSeconds;
        this.frameRate = frameRate;
        this.delta = TWO_PI / (durationSeconds * DEFAULT_FPS);
    }

    public void init() {
        init(DEFAULT_DURATION_S, DEFAULT_FPS);
    }

    private void debugTextStyle() {
        noStroke();
        fill(255); // TODO light/dark colour scheme
        textSize(20);
    }

    protected void drawFrameCounter() {
        final String mesg = "frame " + frameNum;
        float textWidth = textWidth(mesg);
        noStroke();
        fill(0);
        rect(0, 0, MARGIN * 2 + textWidth, MARGIN + 25);
        debugTextStyle();
        textAlign(LEFT);
        text(mesg, MARGIN, MARGIN);
    }

    protected void drawSeconds() {
        textAlign(RIGHT);
        final String mesg = elapsedSeconds + "s";
        float textWidth = textWidth(mesg);
        fill(0);
        noStroke();
        rect(width - MARGIN * 2 - textWidth, 0, width, MARGIN + 25);
        debugTextStyle();
        text(mesg, width - MARGIN, MARGIN);
    }

    public void keyPressed() {
        if (key == 'd') {
            drawDebug = !drawDebug;
        }
    }


    /**
     * After all drawing work has been done, subclasses must call this.
     */
    protected void endFrame() {
        if (drawDebug) {
            drawFrameCounter();
            drawSeconds();
        }
        theta += delta;
        elapsedSeconds = frameNum / DEFAULT_FPS;
        frameNum++;
    }

    public float getTheta() {
        return theta;
    }
}
