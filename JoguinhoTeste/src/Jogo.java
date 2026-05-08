import javax.swing.JFrame;

public class Jogo extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public Jogo() { 
		// operações da Janela do Jogo.
		setTitle("The Square");
		setSize(800,600); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setLocationRelativeTo(null);
		setResizable(false); 
		
		add(new Menu(this));
		
		setVisible(true);
	}
	
	public void iniciarJogo() {
		getContentPane().removeAll();
		Tela tela = new Tela();
	    add(tela);

	    revalidate();
	    repaint();

	    tela.requestFocusInWindow(); //foca na janela do jogo, pois estava bugado.
	}
	
	public static void main(String[] args) {
		new Jogo();
	}

}
