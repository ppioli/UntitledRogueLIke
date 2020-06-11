package com.ppioli.url.actors;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;
import com.ppioli.url.actors.actions.IAction;
import com.ppioli.url.ai.ai.Brain;
import com.ppioli.url.utils.exception.URLException;

import java.util.Optional;

public class ActorComponent implements Component, Pool.Poolable {
    //state
    private Brain brain;
    private int maxAP;
    private int APGain;
    //control
    private int initiative;
    private int currentAP;
    private boolean skipTurn = true;

    public ActorComponent set( Brain brain, int initiative, int APGain, int maxAP ) {
        this.brain = brain;
        this.initiative = initiative;
        this.APGain = APGain;
        this.maxAP = maxAP;
        return this;
    }

    public Optional<IAction> getAction() {
        return brain.getAction();
    }

    public boolean turnFinished() {
        return this.currentAP == 0 || skipTurn;
    }

    public void startTurn() {
        addAP( APGain );
        this.skipTurn = false;
    }

    public void addAP( int gain ) {
        this.currentAP = Math.min( currentAP + gain, maxAP );
    }

    @Override
    public void reset() {
        this.brain = null;
    }

    public int getInitiative() {
        return initiative;
    }

    public void onInterrupt() {
        this.brain.onInterrupt();
    }

    public int currentAp() {
        return currentAP;
    }
}
