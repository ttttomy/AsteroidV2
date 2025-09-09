import greenfoot.*;

public class Bala extends Actor {
    private int speed = 7;

    public Bala(int rotation) {
        setRotation(rotation);
    }

    public void act() {
        // Por si fue eliminada en el frame anterior por otro actor
        if (getWorld() == null) return;

        move(speed);

        // Si impactó y se eliminó, NO sigas ejecutando act()
        if (verificarImpacto()) return;

        // Puede haberse eliminado dentro de verificarImpacto()
        World w = getWorld();
        if (w == null) return;

        if (isAtEdge()) {
            w.removeObject(this);
        }
    }

    /** @return true si la bala se eliminó (por impacto) */
    private boolean verificarImpacto() {
        // Si ya no está en el mundo, trátalo como eliminado
        if (getWorld() == null) return true;

        Asteroide a = (Asteroide) getOneIntersectingObject(Asteroide.class);
        if (a != null) {
            a.onHit();  // esto puede eliminar al asteroide y crear hijos
            World w = getWorld();
            if (w != null) {
                w.removeObject(this); // eliminar bala
            }
            return true; // IMPORTANTE: avisar que ya se eliminó
        }
        return false;
    }
}
