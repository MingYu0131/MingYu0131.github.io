package com.HspProject.TankWar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author mingyu
 * @version 1.0
 * 借用一个小球移动的案例，讲解Java的事件机制和监听器
 */
public class BallMove extends JFrame{
    public static void main(String[] args) {
        new BallMove();
    }

    private BallPanel bp = null;

    public BallMove(){
        bp = new BallPanel();
        this.add(bp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //在画板中监听键盘事件
        //bp实现了KeyListener接口，这里体现了接口的多态
        this.addKeyListener(bp);
        this.setSize(400, 300);
        this.setVisible(true);
    }

}

//KeyListener 是监听器, 可以监听键盘事件，此时由BallPanel类创建的对象就称为事件监听者，可以处理对应的事件
class BallPanel extends JPanel implements KeyListener {
    //为了让小球可以移动，把他的左上角x和y设置为变量
    private int x = 10;
    private int y = 10;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillOval(x, y, 20, 20);
    }

    //监听字符输出
    @Override
    public void keyTyped(KeyEvent e) {

    }

    //监听按下键
    //并对监听到的事件进行处理
    @Override
    public void keyPressed(KeyEvent e) {
        //在KeyEvent类中给每个键都分配了一个值
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            y++;
        }else if(e.getKeyCode() == KeyEvent.VK_UP){
            y--;
        }else if(e.getKeyCode() == KeyEvent.VK_LEFT){
            x--;
        }else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            x++;
        }
        this.repaint();
    }

    //监听松开键
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
