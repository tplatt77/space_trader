package com.mygdx.spacetrader.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.spacetrader.GameMain;
import com.mygdx.spacetrader.asteroid.Asteroid;
import com.mygdx.spacetrader.helpers.GameInfo;
import com.mygdx.spacetrader.helpers.GameManager;
import com.mygdx.spacetrader.missile.Missile;
import com.mygdx.spacetrader.player.Player;

/**
 * Created by Gator King on 6/4/2017.
 */

public class Gameplay implements Screen, ContactListener {

    private GameMain game;

    private OrthographicCamera mainCamera;
    private Viewport gameViewport;

    private OrthographicCamera box2DCamera;
    private Box2DDebugRenderer debugRenderer;

    private World world;

    private Sprite[] bgs;
    private float lastYPosition;

    private float cameraSpeed = 10;
    private float maxSpeed = 10;
    private float acceleration = 10;

    private boolean touchedForTheFirstTime;

    private Player player;
    private float lastPlayerY;

    private Missile missile;
    private boolean missileFired = false;

    private Asteroid asteroid;

    private Sound coinSound, lifeSound;

    public Gameplay(GameMain game) {
        this.game = game;

        mainCamera = new OrthographicCamera(GameInfo.WIDTH, GameInfo.HEIGHT);
        mainCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2, 0);

        gameViewport = new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT,
                mainCamera);

        box2DCamera = new OrthographicCamera();
        box2DCamera.setToOrtho(false, GameInfo.WIDTH / GameInfo.PPM,
                GameInfo.HEIGHT / GameInfo.PPM);
        box2DCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);

        debugRenderer = new Box2DDebugRenderer();
        world = new World(new Vector2(0, 0), true);
        // inform the world that the contanct listener is the gameplay class
        world.setContactListener(this);

        //cloudsController = new CloudsController(world);

        //player = cloudsController.positionThePlayer(player);
        player = new Player(world, GameInfo.WIDTH /2.0f, GameInfo.HEIGHT / 50.0f);

        // Create the missile
        Texture missileTexture = new Texture("missile.png");
        Sprite missileSprite = new Sprite(missileTexture);
        missile = new Missile("Le Missile", 15.0, 5.0, missileTexture, missileSprite, new Vector2 (-1, -1),
            0.0);

        Texture asteroidTexture = new Texture("asteroid.png");
        Sprite asteroidSprite = new Sprite(asteroidTexture);
        asteroid = new Asteroid("AX7-One", 500.0 , 0.0 , asteroidTexture, asteroidSprite,
                new Vector2(300, 100), world);

        createBackgrounds();

        setCameraSpeed();

        //coinSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/Coin Sound.wav"));
        //lifeSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/Life Sound.wav"));

    }

    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();

        drawBackgrounds();

        //cloudsController.drawClouds(game.getBatch());
        //cloudsController.drawCollectables(game.getBatch());

        if(missileFired) {
            missile.draw(game.getBatch());
        }

        asteroid.draw(game.getBatch(), 1.0f);

        player.draw(game.getBatch());


        game.getBatch().end();

//        debugRenderer.render(world, box2DCamera.combined);

       // game.getBatch().setProjectionMatrix(hud.getStage().getCamera().combined);
        //hud.getStage().draw();
        //hud.getStage().act();

        game.getBatch().setProjectionMatrix(mainCamera.combined);
        mainCamera.update();

        player.updatePlayer();

        world.step(Gdx.graphics.getDeltaTime(), 6, 2);


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
    void setCameraSpeed() {

        if(GameManager.getInstance().gameData.isEasyDifficulty()) {
            cameraSpeed = 80;
            maxSpeed = 100;
        }

        if(GameManager.getInstance().gameData.isMediumDifficulty()) {
            cameraSpeed = 100;
            maxSpeed = 120;
        }

        if(GameManager.getInstance().gameData.isHardDifficulty()) {
            cameraSpeed = 120;
            maxSpeed = 140;
        }

    }

    void createBackgrounds() {
        bgs = new Sprite[3];

        for (int i = 0; i < bgs.length; i++) {
            bgs[i] = new Sprite(new Texture("galaxyBackground.tga"));
            bgs[i].setPosition(0, -(i * bgs[i].getHeight()));
            lastYPosition = Math.abs(bgs[i].getY());
        }
    }

    void drawBackgrounds() {
        for (int i = 0; i < bgs.length; i++) {
            game.getBatch().draw(bgs[i], bgs[i].getX(), bgs[i].getY());
        }
    }

    void handleInput(float dt) {
        float x = 0f;
        float y = 0f;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            //player.movePlayer(-2f, 0f);
            x = -2f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            //player.movePlayer(2f, 0f);
            x = 2f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            //player.movePlayer(0f, 2f);
            y = 2f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            //player.movePlayer(0f, -2f);
            y = -2f;
        } if (!Gdx.input.isKeyPressed(Input.Keys.LEFT ) &&
                !Gdx.input.isKeyPressed(Input.Keys.RIGHT ) &&
                !Gdx.input.isKeyPressed(Input.Keys.UP ) &&
                !Gdx.input.isKeyPressed(Input.Keys.DOWN )) {
            player.setWalking(false);
            //player.movePlayer(0f, 0f);
        }

        // Move the player
        player.movePlayer(x, y);

        // Missile firing

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            missileFired = true;
            missile.setPosition(new Vector2 (player.getX(), player.getY()));
        }
    }

    void moveCamera(float delta) {
        mainCamera.position.y -= cameraSpeed * delta;

        cameraSpeed += acceleration * delta;

        if(cameraSpeed > maxSpeed) {
            cameraSpeed = maxSpeed;
        }

    }

    void update(float dt) {

        checkForFirstTouch();

        if(!GameManager.getInstance().isPaused) {
            handleInput(dt);
//            handleInputAndroid();
            //moveCamera(dt);
            checkBackgroundsOutOfBounds();
//            cloudsController.setCameraY(mainCamera.position.y);
//            cloudsController.createAndArrangeNewClouds();
//            cloudsController.removeOffScreenCollectables();
            checkPlayersBounds();
//            countScore();
        }
    }

    void checkBackgroundsOutOfBounds() {
        for(int i = 0; i < bgs.length; i++) {
            if((bgs[i].getY() - bgs[i].getHeight() / 2f - 5) > mainCamera.position.y) {
                float newPosition = bgs[i].getHeight() + lastYPosition;
                bgs[i].setPosition(0, -newPosition);
                lastYPosition = Math.abs(newPosition);
            }
        }
    }

    void checkPlayersBounds() {

        if(player.getY() - GameInfo.HEIGHT / 2f - player.getHeight() / 2f
                > mainCamera.position.y) {
            if(!player.isDead()) {
                playedDied();
            }
        }

        if(player.getY() + GameInfo.HEIGHT / 2f + player.getHeight() / 2f
                < mainCamera.position.y) {
            if(!player.isDead()) {
                playedDied();
            }
        }

        if(player.getX() - 25 > GameInfo.WIDTH || player.getX() + 70 < 0) {
            if(!player.isDead()) {
                playedDied();
            }
        }

    }

    void playedDied() {

        GameManager.getInstance().isPaused = true;

        // decrement life count
        //hud.decrementLife();

        player.setDead(true);

        player.setPosition(1000, 1000);

        if(GameManager.getInstance().lifeScore < 0) {
            // player has no more lifes left to continue the game

            // check if we have a new highscore
            GameManager.getInstance().checkForNewHighscores();

            // show the end score to the user
            //hud.createGameOverPanel();

            // load main menu
//            RunnableAction run = new RunnableAction();
//            run.setRunnable(new Runnable() {
//                @Override
//                public void run() {
//                    game.setScreen(new MainMenu(game));
//                }
//            });

            SequenceAction sa = new SequenceAction();
            sa.addAction(Actions.delay(3f));
            sa.addAction(Actions.fadeOut(1f));
           // sa.addAction(run);

           //hud.getStage().addAction(sa);

        } else {

            // reload the game so that the player can continue to play
            RunnableAction run = new RunnableAction();
            run.setRunnable(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(new Gameplay(game));
                }
            });

            SequenceAction sa = new SequenceAction();
            sa.addAction(Actions.delay(3f));
            sa.addAction(Actions.fadeOut(1f));
            sa.addAction(run);

           // hud.getStage().addAction(sa);

        }


    }

    void checkForFirstTouch() {
        if(!touchedForTheFirstTime) {
            if(Gdx.input.justTouched()) {
                touchedForTheFirstTime = true;
                GameManager.getInstance().isPaused = false;
                lastPlayerY = player.getY();
            }
        }
    }
}
