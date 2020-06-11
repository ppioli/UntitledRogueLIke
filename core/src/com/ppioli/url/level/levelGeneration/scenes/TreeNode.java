package com.ppioli.url.level.levelGeneration.scenes;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.ppioli.url.constants.URLConstants;

import java.util.LinkedList;
import java.util.List;

public abstract class TreeNode<T> implements ITreeNode<T>, Json.Serializable {
    public T content;
    public List<ITreeNode<T>> children;
    public ITreeNode<T> parent;

    public TreeNode( T content, ITreeNode<T> parent ) {
        this.content = content;
        this.parent = parent;
        this.children = new LinkedList<>();
    }

    public boolean isLeaf() {
        return getChildren().size() == 0;
    }

    @Override
    public void write( Json json ) {
        json.writeValue( "content", content );
        json.writeValue( "children", children );
    }

    @Override
    public void read( Json json, JsonValue jsonData ) {
        //TODO finish this
    }

    @Override
    public List<ITreeNode<T>> getChildren() {
        return children;
    }

    @Override
    public ITreeNode<T> getParent() {
        return parent;
    }

    @Override
    public T getContent() {
        return content;
    }

    @Override
    public String toString() {
        return URLConstants.JSON_MAPPER.prettyPrint( this );
    }
}
