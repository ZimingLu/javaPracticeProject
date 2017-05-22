import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.net.*;
import java.awt.event.*;
import java.io.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;


public class QQClient{
	private JFrame frame = new JFrame("QQClient");
	//show the send info and receive info
	private static JTextArea ta = new JTextArea(30,30);
	//show the type area
	private JTextField tf = new JTextField(25);
	private JButton send = new JButton("send");
	private JPanel jp = new JPanel();
	private JOptionPane jop = new JOptionPane();

	public void init(PrintStream ps, String name){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//set enter as the default operation for button
		frame.getRootPane().setDefaultButton(send);
		frame.add(ta);
		jp.add(tf);
		jp.add(send);
		send.addActionListener(new sendAction(ps, name));
		frame.add(jp, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
	}

	private class sendAction implements ActionListener{
		PrintStream ps = null;
		String name;
		public sendAction(PrintStream ps, String name){
			this.ps = ps;
			this.name = name;
		}
		public void actionPerformed(ActionEvent e){
			try{
				if(tf.getText().equals("")){
				JOptionPane.showMessageDialog(frame,
					"The sent Message cannot be empty",
					"Warning",
					JOptionPane.WARNING_MESSAGE);			
				}
				else{
					String sendText = tf.getText();
					ta.append("I say: " + sendText + "\n");
					tf.setText("");
					ps.println(name + ":" + sendText);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}

		}
	}

private static class ClientThreadReceive implements Runnable{
	Socket s = null;
	BufferedReader br = null;
	public ClientThreadReceive(Socket s) throws Exception{
		this.s = s;
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));
	}

	public void run(){
		try{
			String line;
			while((line = br.readLine()) != null){
				ta.append(line + "\n");
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

	public static void main(String[] args) throws Exception{
		Socket s = new Socket("127.0.0.1", 30000);
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HHMMSS");
		String name = sdf.format(cal.getTime());
		PrintStream ps = new PrintStream(s.getOutputStream());
		new Thread(new ClientThreadReceive(s)).start();
		new QQClient().init(ps, name);
	}
}

