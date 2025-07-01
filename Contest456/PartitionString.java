package Contest456;

import java.util.*;

public class PartitionString{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter String -> ");
        String word = sc.nextLine();
        System.out.println("Your output is here -> ");
        System.out.println(helper(word));
    }

    public static List<String> helper(String s) {
        Set<String> seen = new HashSet<>();
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        for (char ch : s.toCharArray()) {
            sb.append(ch); // character add kiya segment mein

            String current = sb.toString();
            if (!seen.contains(current)) {
                // agar segment unique hai
                seen.add(current);         //add in set
                result.add(current);        //add in list
                sb = new StringBuilder();   // make sb empty for new segment
            }
        }

        // Agar koi last segment bach gaya ho (edge case)
        //eg - aaaa
        if (sb.length() > 0 && !seen.contains(sb.toString())) {
            result.add(sb.toString());
        }
        return result;
    }
}

//intution - segment unique -> set

// Approach – Kaise karna hai?

// 1. Hum ek HashSet lenge jisme already mil chuke segments ko store karenge.
// 2. Ek StringBuilder lenge jo current segment bana raha hoga.
// 3. Har character ko string se uthao:
//        Us character ko current segment mein add karo.
//        Dekho kya StringBuilder.toString() set mein already hai?
//               Nahi hai → toh add karo set mein + result list mein.
//               Hai → toh continue karo next character ke saath (segment banana jaari rakho).
// Jab tak unique segment nahi milta, tab tak characters jodte jao.
//lat m edge case handle kr lo

