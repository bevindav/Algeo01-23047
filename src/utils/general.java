package utils;

public class general {
    public static int factorial(int num){           
        if (num == 0){
            return 1;
        }
        else {
            return(num * factorial(num-1)); 
        }      
    }

    public static int nCr (int n, int r){
        if (n>1){
            int combi = factorial(n)/(factorial(r)*factorial(n-r));
            return combi;
        } else {
            return 0;
        }
    }

    public static int [][] nCrMember (int n, int r){
        int[][] members = new int[nCr(n, r)][2];
        int i=0;
        for (int j=0; j<n-1;j++){
            for (int k=j+1; k<n;k++){
                members[i][0]=j+1;
                members[i][1]=k+1;
                i++;
            }
        }
        return members;
    }
}
