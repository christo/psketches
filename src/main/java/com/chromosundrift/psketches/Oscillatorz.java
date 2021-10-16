package com.chromosundrift.psketches;

import java.util.List;
import java.util.ArrayList;
//import com.hamoid.VideoExport;

import processing.core.PApplet;
import processing.core.PImage;

public class Oscillatorz extends MainBase {


    int w, h;
    float t;

    final boolean DO_VIDEO_EXPORT = false;
    final float DELTA_TIME = 0.0002f;
    final boolean SHOW_IMAGE = false;
    final boolean FIXED_ALPHA = true;
    final float FADE_ALPHA = 255;
    final int NUM_PARTICLES = 50;
    final boolean EASY_FIT = false;
    final int DOT_SIZE = 2;
    final boolean VAR_DOTS = true;
    final String BG_IMAGE = "transparentOrchidFunstorm.png";

    final double PI2 = Math.PI * 2;
    PImage img;
    //VideoExport videoExport;


    @Override
    public void settings() {
        size(720, 1280);
        //  size(1920, 1005, P3D);
        //  fullScreen(P3D);
//        pixelDensity(2);
    }

    public void setup() {



        smooth();
        if (SHOW_IMAGE) {
            img = loadImage(BG_IMAGE);
            image(img, 0, 0);
        }
        if (EASY_FIT) {
            w = (int) (width * 0.5);
            h = (int) (height * 0.5);
        } else {
            w = width;
            h = height;
        }

        // start with fully opaque background colour
        background(16, 20, 53, 255);

        t = 0;

//        if (DO_VIDEO_EXPORT) {
//            videoExport = new VideoExport(this, "oscillatorz.mp4");
//            videoExport.setFrameRate(30);
//            videoExport.startMovie();
//        }
    }


    public void draw() {

        // first clear
        noStroke();
        fill(16, 20, 53, FADE_ALPHA);
        rect(0, 0, w, h);

        float sWeight;
        if (VAR_DOTS) {
            sWeight= DOT_SIZE * (sin(t*37) * 2) + DOT_SIZE*2 + 4;
        } else {
            sWeight = DOT_SIZE;
        }
        float alpha;
        if (FIXED_ALPHA) {
            alpha = 255;
        } else {
            alpha = 190 - sWeight*7;
        }


        //text("FPS " + frameRate, 10, 20);
        translate(width/2, height/2);

        strokeWeight(sWeight);

        // s is the toroidal profile angle
        for (float s = 0.1f; s< Math.PI; s+=0.2) {
            // a is amplitude of the toroid segment
            float a = Math.min(w,h) / ((cos(t*40+s)-2.5f));
            stroke(255 - s*20, 240, 249, alpha);
            for (float p = 0; p < NUM_PARTICLES; p++) {
                // dp is the particle delta
                float dp = (p+s)/NUM_PARTICLES;

                float theta = (float)(PI2 * (t+dp));
                int x = (int)(sin(theta) * a);
                int y = (int)(cos(theta) * a);

                point(x,y);
            }
        }
        translate(-width/2, -height/2);

        if (SHOW_IMAGE) {
            image(img, 0, 0);
        }

        t += DELTA_TIME;

//        if (DO_VIDEO_EXPORT) {
//            videoExport.saveFrame();
//        }
    }

    public void keyPressed() {
        if (key == 'q') {
//            if (DO_VIDEO_EXPORT) {
//                videoExport.endMovie();
//            }
            exit();
        }
    }

    public static void main(String[] args) {
        PApplet.main(Oscillatorz.class, args);
    }
}
