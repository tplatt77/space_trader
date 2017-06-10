package com.mygdx.spacetrader;

import java.util.Comparator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
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
import com.mygdx.spacetrader.helpers.GameManager;
import com.mygdx.spacetrader.planets.Planet;
import com.mygdx.spacetrader.scene.Gameplay;
import com.mygdx.spacetrader.sprite.StationarySprite;
import com.mygdx.spacetrader.vehicle.Vehicle;
import com.mygdx.spacetrader.missile.Missile;

import java.util.ArrayList;

public class GameMain extends Game {

    private SpriteBatch batch;

    @Override
    public void create () {
        batch = new SpriteBatch();
        GameManager.getInstance().initializeGameData();
        setScreen(new Gameplay(this));
    }

    @Override
    public void render () {
        super.render();
    }

    public SpriteBatch getBatch() {
        return this.batch;
    }}
