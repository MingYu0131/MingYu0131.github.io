package com.HspProject.TankWar;

import javax.swing.*;
import java.awt.*;

/**
 * @author mingyu
 * @version 1.0
 * 使用一个简单的案例来讲解java基本的绘图原理
 * 1、理解像素其实不是长度单位而是密度单位
 * 2、JFrame类，用于控制GUI对象，实现最大化最小化关闭等功能
 * 3、JPanel，用于绘图的类，提供了各种方法用于绘图
 */
public class DrawCircle extends JFrame {

    public static void main(String[] args) {
        new DrawCircle();
        System.out.println("程序退出");
    }

    private TankWarPanel mp = null;//定义一个面板

    public DrawCircle(){
//        mp = new TankWarPanel();
        //把画板放入窗口中
        this.add(mp);
        this.setSize(800,600);
        //设置点击窗口关闭时，程序完全退出
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置可以显示
        this.setVisible(true);


    }
}

class MyPanel extends JPanel{
    @Override
    public void paint(Graphics g) {
        super.paint(g);//调用父类的方法完成初始化
        System.out.println("paint被调用了");
//        g.drawOval(10,10,50,50);

        //实现放置图片
        //1、获取图片资源
//        Image image = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bg.png"));
//        g.drawImage(image,10, 10, 175, 221, this);

        //实现绘制坦克
        g.setColor(Color.CYAN);
        g.fillRect(100, 100, 50, 250);
        g.fillRect(250, 100, 50, 250);
        g.fillRect(150, 150, 150, 150);
        g.setColor(Color.BLUE);
        g.fillOval(160, 200, 75, 75);
        g.drawLine(200,200,200,80);
    }
}
