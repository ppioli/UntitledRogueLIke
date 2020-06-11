package com.ppioli.url.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.ppioli.url.Game;
import com.ppioli.url.input.command.AttackCommand;
import com.ppioli.url.input.command.Command;
import com.ppioli.url.input.command.MoveToCommand;
import com.ppioli.url.level.Cell;

import java.util.Optional;

import static com.ppioli.url.constants.URLConstants.TILE_SIZE;

public class InputManager implements InputProcessor, InputAdapter {

    private final Camera camera;
    private final Vector3 touchPoint = new Vector3( 0, 0, 0 );
    boolean dragging;

    private Command lastCommand;


    public InputManager( Camera camera ) {
        this.camera = camera;
        this.dragging = false;
    }


    @Override
    public boolean mouseMoved( int screenX, int screenY ) {
        // we can also handle mouse movement without anything pressed
//		camera.unproject(tp.set(screenX, screenY, 0));
        return false;
    }

    @Override
    public boolean touchDown( int screenX, int screenY, int pointer, int button ) {
        // ignore if its not left mouse button or first touch pointer
        Gdx.app.log( InputManager.class.getCanonicalName(), "Down" );
        this.touchPoint.x = screenX;
        this.touchPoint.y = screenY;
        return true;
    }

    @Override
    public boolean touchDragged( int screenX, int screenY, int pointer ) {
        Gdx.app.log( InputManager.class.getCanonicalName(), "Dragged" );
        this.camera.position.x += this.touchPoint.x - screenX;
        this.camera.position.y -= ( this.touchPoint.y - screenY );
        this.touchPoint.y = screenY;
        this.touchPoint.x = screenX;
        this.dragging = true;
        return true;
    }

    @Override
    public boolean touchUp( int screenX, int screenY, int pointer, int button ) {
        Gdx.app.log( InputManager.class.getCanonicalName(), "Up" );
        if ( !dragging && button == Input.Buttons.LEFT || pointer > 0 ) {
            Gdx.app.log( InputManager.class.getCanonicalName(), "Click" );
            Vector3 p = camera.unproject( touchPoint.set( screenX, screenY, 0 ) );
            Game.world.get( ( int ) ( p.x / TILE_SIZE ), ( int ) ( p.y / TILE_SIZE ) ).ifPresent( this::handleTouch );
            return true;
        }
        dragging = false;
        return false;
    } // disposable stuff must be disposed

    @Override
    public boolean keyDown( int keycode ) {
        Gdx.app.log( InputManager.class.getCanonicalName(), "Key Pressed " + keycode );
        return false;
    }

    @Override
    public boolean keyUp( int keycode ) {
        return false;
    }

    @Override
    public boolean keyTyped( char character ) {
        return false;
    }

    @Override
    public boolean scrolled( int amount ) {
        return false;
    }

    @Override
    public Optional<Command> getCommandAndClear() {
        Optional<Command> ret;
        if ( lastCommand != null ) {
            ret = Optional.of( lastCommand );
        } else {
            ret = Optional.empty();
        }
        lastCommand = null;
        return ret;
    }

    public void handleTouch( Cell cell ) {
        if( cell.isAttackable() ) {
            lastCommand =  new AttackCommand( cell.getEntities().first() );
        } else if ( !cell.isBlocked() ) {
            lastCommand = new MoveToCommand( cell.getEntities().first(), 0 );
        } else {
            lastCommand = null;
        }

    }
}

