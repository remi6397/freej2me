/*
	This file is part of FreeJ2ME.

	FreeJ2ME is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	FreeJ2ME is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with FreeJ2ME.  If not, see http://www.gnu.org/licenses/
*/
package org.recompile.mobile;

import javax.microedition.lcdui.Image;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class PlatformImage extends Image {
    private long canvas;
    protected PlatformGraphics gc;

    public boolean isNull = false;

    public PlatformGraphics getGraphics() {
        return gc;
    }

    public long getCanvas() {
        return canvas;
    }

    protected void createGraphics() {
        gc = new PlatformGraphics(this);
        gc.setColor(0);
    }

    protected static long createCanvas(int width, int height) { return 0; }

    protected static long createCanvasFromStream(InputStream stream) { return 0; }

    protected static long getSubimage(long image, int x, int y, int width, int height) { return 0; }

    protected static float getCanvasWidth(long canvas) { return 0; }

    protected static float getCanvasHeight(long canvas) { return 0; }

    public PlatformImage(int Width, int Height) {
        // Create blank Image
        width = Width;
        height = Height;

        canvas = createCanvas(Width, Height);
        createGraphics();

        gc.setColor(0xFFFFFF);
        gc.fillRect(0, 0, width, height);
        gc.setColor(0x000000);

        platformImage = this;
    }

    public PlatformImage(String name) {
        InputStream stream = Mobile.getPlatform().loader.getMIDletResourceAsStream(name);
        long temp;

        if (stream == null) {
            System.out.println("Couldn't Load Image Stream (can't find " + name + ")");
            isNull = true;
        } else {
            try {
                temp = createCanvasFromStream(stream);
                width = (int) getCanvasWidth(temp);
                height = (int) getCanvasHeight(temp);

                canvas = createCanvas(width, height);
                createGraphics();

                gc.drawImage2(temp, 0, 0);
            } catch (Exception e) {
                System.out.println("Couldn't Load Image Stream " + name);
                e.printStackTrace();
                isNull = true;
            }
        }
        platformImage = this;
    }

    public PlatformImage(InputStream stream) {
        // Create Image from InputStream
        // System.out.println("Image From Stream");
        long temp;
        try {
            temp = createCanvasFromStream(stream);
            width = (int) getCanvasWidth(temp);
            height = (int) getCanvasHeight(temp);

            canvas = createCanvas(width, height);
            createGraphics();

            gc.drawImage2(temp, 0, 0);
        } catch (Exception e) {
            System.out.println("Couldn't Load Image Stream");
            isNull = true;
        }

        platformImage = this;
    }

    public PlatformImage(Image source) {
        // Create Image from Image
        width = source.platformImage.width;
        height = source.platformImage.height;

        canvas = createCanvas(width, height);
        createGraphics();

        gc.drawImage2(source.platformImage.getCanvas(), 0, 0);

        platformImage = this;
    }

    public PlatformImage(byte[] imageData, int imageOffset, int imageLength) {
        // Create Image from Byte Array Range (Data is PNG, JPG, etc.)
        try {
            InputStream stream = new ByteArrayInputStream(imageData, imageOffset, imageLength);

            long temp;

            temp = createCanvasFromStream(stream);
            width = (int) getCanvasWidth(temp);
            height = (int) getCanvasHeight(temp);

            canvas = createCanvas(width, height);
            createGraphics();

            gc.drawImage2(temp, 0, 0);
        } catch (Exception e) {
            System.out.println("Couldn't Load Image Data From Byte Array");
            canvas = createCanvas(Mobile.getPlatform().lcdWidth, Mobile.getPlatform().lcdHeight);
            createGraphics();
            //System.out.println(e.getMessage());
            //e.printStackTrace();
            isNull = true;
        }

        platformImage = this;
    }

    public PlatformImage(int[] rgb, int Width, int Height, boolean processAlpha) {
        // createRGBImage (Data is ARGB pixel data)
        width = Width;
        height = Height;

        if (width < 1) {
            width = 1;
        }
        if (height < 1) {
            height = 1;
        }

        canvas = createCanvas(width, height);
        createGraphics();

        gc.drawRGB(rgb, 0, width, 0, 0, width, height, true);

        platformImage = this;
    }

    public PlatformImage(Image image, int x, int y, int Width, int Height, int transform) {
        // Create Image From Sub-Image, Transformed //
        long sub = getSubimage(image.platformImage.canvas, x, y, Width, Height);

        canvas = transformImage(sub, transform);
        createGraphics();

        width = (int) getCanvasWidth(canvas);
        height = (int) getCanvasHeight(canvas);

        platformImage = this;
    }

    public void getRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height) { }

    public int getARGB(int x, int y) { return 0; }

    public int getPixel(int x, int y) { return 0; }

    public void setPixel(int x, int y, int color) { }

    public static long transformImage(long image, int transform) { return 0; }
}
