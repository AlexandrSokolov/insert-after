#### Java tool to insert a unique block of text into the file.

Workflow:
  1. In the original file the insert block of text is searched.

     If it is already there, the original file is not changed.

  2. If the insert block is not found, the search block of text is searched.
    
        2.1. If the search block of text is not found, 
             the insert block is appended to the end of the original file.

        2.2. Otherwise, the new line symbol (depends on OS) with the insert block of text 
            are inserted immediately after the search block of text.

Usage:

```
$ cat tmp/test1.txt 
line1
line2
  <tag1>
  ??
    someValue$
    <anotherTag />
  </tag1>
  <tag2>&%$§"</tag2>

$  java -jar insert-unique-after.jar
Parsing failed.  Reason: Missing required options: path, insert-after, insert-text
usage: insert-unique-after.jar
    --insert-after <block of text to search>   block of text to search to
                                               insert after it
    --insert-text <block of text to insert>    block of text to insert
    --path <file path>                         path to the file to update

$  java -jar insert-unique-after.jar \
  --path tmp/test1.txt \
  --insert-after "<tag1>
  ??
    someValue$
    <anotherTag />
  </tag1>" \
  --insert-text "<tag1>
  ??
    someAnotherValue$
    <anotherTag2 />
  </tag1>"
$ cat tmp/test1.txt 
line1
line2
  <tag1>
  ??
    someValue$
    <anotherTag />
  </tag1>
<tag1>
  ??
    someAnotherValue$
    <anotherTag2 />
  </tag1>
  <tag2>&%$§"</tag2>
```
