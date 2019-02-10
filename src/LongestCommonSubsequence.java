public class LongestCommonSubsequence {

    public int lcsDynamic(char str1[],char str2[]){

        int temp[][] = new int[str1.length + 1][str2.length + 1];
        for(int i=1; i < temp.length; i++){
            for(int j=1; j < temp[i].length; j++){
                if(str1[i-1] == str2[j-1]) {
                    temp[i][j] = temp[i - 1][j - 1] + 1;
                }
                else
                {
                    temp[i][j] = temp[i - 1][j] > temp[i][j - 1] ? temp[i - 1][j] : temp[i][j - 1];
                }
            }
        }
        return temp[str1.length][str2.length];

    }

    public static void main(String args[]){
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();
        String str1 = "BANXGLAXDM";
        String str2 = "BANGLXADM";

        int result = lcs.lcsDynamic(str1.toCharArray(), str2.toCharArray());
        System.out.print(result);
    }

}
