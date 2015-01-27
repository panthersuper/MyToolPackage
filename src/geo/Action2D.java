package geo;

import igeo.IVec;
import igeo.IVec2;

import java.util.ArrayList;

public class Action2D {

	public static IVec2[] ConvexHull(IVec2[] pts) {
		// get the convex hull of pts

		ArrayList<IVec2> List = new ArrayList<>(); // 输出的路径

		IVec2 Starter; // 起点
		IVec2 Aim; // 目标点
		IVec2 dir = new IVec2(0, 0);// 移动方向向量

		// 找到路径起点-Path中X最小的那个位置
		int n = 0;
		double X = Double.MAX_VALUE;
		for (int i = 0; i < pts.length; i++)
			if (pts[i].x < X) {
				X = pts[i].x;
				n = i;
			}
		Starter = pts[n];
		List.add(Starter);

		// 找到第一步的下一点（其他点中最右侧的点）
		int m = 0;
		double mark = Double.MAX_VALUE;

		IVec2 ave = new IVec2(0, 0); // 其他点的平均坐标
		IVec2 dirF; // 起点到平均点的反方向
		for (int i = 0; i < pts.length; i++)
			ave.add(pts[i]);
		ave.scale((double) 1 / pts.length);
		dirF = ave.dup().sub(Starter).flip();

		for (int i = 0; i < pts.length; i++) {
			if (!pts[i].eq(Starter, 0.1)) {
				double sign = RotAng(dirF, pts[i].dup().sub(Starter));
				if (sign < mark) {
					mark = sign;
					m = i;
				}
			}
		}
		Aim = pts[m];
		List.add(Aim);
		dir = Aim.dup().sub(Starter.dup());
		Starter = Aim;

		while ((!List.get(0).eq(Aim, 0.1)) || List.size() < 3) {// 是否循环了一周
			// 找到ListC中保证逆时针包裹的点

			dirF = dir.dup()/* .flip() */;
			mark = Double.MAX_VALUE;
			m = 0;

			for (int i = 0; i < pts.length; i++) {
				if (!pts[i].eq(Starter, 0.1)) {
					double sign = RotAng(dirF, pts[i].dup().sub(Starter));
					if (sign < mark) {
						mark = sign;
						m = i;
					}
				}
			}

			Aim = pts[m];
			List.add(Aim);
			dir = Aim.dup().sub(Starter.dup());
			Starter = Aim;
		}
		List.remove(List.size() - 1);
		IVec2[] ls = new IVec2[List.size()];
		for (int i = 0; i < List.size(); i++) {
			ls[i] = List.get(i);
		}

		return ls;

	}

	public static double RotAng(IVec2 v1, IVec2 v2) {
		// rotate angle from v1 to v2
		double ang = v1.angle(v2);
		if (v1.cross(v2).z < 0)
			ang = -ang;
		ang = (ang + 2 * Math.PI) % (2 * Math.PI);
		return ang;
	}

}
