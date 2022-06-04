package com.HspProject.TankWar;

/**
 * @author mingyu
 * @version 1.0
 * 绘制墙体，墙体是由小矩形组成，被子弹击中,会粉碎
 */
public class WallRect extends Obstacle{

    //固定砖块的长宽
    private static final int RectWidth = 10;
    private static final int RectLength = 20;


    public static int getRectWidth() {
        return RectWidth;
    }

    public static int getRectLength() {
        return RectLength;
    }

    public WallRect(int x, int y) {
        super(x, y);
        setLength(RectLength);
        setWidth(RectWidth);
    }
}
