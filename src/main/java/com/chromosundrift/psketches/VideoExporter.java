package com.chromosundrift.psketches;

import processing.core.PImage;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

public class VideoExporter {

    private static final String ffmpegPath = "/usr/local/bin/ffmpeg";
    public static final int PIXEL_CHANNELS = 3; // RGB

    private int frameCount;
    private OutputStream stream;
    private int frameRate;
    private int width;
    private int height;
    private final Process process;

    public VideoExporter(int width, int height, int fps) throws IOException {
        this.frameRate = fps;
        this.width = width;
        this.height = height;
        this.frameCount = 0;
        List<String> ffmpegArgs = constructArgs(ffmpegPath, width, height, fps);

        ProcessBuilder processBuilder = new ProcessBuilder(ffmpegArgs);
        processBuilder.redirectErrorStream(true);
        File ffmpegOutputLog = new File("ffmpeg.log");
        processBuilder.redirectOutput(ffmpegOutputLog);
        processBuilder.redirectInput(ProcessBuilder.Redirect.PIPE);
        try {
            this.process = processBuilder.start();
            this.stream = process.getOutputStream();
            // we know proces and videoStream are both constructed
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Ffmpeg failed. Check " + ffmpegOutputLog.getAbsolutePath());
            throw e;
        }
    }

    private static List<String> constructArgs(String executable, int width, int height, int fps) {
        String size = width + "x" + height;
        return Arrays.asList(
                executable,                       // ffmpeg executable
                "-y",                             // overwrite old file
                "-f", "rawvideo",                 // format rgb raw
                "-vcodec", "rawvideo",            // in codec rgb raw
                "-s", size,                       // size
                "-pix_fmt", "rgb24",              // pix format rgb24
                "-r", Integer.toString(fps),      // frame rate
                "-i", "-",                        // pipe input
                "-vcodec", "h264",                // out codec h264
                "-pix_fmt", "yuv420p",            // color space yuv420p
                "-strict", "experimental",        // audio url won't work without this
                "-metadata", "comment=chromosundrift", // comment
                "video.out.m4v"                        // output file
        );
    }

    public void end() throws IOException {
        this.stream.flush();
        this.stream.close();
        this.process.destroy();
    }

    public void writeFrame(PImage img) throws IOException {

        if (img.pixelWidth != width || img.pixelHeight != height) {
            throw new IllegalArgumentException("given image dimensions don't match configured dimensions");
        }
        img.loadPixels();
        final int size = img.pixelWidth * img.pixelHeight * PIXEL_CHANNELS;
        byte[] pixelBytes = new byte[size];

        int n = 0;
        for (final int px : img.pixels) {
            pixelBytes[n++] = (byte) (px >> 16);
            pixelBytes[n++] = (byte) (px >> 8);
            pixelBytes[n++] = (byte) (px);
        }

        stream.write(pixelBytes);
        frameCount++;
    }
}
