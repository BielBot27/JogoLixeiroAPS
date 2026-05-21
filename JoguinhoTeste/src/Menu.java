import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Menu extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage background;

	public Menu(Jogo frame) {
		setLayout(null);

		try {
			background = ImageIO.read(getClass().getResource("/Sprites/menu_bg.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		JButton botao = new JButton("Iniciar Jogo");
		botao.setBounds(285, 250, 200, 50);
		botao.setFont(new Font("Arial", Font.BOLD, 16));
		botao.setFocusPainted(false);
		botao.setBackground(new Color(30, 30, 30));
		botao.setForeground(Color.WHITE);
		botao.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		botao.setOpaque(true);

		botao.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				botao.setBackground(new Color(70, 130, 180));
			}
			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				botao.setBackground(new Color(30, 30, 30));
			}
		});

		botao.addActionListener(e -> frame.iniciarJogo());

		add(botao);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// imagem de fundo
		if (background != null) {
			g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
		}
	}
}
