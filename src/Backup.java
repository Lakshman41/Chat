import java.sql.*;
import java.util.ArrayList;

/**
 * Backup
 */
public class Backup {
    public String username;
    public String url="jdbc:postgresql://localhost:5432/Hizenberg";
    public String usename="postgres";
    public String password="123456";
    public Connection con;
    public Statement st;
    public String msgprint;
    public ArrayList<String> str=new ArrayList<>();
    public Backup(String username){
        this.username=username;
        try{
            con=DriverManager.getConnection(url,usename,password);
            st= con.createStatement();
            String sql=String.format("SELECT users FROM ( SELECT users, id FROM username WHERE userr = '%s' ORDER BY id DESC) subquery GROUP BY users;",this.username);
            ResultSet rs=st.executeQuery(sql);
            String te;
            while(rs.next()){
                te=rs.getString("users");
                str.add(te);
            }
            rs.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public int count(){
        int cn=str.size();
        return cn;
    }
    public void msgoutput(int i){
        if (i < str.size()) {
            this.msgprint=str.get(i);
        } else {
            this.msgprint=null;
        }
    }
    // public static void main(String[] args){
    //     Backup obj=new Backup("Lalo");
    //     int cn=obj.count();
    //     for(int i=0;i<cn;i++){
    //         obj.msgoutput(i);
    //         String strt=obj.msgprint;
    //         System.out.println(strt);
    //     }
    // }
}