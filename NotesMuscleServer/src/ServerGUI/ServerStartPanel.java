package ServerGUI;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ServerStartPanel extends JPanel{
    
    //private class atttributes
    JTextArea clientInfoTextArea;
    JButton closeServerBtn;
    final int WIDHT = 400;
    final int HEIGHT = 600;
    //____________________________________

    public ServerStartPanel(MainFrame mainFrame){
        setLayout(new BorderLayout());
        setSize(WIDTH, HEIGHT);

        clientInfoTextArea = new JTextArea();
        clientInfoTextArea.append("HELLO");

        closeServerBtn = new JButton("CLOSE THE SERVER");
        closeServerBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                mainFrame.closeServer();
            }
        });

        add(new JLabel(" ".repeat(50) + "CLIENTS INFORMATION"), BorderLayout.NORTH);
        add(clientInfoTextArea, BorderLayout.CENTER);
        add(closeServerBtn, BorderLayout.SOUTH);
    }

}
