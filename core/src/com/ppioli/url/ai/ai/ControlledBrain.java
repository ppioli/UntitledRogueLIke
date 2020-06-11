package com.ppioli.url.ai.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.ppioli.url.Game;
import com.ppioli.url.actors.actions.Action;
import com.ppioli.url.actors.actions.IAction;
import com.ppioli.url.actors.actions.MoveAction;
import com.ppioli.url.ai.PathFinder;
import com.ppioli.url.input.InputAdapter;
import com.ppioli.url.input.command.Command;
import com.ppioli.url.utils.geom.Direction;

import java.util.Optional;


public class ControlledBrain implements Brain {
    private Command currentCommand;
    private Entity entity;

    public ControlledBrain( Entity entity ) {
        this.entity = entity;
    }

    @Override
    public Optional<IAction> getAction() {
        if( currentCommand == null || currentCommand.isCompleted() ) {
            Optional<Command> command = Game.adapter.getCommandAndClear();
            if( command.isPresent() ) {
                currentCommand = command.get();
                currentCommand.setSource( entity );
            } else {
                return Optional.empty();
            }
        }
        return Optional.of( currentCommand.getNextAction() );
    }

    @Override
    public void onInterrupt() {

    }
}
