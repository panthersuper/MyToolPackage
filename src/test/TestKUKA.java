package test;

import igeo.IVec;

import java.util.ArrayList;

import kuka.CS;
import kuka.KUKAGenerator;

import peasy.PeasyCam;
import processing.core.PApplet;

public class TestKUKA extends PApplet {
	PeasyCam cam;
	int num = 0;
	ArrayList<CS> list = new ArrayList<>();
	CS cs0 = new CS();

	CS cs1 = new CS(new IVec(-400, 700, 1000), 0,0,0);
	CS cs2 = new CS(new IVec(-200, 700, 1000), 180,0,0);
	CS cs3 = new CS(new IVec(0, 700, 1000), 0,0,0);
	CS cs4 = new CS(new IVec(200, 700, 1000), 180,0,0);

	public void setup() {
		size(800, 600, OPENGL);
		cam = new PeasyCam(this, 2500);
		cam.setMinimumDistance(50);
		cam.setMaximumDistance(10000);
		//cam.setRotations(90, 90, -10);

		list.add(cs1);
		list.add(cs2);
		list.add(cs3);
		list.add(cs4);

		//println(new CS(180, 0, 0).subNew(new CS(180, 0, 0)).Angle());

		KUKAGenerator.setTran(new CS(new IVec(0,0,1200), -90, 0, 180));//E轴转换相对位置（相对前后位置的中点）,Z为转换绝对位置
		KUKAGenerator.setTool(new CS(0, 0, 0, -90, 0, -90), 6);
		KUKAGenerator.setBase(new CS(1042, -1246, 184, 0, 0, 0), 6);
		KUKAGenerator.setST("010", "001010");
		KUKAGenerator.setLocation("C:/Users/Panther/Desktop/pre_grad/kukatest/", "01");
		KUKAGenerator.getMachCode(list, true, true, this);
	}

	public void draw() {
		background(0);
		list.get(num).draw(this, 200);

		cs0.draw(this);
	}

	public void keyPressed() {
		if (key == 'p' || key == 'P') {
			num += 1;
			if (num > list.size() - 1)
				num = list.size() - 1;
		}
		if (key == 'o' || key == 'O') {
			num -= 1;
			if (num < 0)
				num = 0;
		}

	}

}
