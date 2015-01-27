package test;

import geo.DrawLink2D;
import igeo.IVec2;
import processing.core.PApplet;

public class Testlink extends PApplet{
	int num = 100;
	IVec2 v1 = new IVec2(0,250);
	IVec2 v2 = new IVec2(500,250);

	IVec2[] vs = new IVec2[num];
	

	public void setup(){
		smooth();
		size(500,500);
		for(int i=0;i<num;i++)
		vs[i] = new IVec2(400,Math.random()*height);

	}
	
	public void draw(){
		background(255);

/*		for(int i=0;i<num;i++)
		DrawLink2D.Curve(this, v1, vs[i], 0);
*/		DrawLink2D.KochCurve(this, v1, new IVec2(mouseX,mouseY), 2);

	}
	
	public static void main(String args[]) {
		PApplet.main(new String[] { "--location=10,10", "test.Testlink" });
	}
}
