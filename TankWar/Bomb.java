package com.HspProject.TankWar;

/**
 * @author mingyu
 * @version 1.0
 * 坦克被击中时爆炸的炸弹类
 */
public class Bomb {
    int x,y;    //炸弹出现的位置
    int lifeTime = 9;   //为了炸弹爆炸有持续效果，需要分阶段展示图片（爆炸的本质是三张静态图片）
    boolean live = true;    //爆炸是否还存在

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void lifeDown(){
        if(lifeTime > 0){
            lifeTime--;
        }
        if(lifeTime == 0){
            live = false;
        }
    }
}
