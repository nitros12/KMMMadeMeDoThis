public class Particle extends Ball {
    public Vec2 pos;
    public Vec2 force;
    public Vec2 velo;
    public double density;
    public final boolean fixed;

    @Override
    public double getXPosition() {
        return this.pos.x;
    }

    @Override
    public double getYPosition() {
        return this.pos.y;
    }

    @Override
    public void setXPosition(double x) {
        throw new RuntimeException("don't") ;
    }

    @Override
    public void setYPosition(double y) {
        throw new RuntimeException("don't");
    }

    public Particle(Vec2 pos, Vec2 force, Vec2 velo, boolean fixed, String col) {
        super(pos.x, pos.y, 2.0, col);
        this.pos = pos;
        this.force = force;
        this.velo = velo;
        this.density = 0.0;
        this.fixed = fixed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Particle particle = (Particle) o;

        if (Double.compare(particle.density, density) != 0) return false;
        if (fixed != particle.fixed) return false;
        if (!pos.equals(particle.pos)) return false;
        if (!force.equals(particle.force)) return false;
        return velo.equals(particle.velo);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = pos.hashCode();
        result = 31 * result + force.hashCode();
        result = 31 * result + velo.hashCode();
        temp = Double.doubleToLongBits(density);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (fixed ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Particle{" +
                "pos=" + pos +
                ", force=" + force +
                ", velo=" + velo +
                ", density=" + density +
                ", fixed=" + fixed +
                '}';
    }
}