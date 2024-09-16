import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Buttons {
    JButton button=new JButton();
    String name;
    int msgs=0;
    public Buttons(JButton button,String name){
        this.button=button;
        this.name=name;
        button.setPreferredSize(new Dimension(500,30));
        button.setMaximumSize(new Dimension(500,30));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setText(name);
        button.setVisible(true);
    }
    public void newmsg(){
        this.msgs+=1;
    }

}
