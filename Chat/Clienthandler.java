import java.net.*;
import java.io.*;
import java.util.*;

public class Clienthandler implements Runnable {
    
    public static ArrayList<Clienthandler> clientHandlers = new ArrayList<>();
    public static ArrayList<Messages> messages = new ArrayList<>();
    private Socket socket;
    private DataInputStream bufferedReader;
    private DataOutputStream bufferedWriter;
    private String clientusername;
    private String[] buttons=new String[9];
    Vector<String> v = new Vector<>();

    public Clienthandler(Socket socket) {
        try{
            this.socket =socket;
            this.bufferedWriter = new DataOutputStream(socket.getOutputStream());
            this.bufferedReader = new DataInputStream(socket.getInputStream());
            this.clientusername = bufferedReader.readUTF();
            clientHandlers.add(this);
            // broadcastMessage("Server: "+clientusername+" has entered the game");
            for(Messages it:messages){
                if(it.userr.equals(this.clientusername)){
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
        catch(IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }
    @Override
    public void run(){
        String messageFromClient;
        // System.out.println("Working ");
        while(socket.isConnected()){
            try{
                messageFromClient = bufferedReader.readUTF();
                // System.out.println(messageFromClient);
                Messages in=new Messages(null,null);
                Messages in1=new Messages(null,null);
                int flag=0,f=0;
                for(Messages it:messages){
                    if(it.userr.equals(messageFromClient) && it.users.equals(clientusername)){
                        in=it;
                        flag=1;
                        System.out.println(in.userr);
                        break;
                    }
                }
                for(Messages it:messages){
                    if(it.users.equals(messageFromClient) && it.userr.equals(clientusername)){
                        in1=it;
                        f=1;
                        System.out.println(in1.userr);
                        break;
                    }
                }
                if(flag==0){
                    in=new Messages(messageFromClient,clientusername);
                    messages.add(in);
                }
                if(f==0){
                    in1=new Messages(clientusername,messageFromClient);
                    messages.add(in1);
                }
                messageFromClient = bufferedReader.readUTF();
                in.msginput(clientusername+": "+messageFromClient);
                // System.out.println(in1.userr);
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
            }
            catch(IOException e){
                closeEverything(socket,bufferedReader,bufferedWriter);
                break;
            }
        }
    }
    public void broadcastMessage(String messageToSend,String userr){
        for(Clienthandler clientHandler:clientHandlers){
            try {
                if(clientHandler.clientusername.equals(userr)){
                    // System.out.println("Working g");
                    clientHandler.bufferedWriter.writeUTF(messageToSend);
                    // clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
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
