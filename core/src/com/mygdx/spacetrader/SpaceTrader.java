package com.mygdx.spacetrader;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.spacetrader.vehicle.Vehicle;
import com.mygdx.spacetrader.missile.Missile;

import java.util.ArrayList;

public class SpaceTrader extends Game {
	SpriteBatch batch;
	Texture img;
    Texture enemyImage;
	Sprite sprite;
    Sprite enemySprite;
	ExtendViewport viewport;
	Vector2 position;
    Vector2 enemyPosition;
	Vector2 velocity;
	Vehicle playerVehicle;
    Vehicle enemyVehicle;
	float playerRotation;

    //Missile data to be extracted for its own class in the future
    Texture missileImage;
    Sprite missileSprite;
    Vector2 missilePosition;
    boolean missileFired;
    float missileRotation;
    ArrayList<Missile> missiles;

    float animationTime;

	@Override
	public void create () {
        animationTime = 0.0f;
        missiles = new ArrayList<Missile>();

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
	}



	@Override
	public void render () {
		Gdx.gl.glClearColor(0.529f, 0.808f, 0.980f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
            animationTime += Gdx.graphics.getDeltaTime();


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
		batch.end();
	}

	@Override
	public void dispose()
	{
		batch.dispose();
		img.dispose();
	}
}
