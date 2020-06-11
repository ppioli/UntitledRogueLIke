package com.ppioli.url.player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.ppioli.url.Game;
import com.ppioli.url.actors.actions.IAction;
import com.ppioli.url.ai.ai.Brain;
import com.ppioli.url.input.command.Command;

import java.util.Optional;

public class PlayerBrain implements Brain {

    private Command currentCommand;
    private final Entity entity;

    public PlayerBrain( Entity entity ) {
        this.entity = entity;
    }

    @Override
    public Optional<IAction> getAction() {
//        Gdx.app.log( PlayerBrain.class.getCanonicalName(), "Player get next action" );
        Game.adapter.getCommandAndClear().ifPresent( command -> currentCommand = command.isApplicable() ? command : null );

        if( currentCommand != null && !currentCommand.isCompleted() ){
            return Optional.of( currentCommand.getNextAction() );
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void onInterrupt() {
        Gdx.app.log( PlayerBrain.class.getCanonicalName(), "Player interrupted" );
        currentCommand = null;
    }
}
