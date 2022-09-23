package raytracer.primitives;

import org.joml.Vector3f;

import raytracer.Material;
import raytracer.Ray;
import static raytracer.Vector3fOps.*;

public class Sphere extends Primitive {
    private Vector3f center;
    private float radius;

    public Sphere(Vector3f pos, float r, Material mat) {
        super(mat);
        center = pos;
        radius = r;
    }

    public Vector3f getCenter() {
        return center;
    }

    public float getRadius() {
        return radius;
    }

    @Override
    public Vector3f getNormalAt(Vector3f pos) {
        // (pos - center).normalize();
        return sub(pos, center).normalize();
    }

    @Override
    public Result intersect(Ray ray, float distance) {
        Vector3f v = sub(ray.getOrigin(), center);

        float b = -v.dot(ray.getDirection());
        float det = (b * b) - v.dot(v) + radius;
        boolean hit = false;
        int typeOfHit = 0;
        if (det > 0) {
            det = (float) Math.sqrt(det);
            float i1 = b - det;
            float i2 = b + det;
            if (i2 > 0) {
                if (i1 < 0) {
                    // Inside Primitive
                    if (i2 < distance) {
                        distance = i2;
                        hit = true;
                        typeOfHit = -1;
                    }
                } else {
                    // On Primitive
                    if (i1 < distance) {
                        distance = i1;
                        hit = true;
                        typeOfHit = 1;
                    }
                }
            }
        }
        return new Result(hit, distance, typeOfHit);
    }
}
