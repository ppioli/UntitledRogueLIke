package com.ppioli.url.ai;

import com.badlogic.gdx.Gdx;
import com.ppioli.url.Game;
import com.ppioli.url.utils.geom.IntVect;
import com.ppioli.url.utils.geom.Direction;
import com.ppioli.url.utils.geom.MatrixHelpers;

import java.util.*;

public class PathFinder {

    private final Board board;

    public PathFinder( Board board ) {
        this.board = board;
        //Gdx.app.debug( PathFinder.class.getCanonicalName(), "Board: \n" + MatrixHelpers.toString( board.getCells(), cell -> cell.isBlocked() ? "1": "0" ) );
    }

    /**
     * Finds a path from the initial position to the final position using A*
     * @param initialPos initial position
     * @param finalPos final position
     * @return The sequence of directions that conform a path between the two points ( or null if there is no path connecting them )
     */
    public Queue<Direction> route( final IntVect initialPos, final IntVect finalPos ) {
        Gdx.app.debug( PathFinder.class.getCanonicalName(), "Starting path finding" );
        LinkedList<Node> opened = new LinkedList<>();
        Map<IntVect, Node> closed = new HashMap<>();
        Node initialNode = new Node( initialPos );

        opened.add( initialNode );

        while ( !opened.isEmpty() ) {
            opened.sort( Comparator.comparingInt( ( n ) -> n.expectedDistance( finalPos ) ) );
            Node node = opened.getFirst();
            opened.remove( node );
            //expand
            for ( Direction d : Direction.values() ) {
                IntVect newPos = new IntVect( node.position, d );
                if ( newPos.equals( finalPos ) ) {
                    closed.put( node.position, node );
                    return createPath( finalPos, d, closed );
                }
                if ( board.isBlocked( newPos.x, newPos.y ) || closed.containsKey( newPos ) ) {
                    continue;
                }
                Node newNode = new Node( newPos, d, node.distance + 1 );
                if ( opened.contains( newNode ) ) {
                    continue;
                }
                opened.add( newNode );
            }
            //
            closed.put( node.position, node );
            if ( Game.debug ) {

                Gdx.app.debug( PathFinder.class.getCanonicalName(),
                        "Board: \n" + MatrixHelpers.toString( board,
                                initialPos.x,
                                initialPos.y,
                                20,
                                ( cellOpt, x, y ) -> cellRepresentation( x, y, opened, closed ) ));

            }

        }
        Gdx.app.debug( PathFinder.class.getCanonicalName(), "No path found" );
        return null;
    }

    public String cellRepresentation( int x,
                                      int y,
                                      LinkedList<Node> opened,
                                      Map<IntVect, Node> closed ) {
        IntVect vector = new IntVect( x, y );
        Node newNode = new Node( vector );
        if ( opened.contains( newNode ) ) {
            int ix = opened.indexOf( newNode );
            return String.format( "%02d", opened.get( ix ).distance );
        } else if ( closed.containsKey( vector ) ) {
            return "CC";
        } else if ( board.isBlocked( x, y ) ) {
            return "##";
        } else {
            return "__";
        }

    }

    private Queue<Direction> createPath( IntVect finalNode, Direction direction, Map<IntVect, Node> closed ) {
        LinkedList<Direction> result = new LinkedList<>();
        result.addFirst( direction );
        Node nextNode = closed.get( finalNode.add( direction.inverse() ) );
        while ( nextNode.direction != null ) {
            result.addFirst( nextNode.direction );
            nextNode = closed.get( nextNode.previous() );
        }
        return result;
    }

    private class Node implements Comparable<Node> {

        final IntVect position;
        final int distance;
        final Direction direction;

        private Node( IntVect position, Direction direction, int distance ) {
            this.position = position;
            this.direction = direction;
            this.distance = distance;
        }

        public Node( IntVect pos ) {
            this( pos, null, 0 );
        }

        public IntVect previous() {
            assert this.direction != null;
            return this.position.add( this.direction.inverse() );
        }

        public int getDistance() {
            return distance;
        }

        public int expectedDistance( IntVect targetNode ) {
            return distance + position.distance( targetNode );
        }

        @Override
        public boolean equals( Object o ) {
            return position.equals( ( ( Node ) o ).position );
        }

        @Override
        public int hashCode() {
            return Objects.hash( position );
        }

        @Override
        public String toString() {
            return "Node{" +
                    "position=" + position +
                    ", distance=" + distance +
                    ", direction=" + direction +
                    '}';
        }

        @Override
        public int compareTo( Node o ) {
            return o.distance - distance;
        }
    }
}
