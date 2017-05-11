/*
 * MainFrm.java
 *
 * Created on __DATE__, __TIME__
 */

package x224;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JLabel;

/**
 *
 * @author  __USER__
 */
public class MainFrm extends javax.swing.JFrame implements ActionListener,
		KeyListener {

	/** Creates new form MainFrm */
	public MainFrm() {
		initComponents();

		GridLayout layout = new GridLayout(ROW, COL);

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		jMainTbl.addKeyListener(this);
		jMainTbl.setFocusable(true);

		jMainTbl.setBackground(new java.awt.Color(0, 102, 102));
		jMainTbl.setLayout(layout);
		jButtonStart.setActionCommand("jButtonStart");
		jButtonStart.setText("Start !");
		jButtonStart.addActionListener(this);
		jButtonPause.addActionListener(this);
		jTextPaneSig.setText("Developed By KEVX\nPowered BY:\n\b\bJavaSE-1.6");

		controller.setScoreLabel(jLabelScore);
		controller.setnxtShapePane(jLayeredPaneNext);

		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				gbox[i][j] = new JLabel();
				gbox[i][j].setOpaque(true);
				//gbox[i][j].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
				gbox[i][j].setBackground(Color.BLACK);
				jMainTbl.add(gbox[i][j]);
			}
		}

	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jMainTbl = new javax.swing.JLayeredPane();
		jButtonStart = new javax.swing.JButton();
		jButtonPause = new javax.swing.JButton();
		jLabelTitle = new javax.swing.JLabel();
		jLabelScore = new javax.swing.JLabel();
		jLayeredPaneNext = new javax.swing.JLayeredPane();
		jScrollPaneSig = new javax.swing.JScrollPane();
		jTextPaneSig = new javax.swing.JTextPane();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("RussiaToy");
		setResizable(false);

		jMainTbl.setBackground(new java.awt.Color(0, 102, 102));
		jMainTbl.setOpaque(true);

		jButtonStart.setActionCommand("jButtonStart");
		jButtonStart.setLabel("Start !");

		jButtonPause.setText("Pause");

		jLabelTitle.setFont(new java.awt.Font("Calibri", 1, 24));
		jLabelTitle.setForeground(new java.awt.Color(51, 51, 255));
		jLabelTitle.setText("Russia Toy");

		jLabelScore.setForeground(new java.awt.Color(255, 0, 0));
		jLabelScore.setText("Score:");

		jLayeredPaneNext.setBackground(new java.awt.Color(255, 255, 255));
		jLayeredPaneNext.setBorder(javax.swing.BorderFactory
				.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
		jLayeredPaneNext.setOpaque(true);

		jScrollPaneSig
				.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		jTextPaneSig.setBackground(new java.awt.Color(0, 0, 255));
		jTextPaneSig.setFont(new java.awt.Font("Tahoma", 0, 10));
		jTextPaneSig.setForeground(new java.awt.Color(255, 255, 255));
		jScrollPaneSig.setViewportView(jTextPaneSig);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout
				.setHorizontalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addComponent(
																				jLabelTitle,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				253,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jLabelScore,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				79,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addComponent(
																				jMainTbl,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				240,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGroup(
																				layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addGroup(
																								layout
																										.createSequentialGroup()
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																												38,
																												Short.MAX_VALUE)
																										.addGroup(
																												layout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.TRAILING)
																														.addComponent(
																																jLayeredPaneNext,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																80,
																																Short.MAX_VALUE)
																														.addGroup(
																																layout
																																		.createParallelGroup(
																																				javax.swing.GroupLayout.Alignment.TRAILING,
																																				false)
																																		.addComponent(
																																				jButtonStart,
																																				javax.swing.GroupLayout.DEFAULT_SIZE,
																																				javax.swing.GroupLayout.DEFAULT_SIZE,
																																				Short.MAX_VALUE)
																																		.addComponent(
																																				jButtonPause,
																																				javax.swing.GroupLayout.DEFAULT_SIZE,
																																				80,
																																				Short.MAX_VALUE))))
																						.addGroup(
																								layout
																										.createSequentialGroup()
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												jScrollPaneSig,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												111,
																												Short.MAX_VALUE)))))
										.addContainerGap()));
		layout
				.setVerticalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																jLabelTitle,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																41,
																Short.MAX_VALUE)
														.addComponent(
																jLabelScore))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addComponent(
																				jButtonStart)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				jButtonPause)
																		.addGap(
																				18,
																				18,
																				18)
																		.addComponent(
																				jLayeredPaneNext,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				70,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(
																				jScrollPaneSig,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				60,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addComponent(
																jMainTbl,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																400,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainFrm().setVisible(true);
			}
		});
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton jButtonPause;
	private javax.swing.JButton jButtonStart;
	private javax.swing.JLabel jLabelScore;
	private javax.swing.JLabel jLabelTitle;
	private javax.swing.JLayeredPane jLayeredPaneNext;
	private javax.swing.JLayeredPane jMainTbl;
	private javax.swing.JScrollPane jScrollPaneSig;
	private javax.swing.JTextPane jTextPaneSig;

	// End of variables declaration//GEN-END:variables

	@Override
	public void actionPerformed(ActionEvent e) {
		jMainTbl.requestFocus();
		if (e.getSource().equals(jButtonStart)) {
			if (toy == null || !toy.isAlive()) {
				jButtonStart.setText("Stop!");
				toy = new Thread(controller);
				controller.setStopped(false);
				toy.start();
				System.out.println("Start!");
				return;
			}

			if (toy.isAlive()) {
				jButtonStart.setText("Start !");
				jButtonPause.setText("Pause");
				controller.setStopped(true);
			}
		} else if (e.getSource().equals(jButtonPause)) {
			if (controller.isPaused) {
				jButtonPause.setText("Pause");
			} else {
				jButtonPause.setText("Resume");
			}
			controller.isPaused = !controller.isPaused;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (toy != null) {
			if (toy.isAlive()) {
				int kc = e.getKeyCode();
				if (kc == KeyEvent.VK_LEFT || kc == KeyEvent.VK_RIGHT
						|| kc == KeyEvent.VK_UP || kc == KeyEvent.VK_DOWN) {
					controller.turnShape(kc);
				}
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_HOME) {
			System.out.println("## Use this Key to trigger debug procedure");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	private final int ROW = 20;
	private final int COL = 12;
	private JLabel gbox[][] = new JLabel[ROW][COL];
	private MyController controller = new MyController(gbox, ROW, COL);
	private Thread toy;
}