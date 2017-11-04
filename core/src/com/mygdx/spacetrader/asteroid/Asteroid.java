package com.mygdx.spacetrader.asteroid;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.spacetrader.helpers.GameInfo;
import com.mygdx.spacetrader.sprite.AnimatedSprite;
import com.mygdx.spacetrader.vehicle.Vehicle;

import java.util.Comparator;

/**
 * Created by Gator King on 3/11/2017.
 */

public class Asteroid extends AnimatedSprite {


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

    private Body body;
    private World world;

    private TextureAtlas asteroidAtlas;
    private Animation asteroidAnimation;
    private Vector2 size;
    private Vector2 origin;

    public Asteroid() {
    }

    public Asteroid(String name) {
        this.name = name;
    }

    public Asteroid(Asteroid asteroid) {
        super(asteroid);
        this.speed = asteroid.getSpeed();
        this.hitpoints = asteroid.getHitpoints();
        createBody();
    }

    public Asteroid(String name, double hitpoints, double speed, Texture image, Sprite sprite,
                    Vector2 position, World world) {
        this.name = name;
        this.hitpoints = hitpoints;
        this.speed = speed;
        this.image = image;
        this.sprite = sprite;
        super.setSprite(sprite);
        this.position = position;
        this.asteroidAtlas = new TextureAtlas(Gdx.files.internal("asteroid.atlas"));
        Array<TextureAtlas.AtlasRegion> asteroidRegions = new Array<TextureAtlas.AtlasRegion>(asteroidAtlas.getRegions());
        asteroidRegions.sort(new RegionComparator());
        this.asteroidAnimation = new Animation(FRAME_DURATION, asteroidRegions,
                Animation.PlayMode.LOOP);
        this.world = world;
        createBody();
    }

    private void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        // Retrieve width and height
        TextureRegion asteroidFrame = asteroidAnimation.getKeyFrame(0);
        int asteroidWidth = asteroidFrame.getRegionWidth();
        int asteroidHeight = asteroidFrame.getRegionHeight();
        float originX = asteroidWidth * 0.5f;
        float originY = asteroidHeight * 0.5f;

        bodyDef.position.set((position.x + originX) / GameInfo.PPM,
                (position.y + originY) / GameInfo.PPM);
//        bodyDef.position.set(position.x, position.y);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((asteroidWidth  / 2f)/ GameInfo.PPM,
                ((asteroidHeight) / 2f )/ GameInfo.PPM);

        CircleShape circle = new CircleShape();
        if(this.origin != null) {
            circle.setPosition(this.origin);
        }
        circle.setRadius(32f / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.filter.categoryBits = GameInfo.ASTEROID;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("Asteroid");

        shape.dispose();

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

    public Vector2 getSize() {return size;}

    public Vector2 getOrigin() {return origin;}

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void drawAsteroid(SpriteBatch batch) {
        batch.draw(this.getSprite(), getX() + getWidth() / 2f ,
                getY() - getHeight() / 2f);
    }

    public void draw(SpriteBatch batch, float animationTime) {
        //sprite.draw(batch);
        TextureRegion asteroidFrame = asteroidAnimation.getKeyFrame(animationTime);
        int width = asteroidFrame.getRegionWidth();
        int height = asteroidFrame.getRegionHeight();
        this.size = new Vector2 (width, height);
        float originX = width * 0.5f;
        float originY = height * 0.5f;
        this.origin = new Vector2 ( originX, originY);

        batch.draw(asteroidFrame,
                1.0f - originX, 3.70f - originY,
                originX, originY,
                width, height,
                WORLD_TO_SCREEN, WORLD_TO_SCREEN,
                0.0f);

       batch.draw(asteroidAnimation.getKeyFrame(animationTime % 5), position.x, position.y);

    }
    
    private static class RegionComparator implements Comparator<TextureAtlas.AtlasRegion> {
        @Override
        public int compare(TextureAtlas.AtlasRegion region1, TextureAtlas.AtlasRegion region2) {
            return region1.name.compareTo(region2.name);
        }
    }
}
