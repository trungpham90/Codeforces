import java.io.*;
import java.util.*;

public class BabyStepGiantStep {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        for(int z = 0;  z < q; z++){
            int a = in.nextInt();
            int b = in.nextInt();
            int d = in.nextInt();
            if(d == 0){
                System.out.println(0);
            }else if(a == d || b == d){
                System.out.println(1);
            }else{
                if(d < b){
                    System.out.println(2);
                }else{
                    int v = d/b;
                    if(v*b < d){
                        v++;
                    }
                    System.out.println(v);
                }
            }
        }
    }
}