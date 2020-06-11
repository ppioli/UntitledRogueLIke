package com.ppioli.url.ai;

import com.badlogic.ashley.core.Entity;
import com.ppioli.url.level.Cell;
import com.ppioli.url.utils.geom.Direction;

import java.util.Optional;

public interface Board {
//    BoardCell[][] getCells();
    boolean isBlocked( int x, int y );
    Optional<Cell> get( int x, int y );
    void initialize();
}
