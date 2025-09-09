import greenfoot.*;

public class GameOverWorld extends World {

    private GreenfootImage gameOverImg;
    private Actor gameOverActor;
    private int frameCount = 0;

    public GameOverWorld() {
        super(900, 700, 1);

        // Crear un actor "dummy" para mostrar la imagen
        gameOverActor = new Actor() {};
        gameOverImg = new GreenfootImage("game_over_text.png");

        // Centrar la imagen en el mundo
        gameOverActor.setImage(gameOverImg);
        addObject(gameOverActor, getWidth() / 2, getHeight() / 2);
    }

    public void act() {
        frameCount++;

        // Hacer parpadear cada 30 frames aprox.
        if (frameCount % 60 < 30) {
            gameOverActor.setImage(gameOverImg); // visible
        } else {
            gameOverActor.setImage((GreenfootImage) null); // invisible
        }

        // Reiniciar con ENTER
        if (Greenfoot.isKeyDown("enter")) {
            Greenfoot.setWorld(new GameWorld());
        }
    }
}
