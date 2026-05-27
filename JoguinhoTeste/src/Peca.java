import java.awt.Graphics;
import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Peca extends Entidade {
	
	private static Random rand = new Random();
	private BufferedImage imagem;
	
	public Peca(int x, int y) {
		super(x, y, 32);
		
		try {
			imagem = ImageIO.read(getClass().getResource("/Sprites/LataTeste.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void reposicionar() {
		x = rand.nextInt(750);
		y = rand.nextInt(550);
	}
	
	public void desenhar(Graphics g) {
		g.drawImage(imagem, x, y, tamanho, tamanho, null);
	}

}
