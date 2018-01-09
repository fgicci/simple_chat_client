package edu.uel.cw.simplechatclient.app;

import javax.swing.UIManager;

import edu.uel.cw.simplechatclient.gui.SimpleChatClientFrame;

public class SimpleChatClient {

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
				} catch (Exception ex) {
				    ex.printStackTrace();
                }
                new SimpleChatClientFrame().setVisible(true);
            }
		});
	}

}
