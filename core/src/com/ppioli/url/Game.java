package com.ppioli.url;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ppioli.url.actors.ActorSystem;
import com.ppioli.url.ai.Board;
import com.ppioli.url.ai.PathFinder;
import com.ppioli.url.input.InputAdapter;
import com.ppioli.url.input.InputManager;
import com.ppioli.url.level.World;
import com.ppioli.url.entitiesFactories.EntitiesFactory;
import com.ppioli.url.renderer.RenderSystem;

public class Game extends ApplicationAdapter {

	public static EntitiesFactory factories;
	public static Board world;
	public static InputManager adapter;
	public static PooledEngine engine;
	public static PathFinder pathFinder;
	public static boolean debug = true;
    private OrthographicCamera camera;

	private Viewport viewport;

	private float gameSpeed = 0.01f;


	@Override
	public void create () {
		engine = new PooledEngine();

		factories = new EntitiesFactory( engine );

		Gdx.app.setLogLevel( Application.LOG_DEBUG );
		camera = new OrthographicCamera();
		camera.zoom = 0.2f;
		viewport = new ScreenViewport( camera );

		engine.addSystem( new RenderSystem( camera ) );
		engine.addSystem( new ActorSystem() );

		world = new World( engine );
		world.initialize();

		pathFinder = new PathFinder( world );
		adapter = new InputManager( camera );
		Gdx.input.setInputProcessor( adapter );
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		engine.update( Gdx.graphics.getDeltaTime() );
	}
	
	@Override
	public void dispose () {

	}

	@Override
	public void resize( int width, int height ) {
		this.viewport.update( width, height );
	}
}
