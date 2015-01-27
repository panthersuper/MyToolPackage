package test;

import geo.Action3D;
import igeo.IVec;
import peasy.PeasyCam;
import processing.core.PApplet;

public class Test3DFunction extends PApplet {

	PeasyCam cam;
	IVec[] vs = new IVec[3];
	int num = 0;
	IVec[][] v;

	public void setup() {
		vs[0] = new IVec(0, 0, 0);
		vs[1] = new IVec(100, 0, 0);
		vs[2] = new IVec(50, 50 * 1.73, 0);
		smooth();
		size(1000, 500, OPENGL);
		cam = new PeasyCam(this, 100);
		cam.setMinimumDistance(5);
		cam.setMaximumDistance(10000);
		cam.setDistance(200);

		v = Action3D.subDivCenter(this, vs, num);

	}

	public void draw() {
		background(0);
		Action3D.subDivCenter(this, vs, num);

/*		pushStyle();
		for (int i = 0; i < v.length; i++) {
			beginShape();
			vertex((float) v[i][0].x, (float) v[i][0].y, (float) v[i][0].z);
			vertex((float) v[i][1].x, (float) v[i][1].y, (float) v[i][1].z);
			vertex((float) v[i][2].x, (float) v[i][2].y, (float) v[i][2].z);

			endShape();
		}

		popStyle();
*/	}

	public void keyPressed() {
		num++;

	}

}
