package tankwar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

public class GamePanel extends JFrame {
	
	

    /** 定义双缓存图片 */
    private Image offScreenImage = null;
    //游戏状态 0 游戏未开始，1 单人模式，2 双人模式， 3 游戏暂停， 4 游戏失败，5 游戏成功
    public int state= 0;
    
    //临时变量
    private int a = 1;
    //重绘次数
    public int count = 0;
    //窗口长宽
    private int width = 1100;
    private int height = 670;
    //敌人数量
    private int enemyCount = 0;
    //高度
    private int y = 460;//指针位置
    private int y1 = -200;//tank.png位置
    private int y2 = 20;//背景位置
    //是否开始
    private boolean start = false;
    //物体集合
    public List<Bullet> bulletList = new ArrayList<>();
    public List<Bot> botList = new ArrayList<>();
    public List<Tank> tankList = new ArrayList<>();
    public List<Wall> wallList = new ArrayList<>();
    public List<Bullet> removeList = new ArrayList<>();
    public List<Base> baseList = new ArrayList<>();
    public List<BlastObj> blastList = new ArrayList<>();
    //背景图片
    public Image background = Toolkit.getDefaultToolkit().getImage("images/bj.jpg");
    //指针图片
    private Image select = Toolkit.getDefaultToolkit().getImage("images/selecttank.gif");
    private Image select1 = Toolkit.getDefaultToolkit().getImage("images/1.png");//tank png图片
    private Image select2 = Toolkit.getDefaultToolkit().getImage("images/2.jpg");//背景图片
    //基地
    private Base base = new Base("images/star.gif", 560, 630, this);
    //玩家
    private PlayerOne playerOne = new PlayerOne("images/player1/p1tankU.gif", 125, 510,
            "images/player1/p1tankU.gif","images/player1/p1tankD.gif",
            "images/player1/p1tankL.gif","images/player1/p1tankR.gif", this);
    private PlayerTwo playerTwo = new PlayerTwo("images/player2/p2tankU.gif", 920, 510,
            "images/player2/p2tankU.gif","images/player2/p2tankD.gif",
            "images/player2/p2tankL.gif","images/player2/p2tankR.gif", this);

    //窗口的启动方法
    public void launch(){
        //标题
        setTitle("坦克大战");
        //窗口初始大小
        setSize(width, height);
        //用户不能调整大小
        setResizable(false);
        //设置窗口一直在最上层
        super.setAlwaysOnTop(true);
        //使窗口可见
        setVisible(true);
        //获取屏幕分辨率，使窗口生成时居中
        setLocationRelativeTo(null);
        //添加关闭事件
        setDefaultCloseOperation(3);
        //添加键盘事件
        this.addKeyListener(new GamePanel.KeyMonitor());
        //添加围墙
        for(int i = 0; i< 19; i ++){
            wallList.add(new Wall("images/walls.gif", i*60 ,170, this ));
        }
        for(int i = 0; i< 19; i ++){
            wallList.add(new Wall("images/walls.gif", i*60 ,300, this ));
        }
        
        wallList.add(new Wall("images/walls.gif", 200 ,400,this ));
        wallList.add(new Wall("images/walls.gif", 490 ,610,this ));
        wallList.add(new Wall("images/walls.gif", 490 ,570,this ));
        wallList.add(new Wall("images/walls.gif", 550 ,570,this ));
        wallList.add(new Wall("images/walls.gif", 610 ,570,this ));
        wallList.add(new Wall("images/walls.gif", 610 ,610,this ));//基地坐标
        //添加基地
        baseList.add(base);

        while (true){
            if(botList.size() == 0 && enemyCount == 10){
                state = 5;
            }
            if(tankList.size() == 0 && (state == 1 || state == 2)){

                state = 4;
            }
            if(state == 1 || state == 2){
                if (count % 100 == 1 && enemyCount < 10) {
                    Random r = new Random();
                    int rnum =r.nextInt(800);
                    botList.add(new Bot("images/enemy/enemy1U.gif", rnum, 110,
                            "images/enemy/enemy1U.gif","images/enemy/enemy1D.gif",
                            "images/enemy/enemy1L.gif","images/enemy/enemy1R.gif", this));//敌方坦克
                    enemyCount++;
                    //System.out.println("bot: " + botList.size());
                }
            }
            repaint();
            try {
                //线程休眠  1秒 = 1000毫秒
                Thread.sleep(25);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //paint方法
    @Override
    public void paint(Graphics g) {
    	
        //System.out.println(bulletList.size());
        //System.out.println("tank"+tankList.size());
        //创建和容器一样大小的Image图片
        if(offScreenImage ==null){
            offScreenImage=this.createImage(width, height);
        }
        
        //获得该图片的画布
        Graphics gImage= offScreenImage.getGraphics();
        //设置背景颜色
        gImage.setColor(Color.black);
        //填充整个画布
        gImage.fillRect(0, 0, width, height);
        //改变画笔的颜色
        gImage.setColor(Color.yellow);
        //改变文字大小和样式
        //添加文字
        gImage.setFont(new Font("宋体",Font.BOLD,5));
        if(state == 0){
        	gImage.drawString("<此产品仍在开发阶段为测试版  解释权归闪电团队>",800,700);
        }
        
        gImage.setFont(new Font("仿宋",Font.BOLD,50));
        if(state == 0){
            
            gImage.drawString("选择游戏模式",400,400);
            gImage.drawString("单人游戏",400,500);
            gImage.drawString("双人游戏",400,600);
            
            
            gImage.drawImage(select,350,y,null);//指针高度
            gImage.drawImage(select1,-250,y1,null);//tank.png高度
            gImage.drawImage(select2,100,y2,null);//背景图片高度
        }
        gImage.setFont(new Font("仿宋",Font.BOLD,15));
        if(state == 0){
        	gImage.drawString("按1，2选择模式，按回车开始游戏",400,650);
        }
        
        else if(state == 1||state == 2){
            gImage.setColor(Color.red);
            gImage.setFont(new Font("仿宋",Font.BOLD,20));
            gImage.drawString("WASD控制移动",15,510);
            gImage.drawString("空格射击",15,550);
            if(state == 2){
                gImage.drawString("方向键控制移动",800,510);
                gImage.drawString("K射击",800,550);
            }

            //paint重绘游戏元素
            for(Tank tank : tankList){
                tank.paintSelf(gImage);
            }
            for(Bullet bullet: bulletList){
                bullet.paintSelf(gImage);
            }
            bulletList.removeAll(removeList);
            for(Bot bot: botList){
                bot.paintSelf(gImage);
            }
            for (Wall wall: wallList){
                wall.paintSelf(gImage);
            }
            for(Base base : baseList){
                base.paintSelf(gImage);
            }
            for(BlastObj blast : blastList){
                blast.paintSelf(gImage);
            }
            //重绘次数+1
            count++;
        }
        gImage.setFont(new Font("仿宋",Font.BOLD,100));
            if(state == 3){
            gImage.drawString("游戏暂停",360,400);
        }
        else if(state == 4){
            gImage.drawString("GAME OVER",330,400);
        }
        else if(state == 5){
            gImage.drawString("游戏胜利",350,400);
        }
        /** 将缓冲区绘制好的图形整个绘制到容器的画布中 */ 
        g.drawImage(offScreenImage, 0, 0, null);
    }

    private class KeyMonitor extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            //super.keyPressed(e);
            int key = e.getKeyCode();
            switch (key){
                case KeyEvent.VK_1:
                    y = 460;
                    a = 1;
                    break;
                case KeyEvent.VK_2:
                    y = 560;
                    a = 2;
                    break;
                case KeyEvent.VK_ENTER:
                    state = a;
                    //添加玩家
                    if(state == 1 && !start){
                        tankList.add(playerOne);
                    }else{
                        tankList.add(playerOne);
                        tankList.add(playerTwo);
                    }
                    start = true;
                    break;
                case KeyEvent.VK_P:
                    if(state != 3){
                        a = state;
                        state = 3;
                    }
                    else{
                        state = a;
                        if(a == 0) {
                            a = 1;
                        }
                    }
                    break;
                default:
                    playerOne.keyPressed(e);
                    playerTwo.keyPressed(e);
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e){
            playerOne.keyReleased(e);
            playerTwo.keyReleased(e);
        }
    }

    public static void main(String[] args) throws InterruptedException{
    	Music player = new Music("musics/bgm.wav");//创建音乐播放器
        player.start(false);//以开始以循环的形式播放，player(false)为不循环播放
        GamePanel gamePanel = new GamePanel();
        gamePanel.launch();
    }
}