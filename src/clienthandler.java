import java.net.*;
import java.io.*;
import java.util.*;

public class clienthandler{
    
    public static ArrayList<clienthandler> clientHandlers = new ArrayList<>();
    public static HashMap<String,String> point=new HashMap<String,String>();
    public ArrayList<Messagess> messages = new ArrayList<>();
    private Socket socket;
    private DataInputStream bufferedReader;
    private DataOutputStream bufferedWriter;
    private String clientusername;
    private int flag1=0;
    private String temp_username="";
    Vector<String> v = new Vector<>();

    public clienthandler(Socket socket) {
        try{
            this.socket =socket;
            this.bufferedWriter = new DataOutputStream(socket.getOutputStream());
            this.bufferedReader = new DataInputStream(socket.getInputStream());
            new Thread(new Runnable() {
                @Override
                public void run(){
                    try{
                        String password="",accessc="";
                        while(socket.isConnected()){
                            if(flag1==0){
                            accessc=bufferedReader.readUTF();
                            if(accessc.equals("0")){
                                temp_username=bufferedReader.readUTF();
                                password=bufferedReader.readUTF();
                                Registration registration=new Registration(temp_username,password);
                                if((registration.status).equals("0")){
                                    bufferedWriter.writeUTF(registration.status);
                                    bufferedWriter.flush();
                                }
                                else if((registration.status).equals("1")){
                                    bufferedWriter.writeUTF(registration.status);
                                    bufferedWriter.flush();
                                    bufferedWriter.writeUTF(registration.msg);
                                    bufferedWriter.flush();
                                }
                                accessc="";
                            }
                            else if(accessc.equals("1")){
                                temp_username=bufferedReader.readUTF();
                                password=bufferedReader.readUTF();
                                Login login=new Login(temp_username,password);
                                if((login.status).equals("0")){
                                    bufferedWriter.writeUTF(login.status);
                                    bufferedWriter.flush();
                                    backups(temp_username);
                                    flag1=1;
                                    point.put(temp_username,"0");
                                }
                                else if((login.status).equals("1")){
                                    bufferedWriter.writeUTF(login.status);
                                    bufferedWriter.flush();
                                    bufferedWriter.writeUTF(login.msg);
                                    bufferedWriter.flush();
                                }
                                accessc="";
                            }
                            }
                            else if(flag1==1){
                                String messageFromClient="";
                                messageFromClient = bufferedReader.readUTF();
                                // System.out.println(messageFromClient);
                                Messagess in=new Messagess(null,null);
                                Messagess in1=new Messagess(null,null);
                                int flag=0,f=0;
                                for(Messagess it:messages){
                                    if(it.userr.equals(messageFromClient) && it.users.equals(temp_username)){
                                        in=it;
                                        flag=1;
                                        // System.out.println(in.userr);
                                        break;
                                    }
                                }
                                for(Messagess it:messages){
                                    if(it.users.equals(messageFromClient) && it.userr.equals(temp_username)){
                                        in1=it;
                                        f=1;
                                        // System.out.println(in1.userr);
                                        break;
                                    }
                                }
                                if(flag==0){
                                    in=new Messagess(messageFromClient,temp_username);
                                    messages.add(in);
                                }
                                if(f==0){
                                    in1=new Messagess(temp_username,messageFromClient);
                                    messages.add(in1);
                                }
                                messageFromClient = bufferedReader.readUTF();
                                in.msginput(temp_username+": "+messageFromClient);
                                // System.out.println(in1.userr);
                                if((point.getOrDefault(in.userr, "0")).equals("0") && !messageFromClient.equals("")){
                                    point.put(in.userr,"1");
                                }
                                else{
                                    while((point.get(in.userr)).equals("1")){
                                        try {
                                            Thread.sleep(500); 
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        } 
                                    }
                                    point.put(in.userr,"1");
                                }
                                broadcastMessage(in.users,in.userr);
                                for(int i=0;i<20;i++){
                                    in.msgoutput(i);
                                    // in1.msgoutput(i);
                                    String te=in.msgprint;
                                    // if(!te1.equals(" ")) System.out.println(te1);
                                    // System.out.println(te);
                                    broadcastMessage(te,in.userr);
                                }
                                in1.msginput("You: "+messageFromClient);
                                messageFromClient="";
                                point.put(in.userr,"0");
                            }
                        }
                    }
                    catch(IOException e){
                        closeEverything(socket,bufferedReader,bufferedWriter);
                    }
                }
            }).start();

            // broadcastMessage("Server: "+clientusername+" has entered the game");
        }
        catch(IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }
    public void backups(String t_username){
        Backup backup=new Backup(t_username);
        int cn=backup.count();
        this.clientusername = t_username;
        clientHandlers.add(this);
        for(int i=0;i<cn;i++){
            backup.msgoutput(i);
            Messagess obj=new Messagess(t_username,backup.msgprint);
            messages.add(obj);
        }
        for(Messagess it:messages){
            if(it.userr.equals(t_username)){
                broadcastMessage(it.users,it.userr);
                for(int i=0;i<20;i++){
                    it.msgoutput(i);
                    // in1.msgoutput(i);
                    String te=it.msgprint;
                    // if(!te1.equals(" ")) System.out.println(te1);
                    // System.out.println(te);
                    broadcastMessage(te,it.userr);
                }
            }
        }                        
    }
    public void broadcastMessage(String messageToSend,String userr){
        for(clienthandler clientHandlerr:clientHandlers){
            try {
                if(clientHandlerr.clientusername.equals(userr) && clientHandlerr.socket.isConnected()){
                    // System.out.println("Working g");
                    clientHandlerr.bufferedWriter.writeUTF(messageToSend);
                    // clientHandler.bufferedWriter.newLine();
                    clientHandlerr.bufferedWriter.flush();
                }
            }
            catch(IOException e){
                closeEverything(socket,bufferedReader,bufferedWriter);
            }
        }
    }
    public void removeClientHandler(){
        clientHandlers.remove(this);
        // broadcastMessage("Server: "+clientusername+"has left the chat!");
    }
    public void closeEverything(Socket socket,DataInputStream bufferedReader,DataOutputStream bufferedWriter){
        removeClientHandler();
        try{
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
}
