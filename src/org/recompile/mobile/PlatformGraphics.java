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

import com.nokia.mid.ui.DirectGraphics;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class PlatformGraphics extends Graphics implements DirectGraphics {
    protected int strokeStyle = SOLID;

    protected Font font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);

    public PlatformGraphics platformGraphics;
    public PlatformImage platformImage;

    protected long canvas;

    public PlatformGraphics(PlatformImage image) {
        platformImage = image;
        canvas = platformImage.getCanvas();

        platformGraphics = this;

        clipX = 0;
        clipY = 0;
        clipWidth = image.getWidth();
        clipHeight = image.getHeight();

        setColor(0, 0, 0);
    }

    public void reset() { } //Internal use method, resets the Graphics object to its inital values

    public void clearRect(int x, int y, int width, int height) { }

    public void copyArea(int subx, int suby, int subw, int subh, int x, int y, int anchor) { }

    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) { }

    public void drawChar(char character, int x, int y, int anchor) {
        drawString(Character.toString(character), x, y, anchor);
    }

    public void drawChars(char[] data, int offset, int length, int x, int y, int anchor) {
        char[] str = new char[length];
        for (int i = offset; i < offset + length; i++) {
            if (i >= 0 && i < data.length) {
                str[i - offset] = data[i];
            }
        }
        drawString(new String(str), x, y, anchor);
    }

    void drawImage2(long canvas, int x, int y) { }

    public void drawImage(Image image, int x, int y, int anchor) {
        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();

        x = AnchorX(x, imgWidth, anchor);
        y = AnchorY(y, imgHeight, anchor);

        drawImage(image, x, y);
    }

    public void drawImage(Image image, int x, int y) { }

    public void flushGraphics(Image image, int x, int y, int width, int height) { }

    public void drawRegion(Image image, int subx, int suby, int subw, int subh, int transform, int x, int y, int anchor) { }

    public void drawRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height, boolean processAlpha) { }


    public void drawLine(int x1, int y1, int x2, int y2) { }

	public void drawRect(int x, int y, int width, int height) { }

	public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) { }

    public void drawString(String str, int x, int y, int anchor) { }

    public void drawSubstring(String str, int offset, int len, int x, int y, int anchor) {
        if (str.length() >= offset + len) {
            drawString(str.substring(offset, offset + len), x, y, anchor);
        }
    }

	public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) { }

    public void fillRect(int x, int y, int width, int height) { }

	public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) { }

    public void setColor(int rgb) {
        color = rgb;
    }

    public void setColor(int r, int g, int b) {
        color = (r << 16) + (g << 8) + b;
    }

    public void setClipNative(int x, int y, int width, int height) { }

    public void clipRectNative(int x, int y, int width, int height) { }

    public float getClipXNative() { return 0; }

    public float getClipYNative() { return 0; }

    public float getClipWidthNative() { return 0; }

    public float getClipHeightNative() { return 0; }

    public void setClip(int x, int y, int width, int height) {
        setClipNative(x, y, width, height);
        clipX = (int) getClipXNative();
        clipY = (int) getClipYNative();
        clipWidth = (int) getClipWidthNative();
        clipHeight = (int) getClipHeightNative();
    }

    public void clipRect(int x, int y, int width, int height) {
        clipRectNative(x, y, width, height);
        clipX = (int) getClipXNative();
        clipY = (int) getClipYNative();
        clipWidth = (int) getClipWidthNative();
        clipHeight = (int) getClipHeightNative();
    }

	public void translate(int x, int y) { }

    private int AnchorX(int x, int width, int anchor) {
        int xout = x;
        if ((anchor & HCENTER) > 0) {
            xout = x - (width / 2);
        }
        if ((anchor & RIGHT) > 0) {
            xout = x - width;
        }
        if ((anchor & LEFT) > 0) {
            xout = x;
        }
        return xout;
    }

    private int AnchorY(int y, int height, int anchor) {
        int yout = y;
        if ((anchor & VCENTER) > 0) {
            yout = y - (height / 2);
        }
        if ((anchor & TOP) > 0) {
            yout = y;
        }
        if ((anchor & BOTTOM) > 0) {
            yout = y - height;
        }
        if ((anchor & BASELINE) > 0) {
            yout = y + height;
        }
        return yout;
    }

    public void setAlphaRGB(int ARGB) { }

	/*
		****************************
			Nokia Direct Graphics
		****************************
	*/
    // http://www.j2megame.org/j2meapi/Nokia_UI_API_1_1/com/nokia/mid/ui/DirectGraphics.html

    private int colorAlpha;

    public int getNativePixelFormat() {
        return DirectGraphics.TYPE_INT_8888_ARGB;
    }

    public int getAlphaComponent() {
        return colorAlpha;
    }

    public void setARGBColor(int argbColor) {
        colorAlpha = (argbColor >>> 24) & 0xFF;
        setColor(argbColor);
    }

    public void drawImage(Image img, int x, int y, int anchor, int manipulation) { }

    public void drawPixels(byte[] pixels, byte[] transparencyMask, int offset, int scanlength, int x, int y, int width, int height, int manipulation, int format) { }

    public void drawPixels(int[] pixels, boolean transparency, int offset, int scanlength, int x, int y, int width, int height, int manipulation, int format) { }

    public void drawPixels(short[] pixels, boolean transparency, int offset, int scanlength, int x, int y, int width, int height, int manipulation, int format) { }

    public void drawPolygon(int[] xPoints, int xOffset, int[] yPoints, int yOffset, int nPoints, int argbColor) { }

    public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int argbColor) { }

    public void fillPolygon(int[] xPoints, int xOffset, int[] yPoints, int yOffset, int nPoints, int argbColor) { }

    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) { }

    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int argbColor) { }

    public void getPixels(byte[] pixels, byte[] transparencyMask, int offset, int scanlength, int x, int y, int width, int height, int format) {
        System.out.println("getPixels A");
    }

    public void getPixels(int[] pixels, int offset, int scanlength, int x, int y, int width, int height, int format) { }

    public void getPixels(short[] pixels, int offset, int scanlength, int x, int y, int width, int height, int format) { }

    private int pixelToColor(short c, int format) {
        int a = 0xFF;
        int r = 0;
        int g = 0;
        int b = 0;
        switch (format) {
            case DirectGraphics.TYPE_USHORT_1555_ARGB:
                a = ((c >> 15) & 0x01) * 0xFF;
                r = (c >> 10) & 0x1F;
                g = (c >> 5) & 0x1F;
                b = c & 0x1F;
                r = (r << 3) | (r >> 2);
                g = (g << 3) | (g >> 2);
                b = (b << 3) | (b >> 2);
                break;
            case DirectGraphics.TYPE_USHORT_444_RGB:
                r = (c >> 8) & 0xF;
                g = (c >> 4) & 0xF;
                b = c & 0xF;
                r = (r << 4) | r;
                g = (g << 4) | g;
                b = (b << 4) | b;
                break;
            case DirectGraphics.TYPE_USHORT_4444_ARGB:
                a = (c >> 12) & 0xF;
                r = (c >> 8) & 0xF;
                g = (c >> 4) & 0xF;
                b = c & 0xF;
                a = (a << 4) | a;
                r = (r << 4) | r;
                g = (g << 4) | g;
                b = (b << 4) | b;
                break;
            case DirectGraphics.TYPE_USHORT_555_RGB:
                r = (c >> 10) & 0x1F;
                g = (c >> 5) & 0x1F;
                b = c & 0x1F;
                r = (r << 3) | (r >> 2);
                g = (g << 3) | (g >> 2);
                b = (b << 3) | (b >> 2);
                break;
            case DirectGraphics.TYPE_USHORT_565_RGB:
                r = (c >> 11) & 0x1F;
                g = (c >> 5) & 0x3F;
                b = c & 0x1F;
                r = (r << 3) | (r >> 2);
                g = (g << 2) | (g >> 4);
                b = (b << 3) | (b >> 2);
                break;
        }
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    private short colorToShortPixel(int c, int format) {
        int a = 0;
        int r = 0;
        int g = 0;
        int b = 0;
        int out = 0;
        switch (format) {
            case DirectGraphics.TYPE_USHORT_1555_ARGB:
                a = c >>> 31;
                r = ((c >> 19) & 0x1F);
                g = ((c >> 11) & 0x1F);
                b = ((c >> 3) & 0x1F);
                out = (a << 15) | (r << 10) | (g << 5) | b;
                break;
            case DirectGraphics.TYPE_USHORT_444_RGB:
                r = ((c >> 20) & 0xF);
                g = ((c >> 12) & 0xF);
                b = ((c >> 4) & 0xF);
                out = (r << 8) | (g << 4) | b;
                break;
            case DirectGraphics.TYPE_USHORT_4444_ARGB:
                a = ((c >>> 28) & 0xF);
                r = ((c >> 20) & 0xF);
                g = ((c >> 12) & 0xF);
                b = ((c >> 4) & 0xF);
                out = (a << 12) | (r << 8) | (g << 4) | b;
                break;
            case DirectGraphics.TYPE_USHORT_555_RGB:
                r = ((c >> 19) & 0x1F);
                g = ((c >> 11) & 0x1F);
                b = ((c >> 3) & 0x1F);
                out = (r << 10) | (g << 5) | b;
                break;
            case DirectGraphics.TYPE_USHORT_565_RGB:
                r = ((c >> 19) & 0x1F);
                g = ((c >> 10) & 0x3F);
                b = ((c >> 3) & 0x1F);
                out = (r << 11) | (g << 5) | b;
                break;
        }
        return (short) out;
    }

}
