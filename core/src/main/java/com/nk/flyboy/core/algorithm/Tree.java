package com.nk.flyboy.core.algorithm;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created on 2017/11/7.
 */
public class Tree {

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    /*
                1
               / \
              2   3
             / \   \
            4  5   6
    */
    public static void main(String[] args) {
        TreeNode r1 = new TreeNode(1);
        TreeNode r2 = new TreeNode(2);
        TreeNode r3 = new TreeNode(3);
        TreeNode r4 = new TreeNode(4);
        TreeNode r5 = new TreeNode(5);
        TreeNode r6 = new TreeNode(6);

        r1.left = r2;
        r1.right = r3;
        r2.left = r4;
        r2.right = r5;
        r3.right = r6;

        System.out.println(getNodeNumRec(r1));

        System.out.println(getNodeNum(r1));

        System.out.println(getDepthRec(r1));

        System.out.println(getDepth(r1));

        levelTraversal(r1);
    }

    public static int getNodeNumRec(TreeNode root){

        if(root==null){
            return 0;
        }

        int count=0;

        count+=getNodeNumRec(root.left);
        count+=getNodeNumRec(root.right);
        count++;

        return count;
    }

    public static int getNodeNum(TreeNode root){

        if(root==null){
            return 0;
        }

        int count=0;

        Queue<TreeNode> queue=new LinkedList<TreeNode>();
        queue.offer(root);

        while (!queue.isEmpty()){
            TreeNode node=queue.poll();
            if(node.left!=null){
                count++;
                queue.offer(node.left);
            }
            if(node.right!=null){
                count++;
                queue.offer(node.right);
            }
        }

        return count+1;
    }

    public static int getDepthRec(TreeNode root){

        if(root==null){
            return 0;
        }

        int left=getDepthRec(root.left);
        int right=getDepthRec(root.right);

        return Math.max(left,right)+1;
    }

    public static int getDepth(TreeNode root){

        if(root==null){
            return 0;
        }
        int depth=0;
        int currentLevelNode=1;
        int nextLevelNode=0;

        LinkedList<TreeNode> list=new LinkedList<>();
        list.add(root);

        while (!list.isEmpty()){
            TreeNode treeNode=list.poll();
            currentLevelNode--;
            if(treeNode.left!=null){
                list.offer(treeNode.left);
                nextLevelNode++;
            }
            if(treeNode.right!=null){
                list.offer(treeNode.right);
                nextLevelNode++;
            }

            if(currentLevelNode==0){
                currentLevelNode=nextLevelNode;
                depth++;
                nextLevelNode=0;
            }

        }

        return depth;
    }

    public static void levelTraversal(TreeNode root){
        if(root==null){
            return;
        }

        LinkedList<TreeNode> list=new LinkedList<>();
        list.add(root);

        while (!list.isEmpty()){
            TreeNode node=list.poll();
            System.out.print(node.val +" ");
            if(node.left!=null){
                list.offer(node.left);
            }
            if(node.right!=null){
                list.offer(node.right);
            }
        }

    }

    public static int getNodeNumKthLevelRec(TreeNode root,int k){
        if(k<1||root==null) return 0;

        if(k==1){
            return 1;
        }

        int leftNum=getNodeNumKthLevelRec(root.left,k-1);
        int rightNum=getNodeNumKthLevelRec(root.right,k-1);

        return leftNum+rightNum;
    }


}
