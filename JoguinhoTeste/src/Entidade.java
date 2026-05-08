import java.awt.Rectangle;

public class Entidade {
	protected int x, y, tamanho;
	
	public Entidade(int x, int y, int tamanho) {
		this.x = x;
		this.y = y;
		this.tamanho = tamanho;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, tamanho, tamanho);
	}

}
