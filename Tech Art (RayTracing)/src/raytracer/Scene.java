package raytracer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joml.Vector3f;

import raytracer.primitives.Plane;
import raytracer.primitives.Primitive;
import raytracer.primitives.Sphere;

public class Scene {
    private Vector3f background = new Vector3f(0,0,0);
    private List<Primitive> objects = new ArrayList<>();
    private List<Light> lights = new ArrayList<>();

    public Scene() {
        // Ground
        Material tint = new Material(new Vector3f(0.4f, 0.3f, 0.3f), 1.0f, 0.0f, 0.0f, 0, 1.5f);
        objects.add(new Plane(new Vector3f(0, 1, 0), 4.4f, tint));

        // Small Sphere
        objects.add(new Sphere(new Vector3f(-5.5f, -0.5f, 7 ), 2 ,
                new Material(new Vector3f(0.7f, 0.7f, 1.0f), 0.1f, 15f, 0.5f, 0.0f, 1.3f)));

        // Big Sphere
        objects.add(new Sphere(new Vector3f(0.8f, 0.8f, 0.2f),2.5f,
                new Material(new Vector3f(0.7f, 0.7f, 1.0f), 0.1f, 35f, 0.2f, 0.8f, 1.3f)));

        // "Refraction" Sphere
        objects.add(new Sphere(new Vector3f(-1.5f, -3.8f, 1), 1.5f ,
                new Material(new Vector3f(1.0f, 0.4f, 0.4f), 0.4f, 15f, 0, 0.8f, 1.3f)));

        // Wall
        objects.add(new Plane(new Vector3f(0.4f, 0, -1 ), 12,
                new Material(new Vector3f(0.5f, 0.3f, 0.5f), 0.6f, 0, 0, 0, 1.5f)));

        // Ceiling
        objects.add(new Plane(new Vector3f(0, -1, 0 ), 7.4f,
                new Material(new Vector3f(0.4f, 0.7f, 0.7f), 0.5f, 0, 0, 0, 1.5f)));

        // Lights
        Vector3f light1 = new Vector3f(0.4f, 0.4f, 0.4f);
        Vector3f light2 = new Vector3f(0.6f, 0.6f, 0.8f);
        lights.add(new Light(new Vector3f(0, 5, 5), light1));
        lights.add(new Light(new Vector3f(-3, 5, 1), light2));

        // grid
        int prim = 8;
        for ( int x = 0; x < 8; x++ ) for ( int y = 0; y < 7; y++ )
        {
            objects.add(new Sphere(new Vector3f(-4.5f + x * 1.5f, -4.3f + y * 1.5f, 10 ), 0.15f ,
                    new Material(new Vector3f(0.3f, 1.0f, 0.4f), 0.6f, 30f, 0, 0, 1.3f)));

        }
    }

    public Vector3f getBackground() {
        return background;
    }

    public List<Primitive> getObjects() {
        return Collections.unmodifiableList(objects);
    }

    public List<Light> getLights() {
        return Collections.unmodifiableList(lights);
    }
}
