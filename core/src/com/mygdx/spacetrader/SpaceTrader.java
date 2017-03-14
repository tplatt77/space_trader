package com.mygdx.spacetrader;

import java.util.Comparator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.spacetrader.asteroid.Asteroid;
import com.mygdx.spacetrader.vehicle.Vehicle;
import com.mygdx.spacetrader.missile.Missile;

import java.util.ArrayList;

public class SpaceTrader extends Game {


    private static final float WORLD_TO_SCREEN = 1.0f / 100.0f;
    private static final float SCENE_WIDTH = 12.80f;
    private static final float SCENE_HEIGHT = 7.20f;
    private static final float FRAME_DURATION = 1.0f / 30.0f;

	private SpriteBatch batch;
    private Texture img;
    private Texture enemyImage;
    private Sprite sprite;
    private Sprite enemySprite;
    private ExtendViewport viewport;
    private Vector2 position;
    private Vector2 enemyPosition;
    private Vector2 velocity;
    private Vehicle playerVehicle;
    private Vehicle enemyVehicle;
    private float playerRotation;

    //Missile data to be extracted for its own class in the future
    private Texture missileImage;
    private Sprite missileSprite;
    private Vector2 missilePosition;
    private boolean missileFired;
    private float missileRotation;
    private ArrayList<Missile> missiles;

    private float animationTime;

    // Practice with animated sprites
    private TextureAtlas scottPilgrimAtlas;
    private Animation scottPilgrimWalk;

    // Asteroid objects
    private Texture asteroidImage;
    private Sprite asteroidSprite;
    private Vector2 asteroidPosition;
    private float asteroidRotation;
    private ArrayList<Asteroid> asteroids;


	@Override
	public void create () {

        animationTime = 0.0f;
        missiles = new ArrayList<Missile>();
        asteroids = new ArrayList<Asteroid>();

		batch = new SpriteBatch();
		img = new Texture("spaceship.png");
        enemyImage = new Texture("enemy.png");

		playerRotation = 0.0f;
        missileRotation = 0;

		//viewport = new ExtendViewport(100, 100);
		sprite = new Sprite(img);
        enemySprite = new Sprite(enemyImage);
        //enemySprite.setColor(1.0f, 0.5f, 0.5f, 0.65f);
		position = new Vector2(10,10);
        enemyPosition = new Vector2(100, 100);
        playerVehicle = new Vehicle("Player", 100.0, 20.0, img, sprite, position);
        enemyVehicle = new Vehicle("Enemy", 100.0, 20.0, enemyImage, enemySprite, enemyPosition);

        // Missile stuff
        missileImage = new Texture("missile.png");
        missilePosition = new Vector2(10, 10);
        missileSprite = new Sprite(missileImage);
        missileFired = false;

        // Asteroid stuff
        asteroidImage = new Texture("asteroid.png");
        asteroidPosition = new Vector2(10, 10);
        asteroidSprite = new Sprite(asteroidImage);
        asteroids.add(new Asteroid("asteroid", 10.0, 10.0, asteroidImage, asteroidSprite,
                asteroidPosition));

        // Animated sprite practice
        scottPilgrimAtlas = new TextureAtlas(Gdx.files.internal("ScottPilgrim.atlas"));
        Array<AtlasRegion> scottPilgrimRegions = new Array<AtlasRegion>(scottPilgrimAtlas.getRegions());
        scottPilgrimRegions.sort(new RegionComparator());
        scottPilgrimWalk = new Animation(FRAME_DURATION, scottPilgrimRegions, PlayMode.LOOP);

        animationTime = 0.0f;
	}



	@Override
	public void render () {
		Gdx.gl.glClearColor(0.529f, 0.808f, 0.980f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update animationTime
        animationTime += Gdx.graphics.getDeltaTime();

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && position.x > 0)
		{
			position.x -= 5.0f;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && position.x < Gdx.graphics.getWidth() - sprite.getWidth())
		{
			position.x += 5.0f;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.UP) && position.y < Gdx.graphics.getHeight() - sprite.getHeight())
		{
			position.y += 5.0f;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && position.y > 0)
		{
			position.y -= 5.0f;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
		{
			sprite.rotate(5.0f);
			playerRotation += 5.0f;
		}

        if(Gdx.input.isKeyPressed(Input.Keys.ENTER))
        {
            float previousAnimationTime = animationTime;

            missilePosition.x = position.x;
            missilePosition.y = position.y;
            missileFired = !missileFired;
            missileSprite.setPosition(missilePosition.x, missilePosition.y);
            missileRotation = playerRotation;
            missileSprite.setRotation(missileRotation);
            if(animationTime >= 0.9f) {
                missiles.add(new Missile("Missile", 100.0, 10.0, missileImage, missileSprite, missilePosition, missileRotation));
            }
        }

        playerVehicle.setSpriteToPosition(position.x, position.y);

        batch.begin();
		//batch.draw(img, position.x, position.y);
		//sprite.setPosition(position.x, position.y);
		//sprite.setScale(0.5f);
		//sprite.draw(batch);

        //enemyVehicle.setSpriteToPosition(10.0f, 10.0f);
        //enemyVehicle.draw(batch);
        if(!missiles.isEmpty()) {
            for(Missile missile: missiles) {
                Vector2 thisMissilePosition = missile.getPosition();
                double missileSpriteWidth = missile.getSprite().getWidth();
                double missileSpriteHeight = missile.getSprite().getHeight();
                // "Collision detection"
                if(thisMissilePosition.x >= Gdx.graphics.getWidth() + missileSpriteWidth/2 ||
                        thisMissilePosition.x + missileSpriteWidth/2 <= 0) {
                    missiles.remove(missile);
                    break;
                }
                else if(thisMissilePosition.y >= Gdx.graphics.getHeight() + missileSpriteHeight/2 ||
                        thisMissilePosition.y  + missileSpriteHeight/2 <= 0) {
                    missiles.remove(missile);
                    break;
                }
                missile.draw(batch);
            }
        }
        playerVehicle.draw(batch);

        TextureRegion scottPilgrimFrame = scottPilgrimWalk.getKeyFrame(animationTime);
        int width = scottPilgrimFrame.getRegionWidth();
        int height = scottPilgrimFrame.getRegionHeight();
        float originX = width * 0.5f;
        float originY = height * 0.5f;

        batch.draw(scottPilgrimFrame,
                1.0f - originX, 3.70f - originY,
                originX, originY,
                width, height,
                WORLD_TO_SCREEN, WORLD_TO_SCREEN,
                0.0f);

        batch.draw(scottPilgrimWalk.getKeyFrame(animationTime % 1000), position.x, 275.0f);

        //asteroidSprite.draw(batch);
        for (Asteroid asteroid: asteroids) {
            asteroid.draw(batch, animationTime);
        }

        batch.end();
	}

	@Override
	public void dispose()
	{
		batch.dispose();
		img.dispose();
        scottPilgrimAtlas.dispose();
	}

    private static class RegionComparator implements Comparator<AtlasRegion> {
        @Override
        public int compare(AtlasRegion region1, AtlasRegion region2) {
            return region1.name.compareTo(region2.name);
        }
    }
}
