import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Tela extends JPanel implements ActionListener, KeyListener {

	private BufferedImage background;

	private static final long serialVersionUID = 1L;
	private Player player;
	private ArrayList<Inimigo> inimigos;
	private ArrayList<Peca> pecas;
	private Timer timer;
	private int pontos = 0;
	private boolean gameOver = false;
	private int vida = 3;
	private boolean invencivel = false;
	private int tempoInvencivel = 0;
	private JButton botaoReiniciar;
	private BufferedImage coracao;
	private boolean pausado = false;
	private int spawnTimer = 0;
	private int spawnDelay = 120; //tempo inicial
	private int nivel = 1;
	private int pontosParaProximoNivel = 10;
	private boolean levelUpAtivo = false;
	private int tempoLevelUp = 0;
	private int recorde = 0;

	private boolean up, down, left, right;

	public Tela() {

		carregarRecorde();
		setFocusable(true);
		addKeyListener(this);
		setLayout(null); //posicionamento do botão reiniciar
		
		//botao reiniciar
		botaoReiniciar = new JButton("Reiniciar");
		botaoReiniciar.setBounds(280, 350, 200, 50);
		botaoReiniciar.setVisible(false);
		botaoReiniciar.setFocusPainted(false);
		botaoReiniciar.setBorderPainted(false);
		botaoReiniciar.setContentAreaFilled(false);
		botaoReiniciar.setOpaque(true);

		botaoReiniciar.setBackground(new Color(30, 30, 30));
		botaoReiniciar.setForeground(Color.WHITE);
		botaoReiniciar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

		botaoReiniciar.setFont(new Font("Arial", Font.BOLD, 18));
		
		botaoReiniciar.addActionListener(e -> reiniciarJogo());
		
		botaoReiniciar.addMouseListener(new MouseAdapter() {

		    @Override
		    public void mouseEntered(MouseEvent e) {
		        botaoReiniciar.setBackground(new Color(70, 130, 180)); // azul
		        botaoReiniciar.setForeground(Color.WHITE);
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        botaoReiniciar.setBackground(new Color(30, 30, 30));
		        botaoReiniciar.setForeground(Color.WHITE);
		    }
		});
		
		add(botaoReiniciar);

		player = new Player(100, 100);
		pecas = new ArrayList<>();
		inimigos = new ArrayList<>();

		// cria Inimigos
		int[] faixas = {200, 300, 400};

		for (int y : faixas) {
		    inimigos.add(new Inimigo(800, y));
		}

		try {
			background = ImageIO.read(getClass().getResource("/sprites/background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
		    coracao = ImageIO.read(getClass().getResource("/sprites/heart.png"));
		} catch (Exception e) {
		    e.printStackTrace();
		}

		timer = new Timer(16, this);
		timer.start();
	}
	
	public void reiniciarJogo() {
		vida = 3;
		pontos = 0;
		gameOver = false;
		
		invencivel = false;
		tempoInvencivel = 0;
		
		// reset de nivel
		nivel = 1;
		pontosParaProximoNivel = 10;
		
		levelUpAtivo = false;
		tempoLevelUp = 0;
		spawnDelay = 120;
		
		pecas.clear();
		inimigos.clear();
		
		//recriar inimigos
		inimigos.add(new Inimigo(800, 200));
		inimigos.add(new Inimigo(800, 300));
		inimigos.add(new Inimigo(800, 400));
		
		player = new Player(100, 100);
		
		botaoReiniciar.setVisible(false);
		
		timer.start();
		requestFocusInWindow();
	}
	
	public void spawnarInimigo() {
		Random rand = new Random();
		
		int[] faixas = {100, 200, 300, 400};
	    int y = faixas[rand.nextInt(faixas.length)];
	    
	    // verifica se já tem um inimigo na faixa
	    for (Inimigo i: inimigos) {
	    	if (i.getY() == y) {
	    		if (Math.abs(i.getX() - 0) < 150 || Math.abs(i.getX() - 800) < 150) {
	    			return; //cancela o spawn
	    		}
	    	}
	    }
	    
	    inimigos.add(new Inimigo(800, 600));
	}
	
	public void aumentarDificuldade() {
		spawnDelay = Math.max(30, 120 - nivel * 5);

	    // diminui tempo de spawn
	    if (spawnDelay > 40) {
	        spawnDelay -= 10;
	    }

	    // aumenta velocidade dos inimigos
	    for (Inimigo i : inimigos) {
	        i.aumentarVelocidade();
	    }

	    // adiciona mais inimigos
	    if (inimigos.size() < 10) {
	        int[] faixas = {100, 200, 300, 400};
	        Random rand = new Random();
	        int y = faixas[rand.nextInt(faixas.length)];

	        inimigos.add(new Inimigo(800, y));
	    }
	}
	
	public void subirNivel() {
	    nivel++;

	    pontosParaProximoNivel += 10; // aumenta dificuldade progressiva

	    levelUpAtivo = true;
	    tempoLevelUp = 120; // duração da mensagem (~2 segundos)

	    aumentarDificuldade();
	}
	
	public void carregarRecorde() {
	    try {
	        File file = new File("recorde.txt");

	        if (!file.exists()) {
	            file.createNewFile();
	        }

	        BufferedReader br = new BufferedReader(new FileReader(file));
	        String linha = br.readLine();

	        if (linha != null) {
	            recorde = Integer.parseInt(linha);
	        }

	        br.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void salvarRecorde() {
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("recorde.txt"));
			bw.write(String.valueOf(recorde));
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (pontos >= pontosParaProximoNivel) {
		    subirNivel();
		}
		
		if (levelUpAtivo) {
		    tempoLevelUp--;

		    if (tempoLevelUp <= 0) {
		        levelUpAtivo = false;
		    }
		}
		
		spawnTimer++;

		if (spawnTimer >= spawnDelay) {
		    spawnarInimigo();
		    spawnTimer = 0;
		}
		
		if (spawnDelay > 40) {
		    spawnDelay--; // fica mais rápido com o tempo
		}

		if (!gameOver && !pausado) {

			player.mover(up, down, left, right);

			// atualizar inimigos
			boolean playerMovendo = up || down || left || right;
			for (Inimigo i : inimigos) {
				if (i.atualizar(playerMovendo)) {
					pecas.add(new Peca(i.getX() + 10, i.getY() + 10)); // inimigo soltou peça
				}
			}

			// verificar colisão com todas as peças
			for (int i = 0; i < pecas.size(); i++) {
				if (player.getBounds().intersects(pecas.get(i).getBounds())) {
					pontos++;
					pecas.remove(i);
					i--;
				}
			}

			// colisão com inimigos
			for (Inimigo i : inimigos) {
				if (player.getBounds().intersects(i.getBounds())) {
					if (!invencivel) {
						vida--;
						invencivel = true;
						tempoInvencivel = 60; // ~1 segundo
					}
				}
			}

			if (invencivel) {
				tempoInvencivel--;

				if (tempoInvencivel <= 0) {
					invencivel = false;
				}
			}
			
			if (vida <= 0) {
				gameOver = true;
				timer.stop();
				botaoReiniciar.setVisible(true);
			}
			
			if (vida <= 0) {
			    gameOver = true;
			    timer.stop();

			    if (pontos > recorde) {
			        recorde = pontos;
			        salvarRecorde();
			    }
			}
			
		}

		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// desenha o fundo
		g.drawImage(background, 0, 0, getWidth(), getHeight(), null);

		// desenhar inimigos
		for (Inimigo i : inimigos) {
			i.desenhar(g);
		}

		// desenhar pecas
		for (Peca p : pecas) {
			p.desenhar(g);
		}

		// player invencibilidade
		player.desenhar(g, invencivel);

		// HUD
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.setColor(Color.WHITE);

		g.drawString("Pontos: " + pontos, 10, 20);
		
		g.setFont(new Font("Arial", Font.BOLD, 18));
		g.setColor(Color.WHITE);
		g.drawString("Nível: " + nivel, 10, 90);
		
		// mostrar recorde
		g.setColor(Color.YELLOW);
		g.drawString("Recorde: " + recorde, 660, 20);
		
		// ícones de vida
	    int x = 10;
	    int y = 30;

	    for (int i = 0; i < vida; i++) {
	        g.drawImage(coracao, x + i * 40, y, 32, 32, null);
	    }
	    
	    if (levelUpAtivo) {
	        g.setFont(new Font("Arial", Font.BOLD, 40));
	        g.setColor(Color.YELLOW);
	        g.drawString("LEVEL UP!", 290, 300);
	        
	        int yTexto = 250 - (120 - tempoLevelUp);

	        g.drawString("LEVEL UP!", 290, yTexto);
	        
	        if (tempoLevelUp % 20 < 10) {
	            g.setColor(Color.YELLOW);
	        } else {
	            g.setColor(Color.ORANGE);
	        }
	    }
	    
	    // pausa
	    if (pausado) {
	        g.setFont(new Font("Arial", Font.BOLD, 40));
	        g.setColor(Color.BLACK);
	        g.drawString("PAUSADO", 290, 300);
	    }

		// game over
		if (gameOver) {
			
			//fundo escuro transparente
			g.setColor(new Color(0, 0, 0, 180));
			g.fillRect(0, 0, getWidth(), getHeight());
			
			// texto GAME OVER
		    g.setColor(Color.RED);
		    g.setFont(new Font("Arial", Font.BOLD, 50));
		    g.drawString("GAME OVER", 210, 200);
			
			// mostrar nivel
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 18));
			g.drawString("Nível: " + nivel, 10, 90);

		    // pontuação final
		    g.setColor(Color.WHITE);
		    g.setFont(new Font("Arial", Font.BOLD, 25));
		    g.drawString("Pontuação: " + pontos, 290, 260);

		    // recorde
		    g.drawString("Recorde: " + recorde, 290, 300);

		    // mostra botão
		    botaoReiniciar.setVisible(true);
		}
	}
		
	// teclas para movimentação do boneco
	@Override
	public void keyPressed(KeyEvent e) {
	    int key = e.getKeyCode();

	    if (key == KeyEvent.VK_ESCAPE && !gameOver) {
	        pausado = !pausado;
	        return;
	    }

	    if (pausado) return;

	    if (key == KeyEvent.VK_W) up = true;
	    if (key == KeyEvent.VK_S) down = true;
	    if (key == KeyEvent.VK_A) left = true;
	    if (key == KeyEvent.VK_D) right = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_W)
			up = false;
		if (e.getKeyCode() == KeyEvent.VK_S)
			down = false;
		if (e.getKeyCode() == KeyEvent.VK_A)
			left = false;
		if (e.getKeyCode() == KeyEvent.VK_D)
			right = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
