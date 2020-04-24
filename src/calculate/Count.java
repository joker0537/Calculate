package calculate;

public class Count {

    private  static int plusCounter ;
    private  static int minusCounter ;
    private  static int multipleCounter;
    private  static int divideCounter;

    public static int getPlusCounter() {
        return plusCounter;
    }

    public static int getMinusCounter() {
        return minusCounter;
    }

    public static int getMultipleCounter() {
        return multipleCounter;
    }

    public static int getDivideCounter() {
        return divideCounter;
    }

    public synchronized static void incPlus(){
        plusCounter++;
    }

    public synchronized static void incMinus(){

        minusCounter++;
    }

    public synchronized static void incMultiple(){

        multipleCounter++;
    }

    public synchronized static void incDivide(){
        divideCounter++;
    }

}
