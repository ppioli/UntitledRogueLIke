package com.ppioli.url.actors;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.ppioli.extensions.utils.SortedIteratingSystem;
import com.ppioli.url.actors.actions.ActionResult;
import com.ppioli.url.actors.actions.IAction;
import com.ppioli.url.components.VisualComponent;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class ActorSystem extends EntitySystem implements EntityListener {
    private final InitiativeComparator comparator = new InitiativeComparator();
    private final Family family;

    private LinkedList<Entity> actors;
    private Entity currentActor;
    private IAction currentAction;
    private State state;

    private ComponentMapper<ActorComponent> am = ComponentMapper.getFor( ActorComponent.class );

    private enum State {
        ACTION,
        TRANSITIONING,
        TURN_BASED
    }

    public ActorSystem() {
        this.family = Family.all( ActorComponent.class ).get();
        actors = new LinkedList<>();
    }


    @Override
    public void update( float deltaTime ) {
        // if there is no actors, return.

        // check that we have a current actor
        if( currentActor == null ) {
            if ( actors.size() == 0 ) return;
            currentActor = getNextActor();
        }
        // check that we have an action
        if( currentAction == null ) {
            // try get an action
            ActorComponent actor = am.get( currentActor );
            Optional<IAction> newAction = actor.getAction();
            // if we dont get one, wait till next round
            if( !newAction.isPresent() ) return;
            // if we get one, check that its actually applicable
            if( actor.currentAp() < newAction.get().getCost() || !newAction.get().applicable() ) {
                currentActor = null;
                return;
            }
            // we finally have an action;
            currentAction = newAction.get();
            actor.addAP( -currentAction.getCost() );
        }
        // execute the action
        float remainingTime = deltaTime;
        if( currentAction.execute( remainingTime ) > 0 ) {
            // if the action is completed, clear it.
            Gdx.app.debug( ActorSystem.class.getCanonicalName(), "Actor action completed" );

            currentAction = null;
            // also check if the current actor completed its turn, and clear it if necessary

        }

        if ( am.get( currentActor ).turnFinished() ) {
            currentActor = null;
            Gdx.app.debug( ActorSystem.class.getCanonicalName(), "Actor turn completed" );
        }

    }

    @Override
    public void addedToEngine( Engine engine ) {
        ImmutableArray<Entity> newEntities = engine.getEntitiesFor( family );
        actors.clear();
        if ( newEntities.size() > 0 ) {
            for ( int i = 0; i < newEntities.size(); ++i ) {
                actors.add( newEntities.get( i ));
            }
            actors.sort( comparator );
        }
        engine.addEntityListener( family, this );
    }

    @Override
    public void removedFromEngine( Engine engine ) {
        engine.removeEntityListener( this );
        actors.clear();
    }

    @Override
    public void entityAdded( Entity entity ) {
        actors.addLast( entity );
    }

    @Override
    public void entityRemoved( Entity entity ) {
        if( currentActor == entity ) currentActor = null;
        actors.remove( entity );
    }

    private Entity getNextActor() {
        Entity actor = actors.poll();
        actors.addLast( actor );
        am.get( actor ).startTurn();
        return actor;
    }


    private static class InitiativeComparator implements Comparator<Entity> {

        private ComponentMapper<ActorComponent> am = ComponentMapper.getFor( ActorComponent.class );

        @Override
        public int compare( Entity o1, Entity o2 ) {
            return am.get( o1 ).getInitiative() - am.get(o2).getInitiative();
        }
    }
}


