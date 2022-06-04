package com.HspProject.TankWar;

/**
 * @author mingyu
 * @version 1.0
 * 钢板障碍物，子弹不能打穿，坦克不能经过
 */
public class SteelPlateObstacles extends Obstacle{
    //钢板也由小方块构成，长宽固定
    private static int SteelWidth = 20;
    private static int SteelLength = 20;
    public SteelPlateObstacles(int x, int y) {
        super(x, y, SteelWidth, SteelLength);
    }

    public static int getSteelWidth() {
        return SteelWidth;
    }

    public static int getSteelLength() {
        return SteelLength;
    }
}
