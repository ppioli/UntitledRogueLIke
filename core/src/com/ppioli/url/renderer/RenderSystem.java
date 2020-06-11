package com.ppioli.url.renderer;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ppioli.extensions.utils.SortedIteratingSystem;
import com.ppioli.url.components.PositionComponent;
import com.ppioli.url.components.VisualComponent;

import java.util.Comparator;

public class RenderSystem extends SortedIteratingSystem {

    private SpriteBatch batch;
    private OrthographicCamera camera;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor( PositionComponent.class );
    private ComponentMapper<VisualComponent> vm = ComponentMapper.getFor( VisualComponent.class );

    public RenderSystem( OrthographicCamera camera ) {
        super(Family.all( PositionComponent.class, VisualComponent.class).get(), new ZComparator(ComponentMapper.getFor( PositionComponent.class )) );
        this.camera = camera;
        this.batch = new SpriteBatch();
    }


    @Override
    public void update( float deltaTime ) {

        camera.update();
        batch.begin();

        for( Entity entity : getEntities() ) {
            vm.get( entity ).renderer.render( batch, pm.get( entity ).getRenderPosition() );
        }

        batch.setProjectionMatrix( camera.combined );

        batch.end();
    }


    private static class ZComparator implements Comparator<Entity> {
        private final ComponentMapper<PositionComponent> pm;

        private ZComparator( ComponentMapper<PositionComponent> pm ) {
            this.pm = pm;
        }

        @Override
        public int compare(Entity e1, Entity e2) {
            return (int)Math.signum(pm.get(e1).getZ() - pm.get(e2).getZ());
        }
    }
}