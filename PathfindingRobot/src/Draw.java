
//3/12/2021
//A* algorithm in a 2D plane with polygon obstacles.

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import Pathing.Vertex;
import Pathing.Pathfinder;
import Pathing.Vector;
	public class Draw extends JPanel
	{
		private static final long serialVersionUID = 1L;
		static ArrayList<Vector> path = new ArrayList<Vector>();
		
		public static void main(String args[])
	 	{
			JFrame frame = new JFrame( "Pathfinding" );
			frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
			PolygonsJPanel polygonsJPanel = new PolygonsJPanel();
			frame.setBackground(Color.lightGray);
			frame.add( polygonsJPanel );
			frame.setSize( 1200, 800 );
			frame.setVisible( false );	  
			int budget = 1300;
			int j = 0;
			Vertex start = new Vertex(2*4, 30*4);
			Vertex end = new Vertex(280*4, 150*4);
			Scanner input = new Scanner(System.in);
			Pathfinder.currentEnv = 0;
	  		System.out.println("Suggested range for testing: 200 < C < 2000");
			while(budget != 0) {
				System.out.println("\n-----------------------------------\n");
				System.out.print("Enter budget C, or enter 0 to quit: ");
				budget = input.nextInt();
				if(budget == 0)
					break;
				System.out.println("\n");
				System.out.print("Choose the polygonal environment, 1 or 2: ");
				j = input.nextInt();
				switch(j) {
					case 1 : {
						Pathfinder.currentEnv = 0;
						break;
					}
					case 2 : {
						Pathfinder.currentEnv = 1;
						break;
					}
					default : {
						Pathfinder.currentEnv = 0;
						break;
					}
				}
				Pathfinder.setVertices();
				frame.setVisible(false);
				path = Pathfinder.aStar(start, end, budget);
				frame.setVisible(true);
			}
			System.out.println("Exited.");
	 	} 
	}

class PolygonsJPanel extends JPanel{
		private static final long serialVersionUID = 1L;
	//Draw output
	public void paintComponent( Graphics g ) {
		super.paintComponent( g );
		g.setColor(Color.GREEN);
		g.fillOval(0, 27*4, 20, 20);
		g.setColor(Color.red);
		g.fillOval(277*4, 147*4, 20, 20);
		g.setColor(Color.gray);
		for(int i = 0; i < Pathfinder.getShapes().length; i++) {
			g.fillPolygon(Pathfinder.getShapes()[i]);
		}
		g.setColor(Color.blue);
		for(Vector item: Draw.path){
			g.drawLine(
			(int) item.getFirst().getX(),
			(int) item.getFirst().getY(),
			(int) item.getSecond().getX(),
			(int) item.getSecond().getY());
		}
	 } 
}

