public class Tuple<E1, E2> {
    private final E1 element1;
    private final E2 element2;

    public Tuple(E1 element1, E2 element2) {
        this.element1 = element1;
        this.element2 = element2;
    }

    public E1 getElement1() {
        return element1;
    }

    public E2 getElement2() {
        return element2;
    }

    public boolean contains(Object element) {
        return element1.equals(element) || element2.equals(element);
    }

}
