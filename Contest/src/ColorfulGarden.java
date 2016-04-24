import java.util.Arrays;

public class ColorfulGarden {
	public String[] rearrange(String[] garden) {
		int[] count = new int[26];
		int n = garden[0].length();
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < n; j++) {
				count[garden[i].charAt(j) - 'a']++;
			}
		}
		char[][] result = new char[2][n];

		for (int z = 0; z < n; z++) {
			int max = -1, other = -1;
			for (int i = 0; i < 26; i++) {
				if (count[i] > 0) {
					if (max == -1 || count[max] < count[i]) {
						max = i;
					}
				}
			}
			if (max == -1) {
				break;
			}
			for (int i = 0; i < 26; i++) {
				if (count[i] > 0 && i != max) {
					if (other == -1 || count[other] < count[i]) {
						other = i;
					}
				}
			}
			if (other == -1) {
				return new String[] {};
			}
			count[max]--;
			count[other]--;
			char x = (char) (max + 'a');
			char y = (char) (other + 'a');
			if (z == 0) {
				result[0][z] = x;
				result[1][z] = y;
			} else if (result[0][z - 1] != x && result[1][z - 1] != y) {
				result[0][z] = x;
				result[1][z] = y;
			} else {
				result[0][z] = y;
				result[1][z] = x;
			}
		}

		return new String[] { new String(result[0]), new String(result[1]) };

	}

	

	public static void main(String[] args) {
		String[] data = {"abcd",
		 "abcd"};
		String[] re = new ColorfulGarden().rearrange(data);
		System.out.println(Arrays.toString(re));
		// System.out.println(re[0]);
		// System.out.println(re[1]);

	}
}
