package com.mygdx.spacetrader.planets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.spacetrader.sprite.StationarySprite;

/**
 * Created by Gator King on 3/20/2017.
 */

public class Planet extends StationarySprite{

    // Default
    public Planet() {
        // STUB
    }

    public Planet(String name, Texture image, Sprite sprite,
           Vector2 position) {
        super(name, image, sprite, position);
    }
}
