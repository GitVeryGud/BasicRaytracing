package raytracer;

// Changes made: Added super sampling and added the refraction effect to primitives
public class Main {
    public static void main(String[] args) {
        System.out.print("Rendering... ");
        Raytracer tracer = new Raytracer();
        long time = System.currentTimeMillis();
        tracer.render();
        double secs = (System.currentTimeMillis() - time) / 1000.0;
        System.out.printf("Done in %.2f seconds.%n", secs);
    }
}
