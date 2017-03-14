package com.mygdx.spacetrader.asteroid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.spacetrader.SpaceTrader;
import com.mygdx.spacetrader.vehicle.Vehicle;

import java.util.Comparator;

/**
 * Created by Gator King on 3/11/2017.
 */

public class Asteroid {


    private static final float WORLD_TO_SCREEN = 1.0f / 100.0f;
    private static final float SCENE_WIDTH = 12.80f;
    private static final float SCENE_HEIGHT = 7.20f;
    private static final float FRAME_DURATION = 1.0f / 20.0f;

    private String name;
    private double hitpoints;
    private double speed;
    private Texture image;
    private Sprite sprite;
    private Vector2 position;

    private TextureAtlas asteroidAtlas;
    private Animation asteroidAnimation;

    public Asteroid() {

    }

    public Asteroid(String name) {
        this.name = name;
    }

    public Asteroid(Asteroid asteroid) {
        this.name = asteroid.getName();
        this.hitpoints = asteroid.getHitpoints();
        this.speed = asteroid.getSpeed();
        this.image = asteroid.getImage();
        this.sprite = asteroid.getSprite();
        this.position = asteroid.getPosition();
        this.asteroidAtlas = new TextureAtlas(Gdx.files.internal("asteroid.atlas"));
        Array<TextureAtlas.AtlasRegion> asteroidRegions = new Array<TextureAtlas.AtlasRegion>(asteroidAtlas.getRegions());
        asteroidRegions.sort(new RegionComparator());
        this.asteroidAnimation = new Animation(FRAME_DURATION, asteroidRegions,
                Animation.PlayMode.LOOP);
    }

    public Asteroid(String name, double hitpoints, double speed, Texture image, Sprite sprite,
                    Vector2 position) {
        this.name = name;
        this.hitpoints = hitpoints;
        this.speed = speed;
        this.image = image;
        this.sprite = sprite;
        this.position = position;
        this.asteroidAtlas = new TextureAtlas(Gdx.files.internal("asteroid.atlas"));
        Array<TextureAtlas.AtlasRegion> asteroidRegions = new Array<TextureAtlas.AtlasRegion>(asteroidAtlas.getRegions());
        asteroidRegions.sort(new RegionComparator());
        this.asteroidAnimation = new Animation(FRAME_DURATION, asteroidRegions,
                Animation.PlayMode.LOOP);
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(double hitpoints) {
        this.hitpoints = hitpoints;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
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

    public void draw(SpriteBatch batch, float animationTime) {
        //sprite.draw(batch);
        TextureRegion asteroidFrame = asteroidAnimation.getKeyFrame(animationTime);
        int width = asteroidFrame.getRegionWidth();
        int height = asteroidFrame.getRegionHeight();
        float originX = width * 0.5f;
        float originY = height * 0.5f;

        batch.draw(asteroidFrame,
                1.0f - originX, 3.70f - originY,
                originX, originY,
                width, height,
                WORLD_TO_SCREEN, WORLD_TO_SCREEN,
                0.0f);

       batch.draw(asteroidAnimation.getKeyFrame(animationTime % 5), position.x, 275.0f);

    }

    public void dispose() {
        getImage().dispose();
    }

    private static class RegionComparator implements Comparator<TextureAtlas.AtlasRegion> {
        @Override
        public int compare(TextureAtlas.AtlasRegion region1, TextureAtlas.AtlasRegion region2) {
            return region1.name.compareTo(region2.name);
        }
    }
}
