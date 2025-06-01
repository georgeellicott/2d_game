package main;

import enitity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    //Screen settings
    public  final int ogrinalTileSize=16; //16x16
    public final int scale=3; //times the 16

    public final int tileSize = ogrinalTileSize * scale;
    public final int maxScreenCol =16;
    public final int maxScreenRow =12;

    //ratio 4/3
    public final int screenWidth = tileSize * maxScreenCol; //768
    public final int screenHeight = tileSize * maxScreenRow; //576



    //world settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;





    int FPS=60;
    //end of screen settings

    //create sound
    Sound music = new Sound();
    Sound sound_effects = new Sound();
    //collision checker
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public SuperObject obj[] = new SuperObject[10];

    //ui
    public UI ui = new UI(this);

    //create tile manager
    TileManager tileM = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    public Player player = new Player(this,keyHandler);



    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

    }

    public void setUpGame(){
        aSetter.setObject();

        playMusic(0);
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS; //0.01666
        double nextDrawTime = System.nanoTime() + drawInterval;



        while(gameThread != null){

            long currentTime = System.nanoTime();


            //Update info character pos
            update();
            //draw the screen updated pos
            repaint();


            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime =  remainingTime/1000000;

                if(remainingTime < 0 ){
                    remainingTime = 0;
                }
                Thread.sleep((long)remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void update(){
        player.update();

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D)g;
        //draw tiles before player
        tileM.draw(graphics2D);

        //Object
        for(int i=0;i<obj.length;i++){
          if (obj[i]!=null){
              obj[i].draw(graphics2D,this);
          }
        }


        //draw the player as an overlayer
        player.draw(graphics2D);
        ui.draw(graphics2D);

        graphics2D.dispose();

    }
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    public void play_soundEffect(int i){
        sound_effects.setFile(i);
        sound_effects.play();
    }
}
