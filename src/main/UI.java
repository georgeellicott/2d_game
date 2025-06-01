package main;

import object.OBJ_key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    //create here first before drawing!!
    Font arial_40, Arial_80b;
    BufferedImage keyimg;
    public boolean messageOn = false;
    public String message = "";
    int message_time = 0;
    public boolean GameEnd = false;
    double playTime;
    DecimalFormat dformat = new DecimalFormat("#0.00");
    int x;
    int y;


    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        Arial_80b = new Font("Arial", Font.BOLD, 80);
        OBJ_key key = new OBJ_key();
        keyimg = key.image;
    }
    public void showMessage(String text){
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D graphics2D){

        if (GameEnd){
            String text ;
            int textLength;
            graphics2D.setFont(arial_40);
            graphics2D.setColor(Color.white);
            text  = "You found the treasure";
            textLength = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();

            //getting a center message
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 - (gp.tileSize*3);
            graphics2D.drawString(text, x, y);

            text  = "Your time is" + dformat.format(playTime) + " !";
            textLength = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize*4);
            graphics2D.drawString(text, x, y);


            graphics2D.setFont(Arial_80b);
            graphics2D.setColor(Color.yellow);

            text = "Congratulations";
            textLength = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
            x = gp.screenWidth/2 - (textLength/2);
            y = gp.screenHeight/2 + (gp.tileSize*2);
            graphics2D.drawString(text, x, y);
            gp.gameThread =null;

        } else {
            graphics2D.setFont(arial_40);
            graphics2D.setColor(Color.white);
            graphics2D.drawImage(keyimg, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            graphics2D.drawString("x " + gp.player.hasKey, 74, 65);

            //Time
            playTime +=(double)1/60;

            graphics2D.drawString("Time: "+ dformat.format(playTime), gp.tileSize*11,65);
            //message
            if (messageOn) {
                graphics2D.setFont(graphics2D.getFont().deriveFont(30F));
                graphics2D.drawString(message, gp.tileSize / 2, gp.tileSize * 5);

                message_time++;
                //two seconds -frame counts
                if (message_time > 120) {
                    messageOn = false;
                    message_time = 0;
                }
            }
        }

    }
}
