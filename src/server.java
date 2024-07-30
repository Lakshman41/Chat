import java.net.*;
import java.io.IOException;

public class server {
    private ServerSocket serverSocket;

    public server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public void startServer()
    {
        try{
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("A new client has connected");
                clienthandler clientHandlers = new clienthandler(socket);

                Thread thread =new Thread(clientHandlers);
                thread.start();
            }
        }
        catch(IOException e){
            closeServerSocket();
        }
    }
    public void closeServerSocket(){
        try{
            if(serverSocket !=null){
                serverSocket.close();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException{

        ServerSocket serverSocket = new ServerSocket(1234);
        server servers = new server(serverSocket);
        servers.startServer();
    }
}