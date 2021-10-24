package com.chromosundrift.psketches;

import processing.core.PApplet;

public class Looperoid extends LooperBase {

    public static final float FADE_ALPHA = 15f;
    public static final int REVOLUTIONS = 26;
    public static final int DURATION = 120;
    public static final int FPS = 60;

    public static void main(String[] args) {
        PApplet.main(Looperoid.class, args);
    }


    @Override
    public void settings() {
        super.settings();
        init(DURATION, FPS, REVOLUTIONS);
    }

    @Override
    public void setup() {
        background(53, 20, 16, 255);
    }

    @Override
    public void draw() {
        noStroke();
        fill(53, 20, 16, FADE_ALPHA);
        rect(0, 0, width, height);
        stroke(255);
        strokeWeight(8);

        int branches = 17;
        for (int i = 0; i < branches; i++) {
            final float ang = getTheta() + (TWO_PI * i / branches);
            circ(ang);
        }
        endFrame();
    }

    private void circ(float ang) {
        float a = abs(sin(ang / 1.8f)) * min(width, height) / 3;
        final int ma = (int) (ang * 8f);
        int stripe = 64 * (ma % 2);
        stroke(255, 192 - stripe, 255);
        noFill();
        a = a + sin(getTheta() * 25) * 2.7f;
        int x = (int) (width / 2 + (sin(ang / 1.3f) * a));
        int y = (int) (height / 2 + (cos(ang / 1.3f) * a));

        final float radius = a * a / 198 + 8;
        circle(x, y, radius);

    }
}
