
public class PrefixPair {
	public int IPV4;
	public int slash_x;
	public PrefixPair(int IPV4, int slash_x) {
		this.IPV4 = IPV4;
		this.slash_x = slash_x;
	}
	public boolean equals(Object o) {
		if(o instanceof PrefixPair) {
			return ((PrefixPair)o).IPV4 == IPV4 && ((PrefixPair)o).slash_x == slash_x;
		} else {
			return false;
		}
	}
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + IPV4;
		result = prime * result + slash_x;
		return result;
	}
}
