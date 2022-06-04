package com.HspProject.TankWar;

/**
 * @author mingyu
 * @version 1.0
 * 所有障碍物的基类
 */
public class Obstacle {
    private int x;
    private int y;
    private boolean live = true;
    private int width;
    private int length;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Obstacle(int x, int y, int width, int length) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.length = length;
    }

    public Obstacle(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
