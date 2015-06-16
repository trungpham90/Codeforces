public class PaperFold {
	public int numFolds(int[] paper, int[] box) {
		int result = Integer.MAX_VALUE;
		int a = -1, b = -1;
		double x = paper[0];
		double y = paper[1];
		for (int i = 1; i <= 9; i++) {
			if (x > box[0]) {
				x /= 2;
			} else if (a == -1) {
				a = i - 1;
			}
			if (y > box[1]) {
				y /= 2;
			} else if (b == -1) {
				b = i - 1;
			}
		}
		if (a != -1 && b != -1) {
			result = a + b;
		}
		a = -1;
		b = -1;

		x = paper[1];
		y = paper[0];
		for (int i = 1; i <= 9; i++) {
			if (x > box[0]) {
				x /= 2;
			} else if (a == -1) {
				a = i - 1;
			}
			if (y > box[1]) {
				y /= 2;
			} else if (b == -1) {
				b = i - 1;
			}
		}
		if (a != -1 && b != -1) {
			result = Math.min(result, a + b);
		}
		if (result <= 8)
			return result;
		return -1;
	}
}
