package tsp;

public class Node {

	double x, y;
	int index;
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	
	public Node(int index, double x, double y) {
		
		this.x = x;
		this.y = y;
		this.index = index;
	}

	
	@Override
	public String toString() {
		//return "Node [x=" + x + ", y=" + y + ", index=" + index + "]";
		return index +  "(" + x + "," + y + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		Node other = (Node) obj;
		return (index == other.index) && (x==other.x) && (y==other.y);
		
		
	}
	
}
