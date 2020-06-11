package com.ppioli.url.utils;

import com.ppioli.url.level.levelGeneration.scenes.ITreeNode;

import java.util.function.Consumer;

public class TreeHelpers {
    public static <T> void iterateLeafs( ITreeNode<T> node, Consumer<T> action ){
        if( !node.isLeaf() ) {
            for ( ITreeNode child : node.getChildren() ) {
                iterateLeafs( child, action );
            }
        } else {
            action.accept( node.getContent() );
        }
    }
}
