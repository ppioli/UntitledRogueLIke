package com.ppioli.url.input.command;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.ppioli.url.Game;
import com.ppioli.url.actors.actions.FuckedUpAction;
import com.ppioli.url.actors.actions.IAction;
import com.ppioli.url.actors.actions.MoveAction;
import com.ppioli.url.components.PositionComponent;
import com.ppioli.url.utils.geom.Direction;
import com.ppioli.url.utils.geom.IntVect;

import java.util.LinkedList;
import java.util.Queue;

import static com.ppioli.url.constants.URLTypes.*;
import static com.ppioli.url.constants.URLComponentMappers.*;

public class MoveToCommand implements Command {


    private Queue<Direction> path;
    private Entity entity;
    private PositionComponent from;
    private PositionComponent to;
    private int distance;

    public MoveToCommand( Entity to, int distance ) {
        this.to = POS_CM.get( to );
        this.distance = distance;
    }

    @Override
    public IAction getNextAction() {
        updatePath();
        if ( path.size() == 0 ) {
            Gdx.app.error( MoveToCommand.class.getCanonicalName(),
                    "Warning - Called getNextAction() on an empty command. This should not happen" );
            return new FuckedUpAction( this.entity );
        }
        return new MoveAction( entity, path.poll() );
    }

    @Override
    public boolean isApplicable() {
        if( entity == null ) return false;
        if ( MOBIL.matches( entity ) ) {
            updatePath();
            return path != null;
        } else {
            return false;
        }
    }

    @Override
    public boolean isCompleted() {
        return from.distance( to ) < distance || path.isEmpty();
    }

    private void updatePath( ) {
        path = new LinkedList<>();
        path = Game.pathFinder.route( new IntVect( from.getX(), from.getY() ), new IntVect( to.getX(), to.getY() ) );
    }

    public void setSource( Entity entity ){
        this.entity = entity;
        this.from = POS_CM.get( entity );
    }
}
