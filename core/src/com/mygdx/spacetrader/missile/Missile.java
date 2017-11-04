package com.mygdx.spacetrader.missile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.spacetrader.asteroid.Asteroid;
import com.mygdx.spacetrader.helpers.GameInfo;

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

    private World world;
    private Body body;

    public Missile(Missile missile, World world) {
        this.name = missile.getName();
        this.hitpoints = missile.getHitpoints();
        this.speed = missile.getSpeed();
        this.image = missile.getImage();
        this.sprite = missile.getSprite();
        this.position = missile.getPosition();
        this.world = world;

        createBody();
    }

    public Missile(String name, double hitpoints, double speed,
                   Texture image, Sprite sprite, Vector2 position,
                   double rotationAngle, World world) {
        this.name = name;
        this.hitpoints = hitpoints;
        this.speed = speed;
        this.image = image;
        this.sprite = sprite;
        this.position = position;
        this.rotationAngle = rotationAngle;

        this.world = world;
        createBody();
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
        body.setLinearVelocity(position.x, position.y);
    }

    private void createBody() {

        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);

        body = world.createBody(bodyDef);
        body.setFixedRotation(true);
        body.setActive(false);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / 2f - 20f) / GameInfo.PPM,
                (getHeight() / 2f) / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0f;
        fixtureDef.friction = 2f;
        fixtureDef.filter.categoryBits = GameInfo.PLAYER;
        fixtureDef.filter.maskBits = GameInfo.DEFAULT | GameInfo.COLLECTABLE | GameInfo.ASTEROID;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("Player");

        shape.dispose();
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

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return sprite.getWidth();
    }

    public float getHeight() {
        return sprite.getHeight();
    }

}
