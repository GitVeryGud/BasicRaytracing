package raytracer.primitives;

public class Result {
    private boolean hit;
    private float distance;
    // 1 = hit | -1 = hit inside primitive
    private int typeOfHit;

    public Result(boolean hit, float distance) {
        this.hit = hit;
        this.distance = distance;
        if(hit) typeOfHit = 1;
        else typeOfHit = 0;
    }

    public Result(boolean hit, float distance, int typeOfHit) {
        this.hit = hit;
        this.distance = distance;
        this.typeOfHit = typeOfHit;
    }

    public boolean isHit() {
        return hit;
    }

    public float getDistance() {
        return distance;
    }

    public int getTypeOfHit() {
        return typeOfHit;
    }
}
