import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Fluid {
    static double gravity = 0.1;
    static double pressure = 4.0;
    static double viscosity = 10.0;

    private int x, y;
    private ArrayList<Particle> particles;
    private GameArena arena;

    public Fluid(int x, int y) {
        this.x = x;
        this.y = y;
        this.particles = new ArrayList<>();
        this.arena = new GameArena(x, y);
    }

    public static void main(String[] args) throws InterruptedException {
        new Fluid(400, 400).main();
    }

    void step() {
        for (Particle lhs : this.particles) {
            lhs.density = lhs.fixed ? 9 : 0;

            for (Particle rhs : this.particles) {
                var dist = lhs.pos.sub(rhs.pos).abs();
                var interactionMagic = dist / 2.0 - 1.0;

                if (Math.floor(1.0 - interactionMagic) > 0.0) {
                    lhs.density += interactionMagic * interactionMagic;
                }
            }
        }

        for (Particle lhs : this.particles) {
            lhs.force = new Vec2(0, gravity);
            for (Particle rhs : this.particles) {
                var distVec = lhs.pos.sub(rhs.pos);
                var dist = distVec.abs();
                var interactionMagic = dist / 2.0 - 1.0;
                if (Math.floor(1.0 - interactionMagic) > 0.0) {
//                    var fx = lhs.force.x + interactionMagic * (distVec.x * (3 - lhs.density - rhs.density) * pressure + lhs.velo.x * viscosity
//                            - rhs.velo.x * viscosity) / lhs.density;
//                    var fy = lhs.force.y + interactionMagic * (distVec.y * (3 - lhs.density - rhs.density) * pressure + lhs.velo.y * viscosity
//                            - rhs.velo.y * viscosity) / lhs.density;
//                    lhs.force = new Vec2(fx, fy);
                    lhs.force = lhs.force.add(distVec.scale((3.0 - lhs.density - rhs.density) *  pressure)
                        .add(lhs.velo.scale(viscosity)
                                .sub(rhs.velo.scale(viscosity)))
                            .scale(interactionMagic / lhs.density));
                }
            }
        }

        var toRemove = new ArrayList<Particle>();

        for (Particle p : this.particles) {
            if (!p.fixed) {
                // ???
                if (p.force.absSquared() < (4.2 * 4.2)) {
                    p.velo = p.velo.add(p.force.scale(1.0 / 100.0));
                } else {
                    p.velo = p.velo.add(p.force.scale(1.0 / 110.0));
                }

                p.pos = p.pos.add(p.velo);
                if ((p.pos.x < 0) || (p.pos.x > this.x) || (p.pos.y < 0) || (p.pos.y > this.y * 2)) {
                    // F
                    toRemove.add(p);
                }
            }
        }

        for (Particle p : toRemove) {
            this.particles.remove(p);
            this.arena.removeBall(p);
        }
    }

    Vec2 getoffset() {
        var x = ThreadLocalRandom.current().nextInt(0, 10) - 5;
        var y = ThreadLocalRandom.current().nextInt(0, 10) - 5;
        return new Vec2(x, y);
    }

    void addParticle(int x, int y) {
        var offset = getoffset();
        var p = new Particle(new Vec2(x, y).add(offset), new Vec2(0, 0), new Vec2(0, 0), false, "blue");
        this.particles.add(p);
        arena.addBall(p);
    }

    void addWall(int x, int y) {
        var p = new Particle(new Vec2(x, y), new Vec2(0, 0), new Vec2(0, 0), true, "red");
        this.particles.add(p);
        arena.addBall(p);
    }

    public void main() throws InterruptedException {
        while (true) {
            if (this.arena.leftMousePressed()) {
                this.addParticle(this.arena.getMousePositionX(), this.arena.getMousePositionY());
            } else if (this.arena.rightMousePressed()) {
                var x = this.arena.getMousePositionX();
                var y = this.arena.getMousePositionY();

                for (int dx = -3; dx < 3; dx++) {
                    for (int dy = -3; dy < 3; dy++) {
                        this.addWall(x + dx, y + dy);
                    }
                }
            }
            this.step();
            this.arena.repaint();
//            this.arena.run();
        }
    }
}