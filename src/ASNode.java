/* Andrew Wilder *
 * Ilyssa Widen  */

import java.util.Map;
import java.util.ArrayList;

public class ASNode {
	int ASNum;
	Map<Integer, ArrayList<ASNode>> paths;

	public ASNode(int ASNum, Map<Integer, ArrayList<ASNode>> paths) {
		this.ASNum = ASNum;
		this.paths = paths;
	}

	public void announce(ArrayList<ASNode> paths){
		
	}
	
	
	
	
	
	public int getASNum() {
		return ASNum;
	}

	public void setASNum(int aSNum) {
		ASNum = aSNum;
	}

	public Map<Integer, ArrayList<ASNode>> getPaths() {
		return paths;
	}

	public void setPaths(Map<Integer, ArrayList<ASNode>> paths) {
		this.paths = paths;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
