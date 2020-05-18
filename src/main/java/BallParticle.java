public class BallParticle extends Ball {
    private Particle particle;

    public BallParticle(Particle p, double diameter, String col) {
        super(p.pos.x, p.pos.y, diameter, col);
        this.particle = p;
    }

    public BallParticle(Particle p, double diameter, String col, int layer) {
        super(p.pos.x, p.pos.y, diameter, col, layer);
        this.particle = p;
    }

    @Override
    public double getXPosition() {
        return this.particle.pos.x;
    }

    @Override
    public double getYPosition() {
        return this.particle.pos.y;
    }

    @Override
    public void setXPosition(double x) {
        throw new RuntimeException("don't");
    }

    @Override
    public void setYPosition(double y) {
        throw new RuntimeException("don't");
    }
}
