package com.mygdx.spacetrader.building;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.spacetrader.sprite.StationarySprite;
import com.mygdx.spacetrader.vehicle.Vehicle;

/**
 * Created by Gator King on 3/13/2017.
 */

public class SpacePort extends StationarySprite {
    private String name;
    private double hitpoints;
    private double speed;
    private Texture image;
    private Sprite sprite;
    private Vector2 position;

    public SpacePort() {

    }

    public SpacePort(String name) {
        this.name = name;
    }

    public SpacePort(SpacePort spacePort) {
        this.name = spacePort.getName();
        this.hitpoints = spacePort.getHitpoints();
        this.speed = spacePort.getSpeed();
        this.image = spacePort.getImage();
        this.sprite = spacePort.getSprite();
        this.position = spacePort.getPosition();
    }

    public SpacePort(String name, double hitpoints, double speed, Texture image, Sprite sprite,
                   Vector2 position) {
        this.name = name;
        this.hitpoints = hitpoints;
        this.speed = speed;
        this.image = image;
        this.sprite = sprite;
        this.position = position;
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

}
