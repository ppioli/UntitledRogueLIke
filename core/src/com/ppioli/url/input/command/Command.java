package com.ppioli.url.input.command;

import com.badlogic.ashley.core.Entity;
import com.ppioli.url.actors.actions.IAction;

public interface Command {
    IAction getNextAction();
    boolean isApplicable();
    boolean isCompleted();
    void setSource( Entity source );
}
