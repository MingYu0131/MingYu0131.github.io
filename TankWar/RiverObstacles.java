package com.HspProject.TankWar;

/**
 * @author mingyu
 * @version 1.0
 * 河流子弹可以穿过，坦克不能开过
 */
public class RiverObstacles extends Obstacle{
    public RiverObstacles(int x, int y, int width, int length) {
        super(x, y, width, length);
    }
}
