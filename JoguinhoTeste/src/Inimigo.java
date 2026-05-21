import java.util.Random;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Inimigo extends Entidade {

	private int velocidade = 3;
	private int direcao = 1; // 1 = direita | -1 = esquerda
	private BufferedImage sprite;
	private BufferedImage[] frames;
	private TipoInimigo tipo;

	private int contadorDrop = 0;
	private int tempoDrop = 60; // quanto maior, mais demora pra soltar peça
	
	public Inimigo(int larguraTela, int yFixo) {
		super(0, yFixo, 64);
		
		Random rand = new Random();
		
		y = yFixo;
		
	    if (y == 100 || y == 300) {
	    	direcao = 1;
	    	x = -tamanho;
	    } else {
	    	direcao = -1;
	    	x = larguraTela;
	    }
		
		velocidade = 2 + rand.nextInt(3);
		
		if (y == 100 || y == 300) {
		    direcao = 1; // direita
		} else {
		    direcao = -1; // esquerda
		}
		
		//lado de spawn do inimigo
		if (rand.nextBoolean()) {
			//esquerda
			x = -tamanho;
			direcao = 1;
		} else {
			//direita
			x = larguraTela;
			direcao = -1;
		}
		
		try {
		    sprite = ImageIO.read(getClass().getResource("/Sprites/carrinho.png"));
		    
		    frames = new BufferedImage[4];
		    
		    for (int i = 0; i < 4; i++) {
		    	frames[i] = sprite.getSubimage(i * 32, 0, 32, 32);
		    }
		    
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	public void aumentarVelocidade() {
	    if (velocidade < 10) {
	        velocidade++;
	    }
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean atualizar(boolean playerMovendo) {
		
		// movimento lateral
		x += velocidade * direcao;
		
		// 🔁 loop de tela (AQUI 👇)
	    int larguraTela = 800;

	    if (x > larguraTela) {
	        x = -tamanho;
	    } else if (x < -tamanho) {
	        x = larguraTela;
	    }

		// lógica de soltar peça (só dropa se o player estiver se movendo)
		if (playerMovendo) {
			contadorDrop++;

			if (contadorDrop >= tempoDrop) {
				contadorDrop = 0;
				return true; // sinaliza que deve criar uma peça
			}
		}

		return false;
	}
	
	public enum TipoInimigo {
	    CARRO,
	    POLICIA,
	    CAMINHAO
	}

	public void desenhar(Graphics g) {
		
		int frameAtual = (int)((System.currentTimeMillis() / 150) % 4);
		
		g.drawImage(frames[frameAtual], x, y, tamanho, tamanho, null);
	}

}
