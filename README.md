# HuffmanCoding
 
Uses Huffman Coding to Encode/Compress and Decode/Extract a text file.

Currently decoding and extraction of the resulting .huf file can only be done right after the respective encoding and compression of the original file.
This is due to the information needed to decode/extract the file not being added to the encoded/compressed file, requiring the use of the Huffman tree generated from the original file. <br /> 
This will be changed in the future.


Below are some examples of compression done using this project.

File 1: <br />
Original size - 1231 KB <br />
Compressed size - 804 KB <br />
Extracted size - 1231 KB

File 2: <br />
Original size - 27276 KB <br />
Compressed size - 18972 KB <br />
Extracted size - 27276 KB


Using the first file as an example, with the addition of the information needed to extract the file, the compressed size increases by around 1 KB.
