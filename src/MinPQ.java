import java.util.Iterator;
import java.util.NoSuchElementException;

public class MinPQ<Key extends Comparable<Key>> implements Iterable<Key>
{
    private Key[] pq;
    private int N = 0;

    /**
     * initializes the pq
     */
    @SuppressWarnings("unchecked")
    public MinPQ() { pq = (Key[]) new Comparable[2]; }

    /**
     * returns the number of keys in the pq
     *
     * @return number of keys in the pq
     */
    public int size() { return N; }

    /**
     * returns whether the pq is empty
     *
     * @return {@code true} if it is empty;
     *         {@code false} if it isn't empty
     */
    public boolean isEmpty() { return N == 0; }

    /**
     * adds a new key to the pq
     *
     * @param key the key to add
     */
    public void insert(Key key)
    {
        if (N == pq.length - 1) resize(2 * pq.length);
        pq[++N] = key;
        swim(N);
        assert isMinHeap();
    }

    /**
     * returns the smallest key in the pq
     *
     * @return smallest key in the pq
     * @throws NoSuchElementException if pq is empty
     */
    public Key min()
    {
        if (isEmpty()) throw new NoSuchElementException("Priority Queue underflow");
        return pq[1];
    }

    /**
     * removes and returns the smallest key in the pq
     *
     * @return smallest key in the pq
     * @throws NoSuchElementException if pq is empty
     */
    public Key delMin()
    {
        if (isEmpty()) throw new NoSuchElementException("Priority Queue underflow");

        Key min = pq[1];
        exch(1, N--);
        pq[N + 1] = null;
        sink(1);
        if (N > 0 && N == pq.length / 4) resize(pq.length / 2);
        assert isMinHeap();
        return min;
    }

    /**
     * copies the keys within the pq to a new pq
     *
     * @return a new pq with the same keys as the original
     * @throws NoSuchElementException if pq is empty
     */
    public MinPQ<Key> copyMinPQ()
    {
        if (isEmpty()) throw new NoSuchElementException("Priority Queue underflow");

        MinPQ<Key> copy = new MinPQ<>();
        for (int i = 1; i <= N; i++)
            copy.insert(pq[i]);

        return copy;
    }


    // moves the key at index k up
    private void swim(int k)
    {
        while (k > 1 && greater(k/2, k))
        {
            exch(k/2, k);
            k = k/2;
        }
    }

    // moves the key at index k down
    private void sink(int k)
    {
        while (2*k <= N)
        {
            int j = 2*k;
            if (j < N && greater(j, j+1)) j++;
            if (!greater(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    // compares the key at index i with the key at index j
    private boolean greater(int i, int j) { return pq[i].compareTo(pq[j]) > 0; }

    // swaps the key at index i with the key at index j
    private void exch(int i, int j)
    {
        Key temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    // resizes the array to the given size
    private void resize(int newSize)
    {
        @SuppressWarnings("unchecked")
        Key[] copy = (Key[]) new Comparable[newSize];

        for (int i = 1; i <= N; i++)
            copy[i] = pq[i];

        pq = copy;
    }


    // checks if pq is a min heap
    private boolean isMinHeap()
    {
        for (int i = 1; i <= N; i++)
            if (pq[i] == null) return false;

        for (int i = N+1; i < pq.length; i++)
            if (pq[i] != null) return false;

        if (pq[0] != null) return false;

        return isMinHeapOrdered(1);
    }

    // checks if the subtree at k is a min heap
    private boolean isMinHeapOrdered(int k)
    {
        if (k > N) return true;

        int l = 2*k;
        int r = 2*k + 1;

        if (l <= N && greater(k, l)) return false;
        if (r <= N && greater(k, r)) return false;

        return isMinHeapOrdered(l) && isMinHeapOrdered(r);
    }


    @Override
    public Iterator<Key> iterator() { return new PQIterator(); }

    private class PQIterator implements Iterator<Key>
    {
        private int i = 1;

        public boolean hasNext() { return pq[i] != null; }

        public void Remove()
        {
            // not supported
            throw new UnsupportedOperationException("Not supported");
        }

        public Key next()
        {
            if (!hasNext()) throw new NoSuchElementException("no more keys in iterator");

            return pq[i++];
        }
    }
}
