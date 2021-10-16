package com.chromosundrift.psketches;

import processing.core.PApplet;

public class Looperoid extends MainBase {

    public static final float FADE_ALPHA = 15f;
    public static final boolean EASY_FIT = false;
    private float theta = 0;
    private final float delta = TWO_PI / (14 * DEFAULT_FPS);
    private int w, h;

    @Override
    public void settings() {
        size(TIKTOK_WIDTH, TIKTOK_HEIGHT);
        smooth();
    }

    @Override
    public void setup() {

        background(53, 20, 16, 255);

        if (EASY_FIT) {
            w = (int) (width * 0.8);
            h = (int) (height * 0.8);
        } else {
            w = width;
            h = height;
        }
    }

    @Override
    public void draw() {
        noStroke();
        fill(53, 20, 16, FADE_ALPHA);
        rect(0, 0, width, height);
        stroke(255);
        strokeWeight(7);

        int branches = 17;
        for (int i = 0; i < branches; i++) {
            final float ang = theta + (TWO_PI * i / branches);
            circ(ang);
        }
        theta += delta;
    }

    private void circ(float ang) {
        float a = abs(sin(ang/1.8f)) * min(w,h)/3;
        final int ma = (int) (ang * 7.9f);
        int stripe = 64 * (ma % 2);
        stroke(255, 192 - stripe, 255);
        noFill();
        a = a + sin(theta*25)*2.7f;
        int x = (int) (width/2 + (sin(ang /1.3f) * a));
        int y = (int) (height/2 + (cos(ang /1.3f) * a));

        final float radius = a * a / 198 + 8;
        circle(x, y, radius);

    }

    public static void main(String[] args) {
        PApplet.main(Looperoid.class, args);
    }
}
