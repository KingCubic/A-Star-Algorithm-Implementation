package Pathing;

public class Vertex implements Comparable<Vertex>{
	double x;
	double y;
	double distStart;
	double predDist;
	public Vertex(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public double getX() {
		return this.x;
	}
	public double getY() {
		return this.y;
	}
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void setDistToStart(double d) {
		this.distStart = d;
	}
	public void setPredDistance(double d) {
		this.predDist = d;
	}
	public double getPredDistance() {
		return this.predDist;
	}
	public double getDistToStart() {
		return this.distStart;
	}

	@Override
	public int compareTo(Vertex o) {
		return 1;
	}

}
