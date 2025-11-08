package combat;

public final class Facing {
    private Facing() {}

    // Returns a unit-like vector on grid: {-1,0,+1}
    public static int[] dirVector(String dir) {
        int dx = 0, dy = 0;
        if (dir.contains("left"))  dx = -1;
        if (dir.contains("right")) dx =  1;
        if (dir.contains("up"))    dy = -1;
        if (dir.contains("down"))  dy =  1;
        return new int[]{dx, dy};
    }

    // Perpendicular (for sweep center offset or width direction)
    public static int[] perp(int dx, int dy) {
        return new int[]{-dy, dx}; // rotate 90° clockwise
    }
}
