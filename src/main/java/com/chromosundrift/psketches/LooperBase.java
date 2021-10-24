package com.chromosundrift.psketches;

import processing.core.PApplet;

import java.io.IOException;

abstract public class LooperBase extends PApplet {

    public static final int MARGIN = 20;
    public static int DEFAULT_FPS = 60;
    public static int DEFAULT_DURATION_S = 120;
    public static int DEFAULT_REVOLUTIONS = 26;
    public static int TIKTOK_WIDTH = 720;
    public static int TIKTOK_HEIGHT = 1280;

    private float delta;

    private int frameNum = 0;
    private float elapsedSeconds = 0;
    private float theta = 0;
    private int durationSeconds;
    private int frameRate;
    private boolean drawDebug = false;
    private VideoExporter videoExporter;
    private int revolutions;

    @Override
    public void settings() {
        size(TIKTOK_WIDTH, TIKTOK_HEIGHT);
        smooth();
    }

    public void init(int durationSeconds, int frameRate, int revolutions) {
        this.durationSeconds = durationSeconds;
        this.frameRate = frameRate;
        this.revolutions = revolutions;
        this.delta = (TWO_PI * revolutions) / (durationSeconds * frameRate);
        try {
            this.videoExporter = new VideoExporter(width, height, this.frameRate);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void init() {
        init(DEFAULT_DURATION_S, DEFAULT_FPS, DEFAULT_REVOLUTIONS);
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
            drawTheta();
        }
        try {
            videoExporter.writeFrame(this.get());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        theta += delta;
        elapsedSeconds = (float)frameNum / this.frameRate;
        if (elapsedSeconds >= this.durationSeconds) {
            try {
                videoExporter.end();
            } catch (IOException e) {
                System.out.println("problem ending video, exiting anyway");
                e.printStackTrace();
                System.exit(2);
            }
            System.exit(0);
        }
        frameNum++;
    }

    private void drawTheta() {
        stroke(255);
        strokeWeight(2);
        pushMatrix();
        translate(width/2, height/2);
        rotate(theta);
        line(0, 0, max(width, height) * 2, 0);
        popMatrix();
    }

    public final float getTheta() {
        return theta;
    }

    public void dispose() {
        try {
            videoExporter.end();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
