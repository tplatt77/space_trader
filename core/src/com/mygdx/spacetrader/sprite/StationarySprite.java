package com.mygdx.spacetrader.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.spacetrader.asteroid.Asteroid;

import java.util.Comparator;

/**
 * Created by Gator King on 3/14/2017.
 */

public class StationarySprite {


    private static final float WORLD_TO_SCREEN = 1.0f / 100.0f;
    private static final float SCENE_WIDTH = 12.80f;
    private static final float SCENE_HEIGHT = 7.20f;
    private static final float FRAME_DURATION = 1.0f / 20.0f;

    private String name;
    private Texture image;
    private Sprite sprite;
    private Vector2 position;

    public StationarySprite() {

    }

    public StationarySprite(String name) {
        this.name = name;
    }

    public StationarySprite(StationarySprite stationarySprite) {
        this.name = stationarySprite.getName();
        this.image = stationarySprite.getImage();
        this.sprite = stationarySprite.getSprite();
        this.position = stationarySprite.getPosition();
    }

    public StationarySprite(String name,Texture image, Sprite sprite,
                            Vector2 position) {
        this.name = name;
        this.image = image;
        this.sprite = sprite;
        this.position = position;

        // Set the position
        this.getSprite().setPosition(position.x, position.y);
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Texture getImage() {
        return image;
    }

    public void setImage(Texture image) {
        this.image = image;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setSpriteToPosition() {
        sprite.setPosition(position.x, position.y);
    }

    public void setSpriteToPosition(Vector2 newPosition) {
        sprite.setPosition(newPosition.x, newPosition.y);
    }

    public void setSpriteToPosition(float x, float y) {
        sprite.setPosition(x, y);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);

    }

    public void dispose() {
        getImage().dispose();
    }


}
