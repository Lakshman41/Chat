import java.util.ArrayList;

public class Messagec {
    public String userr,users,msgprint;
    // public String[] msgs=new String[20];
    private ArrayList<String> msgs=new ArrayList<>();
    public Messagec(String userr){
        this.userr=userr;
        for(int i=0;i<20;i++){
            msgs.add(" ");
        }
    }
    public void msginput(String msges){
        if((msgs.get(19)).equals(" ")){
            for(int i=0;i<20;i++){
                if((msgs.get(i)).equals(" ")){
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
    public void indxinput(int ind,String s){
        msgs.set(ind,s);
    }
    // public int len(){
    //     return msgs.size();
    // }
}

