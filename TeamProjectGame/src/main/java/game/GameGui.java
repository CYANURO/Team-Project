package game;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
/**
 * 
 * 
 */
public class GameGui extends JFrame implements Commons{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private WelcomePanel welcomePanel;
	private GameCanvas gameCanvas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameGui frame = new GameGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame with CardLayout. 
	 * 
	 */
	public GameGui() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, GAME_WIDTH, GAME_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		gameCanvas = new GameCanvas();
		contentPane.add(gameCanvas, "name_7159208762052");
		gameCanvas.start();
		gameCanvas.setFocusable(true);
		
		
		//welcomePanel = new WelcomePanel();
		//contentPane.add(welcomePanel, "name_6799939532017");
	}

}
