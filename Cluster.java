import java.util.ArrayList;


public class Cluster {
	private String CID = new String();
	private ArrayList<Gene> Members = new ArrayList<Gene>();
	
	public Cluster(String cid, ArrayList<Gene> members) {
		this.CID = cid;
		this.Members = members;
    }
	
	public Cluster() {
		
    }
	
	public String getCID() {
        return CID;
    }
  
    public void setCID(String cid) {
        this.CID = cid;
    }
    
    public ArrayList<Gene> getMembers() {
        return Members;
    }
  
    public void setMembers(ArrayList<Gene> members) {
        this.Members = members;
    }
    
    public int length() {
        return Members.size();
    }
}
