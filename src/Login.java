import java.sql.*;

public class Login {
    private String username="",pass="";
    public String url="jdbc:postgresql://localhost:5432/Hizenberg";
    public String usename="postgres";
    public String password="123456";
    public Connection con;
    public String status="",msg="";
    public Statement st;
    private String te="",p="";
    public Login(String username,String pass){
        this.username=username;
        this.pass=pass;
        try{
            con=DriverManager.getConnection(url,usename,password);
            st= con.createStatement();
            String sql=String.format("SELECT * FROM details WHERE username='%s';",this.username);
            ResultSet rs=st.executeQuery(sql);
            if(rs.next()) {
                this.te=rs.getString("username");
                this.p=rs.getString("password");
            }
            if(rs!=null) rs.close();
            if(!username.equals(te)){
                this.status="1";
                this.msg="Username does not exist";
            }
            else if(!pass.equals(p)){
                this.status="1";
                this.msg="Password is incorrect";
            }
            else{
                this.status="0";
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
