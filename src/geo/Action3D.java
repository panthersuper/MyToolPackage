package geo;

import java.util.ArrayList;

import processing.core.PApplet;
import igeo.IVec;

public class Action3D {

	public static IVec[][] subDivCenter(PApplet app, IVec[] vs, int iterator) {
		// return pts list by subdivide original face by its center point and
		// set Z ratio
		ArrayList<IVec[]> list = new ArrayList();
		list.add(vs);

		int count = 0;
		if (vs.length >= 3) {
			while (count < iterator) {

				count++;
				IVec[][][] templist = new IVec[list.size()][][];

				for (int i = 0; i < list.size(); i++) {
					IVec nor = list.get(i)[1].dup().sub(list.get(i)[0])
							.cross(list.get(i)[2].dup().sub(list.get(i)[1])).unit();
					IVec c = Center(list.get(i));
					double dis = 0;
					for (int j = 0; j < list.get(i).length; j++) {
						dis += c.dist(list.get(i)[j]);
					}
					dis /= list.get(i).length;

					dis = app.noise((float) i, (float) c.y, (float) c.z) * dis * 1;

					IVec vs_ = c.dup().add(nor.dup().scale(dis / 2));
					templist[i] = new IVec[list.get(i).length][3];
					for (int j = 0; j < list.get(i).length; j++) {
						templist[i][j][0] = list.get(i)[j].dup();
						templist[i][j][1] = list.get(i)[(j + 1) % list.get(i).length].dup();
						templist[i][j][2] = vs_;
					}
				}
				list.clear();

				for (int i = 0; i < templist.length; i++) {
					for (int j = 0; j < templist[i].length; j++) {
						list.add(templist[i][j]);
					}
				}

			}

			IVec[][] vlist = new IVec[list.size()][];
			for (int i = 0; i < list.size(); i++) {
				vlist[i] = list.get(i);
			}

			app.pushStyle();
			for (int i = 0; i < vlist.length; i++) {
				app.beginShape();
				app.vertex((float) vlist[i][0].x, (float) vlist[i][0].y, (float) vlist[i][0].z);
				app.vertex((float) vlist[i][1].x, (float) vlist[i][1].y, (float) vlist[i][1].z);
				app.vertex((float) vlist[i][2].x, (float) vlist[i][2].y, (float) vlist[i][2].z);
				app.endShape();
			}

			app.popStyle();
			return vlist;

		} else
			return null;
	}

	public static IVec Center(IVec[] vs) {
		// return center point of vs

		IVec v = new IVec(0, 0, 0);
		for (int i = 0; i < vs.length; i++) {
			v.add(vs[i]);
		}
		v.scale(1.0 / vs.length);
		return v;
	}

}
