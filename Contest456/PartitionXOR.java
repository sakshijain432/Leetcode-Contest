package Contest456;

import java.util.Arrays;
import java.util.Scanner;

public class PartitionXOR {
     static int[][] dp;
      static  int[] prefix;
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter size of array -> ");
        int n = sc.nextInt();
         System.out.println("Enter no. of partitons -> ");
        int k = sc.nextInt();
        int arr[] = new int[n];
        for(int i=0;i<n;i++){
            arr[i] = sc.nextInt();
        }
        System.out.println("Your output is  -> ");
        System.out.println(helper(arr,k));
    }
    
    public static  int helper(int[] nums, int k) {
        int n = nums.length;

        // Build prefix XOR array
        prefix = new int[n + 1];
        prefix[0] = 0;
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] ^ nums[i];
        }

        // DP[i][k]: min max XOR starting from i with k partitions
        dp = new int[n + 1][k + 1];
        for (int[] row : dp) Arrays.fill(row, -1);

        return dfs(0, k, nums.length);
    }
     private static int dfs(int i, int k, int n) {
        if (dp[i][k] != -1) return dp[i][k];

        if (k == 0 && i == n) return 0; // perfect partition
        if (k == 0 || i == n) return Integer.MAX_VALUE;

        int ans = Integer.MAX_VALUE;
        //loop on array -> partition
        for (int j = i + 1; j <= n; j++) {
            int xor = prefix[j] ^ prefix[i]; // xor of nums[i..j-1]
            int next = dfs(j, k - 1, n);
            if (next != Integer.MAX_VALUE) {
                ans = Math.min(ans, Math.max(xor, next));
            }
        }

        return dp[i][k] = ans;
    }
}
    


//prerequiesties -> know DP PATTERNS 

//problem  stmt ->
// AT EVERY idx -> cut ->  we have to perform specific operation -> [1,2,3] => [1],[2,3] or [1,2],[3]
// tum partitions bana rahe ho, unke XOR calculate kar rahe ho, aur unmein se sabse bada find kar rahe ho — usko minimum karna hai.
                // find xor -> MAX xor among all possible splits -> take MIN of them 
                //sbhi subarray ka xor kro -> un m s max find kro
                //[1],[2,3] => 1 ka 1 and 2^3 = 3 ----------max = 3------------- (1) -->
                    //                                                                  |->  min = (3,3) => 3
                //[1,2],[3] => 1^2 = 2 and 3 ka 3 -----------max = 3-------------(2) -->



/**
    intution ->

     "Agar mujhe partition banana hai, toh main har index pe ye soch sakta hoon – kya main yaha cut maaru ya nahi?"                
    Mujhe at every position yeh decision lena hai: -> Agar yahan tak subarray bana diya, toh baaki array ka kya hoga?" => means => "Try all partitions → pick best” problem.=> → DP on partitions
     Toh mujhe loop chahiye jo idx se lekar n tak chale --> Aur har possible subarray try kare:
     currXor ^= nums[i]    -->  curr xor find kro
     phir recursion call karo on i+1
    This looks like:
     for(i=idx to n-1):
       XOR banao
       next = dfs(i+1, rem-1)
       ans = min(ans, currXor ^ next)
    
    Toh recursion ban gaya!

{/*
Approach -

Toh hamare paas do levels of logic hain ->  1. Har partition ka XOR calculate karo
                                            2. Jo sabse bada XOR milega among those partitions → usko minimize karo
So, hum kar rahe hain:
→ Minimize(Max of XORs of k contiguous subarrays) =>  DP + recursion + memoization with partitions

1. Ham soch rahe hain: ->  Tum kisi idx se start kar rahe ho array mein -> Tumhe abhi k partitions banana hai -> So define:
        dfs(idx, k): means from index idx to end, make k partitions, and return the minimum possible maxXOR
        where , 
        idx = abhi kaha ho array mein
        k = abhi kitne subarrays banana baaki hain

2. Ab ek loop chalao idx se end tak:
            XOR build karo from idx to i
            Uske baad: call dfs(i+1, k-1) → remaining array pe kaam -> next idx to end and find ans for k-1 partition
            Har step pe:
            maxXor = max(currXor, dfs(i+1, k-1))
            answer = min(answer, maxXor)

Yeh best combo hai:
→ Har possible split try karo
→ Har partition ka max XOR calculate karo
→ Final answer mein minimum max pick karo

3.  Base Case
    Valid case => if (idx == n && k == 0) → array finish + partitions done → return 0
    Invalid case => if (idx == n || k == 0) → array ya partition khatam, but mismatch → return INF (invalid state)

4. Memo = dp[idx][k] ....... idx -> position in array and k -> remaining partitions

5.Wait .................we ca use prefix array which store xor upto elemert i 


*/
