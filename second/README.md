### Problem

We need you to build a function to justify text, this will be your signature:

`public static String justify(final int len, final String text)`

- For your solution we ask you to use Java 
- Words must be aligned into lines of size `len`
  - There can be no split words, if a word doesn't fit in the line move it to the next
  - Fill the remaining space with blanks for padding
  - Padding must be distributed in decreasing order from left to right
  - No word will be longer than `len`
  
### Example

let's say you are given the following call:

`justify(10, "this is a text with more than 10 characters")`

your result must look like:

    this  is a
    text  with
    more  than
    10
    characters
    
As you can see for the first line "this is a", we needed 3 spaces so we distributed 
2 for the first position and 1 for the last
