package com.ppioli.url.level.levelGeneration.scenes;

import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.ppioli.url.level.levelGeneration.layoutBuilder.IRestriction;
import com.ppioli.url.level.levelGeneration.layoutBuilder.RestrictionHelper;
import com.ppioli.url.constants.URLConstants;
import com.ppioli.url.utils.TreeHelpers;
import com.ppioli.url.utils.exception.NotImplementedException;
import com.ppioli.url.utils.geom.IntArea;
import com.ppioli.url.utils.geom.IntVect;
import com.ppioli.url.utils.exception.URLException;

import java.util.*;
import java.util.stream.Collectors;

public class SceneBuilder {

    private SceneNode root;

    public SceneBuilder( IntArea area, RandomXS128 rng ) {
        this.root = new SceneNode( area, null  );
        this.root.splitVertically( this.root.getContent().getArea().getMiddlePoint().x );

        buidConnections( root, rng );
    }

    private void buidConnections( SceneNode root, RandomXS128 random ) {
        TreeHelpers.iterateLeafs( root, ( node ) -> {
            node.connections.forEach( c -> c.materializeConnection( random ) );
        } );
    }

    public SceneNode getRoot() {
        return root;
    }


    public class SceneNode extends TreeNode<SceneData> {

        public SceneNode( IntArea area, SceneNode parent ) {
            super( new SceneData( area ), parent );
        }

        public SceneNode( IntVect startPoint, IntVect endPoint, SceneNode parent ) {
            super( new SceneData( new IntArea(startPoint, endPoint) ), parent );
        }

        public void splitVertically( Integer... cuts ) {
            if( cuts.length < 1  ) {
                throw new URLException( "You must provide at least one value");
            }
            LinkedList<Integer> cutPoints = new LinkedList<>();
            Collections.addAll( cutPoints, cuts );
            // we add the current area start and end points to simplify the loop
            cutPoints.add( this.getContent().area.startPosition.x );
            cutPoints.add( this.getContent().area.endPosition.x - 1 );

            cutPoints.sort( Integer::compareTo );
            SceneNode lastNode = null;
            SceneNode newNode;
            for ( int i = 0; i < cutPoints.size() - 1 ; i++ ) {
                // starting point
                IntVect fromPoint = new IntVect( cutPoints.get( i ), content.area.startPosition.y );
                // final point  - we add one to create an overlap area between both nodes.
                IntVect toPoint = new IntVect( cutPoints.get( i + 1) + 1, content.area.endPosition.y );

                if( toPoint.x - fromPoint.x < 5  ) {
                    throw new URLException( "The resulting room is to small. Width must be greater than 4 ( Provided width = %d )",  toPoint.x - fromPoint.x );
                }

                newNode = new SceneNode( fromPoint, toPoint, this );

                if( lastNode != null ) {
                    IntArea intersection = lastNode.getContent().area.intersection( newNode.getContent().area).orElseThrow( () -> new URLException( "Invalid room creation. No intersection area." ) ) ;
                    Connection connection = new Connection(newNode, lastNode, intersection );
                    lastNode.getContent().connections.add( connection );
                    newNode.getContent().connections.add( connection );
                }
                this.children.add( newNode );
                // we keep a reference to the last node to add connections if needed
                lastNode = newNode;

            }
        }
    }

    public class SceneData {
        private IntArea area;
        private List<Connection> connections;

        public SceneData( IntArea area ) {
            this.area = area;
            this.connections = new LinkedList<>();
        }

        public IntArea getArea() {
            return area;
        }

        public void setArea( IntArea area ) {
            this.area = area;
        }

        @Override
        public String toString() {
            return URLConstants.JSON_MAPPER.prettyPrint( this );
        }

        public List<IRestriction> getRestrictions() {
            return connections.stream()
                    .filter( connection -> connection.restriction != null )
                    .map( connections -> connections.restriction )
                    .collect( Collectors.toList());
        }
    }

    public class Connection implements Json.Serializable {
        public SceneNode A;
        public SceneNode B;
        public IntArea where;
        public IRestriction restriction;

        public Connection( SceneNode A, SceneNode B, IntArea where ) {
            this.A = A;
            this.B = B;
            this.where = where;
        }

        public void materializeConnection( RandomXS128 random ){
            if( this.restriction != null ) return;
            this.restriction = RestrictionHelper.DoorRestriction( this, random );
        }

        @Override
        public void write( Json json ) {
            json.writeValue( "where" , where );
            json.writeValue( "A" , A.getContent().area );
            json.writeValue( "B" , B.getContent().area );
        }

        @Override
        public void read( Json json, JsonValue jsonData ) {
            throw new NotImplementedException();
        }
    }

}
