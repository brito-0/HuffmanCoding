import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Huffman
{
    // node class
    private static class Node implements Comparable<Node>
    {
        // character of the node
        private final char c;
        // frequency of the character
        private final int weight;

        // left and right child
        private final Node left, right;


        public Node(char c, int weight, Node left, Node right)
        {
            this.c = c;
            this.weight = weight;
            this.left = left;
            this.right = right;
        }

        // checks if the node has children
        private boolean isLeaf() { return left == null && right == null; }

        // compares the weight of two nodes
        public int compareTo(Node other) { return this.weight - other.weight; }
    }


    // size of extended ASCII
    private static final int A_SIZE = 256;


    // name of the file to read from
    private final String inFileName;

    // name of the file to write to
    private String outFileName = "";

    // text within the file
    private String text;

    // root node of the huffman tree
    private Node root;

    // code table
    private final String[] cTable = new String[A_SIZE];


    /**
     *
     *
     * @param inFileName name of the file to read from
     * @throws IllegalArgumentException if the file name is empty or blank
     */
    public Huffman(String inFileName)
    {
        if (inFileName.isBlank()) throw new IllegalArgumentException("The file name can't be empty");

        this.inFileName = inFileName;

        // add text to text variable from the file
        getText();
    }

    /**
     *
     *
     * @param inFileName name of the file to read from
     * @param outFileName name of the file to write to
     * @throws IllegalArgumentException if the inFileName or the outFileName are empty or blank
     */
    public Huffman(String inFileName, String outFileName)
    {
        if (inFileName.isBlank() && outFileName.isBlank()) throw new IllegalArgumentException("The file names can't be empty");

        this.inFileName = inFileName;
        this.outFileName = outFileName;

        // add text to text variable from the file
        getText();
    }

    // initializes everything required to encode/compress the text
    private void  initializeEncodeCompress()
    {
        assert !text.isEmpty();

        System.out.println("Initializing E/C...");

        // create frequency count
        int[] fCount = getFrequencyCount();

        // create minpq
        MinPQ<Node> pq = new MinPQ<>();
        for (char c = 0; c < A_SIZE; c++)
            if (fCount[c] > 0)
                pq.insert(new Node(c, fCount[c], null, null));

        createHuffmanTree(pq);

        createCodeTable(root, "");
    }

    // initializes everything required to decode/extract the text
    private void  initializeDecodeExtract()
    {
        assert !text.isEmpty();

        System.out.println("Initializing D/E...");

        //


    }

    /**
     * creates an output file with the contents of the input file encoded
     *
     * @throws RuntimeException if file can't be accessed
     */
    public void encode()
    {
        initializeEncodeCompress();

        assert !text.isEmpty();
        assert (root != null && !root.isLeaf());
//        assert (cTable != null);

        if (outFileName.isEmpty()) outFileName = "files/encodedOut.txt";

        try
        {
            outEncodedTextNoPQ();
//            outEncodedText();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * NOT IMPLEMENTED
     * creates an output file with the contents of the input file decoded
     *
     * @throws RuntimeException if file can't be accessed
     */
    public void decode()
    {
        initializeEncodeCompress();

        assert !text.isEmpty();
        assert (root != null && !root.isLeaf());
//        assert (cTable != null);

        if (outFileName.isEmpty()) outFileName = "files/decodedOut.txt";

        try
        {
            outDecodedText();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * creates an output file with the compressed contents of the input file
     *
     * @throws RuntimeException if file can't be accessed
     */
    public void compress()
    {
        initializeEncodeCompress();

        assert !text.isEmpty();
        assert (root != null && !root.isLeaf());
//        assert (cTable != null);

        if (outFileName.isEmpty()) outFileName = "files/compressedOut.huf";

        try
        {
            outCompressedTextNoPQ();
//            outCompressedText();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * NOT IMPLEMENTED
     * creates an output file with the extracted contents of the input file
     *
     * @throws RuntimeException if file can't be accessed
     */
    public void extract()
    {
        initializeDecodeExtract();

        assert !text.isEmpty();
        assert (root != null && !root.isLeaf());
//        assert (cTable != null);

        if (outFileName.isEmpty()) outFileName = "files/extractedOut.txt";

        try
        {
            outExtractedText();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }


    }


    // places the text within the inFileName file in text
    private void getText()
    {
        assert !inFileName.isEmpty();

        Path inFile = Path.of(inFileName);
        try { text = Files.readString(inFile); }
        catch (IOException e) { throw new RuntimeException(e); }
    }

    // creates an array with the frequency of every character in text
    private int[] getFrequencyCount()
    {
        int[] count = new int[A_SIZE];

        for (int i = 0; i < text.length(); i++)
            count[text.charAt(i)]++;

        return count;
    }

    // creates a huffman tree from the minpq provided
    private void createHuffmanTree(MinPQ<Node> pq)
    {
        while (pq.size() > 1)
        {
            Node left = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0', left.weight + right.weight, left, right);
            pq.insert(parent);
        }

        root = pq.delMin();
    }

    // places the code of every letter used in text inside cTable
    private void createCodeTable(Node root, String s)
    {
        if (!root.isLeaf())
        {
            createCodeTable(root.left, s + '0');
            createCodeTable(root.right, s + '1');
        }
        else cTable[root.c] = s;
    }

    // encode text and places it into a file
    private void outEncodedTextNoPQ() throws IOException
    {
        System.out.println("Encoding...");

//        FileWriter outW = new FileWriter("encodedOut.txt");
        FileWriter outW = new FileWriter(outFileName);

        // encode
        for (int i = 0; i < text.length(); i++)
        {
            String code = cTable[text.charAt(i)];
            for (int j = 0; j < code.length(); j++)
            {
                if (code.charAt(j) == '0')
                    outW.write('0');
                else
                    outW.write('1');
            }
        }
        outW.close();


        // decode the encoded text into a new file
        outDecodedTextNoPQ();
    }

    // encode text and places it into a file
    private void outEncodedText() throws IOException
    {
        System.out.println("Encoding...");

        throw new UnsupportedOperationException("not implemented");
    }

    // decodes text and places it into a file
    private void outDecodedTextNoPQ() throws IOException
    {
        System.out.println("Decoding...");
        //
        //
        //
        // for now, it only decodes the text that was given at the start to be encoded


//        Path inFile = Path.of("encodedOut.txt");
        Path inFile = Path.of(outFileName);
        String encodedText = Files.readString(inFile);

        FileWriter outW = new FileWriter("files/decodedOut.txt");

        Node cur = root;
        StringBuilder outString = new StringBuilder();

        for (int i = 0; i < encodedText.length(); i++)
        {
            if (encodedText.charAt(i) == '0') cur = cur.left;
            else cur = cur.right;

            if (cur.isLeaf())
            {
//                outW.write(cur.c);
                outString.append(cur.c);
                cur = root;
            }
        }


        //
        // compare the 2 strings
        assert text.contentEquals(outString);

        outW.write(outString.toString());
        outW.close();
    }

    // decodes text and places it into a file
    private void outDecodedText() throws IOException
    {
        System.out.println("Decoding...");

        throw new UnsupportedOperationException("not implemented");
    }

    // compresses text and places it into a file
    private void outCompressedTextNoPQ() throws IOException
    {
        System.out.println("Compressing...");

//        FileWriter outW = new FileWriter("compressedOut.huf");
        FileWriter outW = new FileWriter(outFileName);

        // compress
        int bitCount = 0;
        int buffer = 0;
        int n = 0;
        for (int i = 0; i < text.length(); i++)
        {
            for (int j = 0; j < cTable[text.charAt(i)].length(); j++)
            {
                buffer <<= 1;
                if (cTable[text.charAt(i)].charAt(j) == '1') buffer |= 1;
                n++;
                bitCount++;

                if (n == 7)
                {
                    outW.write(buffer);
                    n = 0;
                    buffer = 0;
                }
            }
        }

        while (n > 0)
        {
            buffer <<= 1;
            n++;
            if (n == 7)
            {
                outW.write(buffer);
                n = 0;
                buffer = 0;
            }
        }

        outW.close();


        // extract the compressed text into a new file
        outExtractedTextNoPQ(bitCount);
    }

    // compresses text and places it into a file
    private void outCompressedText() throws IOException
    {
        System.out.println("Compressing...");

        throw new UnsupportedOperationException("not implemented");
    }

    // extracts text and places it into a file
    private void outExtractedTextNoPQ(int bitCount) throws IOException
    {
        System.out.println("Extracting...");
        //
        //
        //
        // for now, it only extracts the text that was given at the start to be compressed


//        Path inFile = Path.of("compressedOut.huf");
        Path inFile = Path.of(outFileName);
        byte[] compressed = Files.readAllBytes(inFile);

        FileWriter outW = new FileWriter("files/extractedOut.txt");

        Node cur = root;
        StringBuilder outString = new StringBuilder();

        int count = 0;

        for (int i = 0; i < compressed.length; i++)
        {
            for (int j = 6; j >= 0; j--)
            {
                if (count >= bitCount) break;

                if (((compressed[i] >> j) & 1) == (byte) 1) cur = cur.right;
                else cur = cur.left;

                if (cur.isLeaf())
                {
//                    outW.write(cur.c);
                    outString.append(cur.c);
                    cur = root;
                }
                count++;
            }
        }


        //
        // compare the 2 strings
        assert text.contentEquals(outString);

        outW.write(outString.toString());
        outW.close();
    }

    // extracts text and places it into a file
    private void outExtractedText() throws IOException
    {
        System.out.println("Extracting...");

        throw new UnsupportedOperationException("not implemented");
    }


    //

}
