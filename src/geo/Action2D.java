package geo;

import igeo.IVec;
import igeo.IVec2;

import java.util.ArrayList;

public class Action2D {

	public static IVec2[] ConvexHull(IVec2[] pts) {
		// get the convex hull of pts

		ArrayList<IVec2> List = new ArrayList<>(); // �����·��

		IVec2 Starter; // ���
		IVec2 Aim; // Ŀ���
		IVec2 dir = new IVec2(0, 0);// �ƶ���������

		// �ҵ�·�����-Path��X��С���Ǹ�λ��
		int n = 0;
		double X = Double.MAX_VALUE;
		for (int i = 0; i < pts.length; i++)
			if (pts[i].x < X) {
				X = pts[i].x;
				n = i;
			}
		Starter = pts[n];
		List.add(Starter);

		// �ҵ���һ������һ�㣨�����������Ҳ�ĵ㣩
		int m = 0;
		double mark = Double.MAX_VALUE;

		IVec2 ave = new IVec2(0, 0); // �������ƽ������
		IVec2 dirF; // ��㵽ƽ����ķ�����
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

		while ((!List.get(0).eq(Aim, 0.1)) || List.size() < 3) {// �Ƿ�ѭ����һ��
			// �ҵ�ListC�б�֤��ʱ������ĵ�

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
