public class Vec2 {
    public final double x, y;

    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec2 mul(Vec2 other) {
        return new Vec2(this.x * other.x, this.y * other.y);
    }

    public Vec2 scale(double by) {
        return new Vec2(this.x * by, this.y * by);
    }

    public Vec2 add(Vec2 other) {
        return new Vec2(this.x + other.x, this.y + other.y);
    }

    public Vec2 sub(Vec2 other) {
        return new Vec2(this.x - other.x, this.y - other.y);
    }

    public double abs() {
        return Math.sqrt(this.absSquared());
    }

    public double absSquared() {
        return this.x * this.x + this.y * this.y;
    }

    public Vec2 normal() {
        return this.scale(1.0f / this.abs());
    }

    @Override
    public String toString() {
        return "Vec2{" +
                "x=" + this.x +
                ", y=" + this.y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Vec2 vec2 = (Vec2) o;

        if (Double.compare(vec2.x, this.x) != 0) return false;
        return Double.compare(vec2.y, this.y) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(this.x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
