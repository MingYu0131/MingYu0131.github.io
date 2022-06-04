package com.HspProject.TankWar;

import java.io.*;
import java.util.Vector;

/**
 * @author mingyu
 * @version 1.0
 * 记录器，用于记录坦克大战游戏的信息并保存文件
 */
public class Recorder {
    //记录敌方坦克的死亡数
    static int enemyTankDeadNum = iniEnemyTankDeadNum();
    //记录Hero的子弹数
    static int bulletNum = Hero.getBulletNum();

    public static int iniEnemyTankDeadNum()  {
        String filePath = "src\\GameData.txt";
        String line;
        BufferedReader br = null;
        String[] s0 = new String[0];
        try {
            br = new BufferedReader(new FileReader(filePath));
            line = br.readLine();
            s0 = line.split(" ");
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(s0[1]);
    }

    public static void SaveData(Vector<EnemyTank> enemyTanks){
        BufferedWriter bufferedWriter = null;
        String filePath = "src\\GameData.txt";
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(filePath));
            bufferedWriter.write("击毁的坦克数： "+enemyTankDeadNum);
            bufferedWriter.newLine();
            bufferedWriter.write("敌方坦克数量： "+enemyTanks.size());
            bufferedWriter.newLine();
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                bufferedWriter.write(""+enemyTank.getX()+" "+enemyTank.getY()+" "+enemyTank.getDir());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(bufferedWriter != null){
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //选择继续上一次游戏时，读取文件中所有的敌方坦克位置
    public static Vector<EnemyTank> readData(){
        Vector<EnemyTank> enemyTanks = new Vector<>();
        String filePath = "src\\GameData.txt";
        String line;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            line = br.readLine();
            String[] s0 = line.split(" ");
            enemyTankDeadNum = Integer.parseInt(s0[1]);
            line = br.readLine();
            String[] s = line.split(" ");
            //读取第二行的坦克数
            int tankNum = Integer.parseInt(s[1]);
            for (int i = 0; i < tankNum; i++) {
                line = br.readLine();
                String[] tankS = line.split(" ");
                EnemyTank enemyTank = new EnemyTank(Integer.parseInt(tankS[0]), Integer.parseInt(tankS[1]), Integer.parseInt(tankS[2]));
                enemyTanks.add(enemyTank);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return enemyTanks;
    }


    public static int getBulletNum() {
        return bulletNum;
    }

    public static void setBulletNum(int bulletNum) {
        Recorder.bulletNum = bulletNum;
    }

    public static int getEnemyTankDeadNum() {
        return enemyTankDeadNum;
    }

    public static void setEnemyTankDeadNum(int enemyTankDeadNum) {
        Recorder.enemyTankDeadNum = enemyTankDeadNum;
    }

    public static void addNum(){
        enemyTankDeadNum++;
    }

}
