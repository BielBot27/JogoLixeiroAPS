import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Player extends Entidade {
	
	private BufferedImage sprite;
	private BufferedImage[] frames;
	
	private int frameAtual = 0;
	private int contador = 0;
	
	public Player(int x, int y) {
		super(x, y, 64);
		
		try {
			sprite = ImageIO.read(getClass().getResource("/Sprites/player.png"));
			
			frames = new BufferedImage[3];
			
			for (int i = 0; i < 3; i++) {
				frames[i] = sprite.getSubimage(i * 32, 0, 32, 32);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void mover(boolean up, boolean down, boolean left, boolean right) {
		boolean movendo = false;
		
		int velocidade = 3;

	    if (up) y -= velocidade;
	    if (down) y += velocidade;
	    if (left) x -= velocidade;
	    if (right) x += velocidade;

	    // limites da tela
	    int larguraTela = 800;
	    int alturaTela = 600;

	    if (x < 0) x = 0;
	    if (x > larguraTela - tamanho) x = larguraTela - tamanho;
	    if (y < 0) y = 0;
	    if (y > alturaTela - tamanho) y = alturaTela - tamanho;
		
		if (up) { y -= velocidade; movendo = true; }
		if (down) { y += velocidade; movendo = true; }
		if (left) { x -= velocidade; movendo = true; }
		if (right) { x += velocidade; movendo = true; }
		
		  // animação só quando está andando
        if (movendo) {
            contador++;
            if (contador > 10) {
                frameAtual = (frameAtual + 1) % frames.length;
                contador = 0;
            }
        } else {
        	frameAtual = 0; //parado
        }
	}
	
	public void desenhar(Graphics g, boolean invencivel) {
		if (invencivel && (System.currentTimeMillis() / 100) % 2 == 0) {
			return; //efeito piscando	
		}
		
		 g.drawImage(frames[frameAtual], x, y, tamanho, tamanho, null);
	}

}
