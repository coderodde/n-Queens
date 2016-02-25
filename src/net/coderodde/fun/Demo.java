package net.coderodde.fun;

public class Demo {

    public static void main(String[] args) {
        int n = 8;
        int count1 = 0;
        int count2 = 0;
        
        if (args.length > 0) {
            n = Integer.parseInt(args[0]);
        }

        Iterable<Queens> iterable1 = new QueensIterableV1(n);
        Iterable<Queens> iterable2 = new QueensIterableV2(n);

        if (args.length > 1) {
            for (Queens queens : iterable1) {
                System.out.println("Board " + (++count1) + ":");
                System.out.println(queens);
            }
        }

        count1 = 0;
        
        long startTime = System.nanoTime();
        
        for (Queens queens : iterable1) {
            ++count1;
        }
        
        long endTime = System.nanoTime();

        System.out.printf("Duration of QueensIterableV1: %d milliseconds.\n", 
                          (endTime - startTime) / 1_000_000);

        startTime = System.nanoTime();

        for (Queens queens : iterable2) {
            ++count2;
        }
        
        endTime = System.nanoTime();

        System.out.printf("Duration of QueensIterableV2: %d milliseconds.\n", 
                          (endTime - startTime) / 1_000_000);
        
        if (count1 != count2) {
            throw new RuntimeException("The iterators did not agree.");
        }
        
        System.out.println(count1 + " boards generated.");
    }
}
