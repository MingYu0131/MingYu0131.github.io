package com.HspProject.TankWar;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Scanner;

/**
 * @author mingyu
 * @version 1.0
 * 坦克大战的启动程序
 */
public class TankWar01 extends JFrame {
    private TankWarPanel  mp = null;
    public static void main(String[] args) {
        new TankWar01();
    }
    public TankWar01(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入选项:\n1、新游戏 2、继续游戏");
        int key = scanner.nextInt();
        File file = new File("src\\GameData.txt");
        if(!file.exists()){
            System.out.println("文件不存在！开启新游戏");
            Recorder.enemyTankDeadNum = 0;
            key = 1;
        }
        if(key==2 && TankWarPanel.enemyTankNum <= Recorder.getEnemyTankDeadNum()){
            System.out.println("你已经消灭了所有坦克！现在开启新游戏");
            Recorder.enemyTankDeadNum = 0;
            key = 1;
        }
        mp = new TankWarPanel(key);
        new Thread(mp).start();
        this.add(mp);
        this.setSize(1500, 750);
        this.addKeyListener(mp);
        this.setTitle("坦克大战mybb版");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //在JFrame 中增加相应关闭窗口的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //当消灭了所有坦克时提示游戏结束，不要保存了
                if(TankWarPanel.enemyTankNum <= Recorder.getEnemyTankDeadNum()){
                    System.out.println("恭喜你消灭了所有坦克！游戏胜利！");
                }else {
                    Recorder.SaveData(mp.getEnemyTanks());
                }
                System.exit(0);

            }
        });
    }
}
