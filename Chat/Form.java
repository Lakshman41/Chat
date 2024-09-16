import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.net.*;
import java.io.*;
import java.util.*;

public class Form implements ActionListener{
    
    private JFrame Frame= new JFrame();
    private JLabel l=new JLabel();
    private JTextField msg=new JTextField();
    private JLabel Label_Button= new JLabel();
    private JButton send= new JButton();
    private JButton reg= new JButton();
    private JButton login= new JButton();
    private JButton enter=new JButton();
    private JButton back=new JButton();
    private JButton back1=new JButton();
    private JPanel Panel=new JPanel();
	private JPanel Panel1=new JPanel();
	private JPanel Panel2=new JPanel();
    private JPanel Panel3=new JPanel();
    private JPanel Panel4=new JPanel();
    private JPanel Panel5=new JPanel();
    private JPanel Panel6=new JPanel();
    private JPanel Panel7=new JPanel();
    private JPanel Panel8=new JPanel();
    private JScrollPane scrollPane = new JScrollPane();
    private JScrollPane scrollPane1;
    private JTextArea area=new JTextArea();
    private JLabel user=new JLabel();
    private JLabel user1=new JLabel();
    private JButton newuser=new JButton();
    private String userrecieve=new String();
    private int bflag=0;
    private int temp;
    private JButton[] usern=new JButton[40];
    private int iter=0;
    private JButton ENTER = new JButton();
    private JButton ENTER1 = new JButton();
    private JButton ENTER2 = new JButton();
    private JDialog popup,popup1,popup2;
    private JTextField input=new JTextField();
    private JTextField input1=new JTextField();
    private JTextField input2=new JTextField();
    private JTextField input3=new JTextField();
    private JTextField input4=new JTextField();
    private String frog="0";
    private JLabel error=new JLabel(); 
    private JLabel error1=new JLabel(); 
    private ArrayList<Buttons> buttons=new ArrayList<>();

    private ArrayList<Messagec> messagec=new ArrayList<>();

    private CardLayout cardLayout=new CardLayout();
    private JPanel mainPanel;

    private Socket socket;
    private DataInputStream bufferedReader;
    private DataOutputStream bufferedWriter;
    private String username;

    Form(Socket socket){
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		Frame.setSize(500,500);
		Frame.getContentPane().setBackground(Color.GRAY);
		Frame.setLayout(new BorderLayout());
		Frame.setVisible(true);

        Label_Button.setBackground(Color.BLACK);
		Label_Button.setForeground(Color.white);
		Label_Button.setFont(new Font("Ink Free",Font.BOLD,30));
        Label_Button.setHorizontalAlignment(JLabel.CENTER);
		Label_Button.setText("CHAT");
		Label_Button.setOpaque(true);
		Label_Button.setFocusable(true);
		
		Panel.setLayout(new BorderLayout());
		Panel.setBounds(0,0,500,50);
		
		Panel.add(Label_Button);
		Frame.add(Panel,BorderLayout.NORTH);

        try{
            this.socket = socket;
            this.bufferedWriter=new DataOutputStream(socket.getOutputStream());
            this.bufferedReader=new DataInputStream(socket.getInputStream());
            // this.username=username;
            // bufferedWriter.writeUTF(username);
            // bufferedWriter.flush();
        }
        catch(IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
        
        mainPanel = new JPanel(cardLayout);
        mainPanel.setVisible(true);

        JPanel Panelt1=createpanel1();
        JPanel Panelt2=createpanel3();
        JPanel Panelt3=createpanel2();

        mainPanel.add(Panelt1, "Panel 1");
        mainPanel.add(Panelt2, "Panel 2");
        mainPanel.add(Panelt3, "Panel 3");

        mainPanel.setBounds(0,60,500,440);

        Frame.add(mainPanel);

    }
    public void actionPerformed(ActionEvent a){
        if(a.getSource()==send){
            try {
                if(socket.isConnected() && !(msg.getText()).equals("")){
                    String messageToSend = msg.getText();
                    msg.setText("");
                    // System.out.prin
                    // area.append("You: "+messageToSend+"\n");
                    for(Messagec it:messagec){
                        if((it.userr).equals(userrecieve)){
                            it.msginput("You: "+messageToSend);
                            break;
                        }
                    }
                    bflag=1;
                    changearea(userrecieve, 1,username);
                    bufferedWriter.writeUTF(userrecieve);
                    bufferedWriter.flush();
                    bufferedWriter.writeUTF(messageToSend);
                    bufferedWriter.flush();
                }
            }
            catch(IOException e){
                closeEverything(socket,bufferedReader,bufferedWriter);
            }
        }
        // for(temp=0;temp<=iter;temp++){
        //     if(a.getSource()==usern[temp]){
        //         area.setText("");
        //         msg.setText("");
        //         user.setText(usern[temp].getText());
        //         userrecieve=usern[temp].getText();
        //         changearea(userrecieve,0,username);
        //         cardLayout.show(mainPanel, "Panel 3");
        //     }
        // }
        for(Buttons it:buttons){
            if(a.getSource()==it.button){
                area.setText("");
                msg.setText("");
                user.setText(it.name);
                userrecieve=it.name;
                it.msgs=0;
                it.button.setText(it.name);
                Panel7.revalidate();
                Panel7.repaint();
                changearea(userrecieve,0,username);
                cardLayout.show(mainPanel, "Panel 3");
            }
        }
        if(a.getSource()==newuser){
            popup = new JDialog(Frame, "Pop-up Window", true);
            popup.setSize(200, 200);
            popup.setLayout(new FlowLayout());
                
            JLabel label21 = new JLabel("Enter Friends Username");
            popup.add(label21);
                
            input.setPreferredSize(new Dimension(100,50));
            popup.add(input);
            ENTER.setText("Add");
            ENTER.addActionListener(this);
            popup.add(ENTER);
            popup.setLocationRelativeTo(Frame);
            popup.setVisible(true);
        }
        if(a.getSource()==ENTER){
            addButton(input.getText(),iter);
            userrecieve=input.getText();
            iter++;
            Messagec obj=new Messagec(userrecieve);
            messagec.add(obj);
            popup.dispose();
        }
        if(a.getSource()==reg){
            popup1 = new JDialog(Frame, "Pop-up Window", true);
            popup1.setSize(350, 400);
            // popup2.setLayout(new FlowLayout());
                
            JLabel label21 = new JLabel("Enter your details");
            label21.setBounds(100,10,150,30);
            
            JLabel label22 = new JLabel("Username");
            label22.setBounds(100,50,100,30);

            // input3.setPreferredSize(new Dimension(200,30));
            input1.setBounds(100,90,200,30);

            JLabel label23 = new JLabel("Password");
            label23.setBounds(100,130,100,30);

            // input4.setPreferredSize(new Dimension(200,30));
            input2.setBounds(100,170,200,30);

            error.setText(" ");
            error.setBounds(100,250,100,30);

            ENTER1.setText("Register");
            ENTER1.setBounds(100,210,100,30);
            ENTER1.addActionListener(this);

            popup1.add(label21);
            popup1.add(label22);
            popup1.add(input1);
            popup1.add(label23);
            popup1.add(input2);
            popup1.add(ENTER1);
            popup1.add(error);
            popup1.setLocationRelativeTo(Frame);
            popup1.setVisible(true);
        }
        if(a.getSource()==ENTER1){
            if((input1.getText()).equals("") || (input2.getText()).equals("")){
                error.setText("Enter valid values");
                popup1.revalidate();
                popup1.repaint();
            }
            else{
                try {
                    if(socket.isConnected()){
                        String messageToSend = input1.getText();
                        bufferedWriter.writeUTF("0");
                        bufferedWriter.flush();
                        bufferedWriter.writeUTF(messageToSend);
                        bufferedWriter.flush();
                        messageToSend = input2.getText();
                        bufferedWriter.writeUTF(messageToSend);
                        bufferedWriter.flush();
                        String msgrec;
                        msgrec=bufferedReader.readUTF();
                        if(msgrec.equals("0")){
                            popup1.dispose();
                        }
                        else if(msgrec.equals("1")){
                            msgrec=bufferedReader.readUTF();
                            error.setText(msgrec);
                            popup1.revalidate();
                            popup1.repaint();
                        }
                    }
                }
                catch(IOException e){
                    closeEverything(socket,bufferedReader,bufferedWriter);
                }
            }        
        }
        if(a.getSource()==login){
            popup2 = new JDialog(Frame, "Pop-up Window", true);
            popup2.setSize(350, 400);
            // popup2.setLayout(new FlowLayout());
                
            JLabel label21 = new JLabel("Enter your Details");
            label21.setBounds(100,10,150,30);
            
            JLabel label22 = new JLabel("Username");
            label22.setBounds(100,50,100,30);

            // input3.setPreferredSize(new Dimension(200,30));
            input3.setBounds(100,90,200,30);

            JLabel label23 = new JLabel("Password");
            label23.setBounds(100,130,100,30);

            // input4.setPreferredSize(new Dimension(200,30));
            input4.setBounds(100,170,200,30);

            error1.setText(" ");
            error1.setBounds(100,250,100,30);

            ENTER2.setText("Login");
            ENTER2.setBounds(100,210,100,30);
            ENTER2.addActionListener(this);

            popup2.add(label21);
            popup2.add(label22);
            popup2.add(input3);
            popup2.add(label23);
            popup2.add(input4);
            popup2.add(ENTER2);
            popup2.add(error1);
            popup2.setLocationRelativeTo(Frame);
            popup2.setVisible(true);
        }
        if(a.getSource()==ENTER2){
            if((input3.getText()).equals("") || (input4.getText()).equals("")){
                error1.setText("Enter valid values");
                popup2.revalidate();
                popup2.repaint();
            }
            else{
                try {
                    if(socket.isConnected()){
                        String messageToSend = input3.getText();
                        bufferedWriter.writeUTF("1");
                        bufferedWriter.flush();
                        bufferedWriter.writeUTF(messageToSend);
                        bufferedWriter.flush();
                        messageToSend = input4.getText();
                        bufferedWriter.writeUTF(messageToSend);
                        bufferedWriter.flush();
                        String msgrec;
                        msgrec=bufferedReader.readUTF();
                        if(msgrec.equals("0")){
                            popup2.dispose();
                            this.frog="1";
                            this.username=input3.getText();
                            user1.setText(username);
                            Panel6.revalidate();
                            Panel6.repaint();
                            connect(username);
                            cardLayout.show(mainPanel, "Panel 2");
                        }
                        else if(msgrec.equals("1")){
                            msgrec=bufferedReader.readUTF();
                            error1.setText(msgrec);
                            popup2.revalidate();
                            popup2.repaint();
                        }
                    }
                }
                catch(IOException e){
                    closeEverything(socket,bufferedReader,bufferedWriter);
                }
            }
        }
    }
    void connect(String t_username){
        new Thread(new Runnable() {
            @Override
            public void run(){
                String msgFromGroupChat="1";
                while(socket.isConnected()){
                    try{
                        // if(frog.equals("1")){
                        String newuser=" ";
                        newuser=bufferedReader.readUTF();
                        // userrecieve=newuser;
                        if(!newuser.equals(" ")) bflag=1;
                        Messagec in=new Messagec(null);
                        int flag=0;
                        for(Messagec it:messagec){
                            if(it.userr.equals(newuser)){
                                in=it;
                                flag=1;
                                break;
                            }
                        }
                        if(flag==0){
                            in=new Messagec(newuser);
                            messagec.add(in);
                        }
                        for(int i=0;i<20;i++){
                            msgFromGroupChat = bufferedReader.readUTF();
                            in.indxinput(i,msgFromGroupChat);
                        }
                        try {
                            Thread.sleep(500); 
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } 
                        bflag=1;
                        for(Buttons it:buttons){
                            if((it.name).equals(newuser)){
                                it.msgs=it.msgs+1;
                            }
                        }
                        if(!msgFromGroupChat.equals("1")) changearea(newuser,1,t_username);
                        msgFromGroupChat="1";
                    }
                    // }
                    catch(IOException e){
                        closeEverything(socket,bufferedReader,bufferedWriter);
                    }
                }
            }
        }).start();
    }
    void changearea(String recuser,int fl,String t_username){
        Messagec in1=new Messagec(null);
        int f=0;
        for(Messagec it:messagec){
            if(it.userr.equals(recuser)){
                in1=it;
                f=1;
                break;
            }
        }
        if(f==0){
            in1=new Messagec(recuser);
            messagec.add(in1);
        }
        if(fl==1 && bflag==1){
        bflag=0;
        int tempind=-1;
        Buttons temp_button=null;
        // for(int i=0;i<iter;i++){
        //     if((usern[i].getText()).equals(in1.userr)) { 
        //         Panel7.remove(usern[i]);
        //         tempind=i;
        //         break;
        //     }
        // }
        for(Buttons it:buttons){
            if(it.name.equals(in1.userr)){
                Panel7.remove(it.button);
                temp_button=it;
                tempind=1;
                break;
            }
        }
        if(tempind==-1) {
            tempind=iter;
            addButton(in1.userr, tempind);
            for(Buttons it:buttons){
                if(it.name.equals(in1.userr)){
                    // Panel7.remove(it.button);
                    temp_button=it;
                    // tempind=1;
                    break;
                }
            }
            iter++;
        }
        else{
            Panel7.add(temp_button.button,0);
            Panel7.revalidate();
            Panel7.repaint();
        }
        if(temp_button.msgs!=0 && !(user.getText()).equals(temp_button.name)) temp_button.button.setText(temp_button.name+"           "+temp_button.msgs);
        else temp_button.button.setText(temp_button.name);
        Panel7.revalidate();
        Panel7.repaint();
        }
        if(userrecieve.equals(recuser)){
            area.setText("");
            // Panel4.revalidate();
            // Panel4.repaint();
            for(int k=0;k<20;k++){
                in1.msgoutput(k);
                if(!(in1.msgprint).equals(" ")) area.append(in1.msgprint+"\n");
            }
        }
    }
    JPanel createpanel1(){

        reg.setBackground(Color.BLACK);
		reg.setForeground(Color.white);
        reg.setPreferredSize(new Dimension(100,100));
        reg.setMaximumSize(new Dimension(110,110));
        reg.setBounds(200, 100, 100, 50);
        // reg.setAlignmentX(Component.CENTER_ALIGNMENT);
        // reg.setAlignmentY(Component.CENTER_ALIGNMENT);
		reg.setFont(new Font("Ink Free",Font.BOLD,20));
		reg.setText("Registration");
        reg.setVisible(true);
        reg.addActionListener(this);

        login.setBackground(Color.BLACK);
		login.setForeground(Color.white);
        login.setPreferredSize(new Dimension(100,100));
        login.setMaximumSize(new Dimension(110,110));
        login.setBounds(200, 200, 100, 50);
        // login.setAlignmentX(Component.CENTER_ALIGNMENT);
        // login.setAlignmentY(Component.CENTER_ALIGNMENT);
		login.setFont(new Font("Ink Free",Font.BOLD,20));
		login.setText("Login");
        login.setVisible(true);
        login.addActionListener(this);

        // enter.setBackground(Color.BLACK);
		// enter.setForeground(Color.white);
        // // enter.setPreferredSize(new Dimension(70,50));
        // // enter.setMaximumSize(new Dimension(70,50));
        // enter.setBounds(200, 300, 100, 100);
        // // enter.setAlignmentX(Component.CENTER_ALIGNMENT);
		// // enter.setAlignmentY(Component.CENTER_ALIGNMENT);
        // enter.setFont(new Font("Ink Free",Font.BOLD,20));
		// enter.setText("Enter");
        // enter.setVisible(true);

        Panel3.setLayout(new BorderLayout());
        Panel3.setBackground(Color.white);
		// Panel3.setBounds(0,60,500,430);
        Panel3.add(reg,BorderLayout.NORTH);
        Panel3.add(login,BorderLayout.SOUTH);
		// Panel3.add(enter);

        // reg.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent a) {
        //         cardLayout.show(mainPanel, "Panel 2");
        //     }
        // });
        return Panel3;
    }
    JPanel createpanel2(){
        back.setFont(new Font("Ink Free",Font.BOLD,25));
        back.setText("<-");
        back.setPreferredSize(new Dimension(70, 50));
        back.setOpaque(true);
        back.setFocusable(true);

        user.setBackground(Color.YELLOW);
		user.setForeground(Color.black);
        user.setFont(new Font("Ink Free",Font.BOLD,20));
        user.setHorizontalAlignment(JLabel.CENTER);
        user.setPreferredSize(new Dimension(430, 50));

        Panel5.setLayout(new BorderLayout());
		Panel5.setBounds(0,60,500,50);

        Panel5.add(back,BorderLayout.WEST);
        Panel5.add(user,BorderLayout.EAST);

        area.setFont(new Font("Ink Free",Font.BOLD,20));

        scrollPane.setPreferredSize(new Dimension(500, 338));
        scrollPane.setViewportView(area);
		scrollPane.setOpaque(true);
		scrollPane.setFocusable(false);

        Panel1.setLayout(new BorderLayout());
		Panel1.setBounds(0,110,500,388);

        Panel1.add(scrollPane);

        l.setFont(new Font("Ink Free",Font.BOLD,15));
        l.setText("Enter message: ");
        l.setOpaque(true);
        l.setFocusable(true);

        msg.setFont(new Font("Ink Free",Font.BOLD,20));
        msg.setPreferredSize(new Dimension(200, 30));
        msg.setFocusable(true);

		send.setFont(new Font("Ink Free",Font.BOLD,10));
        send.setText("SEND");
		send.setOpaque(true);
		send.setFocusable(false);
		send.addActionListener(this);

        Panel2.setLayout(new BorderLayout());
		Panel2.setBounds(0,390,500,50);

        Panel2.add(l,BorderLayout.WEST);
        Panel2.add(msg,BorderLayout.CENTER);
        Panel2.add(send,BorderLayout.EAST);

        Panel4.setLayout(new BorderLayout());
        // Panel4.setBounds(0,60,500,440);

        Panel4.add(Panel5,BorderLayout.NORTH);
        Panel4.add(Panel1,BorderLayout.CENTER);
        Panel4.add(Panel2,BorderLayout.AFTER_LAST_LINE);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                cardLayout.show(mainPanel, "Panel 2");
            }
        });
        return Panel4;
    }
    JPanel createpanel3(){
        back1.setFont(new Font("Ink Free",Font.BOLD,25));
        back1.setText("<-");
        back1.setPreferredSize(new Dimension(70, 50));

        user1.setBackground(Color.LIGHT_GRAY);
		user1.setForeground(Color.black);
        user1.setFont(new Font("Ink Free",Font.BOLD,20));
        user1.setText(username);
        user1.setHorizontalAlignment(JLabel.CENTER);
        user1.setPreferredSize(new Dimension(360, 50));

        newuser.setBackground(Color.GRAY);
		newuser.setForeground(Color.white);
        newuser.setPreferredSize(new Dimension(70,50));
        newuser.setMaximumSize(new Dimension(70, 50));
        newuser.setAlignmentX(Component.CENTER_ALIGNMENT);
        newuser.setFont(new Font("Ink Free",Font.BOLD,15));
        newuser.addActionListener(this);
        newuser.setText("+");
        newuser.setVisible(true);

        Panel6.setLayout(new BorderLayout());
		Panel6.setBounds(0,60,500,50);

        Panel6.add(back1,BorderLayout.WEST);
        Panel6.add(user1,BorderLayout.CENTER);
        Panel6.add(newuser,BorderLayout.EAST);

        Panel7.setLayout(new BoxLayout(Panel7, BoxLayout.Y_AXIS));

        scrollPane1 = new JScrollPane(Panel7);

        scrollPane1.setBackground(Color.GRAY);
		scrollPane1.setForeground(Color.white);
        scrollPane1.setPreferredSize(new Dimension(500, 388));
		scrollPane1.setOpaque(true);
		scrollPane1.setFocusable(false);

        Panel8.setLayout(new BorderLayout());
        // Panel8.setBounds(0,60,500,440);

        Panel8.add(Panel6,BorderLayout.NORTH);
        Panel8.add(scrollPane1,BorderLayout.LINE_END);

        // back1.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent a) {
        //         cardLayout.show(mainPanel, "Panel 1");
        //     }
        // });      
        return Panel8;
    }
    void addButton(String buttonName,int ind) {
        usern[ind]=new JButton();
        Buttons tButton=new Buttons(usern[ind],buttonName);
        // usern[ind].setPreferredSize(new Dimension(500,30));
        // usern[ind].setMaximumSize(new Dimension(500,30));
        // usern[ind].setAlignmentX(Component.CENTER_ALIGNMENT);
        // usern[ind].setText(buttonName);
        (tButton.button).addActionListener(this);
        // usern[ind].setVisible(true);
        buttons.add(tButton);
        Panel7.add(tButton.button,0);
        Panel7.revalidate();
        Panel7.repaint();
    }
    public void closeEverything(Socket socket,DataInputStream bufferedReader,DataOutputStream bufferedWriter){
        try{
//            Frame.dispose();
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
            if(socket!=null){
                socket.close();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException{
        Socket socket = new Socket("localhost",1234);
        Form frame=new Form(socket);
    }
}
