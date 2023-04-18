package it.unibo.dimhol.properties;

import java.util.Properties;

public class NumSpriteProperty {
    private static Properties numSprite;

    public NumSpriteProperty(){
        numSprite = new Properties();
        numSprite.put("player", "8");
        numSprite.put("enemy", "4");
        numSprite.put("coin", "6");
        numSprite.put("heart", "1");
        numSprite.put("key", "1");
        numSprite.put("chest", "2");
        numSprite.put("bullet", "1");
    }

    public static int getNumSprites(final String type){
        return Integer.parseInt(numSprite.getProperty(type));
    }
    
}
