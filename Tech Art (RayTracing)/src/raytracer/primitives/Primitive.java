package raytracer.primitives;

import org.joml.Vector3f;

import raytracer.Material;
import raytracer.Ray;

public abstract class Primitive {
    private Material material;

    public Primitive(Material material) {
        this.material = material;
    }

    public abstract Vector3f getNormalAt(Vector3f pos);

    public abstract Result intersect(Ray ray, float distance);

    public Vector3f getColor() {
        return material.getColor();
    }

    public Material getMaterial() {
        return material;
    }



}
