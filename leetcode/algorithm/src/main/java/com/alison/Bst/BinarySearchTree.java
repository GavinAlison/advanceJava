package com.alison.Bst;

/**
 * @Author alison
 * @Date 2019/8/18  10:56
 * @Version 1.0
 * @Description
 */
public class BinarySearchTree {

    private BinarySearchTreeNode root;

    public BinarySearchTree() {
        root = null;
    }

    // recursion find element by data
    BinarySearchTreeNode find(BinarySearchTreeNode root, int data) {
        if (root == null) {
            return null;
        }
        if (data > root.getData()) {
            return find(root.getRight(), data);
        } else if (data < root.getData()) {
            return find(root.getLeft(), data);
        } else {
            return root;
        }
    }

    // find by while
    BinarySearchTreeNode findNoRecursive(BinarySearchTreeNode root, int data) {
        while (root != null) {
            if (data < root.getData()) {
                root = root.getLeft();
            } else if (data > root.getData()) {
                root = root.getRight();
            } else {
                return root;
            }
        }
        return null;
    }

    BinarySearchTreeNode findMin(BinarySearchTreeNode root) {
        if (root == null) {
            return null;
        } else if (root.getLeft() == null) {
            return root;
        } else {
            return findMin(root.getLeft());
        }
    }

    BinarySearchTreeNode findMinNoRecursive(BinarySearchTreeNode root) {
        if (root == null) {
            return null;
        }
        while (root.getLeft() != null) {
            root = root.getLeft();
        }
        return root;
    }

    BinarySearchTreeNode findMax(BinarySearchTreeNode root) {
        if (root == null) {
            return null;
        }
        if (root.getRight() == null) {
            return root;
        } else {
            return findMax(root.getRight());
        }
    }

    public BinarySearchTreeNode findMaxNoRecursive(BinarySearchTreeNode root) {
        if (root == null) {
            return null;
        }
        while (root.getRight() != null) {
            root = root.getRight();
        }
        return root;
    }


    public BinarySearchTreeNode insert(BinarySearchTreeNode root, int data) {
        // find index
        if (root == null) {
            return new BinarySearchTreeNode(data);
        }
        if (data < root.getData()) {
            root.setLeft(insert(root.getLeft(), data));// 递归插入左子树
        } else if (data > root.getData()) {
            root.setRight(insert(root.getRight(), data)); // 递归插入右子树
        }
        return root;
    }

    public BinarySearchTreeNode delete(BinarySearchTreeNode root, int data) {
        BinarySearchTreeNode tmp;
        if (root == null) {
            System.out.println("没找到要删除的元素");
        } else if (data < root.getData()) {
//            左子树递归删除
            root.setLeft(delete(root.getLeft(), data));
        } else if (data > root.getData()) {
            root.setRight(delete(root.getRight(), data));//右子树递归删除
        } else {
            //找到要删除的结点
            if (root.getLeft() != null && root.getRight() != null) { //被删除结点有左右两个子结点
                tmp = findMin(root.getRight());//在右子树中找最小的元素填充删除结点
                root.setData(tmp.getData());
                root.setRight(delete(root.getRight(), root.getData()));//在删除结点的右子树中删除最小元素
            } else {   //被删除结点有一个或无子结点
                tmp = root;
                if (root.getLeft() == null)  //有右孩子或无子结点
                    root = root.getRight();
                else if (root.getRight() == null) //有左孩子或无子结点
                    root = root.getLeft();
                tmp = null;
            }
        }
        return root;
    }

    //中序遍历：递归方法  (中序遍历二叉搜索树时，将得到一个有序表)
    public void inOrderRecursive(BinarySearchTreeNode root) {
        if (root == null)
            return;
        inOrderRecursive(root.getLeft());
        System.out.print(root.getData() + " ");
        inOrderRecursive(root.getRight());
    }


    // 前序遍历
    public void preOrderRecursive(BinarySearchTreeNode root) {
        if (root == null) {
            return;
        }
        System.out.print(root.getData() + " ");
        preOrderRecursive(root.getLeft());
        preOrderRecursive(root.getRight());
    }

    // 后序遍历
    public void postOrderRecursive(BinarySearchTreeNode root) {
        if (root == null) {
            return;
        }
        postOrderRecursive(root.getLeft());
        postOrderRecursive(root.getRight());
        System.out.print(root.getData() + " ");
    }

    public void callFindMin() {
        System.out.println("最小元素为：" + findMin(root));
    }

    public void callFindMax() {
        System.out.println("最大元素为：" + findMax(root));
    }

    public void callInsert(int data) {
        root = insert(root, data);
    }

    public void callDelete(int data) {
        root = delete(root, data);
    }

    public void callInOrder() {
        inOrderRecursive(root);
    }

    public void callInOrderHead() {
        preOrderRecursive(root);
    }

    public void callInOrderTail() {
        postOrderRecursive(root);
    }


    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();
        /* 创建如下的二叉搜索树
              5
           /     \
          3       7
         /  \    /  \
        2   4   6   8 */
        tree.callInsert(5);
        tree.callInsert(3);
        tree.callInsert(2);
        tree.callInsert(4);
        tree.callInsert(7);
        tree.callInsert(6);
        tree.callInsert(8);
        System.out.println("中序遍历为：");
        tree.callInOrder();//2 3 4 5 6 7 8
        tree.callFindMin();//最小元素为：2
        tree.callFindMax();//最大元素为：8
        System.out.println("\nD删除结点数据 2");
        tree.callDelete(2);
        System.out.println("删除删除结点数据2后的中序遍历为：");
        tree.callInOrder();//3 4 5 6 7 8
    }
}
