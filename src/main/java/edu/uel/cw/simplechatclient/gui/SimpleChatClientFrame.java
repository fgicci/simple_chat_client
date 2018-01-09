package edu.uel.cw.simplechatclient.gui;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;

import edu.uel.cw.simplechatclient.client.ChatClientSocket;

public class SimpleChatClientFrame extends JFrame {

	private JLabel lblPlainText;
	private JTextField txtPlainText;
	private JTextArea txtMessagePool;
	private JButton btnSend;
	private ClientTask ct;
	
	public SimpleChatClientFrame() {
		initialize();
		defineLayout();
		setTitle("Simple Chat Client - #");
		pack();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public void initialize() {
		try {
			ct = new ClientTask();
			ct.execute();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(ERROR);
		}
		
		lblPlainText = new JLabel("Message:");
		
		txtPlainText = new JTextField(50);
		
		txtMessagePool = new JTextArea(60, 50);
		txtMessagePool.setEditable(false);
		
		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ct.sendMessage(txtPlainText.getText());
				txtPlainText.setText("");
			}
		});
	}
	
	public void defineLayout() {
		GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)	
				.addComponent(txtMessagePool)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(lblPlainText)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(txtPlainText)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(btnSend)
					)
				)
			)
		);

        layout.linkSize(SwingConstants.VERTICAL, lblPlainText, txtPlainText, btnSend);
        
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(txtMessagePool)
			)
			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(lblPlainText)
				.addComponent(txtPlainText)
				.addComponent(btnSend)
			)	
		);

        createBufferStrategy(1);
	}
	
	private class ClientTask extends SwingWorker<Void, String> {
		private ChatClientSocket ccs;
		
		public ClientTask() throws Exception {
			this(new ChatClientSocket());
		}
		
		public ClientTask(ChatClientSocket ccs) {
			this.ccs = ccs;
		}
		
		@Override
        protected Void doInBackground() throws HeadlessException, IOException {
            String line;
			while ((line = ccs.getMessage()) != null) {
            		try {
            			publish(line);
            		} catch (Exception ex) {
    					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    					System.exit(ERROR);
    				}
            }
			return null;
        }
 
		@Override
		protected void process(List<String> chunks) {
			for (String text : chunks) {
				txtMessagePool.append(text + "\n");
			}
		}
		
		public void sendMessage(String message) {
			ccs.sendMessage(message);
		}
    }
}
