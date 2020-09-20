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

$  java -jar insert-after.jar
Parsing failed.  Reason: Missing required options: path, insert-after, insert-text
usage: insert-after.jar
    --insert-after <block of text to search>   block of text to search to
                                               insert after it
    --insert-text <block of text to insert>    block of text to insert
    --path <file path>                         path to the file to update

$  java -jar insert-after.jar \
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
