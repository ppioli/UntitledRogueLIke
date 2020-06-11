package com.ppioli.url.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class SpriteRenderer implements IRenderer {
    private final Sprite sprite;

    public SpriteRenderer( Sprite sprite ) {
        this.sprite = sprite;
    }

    public SpriteRenderer( TextureRegion region ){
        this.sprite = new Sprite( region );
    }


    @Override
    public void render( SpriteBatch batch, Vector2 position ) {
        sprite.setPosition( position.x, position.y );
        sprite.draw( batch );
    }


}
