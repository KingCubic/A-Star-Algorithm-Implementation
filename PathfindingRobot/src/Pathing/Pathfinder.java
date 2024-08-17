
//2/17/2021
//A* algorithm in a 2D plane with polygon obstacles.

package Pathing;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import Pathing.Vertex;

public class Pathfinder {
	//The Array containing all vertices.
	static ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	public static int currentEnv;
	//Actions method that generates all neighbor vertices given a point.
	//Loops through all vertices available in the first loop,
	//then it tests those lines on each polygon to ensure
	//it does not intersect with them.
	//I understand that it is supposed to return a vector
	//based on your instructions.
	//However, I found it easier to implement this into the 
	//A* algorithm when it simply returned the "neighbor"
	//vertices.
	public static ArrayList<Vertex> actions(Vertex x) {
		ArrayList<Vertex> vectorSet = new ArrayList<Vertex>();
		for(int i = 0; i < vertices.size(); i++) {
				Line2D test = new Line2D.Double(x.x, x.y, 
					vertices.get(i).x, vertices.get(i).y);
				for(int j = 0; j < getShapes().length; j++) {
					try {
						if(testIntersects(getShapes()[j], test)) {
							break;
						}	
					} catch (Exception e) {}
					if(j == getShapes().length - 1) {
						vectorSet.add(vertices.get(i));
					}
				}
		}
		return vectorSet;
	}
	//Creates an array of all vertices in the 2d plane.
	public static void setVertices() {
		final ArrayList<Vertex> list = new ArrayList<Vertex>();
		for(int i = 0; i < getShapes().length; i++) {
			for(int j = 0; j < getShapes()[i].npoints; j++) {
				Vertex v = new Vertex(getShapes()[i].xpoints[j], 
				getShapes()[i].ypoints[j]);
				list.add(v);
			}
		}				
		vertices = list;
	}
	//Initializes the polygons in the 2d plane.
	//The points are multiplied by 4 to generate
	//a larger image.
	public static Polygon[] getShapes() {
		if(currentEnv == 0) {
			Polygon[] poly = new Polygon[8];
			int poly0x[] = {120*4, 120*4, 151*4, 172*4};
			int poly0y[] = {116*4, 166*4, 170*4, 147*4};
			poly[0] = new Polygon(poly0x, poly0y, 4);
			int poly1x[] = {241*4, 259*4, 274*4, 266*4};
			int poly1y[] = {155*4, 150*4, 151*4, 77*4};
			poly[1] = new Polygon(poly1x, poly1y, 4);
			int poly2x[] = {211*4, 211*4, 239*4, 261*4, 236*4};
			int poly2y[] = {30*4, 59*4, 78*4, 60*4, 16*4};
			poly[2] = new Polygon(poly2x, poly2y, 5);
			int poly3x[] = {05*4,51*4,80*4,56*4,13*4};
			int poly3y[] = {128*4,170*4,129*4,77*4,87*4};
			poly[3] = new Polygon(poly3x, poly3y, 5);
			int poly4x[] = {18*4,145*4,145*4,18*4};
			int poly4y[] = {17*4,17*4,57*4,57*4};
			poly[4] = new Polygon(poly4x, poly4y, 4);
			int poly5x[] = {180*4,233*4,233*4,180*4};
			int poly5y[] = {83*4,83*4,165*4,165*4};
			poly[5] = new Polygon(poly5x, poly5y, 4);
			int poly6x[] = {152*4,190*4,164*4};
			int poly6y[] = {115*4,62*4,38*4};
			poly[6] = new Polygon(poly6x, poly6y, 3);
			int poly7x[] = {98*4,117*4,82*4};
			int poly7y[] = {135*4,74*4,74*4};
			poly[7] = new Polygon(poly7x, poly7y, 3);
			return poly;
		}
		Polygon[] poly = new Polygon[6];
		int poly0x[] = {120*4, 120*4, 151*4, 172*4};
		int poly0y[] = {116*4, 166*4, 170*4, 147*4};
		poly[0] = new Polygon(poly0x, poly0y, 4);
		int poly1x[] = {241*4, 259*4, 274*4, 266*4};
								//267
		int poly1y[] = {160*4, 150*4, 151*4, 15*4};
		poly[1] = new Polygon(poly1x, poly1y, 4);
		int poly2x[] = {211*4, 150*4, 239*4, 230*4, 236*4};
		int poly2y[] = {30*4, 68*4, 80*4, 60*4, 16*4};
		poly[2] = new Polygon(poly2x, poly2y, 5);
		int poly3x[] = {5*4,51*4,130*4,56*4,13*4};
		int poly3y[] = {128*4,170*4,100*4,77*4,87*4};
		poly[3] = new Polygon(poly3x, poly3y, 5);
		int poly4x[] = {18*4,190*4,130*4,18*4};
		int poly4y[] = {1*4,17*4,80*4,57*4};
		poly[4] = new Polygon(poly4x, poly4y, 4);
		int poly5x[] = {140*4,200*4,233*4,180*4};
		int poly5y[] = {83*4,100*4,169*4,130*4};
		poly[5] = new Polygon(poly5x, poly5y, 4);
		return poly;
		
	}
	//Overloaded methods for calculating distance.
	public static double distance(Vertex a, Vertex b) {
		double d = Math.sqrt(Math.pow((b.getX() - a.getX()), 2) + Math.pow((b.getY() - a.getY()), 2));
		return d;
	}	
	public static double distance(Vector a) {
		double d = Math.sqrt(Math.pow((a.x1 - a.x2), 2) + Math.pow((a.y1 - a.y2), 2));
		return d;
	}
	//A very bothersome workaround for java's lack of support for testing for
	//intersections between a polygon and a line.
	//It simply iterates over the polygon, testing if that
	//segment of the iteration  (a 2dLine) intersects the line
	//in question, using the intersectsLine method.
	public static boolean testIntersects(Polygon poly, Line2D line) throws Exception{
		final PathIterator polyCheck = poly.getPathIterator(null);
		final double[] coords = new double[6];
		final double[] firstCoords = new double[2];
		final double[] previous = new double[2];
		int count = 0;
		if(hasPointOnPoly(poly, line))
			count = count - 1;
		polyCheck.currentSegment(firstCoords);
		previous[0] = firstCoords[0];
		previous[1] = firstCoords[1];
		polyCheck.next();
		while(!polyCheck.isDone()) {
			final int type = polyCheck.currentSegment(coords);
			switch(type) {
				case PathIterator.SEG_LINETO : {
					Line2D cL = new Line2D.Double(previous[0], previous[1], coords[0], coords[1]);
					if(cL.getX1() == line.getX1() && cL.getX2() == line.getX2()) {
						if(cL.getY1() == line.getY1() && cL.getY2() == line.getY2())
							return false;
					}
						
					if(cL.intersectsLine(line)) 
						count = count + 1;
					previous[0] = coords[0];
	                previous[1] = coords[1];
	                break;
				}
				case PathIterator.SEG_CLOSE : {
					final Line2D.Double cL = new Line2D.Double(coords[0], coords[1], firstCoords[0], firstCoords[1]);
					if(cL.getX1() == line.getX1() && cL.getX2() == line.getX2()) {
						if(cL.getY1() == line.getY1() && cL.getY2() == line.getY2())
							return false;
					}
					if(cL.intersectsLine(line)) {
						count = count + 1;
					}
					break;
				}
				default : {
					throw new Exception("default");
				}		
			}
			polyCheck.next();
		}
		//By default, the line connecting to the vertex of the polygon
		//counts as an intersection. To account for this: 
		//As long as there is less than or only one intersection,
		//it will return that it doesn't intersect.
		if(count <= 1) {
			return false;
		}
		return true;		 
	}
	//Method to test if a point of a line is on a given polygon.
	public static boolean hasPointOnPoly(Polygon poly, Line2D line) {
		for(int i: poly.xpoints) {
			if(((int) line.getX1() == i) || ((int) line.getX2() == i)) {
				for(int j: poly.ypoints) {
					if(((int) line.getY1() == j) || ((int) line.getY2() == j))
						return true;
				}
			}
		}
	return false;
	}
	//A* algorithm
	public static ArrayList<Vector> aStar(Vertex start, Vertex end, int C) {
		PriorityQueue<Vertex> openSet = new PriorityQueue<Vertex>();
		HashMap<Vertex, Vertex> cameFrom = new HashMap<Vertex, Vertex>();
		HashMap<Vertex, Double> gScore = new HashMap<Vertex, Double>();
		HashMap<Vertex, Double> fScore = new HashMap<Vertex, Double>();
		ArrayList<Vector> tree = new ArrayList<Vector>();
		Vertex current = new Vertex(1, 2);
		current = start;
		for(Vertex v: vertices) {
			gScore.put(v, 100000000000.00);
			fScore.put(v, 100000000000.00);
		}
		vertices.add(end);
		gScore.put(end, 100000000000.00);
		gScore.put(start, 0.0);
		fScore.put(start, distance(start, end));
		fScore.put(end, 1000000000000.00);
		openSet.add(start);	
		while(!openSet.isEmpty()) {
			current = openSet.element();
			for(Vertex v: openSet) {
				if(fScore.get(v) <= fScore.get(current))
					current = v;
			}
			if(current.getX() == end.getX() && current.getY() == end.getY()) 
				return generateVectors(cameFrom, current, start);
			openSet.remove(current);
			
			double tGScore = 0.0;
			
			for(Vertex neighbor: actions(current)) {
				tGScore = gScore.get(current) + distance(current, neighbor);
				if(tGScore < gScore.get(neighbor)) {
					if(tGScore >= C)
						continue;
					cameFrom.put(neighbor, current);
					gScore.put(neighbor, tGScore);
					fScore.put(neighbor, gScore.get(neighbor) + distance(neighbor, end));
					Vector v = new Vector(neighbor, current);
					tree.add(v);					
					if(!openSet.contains(neighbor)) {					
						openSet.add(neighbor);
					}
				}		
			}
		}
		System.out.println("No path found to goal within budget C.");
		System.out.println("Displaying the tree explored within budget C...");
		return tree;
	}
	//This method retraces the result given by the A*
	//It generates the solution from the end node to the start.
	//It returns an arraylist of vectors for easier output.
	public static ArrayList<Vector> generateVectors(HashMap<Vertex, Vertex> path, Vertex end, Vertex start) {
		System.out.println("Path found!");
		ArrayList<Vector> vectorPath = new ArrayList<Vector>();
		Vertex next = new Vertex(0,0);
		next = path.get(end);
		Vector v = new Vector(end, next);
		vectorPath.add(v);
		while(true) {
			Vector p = new Vector(next, path.get(next));
			next = path.get(next);
			vectorPath.add(p);
			if(path.get(next) == start)
				break;
		}
		Vector m = new Vector(next, start);
		vectorPath.add(m);
		return vectorPath;
	}
	public static ArrayList<Vector> makeTree(HashMap<Vertex, Vertex> path, Vertex start) {
		ArrayList<Vector> vectorPath = new ArrayList<Vector>();
		Vertex next = new Vertex(0,0);
		next = path.get(start);
		Vector v = new Vector(start, next);
		vectorPath.add(v);
		while(true) {
			if(path.get(next) == null)
				break;
			Vector p = new Vector(next, path.get(next));
			next = path.get(next);
			vectorPath.add(p);
		}
		return vectorPath;
	}
}
