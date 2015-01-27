package geo;

import java.util.ArrayList;

import igeo.IVec2;
import processing.core.PApplet;

public class DrawLink2D {

	public static void dashed(PApplet app, IVec2 v1, IVec2 v2, double section) {
		// linke by dashed line
		// section is the interval distance

		int num = (int) ((v1.dist(v2) / section) / 2);
		IVec2 dire = v2.dup().sub(v1).unit();
		for (int i = 0; i < num * 2; i += 2) {
			IVec2 s = v1.dup().add(dire.dup().scale(i * section));
			IVec2 e = v1.dup().add(dire.dup().scale((i + 1) * section));
			app.line((float) s.x, (float) s.y, (float) e.x, (float) e.y);

		}
	}

	public static void Curve(PApplet app, IVec2 v1, IVec2 v2, int direction) {
		// link by curve line
		// direction is the base direction that the curve tend to
		app.pushStyle();
		app.noFill();
		IVec2 center = v1.dup().add(v2).scale(1f / 2);
		IVec2 dir = new IVec2();
		double distX = Math.abs(v1.x - v2.x);
		double distY = Math.abs(v1.y - v2.y);

		if (direction == 0)
			dir = new IVec2(distX / 5, 0);
		else
			dir = new IVec2(0, distY / 5);

		IVec2 v1_ = v1.dup().add(center).scale(1f / 2).add(dir);
		IVec2 v2_ = v2.dup().add(center).scale(1f / 2).add(dir.dup().flip());
		// app.strokeWeight(0.5f);
		app.stroke(200, 200);
		app.bezier((float) v1.x, (float) v1.y, (float) v1_.x, (float) v1_.y, (float) v2_.x,
				(float) v2_.y, (float) v2.x, (float) v2.y);
		app.popStyle();
	}

	public static IVec2[] KochCurve(PApplet app, IVec2 v1, IVec2 v2, int iteration) {
		// draw Koch Curve
		ArrayList<IVec2> listAll = new ArrayList<>();
		listAll.add(v1);
		listAll.add(v2);

		int count = 0;

		while (count < iteration) {
			count++;
			IVec2[][] list = new IVec2[listAll.size()-1][5];
			for(int i=0;i<listAll.size()-1;i++){
				IVec2 vv1 = listAll.get(i).dup();
				IVec2 vv2 = listAll.get(i+1).dup();

			IVec2 dir = vv2.dup().sub(vv1).scale(1f / 3);
			IVec2 vv1_ = vv1.dup().add(dir);
			IVec2 vv2_ = vv1_.dup().add(dir);
			IVec2 vv_ = vv1_.dup().add(dir.dup().rot(Math.PI / 3));
				list[i][0] = vv1;
				list[i][1] = vv1_;
				list[i][2] = vv_;
				list[i][3] = vv2_;
				list[i][4] = vv2;
			}
			listAll.clear();

			for(int i=0;i<list.length;i++){
				listAll.add(list[i][0]);
				listAll.add(list[i][1]);
				listAll.add(list[i][2]);
				listAll.add(list[i][3]);
				listAll.add(list[i][4]);
			}

		}

		IVec2[] list = new IVec2[listAll.size()];
		for (int i = 0; i < listAll.size(); i++){
			list[i] = listAll.get(i);
		}
		for (int i = 0; i < list.length - 1; i++)
			app.line((float) list[i].x, (float) list[i].y, (float) list[i + 1].x,
					(float) list[i + 1].y);

		return list;
	}

}
