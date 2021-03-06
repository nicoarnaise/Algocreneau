import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class Picross {

	public static final int V4 = 4;
	public static final int V8 = 8;

	public static Mat createGrid(int height, int width) {

		double[] buffer = new double[3];
		buffer[0] = 255;
		buffer[1] = 255;
		buffer[2] = 255;
		Mat grid = Mat.ones(height, width, CvType.CV_64FC3);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				grid.put(i, j, buffer);
			}
		}
		return grid;

	}

	public static Mat setBlack(Mat orig, int row, int col) {

		double[] buffer = new double[3];
		buffer[0] = 0;
		buffer[1] = 0;
		buffer[2] = 0;
		orig.put(row, col, buffer);

		return orig;
	}

	public static Mat setWhite(Mat orig, int row, int col) {

		double[] buffer = new double[3];
		buffer[0] = 255;
		buffer[1] = 255;
		buffer[2] = 255;
		orig.put(row, col, buffer);

		return orig;
	}

	public static Mat MonteCarlo(Mat orig, int neighbor) {

		int pointsNumber = 25 * neighbor;
		Mat newMat = Mat.zeros(orig.rows(), orig.width(), orig.type());
		for (int row = 0; row < orig.rows(); row++) {
			for (int col = 0; col < orig.cols(); col++) {
				int toPick = neighbor / 2;
				double[] bufferToSet = new double[3];
				bufferToSet[0] = 0;
				HashMap<Point, Integer> map = initMap(neighbor);
				Vector<Point> pointsToPick = getPointsToPick(map, neighbor, pointsNumber, toPick);
				double[] bufftmp = new double[3];
				for (Point tmp : pointsToPick) {
					Point chosen = new Point((int) tmp.getX() + row, (int) tmp.getY() + col);
					if (cellExist(chosen, orig)) {
						bufftmp = orig.get((int) chosen.getX(), (int) chosen.getY());
						bufferToSet[0] += bufftmp[0];
					} else {
						toPick--;
					}
				}
				if (toPick > 0) {
					bufferToSet[0] /= toPick;
				} else {
					bufferToSet = orig.get(row, col);
				}
				newMat.put(row, col, bufferToSet);
			}
		}
		return newMat;
	}

	private static boolean cellExist(Point toCheck, Mat orig) {
		return !(toCheck.getX() < 0 || toCheck.getY() < 0 || toCheck.getX() >= orig.rows()
				|| toCheck.getY() >= orig.cols());
	}

	private static Vector<Point> getPointsToPick(HashMap<Point, Integer> map, int neighbor, int pointsNumber,
			int toPick) {
		Point p = new Point();
		for (int i = 0; i < pointsNumber; i++) {
			int tokenX = (int) (Math.random() * 3) - 1;
			int tokenY = (int) (Math.random() * 3) - 1;
			if (neighbor == V4) {
				while (Math.abs(tokenX) == 1 && Math.abs(tokenY) == 1) {
					tokenX = (int) (Math.random() * 3) - 1;
					tokenY = (int) (Math.random() * 3) - 1;
				}
			}
			p.setLocation(tokenX, tokenY);
			map.put(p, map.get(p) + 1);
		}

		Vector<Point> pointsToPick = new Vector<Point>();
		while (pointsToPick.size() < toPick) {
			int max = -1;
			Point picked = new Point();
			for (Point tmp : map.keySet()) {
				if (map.get(tmp) > max) {
					picked = tmp;
					max = map.get(tmp);
				}
			}
			map.put(picked, 0);
			pointsToPick.add(picked);
		}

		return pointsToPick;
	}

	private static HashMap<Point, Integer> initMap(int neighbor) {
		HashMap<Point, Integer> map = new HashMap<Point, Integer>();
		if (neighbor == V8) {
			Point pos = new Point();
			pos.setLocation(-1, -1);
			map.put(pos, 0);
			Point pos2 = new Point();
			pos2.setLocation(1, -1);
			map.put(pos2, 0);
			Point pos3 = new Point();
			pos3.setLocation(-1, 1);
			map.put(pos3, 0);
			Point pos4 = new Point();
			pos4.setLocation(1, 1);
			map.put(pos4, 0);
		}
		Point pos5 = new Point();
		pos5.setLocation(-1, 0);
		map.put(pos5, 0);
		Point pos6 = new Point();
		pos6.setLocation(0, -1);
		map.put(pos6, 0);
		Point pos7 = new Point();
		pos7.setLocation(0, 1);
		map.put(pos7, 0);
		Point pos8 = new Point();
		pos8.setLocation(1, 0);
		map.put(pos8, 0);
		Point pos9 = new Point();
		pos9.setLocation(0, 0);
		map.put(pos9, 0);

		return map;
	}
	
	public static int checkScore(Mat orig, Mat current){
		int score = 0;
		for(int i = 0; i < orig.rows(); i++){
			for(int j=0; j < orig.cols(); j++){
				if(Arrays.equals(orig.get(i,j),current.get(i,j)))
					score++;
			}
		}
		score = (score*100)/(orig.rows()*orig.cols());
		return score;
	}
}
