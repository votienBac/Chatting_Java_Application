package pacman.com;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Client  implements ActionListener {
    JPanel p1;
    JTextField textField1;
    JButton jbtSend;
    static JPanel panelTxt;
    static JFrame frame = new JFrame();
    static Box vertical = Box.createVerticalBox();
    static Socket socket;
    static DataInputStream dis;
    static DataOutputStream dos;
    Boolean typing;
    Client(){


        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 400, 60);
        frame.add(p1);



        ImageIcon imageIcon = new ImageIcon("3.png");
        ImageIcon i3 = new ImageIcon(imageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        JLabel l1 = new JLabel(i3);
        l1.setBounds(5, 20, 20, 20);
        p1.add(l1);
        l1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        ImageIcon imageIcon2 = new ImageIcon("bichphuong1.png");
        ImageIcon i4 = new ImageIcon(imageIcon2.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
        JLabel l2 = new JLabel(i4);
        l2.setBounds(40, 0, 60, 60);
        p1.add(l2);

        JLabel l3 = new JLabel("Bui Bich Phuong");
        l3.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
        l3.setForeground(Color.WHITE);
        l3.setBounds(110, 15, 100, 20);
        p1.add(l3);

        JLabel l4 = new JLabel("Active now");
        l4.setFont(new Font("SAN_SERIF", Font.PLAIN, 13));
        l4.setForeground(Color.WHITE);
        l4.setBounds(110, 15, 100, 65);
        p1.add(l4);

        // mode active now
        Timer t = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!typing){
                    l4.setText("Active now");
                }
            }
        });
        t.setInitialDelay(2000); // first delay 2 seconds


        ImageIcon callIcon = new ImageIcon("phone-xxl.png");
        ImageIcon i5 = new ImageIcon(callIcon.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
        JLabel l5 = new JLabel(i5);
        l5.setBounds(320, 17, 25, 25);
        p1.add(l5);

        ImageIcon callVideoIcon = new ImageIcon("video-call-xxl.png");
        ImageIcon i6 = new ImageIcon(callVideoIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        JLabel l6 = new JLabel(i6);
        l6.setBounds(260, 15, 30, 30);
        p1.add(l6);

        panelTxt = new JPanel();
        //panelTxt.setBackground(Color.PINK);
        panelTxt.setBounds(5, 65,390, 480 );
        panelTxt.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        //panelTxt.setEditable(false);
        //.setLineWrap(true);
        //jtaMes.setWrapStyleWord(true);
        frame.add(panelTxt);


        textField1 = new JTextField();
        textField1.setBounds(10, 560, 310, 30);
        textField1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        frame.add(textField1);

        textField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                l4.setText("typing...");
                t.stop();
                typing = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                typing = false;
                if(!t.isRunning()){
                    t.start();
                }
            }
        });




        jbtSend = new JButton("Send");
        jbtSend.setForeground(Color.WHITE);
        jbtSend.setBackground(new Color(17, 94, 84));
        jbtSend.setBounds(325, 560, 70, 30);
        jbtSend.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
        jbtSend.addActionListener(this);
        frame.add(jbtSend);


        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(400, 600);
        frame.setLocation(800, 200);
        frame.setUndecorated(true);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        new Client().frame.setVisible(true);
        try{
            socket = new Socket("127.0.0.1", 1234);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            String msgInput ="";
            while(true){
                panelTxt.setLayout(new BorderLayout());
                msgInput = dis.readUTF();

                //jtaMes.setText(jtaMes.getText()+"\n"+msgInput);
                JPanel p2 = fomatLabel(msgInput);
                JPanel left = new JPanel(new BorderLayout());
                left.add(p2, BorderLayout.LINE_START);
                vertical.add(left);
                vertical.add(Box.createVerticalStrut(15));
                panelTxt.add(vertical, BorderLayout.PAGE_START);
                frame.validate();



            }
        }catch (Exception e){
            e.printStackTrace();
        }




    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(textField1.getText().isEmpty()) return;
        try{
            String out = textField1.getText();
            //jtaMes.setText(jtaMes.getText()+"\n\t\t\t"+out);
            JPanel p2 = fomatLabel(out);
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            panelTxt.add(vertical, BorderLayout.PAGE_START);

            dos.writeUTF(out);
            textField1.setText("");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public static JPanel fomatLabel(String out) {
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));// s???p x???p b??? c???c theo chi???u d???c

        JLabel l1 = new JLabel("<html><p style = \"width : 150px\">"+out+"</p></html>"); // xu???ng d??ng khi tin nh???n v?????t qu?? chi???u r???ng
        l1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        l1.setBackground(new Color(37, 211, 102));
        l1.setOpaque(true);//set color background for JLabel
        l1.setBorder(new EmptyBorder(15,15,15,50));//?????t m???t border v???i size m???c ?????nh
        p3.add(l1);

        Calendar cal = Calendar.getInstance(); // l???y th???i gian t??? timezone
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");//ki???u hi???n th??? th???i gian

        JLabel l2 = new JLabel() ;
        l2.setText(sdf.format(cal.getTime()));
        p3.add(l2);
        return p3;
    }
}
