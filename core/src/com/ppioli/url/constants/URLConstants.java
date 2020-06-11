package com.ppioli.url.constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.utils.Json;
import com.ppioli.url.utils.IRenderer;
import com.ppioli.url.utils.SpriteRenderer;

public class URLConstants {
    public static final int TILE_SIZE = 16;
    //in areas
    public static final int WORLD_SIZE = 1;
    public static final int AREA_SIZE = 64;
    public static final int OBJECTS_PER_TILE = 4;
    public static final int DEFAULT_ACTORS_CAPACITY = 64;
    public static final long MAX_TIME_DELTA = 1000 / 60;

    public static TextureRegion TEXTURE_404 = new TextureRegion( new Texture( Gdx.files.internal( "not-found.png" ) ),
            TILE_SIZE,
            TILE_SIZE );

    public static final IRenderer SPRITE_404 = new SpriteRenderer( TEXTURE_404 );

    public static RandomXS128 RNGSUS = new RandomXS128();
    public static final Json JSON_MAPPER = new Json();


    public static final int TILE_Z = 0;
    public static final int ACTOR_Z = 10;


    /** GameShit */
    public static final float BASE_MOVEMENT = 0.2f; // duration of the move animation w/o modifiers to speed
}
