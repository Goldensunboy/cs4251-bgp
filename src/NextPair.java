
public class NextPair {

	int length;
	ASNode node;

	public NextPair(ASNode node, int length){
		this.length = length;
		this.node = node;
	}
	
	public NextPair(){
		this(null, 0);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + length;
		result = prime * result + ((node == null) ? 0 : node.hashCode());
		return result;
	}
	
	public String toString(){
		return "Node "+node.ASNum+" length"+length;
	}

	public boolean pairEquals(NextPair obj) {
		if(length != obj.length){
			return false;
		}
		if(obj.node.ASNum != node.ASNum){
			return false;
		}
		return true;
	}
	
	

}
