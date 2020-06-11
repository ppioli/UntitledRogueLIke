package com.ppioli.url.level.levelGeneration.layoutBuilder;

import com.ppioli.url.level.Area;
import com.ppioli.url.utils.geom.IntArea;

import java.util.List;

public interface ILayoutBuilder {

    void fill( Area area, IntArea section, List<IRestriction> restrictions, List<IRequirement> requiments );
}
