package com.HspProject.TankWar;

import org.junit.jupiter.api.condition.OS;
import sun.awt.image.ToolkitImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

/**
 * @author mingyu
 * @version 1.0
 * 实现坦克大战的画板
 */
//为了让Panel不停的重绘，需要让Panel实现Runnable接口，当作线程使用
public class TankWarPanel extends JPanel implements KeyListener, Runnable {
    //决定游戏是否继续运行的线程信号，当hero死亡游戏就结束
    boolean flag = true;
    //定义我的坦克
    Hero hero = null;
    //定义敌人坦克
    Vector<EnemyTank> enemyTanks = new Vector<>();
    static int enemyTankNum = 8;
    //定义炸弹集合，涉及到多线程的集合都应该使用vector
    Vector<Bomb> bombs = new Vector<>();
    //定义可以被打掉的砖块集合
    Vector<WallRect> wallRects = new Vector<>();
    //定义河流集合
    Vector<RiverObstacles> riverObstacles = new Vector<>();
    //定义钢板集合
    Vector<SteelPlateObstacles> steelPlateObstacles = new Vector<>();
    //定义所有的障碍物集合
    static Vector<Obstacle> allObstacles = new Vector<>();
    //定义要使用到的三张爆炸图片
    Image image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
    Image image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
    Image image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));


    public Vector<EnemyTank> getEnemyTanks() {
        return enemyTanks;
    }

    public static Vector<Obstacle> getAllObstacles() {
        return allObstacles;
    }

    public TankWarPanel(int key) {
        hero = new Hero(500, 500);  //初始位置，生成一个HeroTank
        hero.setSpeed(5);
        new AePlayWave("src\\111.wav").start();
        //初始化障碍物：墙体
        CreateWall(100, 200, 200, 40, 0);
        CreateWall(700, 200, 200, 40, 0);

        //初始化障碍物：钢板;
        CreateWall(400, 400, 200, 40, 1);
        CreateWall(400, 250, 200, 40, 1);
        CreateWall(100, 500, 200, 40, 1);
        CreateWall(700, 500, 200, 40, 1);

        //初始化障碍物：河流
        CreateWall(100, 300, 40, 800, 2);
        allObstacles.addAll(wallRects);
        allObstacles.addAll(riverObstacles);
        allObstacles.addAll(steelPlateObstacles);

        switch (key) {
            case 1:
                //新游戏，死亡坦克肯定为0
                Recorder.enemyTankDeadNum = 0;
                //初始化敌方坦克
                for (int i = 0; i < enemyTankNum; i++) {
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 100, 1);
                    enemyTanks.add(enemyTank);
                    enemyTank.setEnemyTanks(enemyTanks);
                    //为每个敌方坦克初始化一颗子弹
                    Bullet bullet = new Bullet(enemyTank.getShootX(), enemyTank.getShootY(), enemyTank.getDir());
                    enemyTank.bullets.add(bullet);
                    //启动敌方子弹线程
                    bullet.start();
                    //启动敌方坦克线程，开始随机移动
                    new Thread(enemyTank).start();
                }
                //让hero对象也获取到enemyTanks集合从而可以进行hero和enemyTank的碰撞检测
                hero.setEnemyTanks(enemyTanks);
                break;
            case 2:
                enemyTanks = Recorder.readData();
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    enemyTank.setEnemyTanks(enemyTanks);
                    //为每个敌方坦克初始化一颗子弹
                    Bullet bullet = new Bullet(enemyTank.getShootX(), enemyTank.getShootY(), enemyTank.getDir());
                    enemyTank.bullets.add(bullet);
                    //启动敌方子弹线程
                    bullet.start();
                    //启动敌方坦克线程，开始随机移动
                    new Thread(enemyTank).start();
                }
                hero.setEnemyTanks(enemyTanks);
                break;
            default:
                System.out.println("输入有误！");
        }


    }

    /**
     * 在指定位置初始化矩形的障碍物
     *
     * @param x      障碍物的左上角x坐标
     * @param y      障碍物的左上角y坐标
     * @param length 矩形长度
     * @param width  矩形宽度
     * @param type   障碍物类型：
     *               0代表墙，可以被子弹击破，耐久度为1颗子弹，墙由10×20的小方块组成
     *               1代表钢板，无法被子弹击破
     *               2代表河流，子弹可以穿过但是坦克不能经过
     */
    public void CreateWall(int x, int y, int length, int width, int type) throws RuntimeException {
        //这里还可以加一个判断len和width哪个大从而决定绘制障碍物是横着的还是竖着的
        int lenNum;
        int WidthNum;
        switch (type) {
            //绘制墙体，应该是由一系列可以被击破的小矩形构成，小矩形应该做成一个类
            //注意绘制墙体时，砖块的默认绘制方向是竖着的，所以length应该是WallRect.getWidth()的整数倍（10）
            //width应该是WallRect.getLength()的整数倍（20）
            case 0:
                if (length % WallRect.getRectLength() != 0 || width % WallRect.getRectLength() != 0) {
                    throw new RuntimeException("DrawWall的长度或宽度输入有误，不是WallRect长度或宽度的整数倍");
                }
                lenNum = length / WallRect.getRectWidth();
                WidthNum = width / WallRect.getRectLength();
                //创建小矩形
                for (int i = 0; i < lenNum; i++) {
                    for (int j = 0; j < WidthNum; j++) {
                        wallRects.add(new WallRect(x + i * WallRect.getRectWidth(), y + j * WallRect.getRectLength()));
                    }
                }
                break;
            case 1:
                if (length % SteelPlateObstacles.getSteelLength() != 0 || width % SteelPlateObstacles.getSteelWidth() != 0) {
                    throw new RuntimeException("DrawWall的长度或宽度输入有误，不是SteelPlate的长度或宽度的整数倍");
                }
                lenNum = length / SteelPlateObstacles.getSteelLength();
                WidthNum = width / SteelPlateObstacles.getSteelWidth();
                for (int i = 0; i < lenNum; i++) {
                    for (int j = 0; j < WidthNum; j++) {
                        steelPlateObstacles.add(new SteelPlateObstacles(x + i * SteelPlateObstacles.getSteelLength(),
                                y + j * SteelPlateObstacles.getSteelWidth()));
                    }
                }
                break;
            case 2:
                riverObstacles.add(new RiverObstacles(x, y, width, length));
                break;
            default:
                throw new RuntimeException("Parameter error: type！");
        }
    }

    //绘制信息，提示已经击毁的tank数
    public void DrawInfo(Graphics g) {
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        g.setColor(Color.BLACK);
        //打印游戏目标
        g.drawString("游戏目标：击毁所有敌方坦克", 1050, 40);
        g.drawString("游戏操作：WASD移动,J射击", 1050, 90);
        //绘制击毁的tank数
        g.drawString("击毁的坦克数：", 1050, 150);
        drawTank(1240, 120, g, 0, 1);
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getEnemyTankDeadNum() + "", 1300, 150);
        //绘制Hero子弹数
        g.drawString("Hero的弹匣：", 1050, 230);
//        g.drawString(Recorder.getBulletNum()+"", 1200, 210);
        g.setColor(Color.cyan);
        for (int i = 0; i < Recorder.getBulletNum(); i++) {
            g.fillOval(1200 + i * 20, 210, 20, 20);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //构建背景
        g.fillRect(0, 0, 1000, 750);    //默认黑色
        //绘制障碍物
        g.setColor(Color.ORANGE);
        for (int i = 0; i < wallRects.size(); i++) {
            WallRect wallRect = wallRects.get(i);
            if (wallRect.isLive()) {
                g.fill3DRect(wallRect.getX(), wallRect.getY(), wallRect.getWidth(), wallRect.getLength(), false);
            }
        }
        g.setColor(Color.white);
        for (int i = 0; i < steelPlateObstacles.size(); i++) {
            SteelPlateObstacles spo = steelPlateObstacles.get(i);
            g.fill3DRect(spo.getX(), spo.getY(), spo.getWidth(), spo.getLength(), false);
        }
        g.setColor(Color.BLUE);
        for (int i = 0; i < riverObstacles.size(); i++) {
            RiverObstacles ro = riverObstacles.get(i);
            g.fill3DRect(ro.getX(), ro.getY(), ro.getWidth(), ro.getLength(), false);
        }
        //绘制信息
        DrawInfo(g);
        //使用封装的绘制坦克方法，hero存活时才绘制
        if (hero.isLive()) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDir(), 0);
        }
        //遍历Hero子弹集合，当子弹存在时才绘制
        for (int i = 0; i < hero.bullets.size(); i++) {
            //获取子弹
            Bullet bullet = hero.bullets.get(i);
            if (bullet != null && bullet.isFlag()) {
                g.draw3DRect(bullet.getX(), bullet.getY(), 1, 1, false);
            } else {//如果不满足意味着这颗子弹应该销毁了
                hero.bullets.remove(bullet);
                //有子弹销毁了，就可以新增一颗待射击子弹
                hero.bulletNum++;
                Recorder.setBulletNum(hero.bulletNum);
            }
        }

        //初始化绘制敌人坦克，注意因为enemyTank数量可能会变化（被打死了），所以这里应该使用size
        for (int i = 0; i < enemyTanks.size(); i++) {
            //取出坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            //只有当坦克存活时才绘制坦克
            if (enemyTank.isLive()) {
                //不能在这里启动敌方坦克线程，因为这是paint方法，每100ms重新运行一次，也就意味着会不断的新建新线程
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDir(), 1);
                //绘制坦克子弹
                for (int j = 0; j < enemyTank.getBullets().size(); j++) {
                    //取出子弹
                    Bullet bullet = enemyTank.getBullets().get(j);
                    //绘制
                    if (bullet.isFlag()) {
                        g.draw3DRect(bullet.getX(), bullet.getY(), 1, 1, false);
                    } else {//从vector移除，并添加一颗新的子弹
                        //记得要终止子弹线程，不然线程会无限增加
                        bullet.setFlag(false);
                        enemyTank.getBullets().remove(bullet);
                    }
                }
            }
        }

        //遍历bombs集合，绘制爆炸效果
        for (int i = 0; i < bombs.size(); i++) {
            //取出炸弹
            Bomb bomb = bombs.get(i);
            if (bomb.lifeTime > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.lifeTime > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.lifeTime > 0) {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            bomb.lifeDown();
            if (!bomb.live) {
                bombs.remove(bomb);
            }
        }

    }

    /**
     * @param x         坦克左上角x坐标
     * @param y         坦克左上角y坐标
     * @param g         画笔
     * @param direction 控制坦克的方向，规定0表示上，1表示右，2表示下，3表示左
     * @param type      坦克的类型（敌方还是友方），0为友方，1为敌方
     */
    public static void drawTank(int x, int y, Graphics g, int direction, int type) {
        switch (type) {
            case 0://友方坦克用0表示，使用青色
                g.setColor(Color.cyan);
                break;
            case 1://敌方坦克用1表示，使用黄色
                g.setColor(Color.YELLOW);
                break;
        }
        //规定0表示上，1表示右，2表示下，3表示左
        switch (direction) {
            case 0://表示向上的坦克
                g.fill3DRect(x, y, 10, 60, false);//画坦克左边履带
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边履带
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克盖子
                g.fillOval(x + 10, y + 20, 20, 20); //画出圆形盖子
                g.drawLine(x + 20, y + 30, x + 20, y);//画出炮管
                break;
            case 1:
                g.fill3DRect(x, y, 60, 10, false);//画坦克上边履带
                g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克右边履带
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克盖子
                g.fillOval(x + 20, y + 10, 20, 20); //画出圆形盖子
                g.drawLine(x + 30, y + 20, x + 60, y + 20);//画出炮管
                break;
            case 2:
                g.fill3DRect(x, y, 10, 60, false);//画坦克左边履带
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边履带
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克盖子
                g.fillOval(x + 10, y + 20, 20, 20); //画出圆形盖子
                g.drawLine(x + 20, y + 30, x + 20, y + 60);//画出炮管
                break;
            case 3:
                g.fill3DRect(x, y, 60, 10, false);//画坦克上边履带
                g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克右边履带
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克盖子
                g.fillOval(x + 20, y + 10, 20, 20); //画出圆形盖子
                g.drawLine(x + 30, y + 20, x, y + 20);//画出炮管
                break;
            default:
                System.out.println("其他还没设计");
        }
    }

    //编写方法判断子弹是否击中敌方坦克
    //坦克被击中后，添加一个炸弹进入炸弹集合，获取被击中坦克的x和y
    public void hitTank(Bullet b, EnemyTank enemyTank) {
        //根据坦克的方向判断击中范围
        switch (enemyTank.getDir()) {
            case 0:
            case 2:
                if (b.getX() > enemyTank.getX() && b.getX() < enemyTank.getX() + 40 &&
                        b.getY() > enemyTank.getY() && b.getY() < enemyTank.getY() + 60) {
                    b.setFlag(false);
                    enemyTank.setLive(false);
                    bombs.add(new Bomb(enemyTank.getX(), enemyTank.getY()));
                    this.enemyTanks.remove(enemyTank);
                    Recorder.addNum();
                }
            case 1:
            case 3:
                if (b.getX() > enemyTank.getX() && b.getX() < enemyTank.getX() + 60 &&
                        b.getY() > enemyTank.getY() && b.getY() < enemyTank.getY() + 40) {
                    b.setFlag(false);
                    enemyTank.setLive(false);
                    bombs.add(new Bomb(enemyTank.getX(), enemyTank.getY()));
                    this.enemyTanks.remove(enemyTank);
                    Recorder.addNum();
                }
        }
    }


    //编写方法判断敌方子弹是否击中hero
    public void hitHero(Bullet b) {
        //hero存活时才需要判断
        //根据Hero的方向判断击中范围
        switch (hero.getDir()) {
            case 0:
            case 2:
                if (b.getX() > hero.getX() && b.getX() < hero.getX() + 40 &&
                        b.getY() > hero.getY() && b.getY() < hero.getY() + 60) {
                    b.setFlag(false);
                    hero.setLive(false);
                    bombs.add(new Bomb(hero.getX(), hero.getY()));
                }
            case 1:
            case 3:
                if (b.getX() > hero.getX() && b.getX() < hero.getX() + 60 &&
                        b.getY() > hero.getY() && b.getY() < hero.getY() + 40) {
                    b.setFlag(false);
                    hero.setLive(false);
                    bombs.add(new Bomb(hero.getX(), hero.getY()));
                }
        }
        if (!hero.isLive()) {
            System.out.println("Hero被摧毁！游戏结束！");
            flag = false;
        }
    }

    //编写方法判断子弹是否击中砖块
    public void hitWall(Bullet b, Obstacle o) {
        if (o instanceof WallRect) {
            if (b.getX() >= o.getX() && b.getX() <= o.getX() + 10 &&
                    b.getY() >= o.getY() && b.getY() <= o.getY() + 20) {
                b.setFlag(false);
                o.setLive(false);
                allObstacles.remove(o);
                wallRects.remove(o);
            }
        }
        if (o instanceof SteelPlateObstacles) {
            if (b.getX() >= o.getX() && b.getX() <= o.getX() + 20 &&
                    b.getY() >= o.getY() && b.getY() <= o.getY() + 20) {
                b.setFlag(false);
            }
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //hero在存活时才能移动和发射
        if (hero.isLive()) {
            //hero在没有碰撞到障碍物的时候才能移动，射击可以
            if (e.getKeyCode() == KeyEvent.VK_S) {
                hero.TankMoveDown();
            } else if (e.getKeyCode() == KeyEvent.VK_W) {
                hero.TankMoveUp();
            } else if (e.getKeyCode() == KeyEvent.VK_A) {
                hero.TankMoveLeft();
            } else if (e.getKeyCode() == KeyEvent.VK_D) {
                hero.TankMoveRight();
            }
            if (e.getKeyCode() == KeyEvent.VK_J) {
                //控制只能发射一颗子弹
//            if(hero.getBullet() == null || !hero.getBullet().isFlag()){
//                hero.TankShoot();
//            }
                //可以连发多颗
                hero.TankShoot();
            }
        }
//        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() { //每隔50ms，重绘画板
        while (flag) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            judge();
            this.repaint();
        }
    }

    //判断当前战场上的情况
    public void judge() {
        //遍历hero的所有子弹，查看是否击中敌方坦克
        for (int i = 0; i < hero.bullets.size(); i++) {
            Bullet bullet = hero.bullets.get(i);
            if (bullet != null && bullet.isFlag()) {
                //遍历敌人所有坦克
                for (int j = 0; j < enemyTanks.size(); j++) {
                    EnemyTank enemyTank = enemyTanks.get(j);
                    //在hitTank方法内判断是否击中
                    hitTank(bullet, enemyTank);
                }
            }
        }
        //遍历敌方坦克的所有子弹查看是否击中hero
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            for (int j = 0; j < enemyTank.bullets.size(); j++) {
                Bullet bullet = enemyTank.bullets.get(j);
                if (bullet != null && bullet.isFlag() && hero.isLive()) {
                    //查看是否击中hero
                    hitHero(bullet);
                }
            }
        }
        //遍历hero的所有子弹，查看是否击中砖块
        for (int i = 0; i < hero.bullets.size(); i++) {
            Bullet bullet = hero.bullets.get(i);
            if (bullet != null && bullet.isFlag()) {
                //遍历所有砖块
                for (int j = 0; j < wallRects.size(); j++) {
                    WallRect wallRect = wallRects.get(j);
                    //在hitWall方法内判断是否击中
                    hitWall(bullet, wallRect);
                }
                //遍历所有铁块
                for (int k = 0; k < steelPlateObstacles.size(); k++) {
                    SteelPlateObstacles spo = steelPlateObstacles.get(k);
                    hitWall(bullet, spo);
                }
            }
        }
        //遍历敌方坦克的所有子弹查看是否击中砖块
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            for (int j = 0; j < enemyTank.bullets.size(); j++) {
                Bullet bullet = enemyTank.bullets.get(j);
                if (bullet != null && bullet.isFlag()) {
                    //遍历所有砖块
                    for (int k = 0; k < wallRects.size(); k++) {
                        WallRect wallRect = wallRects.get(k);
                        //在hitWall方法内判断是否击中
                        hitWall(bullet, wallRect);
                    }
                    //遍历所有铁块
                    for (int k = 0; k < steelPlateObstacles.size(); k++) {
                        SteelPlateObstacles spo = steelPlateObstacles.get(k);
                        hitWall(bullet, spo);
                    }
                }
            }
        }

        //遍历所有坦克，进入障碍物就设置阻塞值为true，如果检测到方向发生改变就设置为false
        for (int i = 0; i < enemyTanks.size(); i++) {
            Tank tank = enemyTanks.get(i);
            tank.isTouchObstacleTank();
            if(tank.getDir() != tank.getLastDir()){
                tank.setStuck(false);
            }
        }
        hero.isTouchObstacleTank();
        if(hero.getDir() != hero.getLastDir()){
            hero.setStuck(false);
        }
//        System.out.println(hero.isStuck());
    }



}


