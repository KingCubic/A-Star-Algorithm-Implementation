package Pathing;
public class Vector {
	double x1;
	double x2;
	double y1;
	double y2;
	Vertex first;
	Vertex second;
	public Vector(Vertex one, Vertex two) {
		this.x1 = one.x;
		this.x2 = two.x;
		this.y1 = one.y;
		this.y2 = two.y;
		this.first = one;
		this.second = two;
	}
	public void setFirst(Vertex x) {
		this.x1 = x.x;
		this.y1 = x.y;
	}
	public void setSecond(Vertex x) {
		this.x2 = x.x;
		this.y2 = x.y;
	}
	public Vertex getFirst() {
		return first;
	}
	public Vertex getSecond() {
		return second;
	}
	
}
