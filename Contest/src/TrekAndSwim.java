import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class TrekAndSwim {
	public int solution(int[] A) {

		int n = A.length;
		int[] pre = new int[n];
		int[] pos = new int[n];
		int c = 0;
		int[] hold = new int[n + 2];
		Arrays.fill(hold, -1);
		for (int i = 0; i < n; i++) {
			if (A[i] == 0) {
				c++;
			} else {
				c--;
			}
			if (c < 0) {
				if (hold[-c] == -1) {
					hold[-c] = i;
				}
			}

		}
		int last = -1;
		for (int i = n; i > 0; i--) {
			if (last != -1) {
				if (hold[i] == -1) {
					hold[i] = last;
				} else {
					if (hold[i] < last) {
						last = hold[i];
					} else {
						hold[i] = last;
					}
				}
			} else if (hold[i] != -1) {
				last = hold[i];
			}
		}

		c = 0;
		for (int i = 0; i < n; i++) {
			if (A[i] == 0) {
				c++;
			} else {
				c--;
			}
			if (c <= 0) {
				int v = hold[-(c - 1)];
				if (v != -1 && v < i) {
					pre[i] = i - v;
				}
			} else {
				pre[i] = i + 1;
			}
		}
		Arrays.fill(hold, -1);
		c = 0;

		for (int i = n - 1; i >= 0; i--) {
			if (A[i] == 1) {
				c++;
			} else {
				c--;
			}

			if (c < 0 && hold[-c] == -1) {
				hold[-c] = i;
			}
			// System.out.println(c + " " + pos[i]);
		}
		last = -1;
		for (int i = n; i > 0; i--) {
			if (last != -1) {
				if (hold[i] == -1) {
					hold[i] = last;
				} else {
					if (hold[i] > last) {
						last = hold[i];
					} else {
						hold[i] = last;
					}
				}
			} else if (hold[i] != -1) {
				last = hold[i];
			}
		}
		c = 0;
		for (int i = n - 1; i >= 0; i--) {
			if (A[i] == 1) {
				c++;
			} else {
				c--;
			}

			if (c <= 0) {
				int v = hold[-(c - 1)];
				if (v != -1 && v > i) {
					pos[i] = v - i;
				}
			} else {
				pos[i] = n - i;
			}
		}

		//System.out.println(Arrays.toString(hold));
		int result = 0;
		for (int i = 0; i < n - 1; i++) {
			if (pre[i] > 0 && pos[i + 1] > 0) {
				// System.out.println(i + " " + pre[i] + " " + pos[i + 1]);
				int v = pre[i] + pos[i + 1];
				result = Math.max(result, v);
			}
		}
		return result;
	}

	public static void main(String[] args) {
		int[] a = { 0, 0 };
		System.out.println(new TrekAndSwim().solution(a));
	}
}
