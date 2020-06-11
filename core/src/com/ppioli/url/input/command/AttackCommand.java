package com.ppioli.url.input.command;

import com.badlogic.ashley.core.Entity;
import com.ppioli.url.actors.actions.IAction;

import static com.ppioli.url.constants.URLTypes.*;
import static com.ppioli.url.constants.URLComponentMappers.*;
import static com.ppioli.url.constants.URLConstants.*;

public class AttackCommand implements Command {
    public MoveToCommand command;
    public boolean completed;
    private Entity source;
    private Entity target;

    public AttackCommand( Entity target ) {
        this.target = target;
        command = new MoveToCommand( target, 1 );
    }

    @Override
    public IAction getNextAction() {
        if( this.command.isCompleted() ) {
            this.completed = true;
            return new AttackAction( source, target );
        }
        return this.command.getNextAction();
    }

    @Override
    public boolean isApplicable() {
        return command.isApplicable() && CORPOREAL.matches( target );
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    public void setSource( Entity source ) {
        this.source = source;
        this.command.setSource( source );
    }
}
