package com.alison.readblack;

/**
 * 手撕红黑树
 * By 469の瘸子 （意义不明的口胡：现在应该是420の的瘸子233）
 * linked: https://www.cnblogs.com/469Accelerator/p/13368981.html  add
 * https://www.cnblogs.com/469Accelerator/p/13369437.html del
 **/

public class RedBlackTree<E extends Comparable<E> & PrintToDOS> {

    public static final boolean black = true;
    public static final boolean red = false;
    public Node root;

    class Node {//节点类
        public Node parent;
        public Node left;
        public Node right;
        public E element;
        public boolean color;

        public Node(E element) {//构造方法，默认新节点为红色
            this.element = element;
            this.color = red;
        }

        //打印的红黑树的时候，会调用每个节点的打印方法
        public void print() {
            //先打印颜色
            if (this.color) {
                System.out.print("  black:");
            } else {
                System.out.print("  red:");
            }
            //再打印值
            element.print();
            //最后打印父节并换行
            if (parent == null) {
                System.out.println("  this is root");
            } else {
                System.out.print("  parent is:");
                parent.element.println();
            }
        }

    }

    //插入方法，会调用insert方法和fixAfterInsertion方法
    public void insert(E element) {
        //case1:树中无元素，直接将elemnt插进去涂黑
        if (root == null) {
            root = new Node(element);
            root.color = black;
        } else {//case2:树非空，先按二叉搜索树的方式确定元素位置，再视父元素颜色分类处理
            //先把节点插进去，如果插的元素已经存在会返回null
            Node node = insertBST(element);
            //再对树进行维护
            fixAfterInsertion(node);
        }

    }

    //该方法只负责将新的节点插进树里，不负责维护红黑树性质
    private Node insertBST(E element) {
        Node pointer = root;
        Node pointer_parent = null;

        do {
            switch (element.compareTo(pointer.element)) {
                case 0:
                    System.out.println("已有当前元素！");
                    return null;
                case 1:
                    pointer_parent = pointer;
                    pointer = pointer.right;
                    break;
                case -1:
                    pointer_parent = pointer;
                    pointer = pointer.left;
                    break;
                default:
                    break;
            }
        } while (pointer != null);

        Node child = new Node(element);
        child.parent = pointer_parent;

        //compareTo的结果只会是1或-1。不会出现0，是0的话，在上方的switch语句里就return了
        if (pointer_parent.element.compareTo(element) > 0) {
            pointer_parent.left = child;
        } else {
            pointer_parent.right = child;
        }
        return child;
    }

    //该方法负责插入后的维护工作
    private void fixAfterInsertion(Node node) {
        Node cur, parent, grandparent, uncle;
        cur = node;
        //检查是否需要维护树,cur是null的话说明插的元素已存在，就不用维护了
        if (cur != null) {
            parent = cur.parent;
            cur.print();
            //case2.1:父节点为黑色或为空，不用维护
            if (parent == null || parent.color == black) {
                return;
            } else {//case2.2:父节点为红色，视叔叔节点颜色分类处理

                //region 先获取U、G节点的引用（这里G必然非空，因为G空必然P为根且黑，那就不会执行到这里）
                grandparent = parent.parent;
                if (grandparent.left == parent) {
                    uncle = grandparent.right;
                } else {
                    uncle = grandparent.left;
                }
                //endregion

                //case2.2.1:U节点为黑色（NIL节点也是黑色的）。视C、P、G节点的形态处理
                if (uncle == null || uncle.color == black) {
                    //case2.2.1.1:C、P、G形态为“/”、“\”。以G为支点右旋或左旋，P变黑、G变红
                    if (grandparent.element.compareTo(parent.element) == parent.element.compareTo(cur.element)) {
                        parent.color = black;
                        grandparent.color = red;
                        if (grandparent.element.compareTo(parent.element) > 0) {//“/”形态，右旋
                            rightRotate(grandparent);
                        } else {//“\”形态，左旋
                            leftRotate(grandparent);
                        }
                    } else {//case2.2.1.2:C、P、G形态为“<”、“>”。先以P为支点左旋或右旋，在以P为支点右旋或左旋
                        cur.color = black;
                        grandparent.color = red;
                        if (grandparent.element.compareTo(parent.element) > 0) {//“<”形态，P左旋后、G右旋
                            leftRotate(parent);
                            rightRotate(grandparent);
                        } else {//“>”形态，P右旋后、G左旋
                            rightRotate(parent);
                            leftRotate(grandparent);
                        }
                    }
                } else {//case2.2.2:U节点为红色。将P、G、U节点换色，然后cur指向G节点调用维护函数
                    grandparent.color = red;
                    parent.color = black;
                    uncle.color = black;
                    fixAfterInsertion(grandparent);
                }

            }

        }
        root.color = black;
    }

    //左旋方法
    private void leftRotate(Node node) {
        Node parent = node.parent;
        Node child = node.right;
        Node childLeft = child == null ? null : child.left;
        //子节点上位
        if (parent == null) {//支点为根节点，parent会是空
            child.parent = null;
            root = child;
        } else {
            if (parent.left == node) {
                parent.left = child;
                child.parent = parent;
            } else {
                parent.right = child;
                child.parent = parent;
            }
        }
        //父节点下位
        child.left = node;
        node.parent = child;
        //子树调整
        node.right = childLeft;
        if (childLeft != null) {
            childLeft.parent = node;
        }
    }

    //右旋方法
    private void rightRotate(Node node) {
        Node parent = node.parent;
        Node child = node.left;
        Node childRight = child == null ? null : child.right;
        //子节点上位
        if (parent == null) {//支点为根节点，parent会是空
            child.parent = null;
            root = child;
        } else {//支点不是根节点
            if (parent.left == node) {
                parent.left = child;
                child.parent = parent;
            } else {
                parent.right = child;
                child.parent = parent;
            }
        }

        //父节点下位
        child.right = node;
        node.parent = child;
        //子树调整
        node.left = childRight;
        if (childRight != null) {
            childRight.parent = node;
        }
    }

    //打印红黑树
    public void printRBT(Node node) {

        if (node != null) {
            printRBT(node.left);
            node.print();
            printRBT(node.right);
        } else {
            return;
        }
    }

    public static void main(String[] args) {

        //13,8,5,11,6,22,27,25,14,17 另外一组调试数据
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        RedBlackTree<Element> redBlackTree = new RedBlackTree<Element>();

        for (int i : nums) {
            Element element = new Element(i);
            redBlackTree.insert(element);
        }
        //打印红黑树
        redBlackTree.printRBT(redBlackTree.root);

        //删除操作
        int value = 3;
        redBlackTree.remove(new Element(value));
        System.out.println("删除节点" + value + "后，打印：");

        //打印红黑树
        redBlackTree.printRBT(redBlackTree.root);
    }

    /**
     * ——————————  ——   分割线：以下是删除代码   —————————————
     **/
    //从树中删除一个元素的代码
    public void remove(E element) {
        Node pointer = getNodeByElement(element);
        if (pointer == null) {
            System.out.print("树中并没有要删除的元素");
            return;
        }
        do {
            //case1:要删除的节点仅有一个子树，红黑树性质决定该情况下删除的必然是黑节点，且子节点为红色叶子节点
            if ((pointer.left == null) != (pointer.right == null)) {
                //要删除的节点的子树（仅为一个红色叶子节点）顶上来并变色
                removeOneBranchNode(pointer);
                return;
            } else {//case2:删除节点为叶子节点
                if ((pointer.left == null) && (pointer.right == null)) {
                    removeLeafNode(pointer);
                    return;
                } else {//case3:要删除的节点有两个子树
                    //指针指向后继节点，后继节点element顶替要删除的element。再do一次以判定新指针的case(此时只会是case2、3)
                    pointer = changePointer(pointer);
                }
            }

        } while (true);
    }

    //获取要删除的元素的Node，若返回为null代表树中没有要删除的元素
    public Node getNodeByElement(E element) {
        if (root == null) {//树为空，返回null
            return null;
        }

        Node pointer = root;
        do {
            if (element.compareTo(pointer.element) > 0) {//大于，指针指向右孩子
                pointer = pointer.right;
            } else {
                if (element.compareTo(pointer.element) < 0) {//小于，指针指向左孩子
                    pointer = pointer.left;
                } else {//等于，返回当前的节点
                    return pointer;
                }
            }
        } while (pointer != null);
        return null;
    }

    //指针指向后继节点，并用后继节点的element顶替要删除的element,没有后继节点就返回null
    public Node changePointer(Node pointer) {
        //指针备份方便替换时找到引用
        Node pointer_old = pointer;
        //寻找后继节点
        pointer = pointer.right;
        while (pointer.left != null) {
            pointer = pointer.left;
        }
        pointer_old.element = pointer.element;
        return pointer;
    }

    //删除叶子节点，红色的就直接删，黑色的分情况处理
    public void removeLeafNode(Node pointer) {
        Node parent = pointer.parent;
        Node pointer_old = pointer;
        //case:2.1叶子节点是根节点
        if (parent == null) {
            root = null;
            return;
        }
        //case:2.2叶子节点是红色的的话直接删除，黑色的要分类处理
        if (pointer.color == red) {
            if (pointer.parent.left == pointer) {
                pointer.parent.left = null;
            } else {
                pointer.parent.right = null;
            }
        } else {
            //case2.3:叶子节点是黑色的,视兄弟点分类处理
            while (pointer.parent != null && pointer.color == black) {
                parent = pointer.parent;//在case2.3.2.2下循环，要更新parent
                Node brother;
                if (pointer.parent.left == pointer) {//左叶子节点处理方式
                    brother = pointer.parent.right;
                    //case2.3.1:兄弟节点为红色。那么将其转换为黑色
                    if (brother.color == red) {
                        brother.color = black;
                        parent.color = red;
                        leftRotate(parent);
                        brother = parent.right;
                    }
                    //case2.3.2:兄弟节点为黑色，侄子节点都是黑色（NIL）
                    if ((brother.left == null) && (brother.right == null)) {
                        //case2.3.2.1:父节点为红色
                        if (parent.color == red) {
                            parent.color = black;
                            brother.color = red;
                            break;
                        } else {//case2.3.2.2:父节点为黑色
                            brother.color = red;
                            pointer = parent;
                            //继续循环
                        }
                    } else {
                        //case2.3.3:兄弟节点为黑色,左侄子为红色
                        if ((brother.color == black) && brother.left != null && brother.left.color == red) {
                            brother.left.color = parent.color;
                            parent.color = black;
                            rightRotate(brother);
                            leftRotate(parent);
                            //case2.3.4:兄弟节点为黑色,右侄子为红色
                        } else if ((brother.color == black) && brother.right != null && brother.right.color == red) {
                            brother.color = parent.color;
                            parent.color = black;
                            brother.right.color = black;
                            leftRotate(parent);
                        }
                        break;
                    }
                } else {//右叶子节点处理方式
                    brother = pointer.parent.left;
                    //case2.3.1:兄弟节点为红色。那么将其转换为黑色
                    if (brother.color == red) {
                        brother.color = black;
                        parent.color = red;
                        rightRotate(parent);
                        brother = parent.left;
                    }
                    //case2.3.2:兄弟节点为黑色，侄子节点都是黑色（NIL）
                    if ((brother.left == null) && (brother.right == null)) {
                        //case2.3.2.1:父节点为红色
                        if (parent.color == red) {
                            parent.color = black;
                            brother.color = red;
                            break;
                        } else {//case2.3.2.2:父节点为黑色
                            brother.color = red;
                            pointer = parent;
                            //继续循环
                        }

                    } else {
                        //case2.3.3:兄弟节点为黑色,右侄子为红色
                        if ((brother.color == black) && brother.right != null && brother.right.color == red) {
                            brother.right.color = parent.color;
                            parent.color = black;
                            leftRotate(brother);
                            rightRotate(parent);
                            //case2.3.4:兄弟节点为黑色,左侄子为红色
                        } else if ((brother.color == black) && brother.left != null && brother.left.color == red) {
                            brother.color = parent.color;
                            parent.color = black;
                            brother.left.color = black;
                            rightRotate(parent);
                        }
                        break;
                    }
                }
            }
            //最后别忘了删掉这个节点
            if (pointer_old.parent.left == pointer_old) {
                pointer_old.parent.left = null;
            } else if ((pointer_old.parent.right == pointer_old)) {
                pointer_old.parent.right = null;
            }
            pointer_old.parent = null;

        }
    }

    //删除单分支节点（此时删除节点必为红色，子树仅为一个叶子节点）。子树（就是一个叶子节点）顶上来涂黑即可。
    public void removeOneBranchNode(Node pointer) {
        Node child = pointer.left != null ? pointer.left : pointer.right;
        if (pointer.parent.left == pointer) {
            pointer.parent.left = child;
        } else {
            pointer.parent.right = child;
        }
        child.parent = pointer.parent;
        child.color = black;
    }


}

class Element implements Comparable<Element>, PrintToDOS {

    public Element(int value) {
        this.value = value;
    }

    public int value;

    @Override
    public void println() {
        System.out.println(value);
    }

    @Override
    public void print() {
        System.out.print(value);
    }

    //this大于element返回1，小于返回-1，相等返回0
    @Override
    public int compareTo(Element element) {
        if (this.value > element.value) {
            return 1;
        } else {
            if (this.value < element.value) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}

interface PrintToDOS {
    public void print();  //打印值但不换行

    public void println();//打印值后换行
}