package com.ppioli.url.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.ppioli.url.utils.IRenderer;

public class VisualComponent implements Component, Pool.Poolable {
    public IRenderer renderer;

    public VisualComponent set( IRenderer renderer ) {
        this.renderer = renderer;
        return this;
    }

    @Override
    public void reset() {
        this.renderer = null;
    }
}
