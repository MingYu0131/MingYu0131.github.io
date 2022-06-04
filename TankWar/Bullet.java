package com.HspProject.TankWar;

/**
 * @author mingyu
 * @version 1.0
 * 专门的子弹类，每启动一个子弹就是启动一个线程
 */
public class Bullet extends Thread{
    //子弹的属性
    private int x;
    private int y;
    private int speed = 6;//子弹的前进速率
    private boolean flag = true;//子弹是否还存在
    private int dir;//子弹发射的方向（和坦克的方向一致）规定0表示上，1表示右，2表示下，3表示左

    public Bullet(int x, int y,int dir){
        this.x = x;
        this.y = y;
        this.dir = dir;
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        //启动线程就意味着子弹向前前进
        //子弹还存在的话就前进
        //根据方向来决定子弹的运行方式，规定0表示上，1表示右，2表示下，3表示左
        //注意应该有一个线程休眠的动作来让子弹走的慢一点，也不能停顿太久不然看起来卡卡的
        while (true){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(dir == 0){
                y -= speed;
            }else if(dir == 1){
                x += speed;
            }else if(dir == 2){
                y += speed;
            }else if(dir == 3){
                x -= speed;
            }
//            System.out.println("x="+x+"y="+y);
            //每次运动完后都检测子弹是否运动出边界了
            //当子弹击中敌方坦克也消除坦克，子弹击中坦克时会设置flag为false，故检测isFlag()即可
            if(!(x>=0 && x<=1000 && y>=0 && y<=750 && flag)){
//                System.out.println("子弹越界消失");
                flag = false;
                break;
            }
        }
    }
}
