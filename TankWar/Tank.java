package com.HspProject.TankWar;


import java.util.Vector;

/**
 * @author mingyu
 * @version 1.0
 * 将坦克的属性抽象成一个类
 */
public class Tank {
    private int x;
    private int y;
    private int dir = 0;    //方向,0表示上，1表示右，2表示下，3表示左
    private int lastDir = 0;    //记录上一次发生阻塞时的方向
    private int speed = 1;  //表示速度，默认为每按一次按键移动一个像素
    private Bullet bullet = null;
    //思路：设置阻塞信号，如果发生了碰撞就为t,然后下一次执行时判断是否方向发生了变化，如果发生了变化就意味着没有碰撞了，将阻塞信号设置为false
    private boolean isStuck = false;
    Vector<EnemyTank> enemyTanks = new Vector<>();

    public boolean isStuck() {
        return isStuck;
    }

    public void setStuck(boolean stuck) {
        isStuck = stuck;
    }

    public int getLastDir() {
        return lastDir;
    }

    //编写方法判断坦克与障碍物（包括边界）是否发生碰撞，根据坦克方向不同分为四种情况
    public void isTouchObstacleTank() {
        //判断边界与是否和其他enemyTank碰撞，Hero才需要
        if (this instanceof Hero) {
            switch (this.getDir()) {
                case 0:
                    if (getY() <= 0) {
                        lastDir = dir;
                        setStuck(true);
                    }
                    for (int i = 0; i < enemyTanks.size(); i++) {
                        EnemyTank enemyTank = enemyTanks.get(i);
                        if (enemyTank.getDir() == 0 || enemyTank.getDir() == 2) {
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                lastDir = dir;
                                setStuck(true);
                            }
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                lastDir = dir;
                                setStuck(true);
                            }
                        }
                        if (enemyTank.getDir() == 1 || enemyTank.getDir() == 3) {
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                lastDir = dir;
                                setStuck(true);
                            }
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                lastDir = dir;
                                setStuck(true);
                            }
                        }
                    }
                    break;
                case 1:
                    if (getX() + 60 >= 1000) {
                        lastDir = dir;
                        setStuck(true);
                    }
                    for (int i = 0; i < enemyTanks.size(); i++) {
                        EnemyTank enemyTank = enemyTanks.get(i);
                        if (enemyTank.getDir() == 0 || enemyTank.getDir() == 2) {
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                lastDir = dir;
                                setStuck(true);
                            }
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60) {
                                lastDir = dir;
                                setStuck(true);
                            }
                        }
                        if (enemyTank.getDir() == 1 || enemyTank.getDir() == 3) {
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                lastDir = dir;
                                setStuck(true);
                            }
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40) {
                                lastDir = dir;
                                setStuck(true);
                            }
                        }
                    }
                    break;
                case 2:
                    if (getY() + 60 >= 710) {
                        lastDir = dir;
                        setStuck(true);
                    }
                    for (int i = 0; i < enemyTanks.size(); i++) {
                        EnemyTank enemyTank = enemyTanks.get(i);
                        if (enemyTank.getDir() == 0 || enemyTank.getDir() == 2) {
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                lastDir = dir;
                                setStuck(true);
                            }
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                lastDir = dir;
                                setStuck(true);
                            }
                        }
                        if (enemyTank.getDir() == 1 || enemyTank.getDir() == 3) {
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                lastDir = dir;
                                setStuck(true);
                            }
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                lastDir = dir;
                                setStuck(true);
                            }
                        }
                    }
                    break;
                case 3:
                    if (getX() <= 0) {
                        lastDir = dir;
                        setStuck(true);
                    }
                    for (int i = 0; i < enemyTanks.size(); i++) {
                        EnemyTank enemyTank = enemyTanks.get(i);
                        if (enemyTank.getDir() == 0 || enemyTank.getDir() == 2) {
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                lastDir = dir;
                                setStuck(true);
                            }
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60) {
                                lastDir = dir;
                                setStuck(true);
                            }
                        }
                        if (enemyTank.getDir() == 1 || enemyTank.getDir() == 3) {
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                lastDir = dir;
                                setStuck(true);
                            }
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40) {
                                lastDir = dir;
                                setStuck(true);
                            }
                        }
                    }
                    break;
            }

        }
        //判断坦克是否与障碍物碰撞，如果碰撞就设置阻塞
        for (int i = 0; i < TankWarPanel.getAllObstacles().size(); i++) {
            Obstacle o = TankWarPanel.getAllObstacles().get(i);
            switch (this.getDir()) {
                case 0: //上
                    if (this.getX() >= o.getX() && this.getX() <= o.getX() + o.getWidth() &&
                            this.getY() >= o.getY() && this.getY() <= o.getY() + o.getLength()) {
                        lastDir = dir;
                        setStuck(true);
                    } else if (this.getX() + 40 >= o.getX() && this.getX() + 40 <= o.getX() + o.getWidth() &&
                            this.getY() >= o.getY() && this.getY() <= o.getY() + o.getLength()) {
                        lastDir = dir;
                        setStuck(true);
                    }
                    break;
                case 1: //右
                    if (this.getX() + 60 >= o.getX() && this.getX() + 60 <= o.getX() + o.getWidth() &&
                            this.getY() >= o.getY() && this.getY() <= o.getY() + o.getLength()) {
                        lastDir = dir;
                        setStuck(true);
                    }
                    if (this.getX() + 60 >= o.getX() && this.getX() + 60 <= o.getX() + o.getWidth() &&
                            this.getY() + 40 >= o.getY() && this.getY() + 40 <= o.getY() + o.getLength()) {
                        lastDir = dir;
                        setStuck(true);
                    }
                    break;
                case 2: //下
                    if (this.getX() >= o.getX() && this.getX() <= o.getX() + o.getWidth() &&
                            this.getY() + 60 >= o.getY() && this.getY() + 60 <= o.getY() + o.getLength()) {
                        lastDir = dir;
                        setStuck(true);
                    }
                    if (this.getX() + 40 >= o.getX() && this.getX() + 40 <= o.getX() + o.getWidth() &&
                            this.getY() + 60 >= o.getY() && this.getY() + 60 <= o.getY() + o.getLength()) {
                        lastDir = dir;
                        setStuck(true);
                    }
                    break;
                case 3: //左
                    if (this.getX() >= o.getX() && this.getX() <= o.getX() + o.getWidth() &&
                            this.getY() >= o.getY() && this.getY() <= o.getY() + o.getLength()) {
                        lastDir = dir;
                        setStuck(true);
                    }
                    if (this.getX() >= o.getX() && this.getX() <= o.getX() + o.getWidth() &&
                            this.getY() + 40 >= o.getY() && this.getY() + 40 <= o.getY() + o.getLength()) {
                        lastDir = dir;
                        setStuck(true);
                    }
                    break;
            }
        }

    }

    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    public Bullet getBullet() {
        return bullet;
    }


    public void TankMoveRight() {
        dir = 1;
        if (!isStuck) {
            x += speed;
        }
    }

    public void TankMoveLeft() {
        dir = 3;
        if (!isStuck) {
            x -= speed;
        }
    }

    public void TankMoveUp() {
        dir = 0;
        if (!isStuck) {
            y -= speed;
        }
    }

    public void TankMoveDown() {
        dir = 2;
        if (!isStuck) {
            y += speed;
        }
    }


    //实现一个坦克发射子弹的方法，并返回发射的子弹对象
    public void TankShoot() {
        bullet = new Bullet(getShootX(), getShootY(), dir);
        bullet.start();
    }

    //实现一个获取子弹发射位置横坐标的方法
    public int getShootX() {
        if (dir == 0) {
            return x + 20;
        } else if (dir == 1) {
            return x + 60;
        } else if (dir == 2) {
            return x + 20;
        } else if (dir == 3) {
            return x;
        } else {
            return 0;
        }
    }

    //实现一个获取子弹发射位置纵坐标的方法
    public int getShootY() {
        if (dir == 0) {
            return y;
        } else if (dir == 1) {
            return y + 20;
        } else if (dir == 2) {
            return y + 60;
        } else if (dir == 3) {
            return y + 20;
        } else {
            return 0;
        }
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

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

    @Override
    public String toString() {
        return "Tank{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
