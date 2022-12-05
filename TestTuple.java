public class TestTuple {
    public static void main(String[] args) {
        Tuple<Integer, Integer> testing = new Tuple<Integer, Integer>(3, 4);

        System.out.println(testing.contains(5));
        System.out.println(testing.contains(3));
    }
}
