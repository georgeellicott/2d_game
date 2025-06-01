package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Boot extends SuperObject{
    public OBJ_Boot() {
        name = "Boot";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
