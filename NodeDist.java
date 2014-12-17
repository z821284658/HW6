
public class NodeDist {
	private String Node_begin = new String();
	private String Node_end = new String();
	private double Dist;
	
	public NodeDist(String node1, String node2, double dist) {
		this.Node_begin = node1;
		this.Node_end = node2;
		this.Dist = dist;
    }
	
	public NodeDist() {
		
    }
    
	public String getNode_begin() {
        return Node_begin;
    }
  
    public void setNode_begin(String node1) {
        this.Node_begin = node1;
    }
    
    public String getNode_end() {
        return Node_end;
    }
  
    public void setNode_end(String node2) {
        this.Node_end = node2;
    }
	
    public double getDist() {
        return Dist;
    }
  
    public void setAttributes(double dist) {
        this.Dist = dist;
    }
}
