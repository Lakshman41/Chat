import java.util.ArrayList;
import java.sql.*;

public class Messagess {
    public String userr,users,msgprint;
    // public String[] msgs=new String[20];
    public String url="jdbc:postgresql://localhost:5432/Hizenberg";
    public String usename="postgres";
    public String password="123456";
    public Connection con;
    public Statement st;
    public String temp1;
    public static int count;
    public int count1;
    private ArrayList<String> msgs=new ArrayList<>();
    public Messagess(String userr,String users){
        this.userr=userr;
        this.users=users;
        for(int i=0;i<20;i++){
            msgs.add(" ");
        }
        try{
            con=DriverManager.getConnection(url,usename,password);
            st= con.createStatement();
            String sql="SELECT COUNT(*) FROM Username;";
            ResultSet rs=st.executeQuery(sql);
            rs.next();
            String output =rs.getString(1);
            count=Integer.parseInt(output);
            if(rs!=null) rs.close();
            sql=String.format("SELECT COUNT(*) FROM Username WHERE userr = '%s' and users = '%s' LIMIT 20;",userr,users);
            rs=st.executeQuery(sql);
            rs.next();
            output =rs.getString(1);
            count1=Integer.parseInt(output);
            if(rs!=null) rs.close();
            sql=String.format("SELECT Message FROM Username WHERE userr = '%s' and users = '%s' ORDER BY ID DESC LIMIT 20;",userr,users);
            rs=st.executeQuery(sql);
            String te;
            while(rs.next()){
                count1--;
                te=rs.getString("Message");
                msgs.set(count1,te);
            }
            if(rs!=null) rs.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public void msginput(String msges){
        try{
            count++;
            temp1=String.valueOf(count);
            String sql=String.format("INSERT INTO Username (ID,Userr,Users,Message) VALUES('%s','%s','%s','%s');",temp1,userr,users,msges);
            st.executeUpdate(sql);
        }
        catch(Exception e){
            System.out.println(e);
        }
        if(msgs.get(19)==" "){
            for(int i=0;i<20;i++){
                if(msgs.get(i)==" "){
                    msgs.set(i,msges);
                    break;
                }
            }
        }
        else{
            for(int i=1;i<20;i++){
                msgs.set(i-1,msgs.get(i));
            }
            msgs.set(19,msges);
        }
    }
    public void msgoutput(int i){
        if (i < msgs.size()) {
            this.msgprint=msgs.get(i);
        } else {
            this.msgprint=null;
        }
    }
    // public int len(){
    //     return msgs.size();
    // }
    // public static void main(String[] args){
    //     Messagess obj=new Messagess("Kratos", "Amillio");
    //     obj.msginput("You: Hello Kratos");

    // }
}

