package enitity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyHandler;
    public final int screenX;
    public final int screenY;
    int hasKey = 0;
    public Player(GamePanel gp, KeyHandler keyHandler) {
        this.gp = gp;
        this.keyHandler = keyHandler;
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        //for collision
        solidArea = new Rectangle(8,16, 32, 32);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "left";
    }

    public void getPlayerImage(){
        try{

            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));


        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void update(){

        if (keyHandler.upPressed || keyHandler.downPressed  || keyHandler.leftPressed || keyHandler.rightPressed ){
            if(keyHandler.upPressed){
                direction = "up";
            }
            else if(keyHandler.downPressed){
                direction = "down";
            }

            if(keyHandler.leftPressed){
                direction = "left";
            }
            else if(keyHandler.rightPressed){
                direction = "right";
            }

            //collision check
            collisionOn = false;
            //pass player class is a subclass of entity
            gp.cChecker.checkTile(this);

            //check object collision
            int object_index = gp.cChecker.checkObject(this, true);
            pickUpObject (object_index);
            //if collision is false player can move
            if(!collisionOn){
                switch (direction){
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12){
                if (spriteNum == 1){
                    spriteNum=2;
                }else if (spriteNum == 2){
                    spriteNum =1;
                }
                spriteCounter =0;
            }
        }


    }
    public void pickUpObject (int index){
        if (index != 999){

            String objectName = gp.obj[index].name;
            switch (objectName){
                case "Key":
                    hasKey++;
                    gp.obj[index]=null;
                    break;
                case "Door":
                    if (hasKey>0){
                        gp.obj[index]=null;
                        hasKey--;
                    }
                    break;

            }

        }
    }
    public void draw(Graphics2D graphics2D){
//Not using rectangle anymore
//        graphics2D.setColor(Color.BLUE);
//        graphics2D.fillRect(x,y, gp.tileSize,gp.tileSize);
        BufferedImage image = null;
        switch (direction){
            case "up":
                if (spriteNum == 1){
                    image=up1;
                }
                if (spriteNum == 2){
                    image=up2;
                }
                break;
            case "down":
                if (spriteNum == 1){
                    image=down1;
                }
                if (spriteNum == 2){
                    image=down2;
                }
                break;
            case "left":
                if (spriteNum == 1){
                    image=left1;
                }
                if (spriteNum == 2){
                    image=left2;
                }
                break;
            case "right":
                if (spriteNum == 1){
                    image=right1;
                }
                if (spriteNum == 2){
                    image=right2;
                }
                break;
        }
        graphics2D.drawImage(image,screenX,screenY,gp.tileSize, gp.tileSize, null);
    }
}
