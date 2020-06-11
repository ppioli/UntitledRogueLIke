package com.ppioli.url.level.levelGeneration.scenes;

import java.util.List;

public interface ITreeNode<T> {

    List<ITreeNode<T>> getChildren();
    ITreeNode<T> getParent();
    T getContent();
    boolean isLeaf();
}
