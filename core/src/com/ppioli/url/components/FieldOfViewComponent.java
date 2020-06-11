package com.ppioli.url.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class FieldOfViewComponent implements Component, Pool.Poolable {
    public int range;

    public FieldOfViewComponent() {

    }
    @Override
    public void reset() {

    }
}
