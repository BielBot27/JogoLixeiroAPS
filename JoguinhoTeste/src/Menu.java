import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	public Menu(Jogo frame) {
		setLayout(null);
		
		JButton botao = new JButton("Iniciar Jogo");
		botao.setBounds(285, 250, 200, 50);
		
		botao.addActionListener(e -> frame.iniciarJogo());
		
		add(botao);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString("Lixeiro", 300, 150);
	}
}
