import greenfoot.*;

public class GameWorld extends World {

    private boolean isGameOver = false;

    // Radio seguro en píxeles para que no spawneen encima del jugador
    private static final int SPAWN_SAFE_RADIUS = 150; 
    private static final int MAX_TRIES = 200; // para evitar bucles infinitos

    public GameWorld() {    
        super(900, 700, 1, false);
        prepare();
    }

    public void gameOver() {
        if (isGameOver) return;
        isGameOver = true;
        Greenfoot.setWorld(new GameOverWorld());
    }

    public void wrap(Actor a) {
        int x = a.getX();
        int y = a.getY();
        int w = getWidth();
        int h = getHeight();

        if (x < 0)        x = w - 1;
        else if (x >= w)  x = 0;

        if (y < 0)        y = h - 1;
        else if (y >= h)  y = 0;

        a.setLocation(x, y);
    }

    private void prepare() {
        // Nave en el centro del mundo
        int naveX = getWidth()/2;
        int naveY = getHeight()/2;

        Nave nave = new Nave();
        addObject(nave, naveX, naveY);

        // 3 asteroides grandes en posiciones aleatorias FUERA del radio seguro
        for (int i = 0; i < 3; i++) {
            int[] pos = randomSpawnAwayFrom(naveX, naveY, SPAWN_SAFE_RADIUS);
            addObject(new AsteroideGrande(), pos[0], pos[1]);
        }
    }

    /**
     * Devuelve una posición aleatoria [x,y] que esté a más de 'radius' píxeles de (cx, cy).
     * Intenta hasta MAX_TRIES; si no encuentra, coloca en el borde más lejano.
     */
    private int[] randomSpawnAwayFrom(int cx, int cy, int radius) {
        int w = getWidth();
        int h = getHeight();
        int r2 = radius * radius;

        for (int i = 0; i < MAX_TRIES; i++) {
            int x = Greenfoot.getRandomNumber(w);
            int y = Greenfoot.getRandomNumber(h);
            int dx = x - cx;
            int dy = y - cy;
            if (dx*dx + dy*dy >= r2) {
                return new int[]{x, y};
            }
        }

        // Fallback: si no encontró en MAX_TRIES, ubica lejos en la dirección opuesta al centro
        // (elige un ángulo aleatorio y manda al borde externo del radio)
        double ang = Math.toRadians(Greenfoot.getRandomNumber(360));
        int x = (int)Math.round(cx + Math.cos(ang) * Math.max(radius, Math.max(w, h)));
        int y = (int)Math.round(cy + Math.sin(ang) * Math.max(radius, Math.max(w, h)));
        // clamp dentro del mundo
        x = Math.max(0, Math.min(w - 1, x));
        y = Math.max(0, Math.min(h - 1, y));
        return new int[]{x, y};
    }
}
