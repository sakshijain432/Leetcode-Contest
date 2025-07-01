package Contest456;

import java.util.Scanner;

public class LongestCommonPrefix {
     public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter size of array");
        int n = sc.nextInt();
        String words[] = new String[n];
        System.out.println("Enter String -> ");
        sc.nextLine();
        for(int i=0;i<n;i++){
            words[i] = sc.nextLine();
        }

        System.out.println("Your output is here -> ");
        int ans[] = helper(words);
        for(int i =0;i<ans.length;i++){
            System.out.print(ans[i]+" ");
        }
    }

      public static int[] helper(String[] words) {
        int n = words.length;
        int ans[] = new int[n];
         if (words.length <= 1) return new int[words.length];


        int lcp[] = new int[n-1];
        for(int i=0;i<n-1;i++){
            lcp[i] = getLCP(words[i],words[i+1]);
        }
        // prefix -> max lcp from 0 to i
        int[] prefix = new int[n - 1];
        //suffix -> max lcp from i to end 


        int[] suffix = new int[n - 1];

        prefix[0] = lcp[0];
        for (int i = 1; i < n - 1; i++) {
            prefix[i] = Math.max(prefix[i - 1], lcp[i]);
        } 
        // last el (n-2) hoga bcz size (n-1) h
        suffix[n - 2] = lcp[n - 2];

        for (int i = n - 3; i >= 0; i--) {
            suffix[i] = Math.max(suffix[i + 1], lcp[i]);
        }
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                 //First word removed → lcp[0] gone, use suffixMax[1...]
                ans[i] = (n > 2) ? suffix[1] : 0;
            }
            else if (i == n - 1) {
                 // Last word removed → lcp[n-2] gone, use prefixMax[0...n-3]
                ans[i] = (n > 2) ? prefix[n - 3] : 0;
            } 
            else {
                int newLCP = getLCP(words[i - 1], words[i + 1]); // new pair =>  i-1 aur i ke lcp hata chuke ho 
                int left = (i - 2 >= 0) ? prefix[i - 2] : 0;  // i-1 ko hta diya => So left part bacha: lcp[0..i-2] => Uska max hai: prefixMax[i - 2]
                int right = (i + 1 < n - 1) ? suffix[i + 1] : 0; // i ko hta diya == lcp[i] also removed => Right part bacha: lcp[i+1 ... n-2] ==> Uska max hai: suffixMax[i + 1]   ==> Agar i+1 >= n-1 hai, toh koi right bacha hi nahi → return 0
                ans[i] = Math.max(newLCP, Math.max(left, right)); 
            }
        }

        return ans;
    }
    private static int getLCP(String a ,String b){
        int len = Math.min(a.length(),b.length());
        int i=0;
        while(i< len && a.charAt(i) == b.charAt(i)){
            i++;
        }
        return i;
    }
 //intution -> find common prefix ->  LCP between 2 strings
// Now for every index i:
// Remove words[i]
// Calculate the LCPs between all remaining adjacent pairs => 0 k liy -> last k liy -> remaining el k liy
// Store the maximum of those LCPs in ans[i]
//----------------- but TLE hit here -----------------------------------------------

//what do next ? 

//prefix - suffix array 
}
