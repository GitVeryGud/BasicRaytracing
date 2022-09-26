package raytracer;

import org.joml.Vector3f;


public class Material {
    private Vector3f color;
    private float diffuse;
    private float specular;
    private float reflection;
    private float refraction;
    private float refractionIndex;

    public Material(Vector3f color, float diffuse, float specular, float reflection, float refraction, float refractionIndex) {
        this.color = color;
        this.diffuse = diffuse;
        this.reflection = reflection;
        this.specular = specular;
        this.refraction = refraction;
        this.refractionIndex = refractionIndex;
    }

    public Vector3f getColor() {
        return color;
    }

    public float getDiffuse() {
        return diffuse;
    }

    public float getSpecular() {
        return specular;
    }

    public float getReflection() {
        return reflection;
    }

    public float getRefraction() {
        return refraction;
    }
    public float getRefractionIndex() {
        return refractionIndex;
    }
}
