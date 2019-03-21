package cz.uhk.brabec.graphics.render;

import cz.uhk.brabec.graphics.model.Vertex;
import transforms.Col;

@FunctionalInterface
public interface Shader {

    Col shade(Vertex vertex);

}
