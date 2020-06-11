package com.ppioli.extensions.utils;


import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;
import sun.security.util.PendingException;

import java.util.Comparator;

/**
 * A simple EntitySystem that processes each entity of a given family in the order specified by a comparator and calls
 * processEntity() for each entity every time the EntitySystem is updated. This is really just a convenience class as rendering
 * systems tend to iterate over a list of entities in a sorted manner. Adding entities will cause the entity list to be resorted.
 * Call forceSort() if you changed your sorting criteria.
 * @author Santo Pfingsten
 */
public abstract class SortedIteratingSystem extends EntitySystem implements EntityListener {
    private Family family;
    private Array<Entity> sortedEntities;
    private final ImmutableArray<Entity> entities;
    private boolean shouldSort;
    private Comparator<Entity> comparator;

    /**
     * Instantiates a system that will iterate over the entities described by the Family.
     *
     * @param family     The family of entities iterated over in this System
     * @param comparator The comparator to sort the entities
     */
    public SortedIteratingSystem( Family family, Comparator<Entity> comparator ) {
        this( family, comparator, 0 );
    }

    /**
     * Instantiates a system that will iterate over the entities described by the Family, with a specific priority.
     *
     * @param family     The family of entities iterated over in this System
     * @param comparator The comparator to sort the entities
     * @param priority   The priority to execute this system with (lower means higher priority)
     */
    public SortedIteratingSystem( Family family, Comparator<Entity> comparator, int priority ) {
        super( priority );

        this.family = family;
        sortedEntities = new Array<>( false, 16 );
        entities = new ImmutableArray<>( sortedEntities );
        this.comparator = comparator;
    }

    /**
     * Call this if the sorting criteria have changed. The actual sorting will be delayed until the entities are processed.
     */
    public void forceSort() {
        shouldSort = true;
    }

    private void sort() {
        if ( shouldSort ) {
            sortedEntities.sort( comparator );
            shouldSort = false;
        }
    }

    @Override
    public void addedToEngine( Engine engine ) {
        ImmutableArray<Entity> newEntities = engine.getEntitiesFor( family );
        sortedEntities.clear();
        if ( newEntities.size() > 0 ) {
            for ( int i = 0; i < newEntities.size(); ++i ) {
                sortedEntities.add( newEntities.get( i ) );
            }
            sortedEntities.sort( comparator );
        }
        shouldSort = false;
        engine.addEntityListener( family, this );
    }

    @Override
    public void removedFromEngine( Engine engine ) {
        engine.removeEntityListener( this );
        sortedEntities.clear();
        shouldSort = false;
    }

    @Override
    public void entityAdded( Entity entity ) {
        sortedEntities.add( entity );
        shouldSort = true;
    }

    @Override
    public void entityRemoved( Entity entity ) {
        sortedEntities.removeValue( entity, true );
        shouldSort = true;
    }


    /**
     * @return set of entities processed by the system
     */
    public ImmutableArray<Entity> getEntities() {
        sort();
        return entities;
    }

    /**
     * @return the Family used when the system was created
     */
    public Family getFamily() {
        return family;
    }
}