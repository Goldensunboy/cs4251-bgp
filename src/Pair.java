
public class Pair {

	int length;
	ASNode node;

	public Pair(int length,ASNode node){
		this.length=length;
		this.node=node;
	}
	
	public Pair(){
		this(0,null);
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

	public boolean pairEquals(Pair obj) {
		if(length!=obj.length){
			return false;
		}
		if(obj.node.ASNum!=node.ASNum){
			return false;
		}
		return true;
	}
	
	

}
