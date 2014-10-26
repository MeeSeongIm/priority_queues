// Modeling N particles in a system: collision simulation. 
// from Princeton's applications of priority queues. 
// to be modified and debugged later to model a certain system.
// although this code simulates N particles, it doesn't model 3 or more particle collisions. 
// N = particles.length 


public class BouncingBalls 
{
  public static void main(String[] args)
  {
    int N = Integer.parseInt(args[0]);
    Ball[] balls = new Ball[N];
    for (int i =0; i < N; i++)
      balls[i] = new Ball();
    while(true)
    {
      StdDraw.clear();
      for (int i =0; i< N;i++)
      {
        balls[i].move(0.5);
        balls[i].draw();
      }
      StdDraw.show(50);
    }
  }
}


public class Particle
{
  private double rx, ry:
  private double vs, vy;
  private final double radius;
  private final double mass; 
  private int count; 
  public Particle()
  {/* initial position and velocity */}
  
  public void move(double dt)
  {
    if ((rx + vx*dt < radius) || (rx + vx*dt > 1.0 - radius)) {vx = -vx; }     // basic physics 
    if ((ry + vy*dt < radius) || (ry + vy*dt > 1.0 - radius)) {vy = -vy; }
    rx = rx + vx*dt;
    ry = ry + vy*dt;
  }
  public void draw()
  { StdDraw.filledCircle(rx,ry, radius);  }
  
  public double timeToHit(Particle that) {}
  public double timeToHitVerticalWall() {}
  public double timeToHitHorizontalWall() {}
  
  public void bounceOff(Particle that) {}
  public void bounceOffVerticalWall() {}
  public void bounceOffHorizontalWall() {}
}


public class CollisionSystem {
    private MinPQ<Event> pq;                   // priority queue
    private double t  = 0.0;       
    private Particle[] particles;   
    
    public CollisionSystem(Particle[] particles) {
        this.particles = particles;
    
    private void predict(Particle a, double limit) {
        if (a == null) return;
        for (int i = 0; i < particles.length; i++) {
            double dt = a.timeToHit(particles[i]);
            if (t + dt <= limit)
                pq.insert(new Event(t + dt, a, particles[i]));
        }

        double dtX = a.timeToHitVerticalWall();
        double dtY = a.timeToHitHorizontalWall();
        if (t + dtX <= limit) pq.insert(new Event(t + dtX, a, null));
        if (t + dtY <= limit) pq.insert(new Event(t + dtY, null, a));
    }

    private void redraw(double limit) {
        StdDraw.clear();
        for (int i = 0; i < particles.length; i++) {
            particles[i].draw();
        }
        StdDraw.show(20);
        if (t < limit) {
            pq.insert(new Event(t + 1.0 / hz, null, null));
        }
    }


    public void simulate(double limit) {
        pq = new MinPQ<Event>();
        for (int i = 0; i < particles.length; i++) {
            predict(particles[i], limit);
        }
        pq.insert(new Event(0, null, null));      

        while (!pq.isEmpty()) { 
            Event e = pq.delMin();
            if (!e.isValid()) continue;
            Particle a = e.a;
            Particle b = e.b;

            for (int i = 0; i < particles.length; i++)
                particles[i].move(e.time - t);
            t = e.time;

            if      (a != null && b != null) a.bounceOff(b);              // particle-particle collision
            else if (a != null && b == null) a.bounceOffVerticalWall();   // particle-wall collision
            else if (a == null && b != null) b.bounceOffHorizontalWall(); // particle-wall collision
            else if (a == null && b == null) redraw(limit);               // redraw event

            predict(a, limit);
            predict(b, limit);
        }
    }
}
