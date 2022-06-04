package com.HspProject.TankWar;

import java.util.Vector;

/**
 * @author mingyu
 * @version 1.0
 * 表示自己的坦克
 */
public class Hero extends Tank{
    //定义多颗子弹
    Vector<Bullet> bullets = new Vector<>();
    // 最大拥有子弹数
    static int bulletNum = 3;

    public static int getBulletNum() {
        return bulletNum;
    }

    public static void setBulletNum(int bulletNum) {
        Hero.bulletNum = bulletNum;
    }

    private boolean isLive = true;

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public Hero(int x, int y) {
        super(x, y);
    }


    @Override
    public void TankShoot() {
        //场上存在的子弹数不能大于5
        if(bulletNum > 0){
            bulletNum--;
            Recorder.setBulletNum(bulletNum);
            Bullet bullet = new Bullet(getShootX(), getShootY(), getDir());
            bullets.add(bullet);
            bullet.start();
        }
    }
}
