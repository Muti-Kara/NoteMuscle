package ServerGUI;

import ServerBackEnd.MainServer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Container;


public class MainFrame extends JFrame{

    //the private instance attributes
    final int WIDTH = 500;
    final int HEIGHT = 600;
    MainServer mainServer;
    JLabel serverSetupLabel, serverLogoLabel;
    JButton startServerBtn;
    Container contentPane;
    CardLayout crd;
    JPanel mainFramePanel;

    ServerStartPanel serverStartPanel;
    //______________________________

    private static MainFrame mainFrame = null;

    public static void main(String[] args) {
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.setVisible(true);
    }

    public static MainFrame getInstance(){
        if(MainFrame.mainFrame == null){
            MainFrame.mainFrame = new MainFrame();
        }
        return MainFrame.mainFrame;
    }

    private MainFrame(){
        setMainFrame();
        createComponenets();
        contentPane.add("mainFramePanel", this.mainFramePanel);

        serverStartPanel = new ServerStartPanel(this);
        contentPane.add("serverStartPanel", this.serverStartPanel);
    }

    private void setMainFrame(){
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("NOTES MUSCLE SERVER");
        crd = new CardLayout();
        contentPane = getContentPane();
        contentPane.setLayout(crd);
    }

    private void createComponenets(){
        displayLogo();
        mainFramePanel = new JPanel();
        serverSetupLabel = new JLabel(" ".repeat(55) +"CLICK TO START THE SERVER");
        startServerBtn = new JButton("START SERVER");
        startServerBtn.setSize(50, 100);
        startServerBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(mainServer == null){
                    try{
                        mainServer = new MainServer();
                        JOptionPane.showMessageDialog(null, "SERVER STARTED AT PORT localhost:4444", "SERVER START", JOptionPane.INFORMATION_MESSAGE);
                        mainServer.start();
                        crd.next(contentPane);
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR!", JOptionPane.ERROR_MESSAGE);
                        serverSetupLabel.setText("SERVER FAILED TO START! TRY AGAIN");
                        mainServer.closeEveryThing();
                        mainServer = null;
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "SERVER IS ALREADY RUNNING", "ERROR!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        mainFramePanel.setLayout(new BorderLayout());
        mainFramePanel.add(serverSetupLabel, BorderLayout.NORTH);
        mainFramePanel.add(serverLogoLabel, BorderLayout.CENTER);
        mainFramePanel.add(startServerBtn, BorderLayout.SOUTH);
    }

    private void displayLogo(){
        BufferedImage logoImage;
        try{
            logoImage = ImageIO.read(new File(System.getProperty("user.dir") + "\\assets\\Logo.png"));
            serverLogoLabel = new JLabel(new ImageIcon(logoImage));
        }catch(IOException IOex){
            IOex.printStackTrace();
        }
    }

    public void closeServer(){
        mainServer.setServerState(false);
        mainServer.closeEveryThing();
        mainServer = null;
        crd.previous(contentPane);
    }
}