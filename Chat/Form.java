import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.net.Socket;
import java.io.*;
import java.util.*;

public class Form implements ActionListener{
    
    JFrame Frame= new JFrame();
    // Container c;
    JLabel l=new JLabel();
    JTextField msg=new JTextField();
    JLabel Label_Button= new JLabel();
    JButton send= new JButton();
    JButton enter=new JButton();
    JButton back=new JButton();
    JButton back1=new JButton();
    int cn=25,i=0;
    JPanel Panel=new JPanel();
	JPanel Panel1=new JPanel();
	JPanel Panel2=new JPanel();
    JPanel Panel3=new JPanel();
    JPanel Panel4=new JPanel();
    JPanel Panel5=new JPanel();
    JPanel Panel6=new JPanel();
    JPanel Panel7=new JPanel();
    JPanel Panel8=new JPanel();
    public ArrayList<JButton> usera=new ArrayList<>();
    JScrollPane scrollPane = new JScrollPane();
    JScrollPane scrollPane1;
    JTextArea area=new JTextArea();
    JLabel user=new JLabel();
    JLabel user1=new JLabel();
    JButton Group1=new JButton();
    JButton newuser=new JButton();
    public String userrecieve=new String();
    public int bflag=0;
    public int k,temp;
    private JButton[] usern=new JButton[40];
    public int iter=0;
    JButton ENTER = new JButton();
    JDialog popup;
    JTextField input=new JTextField();

    public static ArrayList<Messagec> messagec=new ArrayList<>();

    CardLayout cardLayout=new CardLayout();
    public JPanel mainPanel;

    private Socket socket;
    private DataInputStream bufferedReader;
    private DataOutputStream bufferedWriter;
    private String username;

    Form(Socket socket,String username){
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
		Label_Button.setFocusable(false);
		
		Panel.setLayout(new BorderLayout());
		Panel.setBounds(0,10,500,50);
		
		Panel.add(Label_Button);
		Frame.add(Panel,BorderLayout.NORTH);

        try{
            this.socket = socket;
            this.bufferedWriter=new DataOutputStream(socket.getOutputStream());
            this.bufferedReader=new DataInputStream(socket.getInputStream());
            this.username=username;
            bufferedWriter.writeUTF(username);
            bufferedWriter.flush();
            
        }
        catch(IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);
        }

        new Thread(new Runnable() {
            @Override
            public void run(){
                String msgFromGroupChat;
                while(socket.isConnected()){
                    try{
                        String newuser=" ";
                        newuser=bufferedReader.readUTF();
                        userrecieve=newuser;
                        if(!newuser.equals(" ")) bflag=1;
                        Messagec in=new Messagec(null,null);
                        int flag=0;
                        for(Messagec it:messagec){
                            if(it.userr.equals(newuser) && it.users.equals(username)){
                                in=it;
                                flag=1;
                                break;
                            }
                        }
                        if(flag==0){
                            in=new Messagec(newuser,username);
                            messagec.add(in);
                        }
                        for(int i=0;i<20;i++){
                            msgFromGroupChat = bufferedReader.readUTF();
                            in.msginput(msgFromGroupChat);
                        }
                        try {
                            Thread.sleep(1000); 
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } 
                        bflag=1;
                        changearea(newuser,1);
                    }
                    catch(IOException e){
                        closeEverything(socket,bufferedReader,bufferedWriter);
                    }
                }
            }
        }).start();

        mainPanel = new JPanel(cardLayout);
        mainPanel.setVisible(true);

        JPanel Panelt1=createpanel1();
        JPanel Panelt2=createpanel3();
        JPanel Panelt3=createpanel2();

        mainPanel.add(Panelt1, "Panel 1");
        mainPanel.add(Panelt2, "Panel 2");
        mainPanel.add(Panelt3, "Panel 3");

        Frame.add(mainPanel);

    }
    public void actionPerformed(ActionEvent a){
        if(a.getSource()==send){
            try {
                input.setText("");
                if(socket.isConnected()){
                    String messageToSend = msg.getText();
                    area.append("You: "+messageToSend+"\n");
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
        for(temp=0;temp<=iter;temp++){
            if(a.getSource()==usern[temp]){
                area.setText("");
                user.setText(usern[temp].getText());
                userrecieve=usern[temp].getText();
                changearea(userrecieve,0);
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
            Messagec obj=new Messagec(userrecieve,username);
            messagec.add(obj);
            popup.dispose();
        }
    }
    void changearea(String recuser,int fl){
        Messagec in1=new Messagec(null,null);
        int f=0;
        for(Messagec it:messagec){
            if(it.userr.equals(recuser) && it.users.equals(username)){
                in1=it;
                f=1;
                break;
            }
        }
        if(f==0){
            in1=new Messagec(recuser,username);
            messagec.add(in1);
        }
        if(fl==1 && bflag==1){
        bflag=0;
        int tempind=-1;
        for(int i=0;i<iter;i++){
            if(usern[i].getText()==in1.userr) { 
                Panel7.remove(usern[i]);
                tempind=i;
                break;
            }
        }
        if(tempind==-1) {
            tempind=iter;
            addButton(in1.userr, tempind);
            iter++;
        }
        else{
            Panel7.add(usern[tempind],0);
            Panel7.revalidate();
            Panel7.repaint();
        }
        bflag=1;
        }
        area.setText(""); 
        for(int k=0;k<20;k++){
            in1.msgoutput(k);
            if(!(in1.msgprint).equals(" ")) area.append(in1.msgprint+"\n");
        }
    }
    JPanel createpanel1(){

        enter.setBackground(Color.BLACK);
		enter.setForeground(Color.white);
        enter.setPreferredSize(new Dimension(70,50));
        enter.setMaximumSize(new Dimension(70,50));
		enter.setFont(new Font("Ink Free",Font.BOLD,20));
		enter.setText("Enter");

        Panel3.setLayout(new BorderLayout());
		Panel3.setBounds(0,60,500,450);
		Panel3.add(enter,BorderLayout.SOUTH);

        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                cardLayout.show(mainPanel, "Panel 2");
            }
        });
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
        Panel4.setBounds(0,60,500,450);

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
        Panel8.setBounds(0,60,500,440);

        Panel8.add(Panel6,BorderLayout.NORTH);
        Panel8.add(scrollPane1,BorderLayout.LINE_END);

        back1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                cardLayout.show(mainPanel, "Panel 1");
            }
        });      
        return Panel8;
    }
    void addButton(String buttonName,int ind) {
        usern[ind]=new JButton();
        usern[ind].setPreferredSize(new Dimension(500,30));
        usern[ind].setMaximumSize(new Dimension(500,30));
        usern[ind].setAlignmentX(Component.CENTER_ALIGNMENT);
        usern[ind].setText(buttonName);
        usern[ind].addActionListener(this);
        usern[ind].setVisible(true);
        Panel7.add(usern[ind],0);
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter yout username for the group chat: ");
        String username = scanner.nextLine();
        Socket socket = new Socket("192.168.1.45",1234);
        Form frame=new Form(socket,username);
    }
}
