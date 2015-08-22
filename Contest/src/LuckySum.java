
import java.util.Arrays;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class LuckySum {

    long[][][] dp;

    public long construct(String note) {
        dp = new long[2][2][note.length()];
        for (long[][] a : dp) {
            for (long[] b : a) {
                Arrays.fill(b, -2);
            }
        }
        return cal(0, 0, 0, note);

    }

    public long cal(int index, int add, int dif, String note) {
        if (index == note.length()) {
            if (add == 0 && dif == 1) {
                return 0;
            }
            return -1;
        }
        if (dp[dif][add][index] != -2) {
            return dp[dif][add][index];
        }
        long result = -1;
        char c = note.charAt(index);
        if (index == 0) {
            if (c == '?') {
                long v = cal(index + 1, 1, 1, note);
                if (v != -1) {
                    result = pow(10, note.length() - index - 1) + v;
                } else {
                    v = cal(index + 1, 0, 0, note);
                    if (v != -1) {
                        result = 4 * pow(10, note.length() - index - 1) + v;
                    } else {

                        v = cal(index + 1, 0, 1, note);
                        if (v != -1) {
                            result = 8 * pow(10, note.length() - index - 1) + v;
                        }
                        v = cal(index + 1, 1, 1, note);
                        if (v != -1) {
                            long other = 8 * pow(10, note.length() - index - 1) + v;
                            if (result == -1 || result > other) {
                                result = other;
                            }
                        }                       
                    }

                }
            } else {
                long tmp = c - '0';
                if (tmp == 1 || tmp == 5) {
                    long v = cal(index + 1, 1, 1, note);
                    if (v != -1) {
                        result = tmp * pow(10, note.length() - index - 1) + v;
                    }
                } else if (tmp == 4 || tmp == 7) {
                    long v = cal(index + 1, 0, 0, note);
                    if (v != -1) {
                        result = tmp * pow(10, note.length() - index - 1) + v;
                    }
                } else if (tmp == 8) {
                    long v = cal(index + 1, 0, 1, note);
                    if (v != -1) {
                        result = tmp * pow(10, note.length() - index - 1) + v;
                    }
                    v = cal(index + 1, 1, 1, note);
                    if (v != -1) {
                        long other = tmp * pow(10, note.length() - index - 1) + v;
                        if (result == -1 || result > other) {
                            result = other;
                        }
                    }
                } else if (tmp == 9) {
                    long v = cal(index + 1, 1, 1, note);
                    if (v != -1) {
                        result = tmp * pow(10, note.length() - index - 1) + v;
                    }
                }
            }
        } else {
            if (c == '?') {
                if (add == 1) {
                    long v = cal(index + 1, 0, 1, note);
                    if (v != -1) {
                        result = pow(10, note.length() - index - 1) + v;
                    } else {
                        v = cal(index + 1, 1, 1, note);
                        if (v != -1) {
                            result = 2 * pow(10, note.length() - index - 1) + v;
                        }
                    }
                } else {
                    if (dif == 0) {
                        long v = cal(index + 1, 0, 0, note);
                        if (v != -1) {
                            result = 4 * pow(10, note.length() - index - 1) + v;
                        } else {
                            v = cal(index + 1, 1, 1, note);
                            if (v != -1) {
                                result = 5 * pow(10, note.length() - index - 1) + v;
                            } else {
                                v = cal(index + 1, 0, 1, note);
                                if (v != -1) {
                                    result = 8 * pow(10, note.length() - index - 1) + v;
                                }
                            }
                        }
                    } else {
                        long v = cal(index + 1, 0, 1, note);
                        if (v != -1) {
                            result = 8 * pow(10, note.length() - index - 1) + v;
                        } else {
                            v = cal(index + 1, 1, 1, note);
                            if (v != -1) {
                                result = 9 * pow(10, note.length() - index - 1) + v;
                            }
                        }
                    }
                }
            } else {
                long tmp = c - '0';
                if (tmp == 1) {
                    if (add == 1) {
                        long v = cal(index + 1, 0, 1, note);
                        if (v != -1) {
                            result = tmp * pow(10, note.length() - index - 1) + v;
                        }
                    }
                } else if (tmp == 2) {
                    if (add == 1) {
                        long v = cal(index + 1, 1, 1, note);
                        if (v != -1) {
                            result = tmp * pow(10, note.length() - index - 1) + v;
                        }
                    }
                } else if (tmp == 4) {
                    if (dif == 0 && add == 0) {
                        long v = cal(index + 1, 0, 0, note);
                        if (v != -1) {
                            result = tmp * pow(10, note.length() - index - 1) + v;
                        }
                    } else if (add == 1) {
                        long v = cal(index + 1, 0, 1, note);
                        if (v != -1) {
                            result = tmp * pow(10, note.length() - index - 1) + v;
                        }
                    }
                } else if (tmp == 7) {
                    if (dif == 0 && add == 0) {
                        long v = cal(index + 1, 0, 0, note);
                        if (v != -1) {
                            result = tmp * pow(10, note.length() - index - 1) + v;
                        }
                    }
                } else if (tmp == 5) {
                    if (dif == 0 && add == 0) {
                        long v = cal(index + 1, 1, 1, note);
                        if (v != -1) {
                            result = tmp * pow(10, note.length() - index - 1) + v;
                        }
                    } else if (add == 1 && dif == 1) {
                        long v = cal(index + 1, 1, 1, note);
                        if (v != -1) {
                            result = tmp * pow(10, note.length() - index - 1) + v;
                        }
                    }
                } else if (tmp == 8) {
                    if (add == 0) {
                        long v = cal(index + 1, 0, 1, note);
                        if (v != -1) {
                            result = tmp * pow(10, note.length() - index - 1) + v;
                        }
                        if (dif == 0) {
                            v = cal(index + 1, 1, 1, note);
                            if (v != -1) {
                                long other = tmp * pow(10, note.length() - index - 1) + v;
                                if (result == -1 || result > other) {
                                    result = other;
                                }
                            }
                        }
                    }
                } else if (tmp == 9) {
                    if (add == 0) {
                        long v = cal(index + 1, 1, 1, note);
                        if (v != -1) {
                            result = tmp * pow(10, note.length() - index - 1) + v;
                        }
                    }

                }
            }
        }

        return dp[dif][add][index] = result;
    }

    long pow(int a, int b) {
        if (b == 0) {
            return 1;
        }
        long v = pow(a, b / 2);
        if (b % 2 == 0) {
            return v * v;
        }
        return v * v * a;
    }

    public static void main(String[] args) {
        String v = "?9??";
        System.out.println(new LuckySum().construct(v));
    }
}
