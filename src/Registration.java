import java.sql.*;

public class Registration {
    private String username="",pass="";
    public String url="jdbc:postgresql://localhost:5432/Hizenberg";
    public String usename="postgres";
    public String password="123456";
    public Connection con;
    public String status="",msg="";
    public Statement st;
    private String te="0";
    public Registration(String username,String pass){
        this.username=username;
        this.pass=pass;
        try{
            con=DriverManager.getConnection(url,usename,password);
            st= con.createStatement();
            String sql=String.format("SELECT username FROM details WHERE username='%s';",this.username);
            ResultSet rs=st.executeQuery(sql);
            if(rs.next()) this.te=rs.getString("username");
            if(rs!=null) rs.close();
            if(te.equals(username)){
                this.status="1";
                this.msg="Username already exists";
            }
            else{
                this.status="0";
                sql="SELECT COUNT(*) FROM details;";
                rs=st.executeQuery(sql);
                if(rs.next()) te=rs.getString(1);
                int temp1=Integer.parseInt(te);
                sql=String.format("INSERT INTO details (p_id,username,password) VALUES('%s','%s','%s');",temp1+1,username,pass);
                st.executeUpdate(sql);
                if(rs!=null) rs.close();
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
