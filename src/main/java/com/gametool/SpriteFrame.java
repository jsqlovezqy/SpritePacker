package com.gametool;

public class SpriteFrame {
    private String name;
    private int x;
    private int y;
    private int w;
    private int h;

    public SpriteFrame(){}

    public SpriteFrame(String name, int x, int y, int w, int h) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    // getter setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getW() { return w; }
    public void setW(int w) { this.w = w; }
    public int getH() { return h; }
    public void setH(int h) { this.h = h; }
}
