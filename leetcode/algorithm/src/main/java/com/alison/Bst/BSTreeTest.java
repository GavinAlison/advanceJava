package com.alison.Bst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author alison
 * @Date 2019/8/18  15:48
 * @Version 1.0
 * @Description
 */
public class BSTreeTest {
    private static final int[] arr = {1, 5, 4, 3, 2, 6};
    /* 创建如下的二叉搜索树
             1
               \
                5
              /   \
             4     6
           /
          3
         /
        2
             */
    private static final int[] arr1 = {5, 3, 2, 4, 7, 6, 8};
 /* 创建如下的二叉搜索树
              5
           /     \
          3       7
         /  \    /  \
        2   4   6   8 */

    public static void main(String[] args) {
        int i, ilen;
        BSTree<Integer> tree = new BSTree<Integer>();
        System.out.print("== 依次添加: ");
        ilen = arr1.length;
        for (i = 0; i < ilen; i++) {
            System.out.print(arr1[i] + " ");
            tree.insert(arr1[i]);
        }

        System.out.print("\n== 前序遍历: ");
        tree.preOrder();

        System.out.print("\n== 中序遍历: ");
        tree.inOrder();

        System.out.print("\n== 后序遍历: ");
        tree.postOrder();
        System.out.println();

        System.out.println("== 3的successor: " + tree.successor(tree.search(3)).key);
        System.out.println("== 3的predecessor: " + tree.predecessor(tree.search(3)).key);
        System.out.println("== 最小值: " + tree.minimum());
        System.out.println("== 最大值: " + tree.maximum());
        System.out.println("== 树的详细信息: ");
        tree.print();

        System.out.print("\n== 删除根节点: " + arr[3]);
        tree.remove(arr[3]);

        System.out.print("\n== 中序遍历: ");
        tree.inOrder();
        System.out.println();
        // 销毁二叉树
        tree.clear();
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    // 错误， input: [10,5,15,null,null,6,20]
    public boolean isValidBST2(TreeNode root) {
        if (root == null)
            return true;
        boolean flag = true;
        if (root.right != null) {
            flag = root.right.val > root.val && isValidBST(root.right);
        } else if (root.left != null) {
            flag = root.left.val < root.val && isValidBST(root.left);
        }
        return flag;
    }

    public boolean isValidBST(TreeNode root) {
        return helper(root, null, null);
    }

    public boolean helper(TreeNode node, Integer lower, Integer upper) {
        if (node == null) return true;
        int val = node.val;
        if (lower != null && val <= lower) return false;
        if (upper != null && val >= upper) return false;

        if (!helper(node.right, val, upper)) return false;
        if (!helper(node.left, lower, val)) return false;
        return true;
    }

    public TreeNode stringToTreeNode(String input) {
        input = input.trim();
        input = input.substring(1, input.length() - 1);
        if (input.length() == 0) {
            return null;
        }

        String[] parts = input.split(",");
        String item = parts[0];
        TreeNode root = new TreeNode(Integer.parseInt(item));
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.add(root);

        int index = 1;
        while (!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.remove();

            if (index == parts.length) {
                break;
            }

            item = parts[index++];
            item = item.trim();
            if (!item.equals("null")) {
                int leftNumber = Integer.parseInt(item);
                node.left = new TreeNode(leftNumber);
                nodeQueue.add(node.left);
            }

            if (index == parts.length) {
                break;
            }

            item = parts[index++];
            item = item.trim();
            if (!item.equals("null")) {
                int rightNumber = Integer.parseInt(item);
                node.right = new TreeNode(rightNumber);
                nodeQueue.add(node.right);
            }
        }
        return root;
    }

    public static String booleanToString(boolean input) {
        return input ? "True" : "False";
    }

    public void testValid(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = in.readLine()) != null) {
            TreeNode root = stringToTreeNode(line);
            boolean ret = isValidBST(root);
            String out = booleanToString(ret);
            System.out.print(out);
        }
    }
}
