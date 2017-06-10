package com.mygdx.spacetrader.missile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.spacetrader.asteroid.Asteroid;

/**
 * Created by Gator King on 2/25/2017.
 */

public class Missile {
    private String name;
    private double hitpoints;
    private double speed;
    private Texture image;
    private Sprite sprite;
    private Vector2 position;
    private double rotationAngle;

    public Missile() {

    }

    public Missile(Missile missile) {
        this.name = missile.getName();
        this.hitpoints = missile.getHitpoints();
        this.speed = missile.getSpeed();
        this.image = missile.getImage();
        this.sprite = missile.getSprite();
        this.position = missile.getPosition();
    }

    public Missile(String name, double hitpoints, double speed, Texture image, Sprite sprite, Vector2 position, double rotationAngle) {
        this.name = name;
        this.hitpoints = hitpoints;
        this.speed = speed;
        this.image = image;
        this.sprite = sprite;
        this.position = position;
        this.rotationAngle = rotationAngle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
        double rotationAngleRadians = (rotationAngle*Math.PI)/180.0 + Math.PI/2;
        position.x += 2.0f * Math.cos(rotationAngleRadians);
        position.y += 2.0f * Math.sin(rotationAngleRadians);
        sprite.setPosition(position.x, position.y);

    }

    /**
     * Simple collision check with asteroid
     * @param asteroid
     * @return
     */
    public boolean checkCollision(Asteroid asteroid) {
        boolean collision = false;
        Rectangle asteroidBoundingBox = new Rectangle();
        Vector2 asteroidOrigin = asteroid.getOrigin();
        Vector2 asteroidSize = asteroid.getSize();
        asteroidBoundingBox.set(asteroidOrigin.x, asteroidOrigin.y,
                asteroidSize.x/2, asteroidSize.y/2);
        Rectangle thisBoundingBox = this.getSprite().getBoundingRectangle();
        if(thisBoundingBox.overlaps(asteroidBoundingBox))
        {
            collision = true;
        }

        return collision;
    }

}
