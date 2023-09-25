// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main
{
    public static void main(String[] args)
    {
        // uncomment when final compress/extract is finished
//        if (args.length > 0)
//        {
////            Huffman h = new Huffman(args[0], args[1]);
////            h.encode();
////            return;
//
//            if (args[0].equalsIgnoreCase("encode"))
//            {
//                Huffman h = new Huffman(args[1], args[2]);
//                h.encode();
//            }
////            else if (args[0].equalsIgnoreCase("decode"))
////            {
////                Huffman h = new Huffman(args[1], args[2]);
////                h.decode();
////            }
//            else if (args[0].equalsIgnoreCase("compress"))
//            {
//                Huffman h = new Huffman(args[1], args[2]);
//                h.compress();
//            }
//            else if (args[0].equalsIgnoreCase("extract"))
//            {
//                Huffman h = new Huffman(args[1], args[2]);
//                h.extract();
//            }
//            else throw new IllegalArgumentException("Illegal command line argument");
//            return;
//        }


        Huffman h = new Huffman("files/MobyDick.txt");
//        Huffman h = new Huffman("files/MobyDick2.txt");
//        Huffman h = new Huffman("files/MobyDick5.txt");
//
//        Huffman h = new Huffman("files/WilliamShakespeare.txt");
//        Huffman h = new Huffman("files/WilliamShakespeare2.txt");
//        Huffman h = new Huffman("files/WilliamShakespeare5.txt");
//
//        h.encode();
        h.compress();
    }
}
