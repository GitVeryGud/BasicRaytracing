package raytracer;

import static raytracer.Vector3fOps.*;

import org.joml.Vector3f;

import raytracer.primitives.Primitive;
import raytracer.primitives.Result;

public class Raytracer {
    protected static final float INFINITE = 1000000.0f;
    protected static final int MAX_DEPTH = 4;
    private Canvas canvas;
    private Scene scene = new Scene();
    private final float wx1,wx2,wy1,wy2,dx,dy;

    public Raytracer() {
        this(1.5f);
    }

    public Raytracer(float zoom) {
        this.canvas = new Canvas(1024, 768);
        float aspect = canvas.getWidth() / canvas.getHeight();
        float size = 10 / zoom;

        wx1 =(-size * aspect/2.0f);
        wx2 = -wx1;

        wy1 = size / 2.0f;
        wy2 = -wy1;

        dx = (wx2 - wx1)/ canvas.getWidth();
        dy = (wy2 - wy1)/ canvas.getHeight();
    }

    public Vector3f renderRay(Vector3f screenPos)
    {
        Vector3f o = new Vector3f(0,0,-5);

        Vector3f dir = screenPos.sub(o).normalize();

        Ray ray = new Ray(o,dir);

        return raytrace(ray, 0, 1f);
    }

    public void render() {
        float sy = wy1;
        for (int y = 0; y < canvas.getHeight(); y++){
            float sx = wx1;
            for (int x = 0; x < canvas.getWidth(); x++){
                Vector3f color = new Vector3f();

                // Fires 64 rays for each pixel
                for ( int tx = 0; tx < 8; tx++ ) for ( int ty = 0; ty < 8; ty++ )
                {
                    color.add(renderRay( new Vector3f(sx + dx * tx/8, sy + dy * ty/8, 0)));
                }

                color.div(64);

                canvas.set(x,y,color);
                sx += dx;
            }
            sy += dy;
        }
        canvas.save();
    }

    public Vector3f raytrace(Ray ray, int depth, float fromRIndex) {
        if (depth > MAX_DEPTH) return new Vector3f();
        float dist = INFINITE;
        Primitive prim = null;


        // find the nearest intersection
        for (Primitive obj : scene.getObjects()){
            Result result = obj.intersect(ray,dist);
            if(result.isHit()){
                dist = result.getDistance();
                prim = obj;
            }
        }

        if (prim == null){
        return scene.getBackground();
        }

        // Diffuse light
        Vector3f pi = ray.getIntersection(dist);
        Vector3f color = new Vector3f();
        for (Light light : scene.getLights()){
            Vector3f L = sub(light.getPosition(), pi);
            float ldist = L.length();
            L.div(ldist);

            // Shadow ray calculation
            boolean lit = true;
            Ray shadowRay = new Ray(pi, L);
            for (Primitive obj : scene.getObjects()){
                if (obj.intersect(shadowRay, ldist).isHit()) {
                    lit = false;
                    break;
                }
            }

            if (!lit) continue;

            // Light calculation if not in shadow
            Vector3f N = prim.getNormalAt(pi);
            if (prim.getMaterial().getDiffuse() > 0){
                float dot = L.dot(N); // Cosine between L and N
                if (dot > 0){
                    float di = dot * prim.getMaterial().getDiffuse();
                    Vector3f diffuse = mul(di
                            ,mul(light.getColor(), prim.getColor()));
                    color.add(diffuse);
                }
            }

            if (prim.getMaterial().getSpecular() > 0){
                Vector3f V = ray.getDirection();
                Vector3f R = reflect(L,N);
                float dot = V.dot(R);
                if (dot > 0){
                    float si = (float) Math.pow(dot, prim.getMaterial().getSpecular());
                    Vector3f specular = mul(si, light.getColor());
                    color.add(specular);
                }
            }
        }

        // Calculate reflection
        float refl = prim.getMaterial().getReflection();
        if (refl > 0.0f){
            Vector3f N = prim.getNormalAt(pi);
            Vector3f R = reflect(ray.getDirection(), N);
            Vector3f rcolor = raytrace(new Ray(pi,R), depth+1, fromRIndex);
            color.lerp(rcolor, refl);
        }

        // calculate refraction
        float refr = prim.getMaterial().getRefraction();
        if ((refr > 0))
        {
            float rindex = prim.getMaterial().getRefractionIndex();
            float n = fromRIndex / rindex;
            Vector3f N = mul((float)prim.intersect(ray,INFINITE).getTypeOfHit(), prim.getNormalAt(pi));
            float cosI = -N.dot(ray.getDirection());
            float cosT2 = 1.0f - n * n * (1.0f - cosI * cosI);
            if (cosT2 > 0.0f)
            {
                Vector3f T = add(mul(n,ray.getDirection()), mul((float)(n * cosI - Math.sqrt(cosT2)),N));
                Vector3f rcolor = raytrace(new Ray(pi,T), depth+1, rindex);
                // Beer's law
                Vector3f absorbance = mul(0.15f * -dist ,prim.getMaterial().getColor());
                Vector3f transparency = new Vector3f((float)Math.exp(absorbance.x), (float)Math.exp(absorbance.y), (float)Math.exp(absorbance.z));
                color.add(rcolor.mul(transparency));
            }
        }

        return  color;
    }
}
