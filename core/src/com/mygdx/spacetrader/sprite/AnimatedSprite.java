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

import java.util.Comparator;

/**
 * Created by Gator King on 3/14/2017.
 */

public class AnimatedSprite {


    private static final float WORLD_TO_SCREEN = 1.0f / 100.0f;
    private static final float SCENE_WIDTH = 12.80f;
    private static final float SCENE_HEIGHT = 7.20f;
    private static final float FRAME_DURATION = 1.0f / 20.0f;

    private String name;
    private Texture image;
    private Sprite sprite;
    private Vector2 position;

    private TextureAtlas atlas;
    private Animation animation;

    public AnimatedSprite() {

    }

    public AnimatedSprite(String name) {
        this.name = name;
    }

    public AnimatedSprite(AnimatedSprite animatedSprite) {
        this.name = animatedSprite.getName();
        this.image = animatedSprite.getImage();
        this.sprite = animatedSprite.getSprite();
        this.position = animatedSprite.getPosition();
        this.atlas = animatedSprite.getAtlas();
        Array<TextureAtlas.AtlasRegion> atlasRegions = new Array<TextureAtlas.AtlasRegion>(atlas.getRegions());
        atlasRegions.sort(new RegionComparator());
        this.animation = animatedSprite.getAnimation();
    }

    public AnimatedSprite(String name, Texture image, Sprite sprite, String atlasFile,
                            Vector2 position) {
        this.name = name;
        this.image = image;
        this.sprite = sprite;
        this.position = position;
        this.atlas = new TextureAtlas(Gdx.files.internal(atlasFile));
        Array<TextureAtlas.AtlasRegion> atlasRegions = new Array<TextureAtlas.AtlasRegion>(atlas.getRegions());
        atlasRegions.sort(new RegionComparator());
        this.animation = new Animation(FRAME_DURATION, atlasRegions,
                Animation.PlayMode.LOOP);
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public void setAtlas(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
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
        TextureRegion frame = animation.getKeyFrame(animationTime);
        int width = frame.getRegionWidth();
        int height = frame.getRegionHeight();
        float originX = width * 0.5f;
        float originY = height * 0.5f;

        batch.draw(frame,
                1.0f - originX, 3.70f - originY,
                originX, originY,
                width, height,
                WORLD_TO_SCREEN, WORLD_TO_SCREEN,
                0.0f);

        batch.draw(animation.getKeyFrame(animationTime % 5), position.x, 275.0f);

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
