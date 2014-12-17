
public class Gene {
	private String ID = new String();
	private int[] Attributes = new int[16];
	
	
	public Gene(String id, int[] attributes) {
		this.ID = id;
		this.Attributes = attributes;
    }
	
	public Gene() {
		
    }
    
	public String getID() {
        return ID;
    }
  
    public void setID(String id) {
        this.ID = id;
    }
	
    public int[] getAttributes() {
        return Attributes;
    }
  
    public void setAttributes(int[] attributes) {
        this.Attributes = attributes;
    }
    
    public int length() {
        return Attributes.length;
    }
    
}
