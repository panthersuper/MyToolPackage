package Tool;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class Convert extends PApplet {

	PImage photo;
	PGraphics pg;
	PGraphics pg1;
	String filename = "13_Graham_Communication-Grids";
	String path = "C:/Users/Panther/Desktop/transform/"+filename+"/";
	String out_path = "C:/Users/Panther/Desktop/transform/"+filename+"/Cutted/";

	int number = 14;// number of pics

	public void setup() {
			photo = loadImage(path + filename + "-" + (number + 1) + ".jpg");
			pg = createGraphics(photo.width / 2, photo.height, P3D);
			pg.beginDraw();
			pg.image(photo, 0, 0);
			pg.endDraw();
			pg.save(out_path + (number+1) + "_1.jpg");
			
			pg1 = createGraphics(photo.width / 2, photo.height, P3D);
			pg1.beginDraw();
			pg1.image(photo, -photo.width / 2, 0);
			pg1.endDraw();
			pg1.save(out_path + (number+1) + "_2.jpg");
			println(number);
		exit();
		
		
		
	
		
		
		
	}

	public void draw() {

	}

}
