>数据结构与算法（十一）Trie字典树-https://blog.csdn.net/johnny901114/article/details/80711441

## 目录
1.  Trie字典树的基本概念
2.  Trie字典树的基本操作
    -   插入
    -   查找
    -   前缀查询
    -   删除
3.  基于链表的Trie字典树
4.  基于Trie的Set性能对比
5.  LeetCode相关线段树的问题
    -   LeetCode第208号问题
    -   LeetCode第211号问题
    -   LeetCode第677号问题


## Trie定义
Trie字典树主要用于存储字符串，Trie 的每个 Node 保存一个字符。用链表来描述的话，就是一个字符串就是一个链表。
每个Node都保存了它的所有子节点。

## 实现
-   基于数组形式：https://yq.aliyun.com/articles/386511
-   基于链表形式：https://blog.csdn.net/johnny901114/article/details/80711441

## 代码
```java
/** *//**
 * A word trie which can only deal with 26 alphabeta letters.
 * @author Leeclipse
 * @since 2007-11-21
 */

public class Trie{
 
   private Vertex root;//一个Trie树有一个根节点

    //内部类
    protected class Vertex{//节点类
        protected int words;
        protected int prefixes;
        protected Vertex[] edges;//每个节点包含26个子节点(类型为自身)
        Vertex() {
            words = 0;
            prefixes = 0;
            edges = new Vertex[26];
            for (int i = 0; i < edges.length; i++) {
                edges[i] = null;
            }
        }
    }

  
    public Trie () {
        root = new Vertex();
    }

   
    /** *//**
     * List all words in the Trie.
     * 
     * @return
     */

    public List< String> listAllWords() {
       
        List< String> words = new ArrayList< String>();
        Vertex[] edges = root.edges;
       
        for (int i = 0; i < edges.length; i++) {
            if (edges[i] != null) {
                     String word = "" + (char)('a' + i);
                depthFirstSearchWords(words, edges[i], word);
            }
        }        
        return words;
    }

     /** *//**
     * Depth First Search words in the Trie and add them to the List.
     * 
     * @param words
     * @param vertex
     * @param wordSegment
     */

    private void depthFirstSearchWords(List words, Vertex vertex, String wordSegment) {
        Vertex[] edges = vertex.edges;
        boolean hasChildren = false;
        for (int i = 0; i < edges.length; i++) {
            if (edges[i] != null) {
                hasChildren = true;
                String newWord = wordSegment + (char)('a' + i);                
                depthFirstSearchWords(words, edges[i], newWord);
            }            
        }
        if (!hasChildren) {
            words.add(wordSegment);
        }
    }

    public int countPrefixes(String prefix) {
        return countPrefixes(root, prefix);
    }

    private int countPrefixes(Vertex vertex, String prefixSegment) {
        if (prefixSegment.length() == 0) { //reach the last character of the word
            return vertex.prefixes;
        }

        char c = prefixSegment.charAt(0);
        int index = c - 'a';
        if (vertex.edges[index] == null) { // the word does NOT exist
            return 0;
        } else {

            return countPrefixes(vertex.edges[index], prefixSegment.substring(1));

        }        

    }

    public int countWords(String word) {
        return countWords(root, word);
    }    

    private int countWords(Vertex vertex, String wordSegment) {
        if (wordSegment.length() == 0) { //reach the last character of the word
            return vertex.words;
        }

        char c = wordSegment.charAt(0);
        int index = c - 'a';
        if (vertex.edges[index] == null) { // the word does NOT exist
            return 0;
        } else {
            return countWords(vertex.edges[index], wordSegment.substring(1));

        }        

    }

    
    /** *//**
     * Add a word to the Trie.
     * 
     * @param word The word to be added.
     */

    public void addWord(String word) {
        addWord(root, word);
    }

    
    /** *//**
     * Add the word from the specified vertex.
     * @param vertex The specified vertex.
     * @param word The word to be added.
     */

    private void addWord(Vertex vertex, String word) {
       if (word.length() == 0) { //if all characters of the word has been added
            vertex.words ++;
        } else {
            vertex.prefixes ++;
            char c = word.charAt(0);
            c = Character.toLowerCase(c);
            int index = c - 'a';
            if (vertex.edges[index] == null) { //if the edge does NOT exist
                vertex.edges[index] = new Vertex();
            }

            addWord(vertex.edges[index], word.substring(1)); //go the the next character
        }
    }

    public static void main(String args[])  //Just used for test
    {
    Trie trie = new Trie();
    trie.addWord("China");
    trie.addWord("China");
    trie.addWord("China");

    trie.addWord("crawl");
    trie.addWord("crime");
    trie.addWord("ban");
    trie.addWord("China");

    trie.addWord("english");
    trie.addWord("establish");
    trie.addWord("eat");
    System.out.println(trie.root.prefixes);
     System.out.println(trie.root.words);


   
     List< String> list = trie.listAllWords();
     Iterator listiterator = list.listIterator();
     
     while(listiterator.hasNext())
     {
          String s = (String)listiterator.next();
          System.out.println(s);
     }

   
     int count = trie.countPrefixes("ch");
     int count1=trie.countWords("china");
     System.out.println("the count of c prefixes:"+count);
     System.out.println("the count of china countWords:"+count1);

 
    }
}
```

```java
public class Trie {

    private Node root;

    private int size;

    private static class Node {
        public boolean isWord;
        public Map<Character, Node> next;

        public Node() {
            next = new TreeMap<>();
        }

        public Node(boolean isWord) {
            this();
            this.isWord = isWord;
        }

    }

    public Trie() {
        root = new Node();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 插入操作
     *
     * @param word 单词
     */
    public void add(String word) {
        Node current = root;
        char[] cs = word.toCharArray();
        for (char c : cs) {
            Node next = current.next.get(c);
            if (next == null) {
                current.next.put(c, new Node());
            }
            current = current.next.get(c);
        }
        //如果当前的node已经是一个word，则不需要添加
        if (!current.isWord) {
            size++;
            current.isWord = true;
        }
    }


    /**
     * 是否包含某个单词
     *
     * @param word 单词
     * @return 存在返回true，反之false
     */
    public boolean contains(String word) {
        Node current = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            Node node = current.next.get(c);
            if (node == null) {
                return false;
            }
            current = node;
        }
        //如果只存在 panda这个词，查询 pan，虽然有这3个字母，但是并不存在该单词
        return current.isWord;
    }


    /**
     * Trie是否包含某个前缀
     *
     * @param prefix 前缀
     * @return
     */
    public boolean containsPrefix(String prefix) {
        Node current = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            Node node = current.next.get(c);
            if (node == null) {
                return false;
            }
            current = node;
        }
        return true;
    }


    /*
     * 1，如果单词是另一个单词的前缀，只需要把该word的最后一个节点的isWord的改成false
     * 2，如果单词的所有字母的都没有多个分支，删除整个单词
     * 3，如果单词的除了最后一个字母，其他的字母有多个分支，
     */

    /**
     * 删除操作
     *
     * @param word
     * @return
     */
    public boolean remove(String word) {
        Node multiChildNode = null;
        int multiChildNodeIndex = -1;
        Node current = root;
        for (int i = 0; i < word.length(); i++) {
            Node child = current.next.get(word.charAt(i));
            //如果Trie中没有这个单词
            if (child == null) {
                return false;
            }
            //当前节点的子节点大于1个
            if (child.next.size() > 1) {
                multiChildNodeIndex = i;
                multiChildNode = child;
            }
            current = child;
        }
        //如果单词后面还有子节点
        if (current.next.size() > 0) {
            if (current.isWord) {
                current.isWord = false;
                size--;
                return true;
            }
            //不存在该单词，该单词只是前缀
            return false;
        }
        //如果单词的所有字母的都没有多个分支，删除整个单词
        if (multiChildNodeIndex == -1) {
            root.next.remove(word.charAt(0));
            size--;
            return true;
        }
        //如果单词的除了最后一个字母，其他的字母有分支
        if (multiChildNodeIndex != word.length() - 1) {
            multiChildNode.next.remove(word.charAt(multiChildNodeIndex + 1));
            size--;
            return true;
        }
        return false;
    }
}
```

## 其他知识点： 

1. 数据结构与算法系列 https://blog.csdn.net/johnny901114/column/info/23432
2. 浅谈Trie树 https://www.cnblogs.com/llllllpppppp/p/9449846.html