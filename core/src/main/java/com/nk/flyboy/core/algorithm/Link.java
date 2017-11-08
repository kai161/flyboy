package com.nk.flyboy.core.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/11/7.
 */
public class Link {

    private static class Node {
        int val;
        Node next;

        public Node(int val) {
            this.val = val;
        }
    }

    public static void printList(Node head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;

        printList(n1);
        System.out.println(getListLength(n1));

//        printList(reverseListNode(n1));

        //printList(reverseListNodeRec(n1));

        //reversePrintListStack(n1);
        reversePrintListStackRec(n1);

    }

    //获取链表长度
    public static int getListLength(Node node){
        int i=0;
        while (node!=null){
            node=node.next;
            i++;
        }

        return i;
    }

    //将单链表反转: reverseList（遍历），reverseListRec（递归）
    public static Node reverseListNode(Node node){

        Node reNode=null;
        Node cur=node;
        while (cur!=null){
            Node preNode=cur;
            cur=cur.next;
            preNode.next=reNode;
            reNode=preNode;
        }

        return reNode;
    }

    public static Node reverseListNodeRec(Node node){

        if(node==null||node.next==null){
            return node;
        }

        Node node1=reverseListNodeRec(node.next);
        node.next.next=node;
        node.next=null;

        return node1;
    }

    public static void reversePrintListStack(Node node){
        List<Node> list=new ArrayList<>();
        while (node!=null){
            list.add(node);
            node=node.next;
        }
        int len=list.size()-1;
        for (int i=len;i>=0;i--){
            System.out.print(list.get(i).val+" ");
        }
    }

    public static void reversePrintListStackRec(Node node){
       if(node.next==null){
           System.out.print(node.val+" ");
           return;
       }
        reversePrintListStackRec(node.next);
        System.out.print(node.val+" ");
    }

    public static Node mergeSortedList(Node node1,Node node2){
        if(node1==null){
            return node2;
        }
        if(node2==null){
            return node1;
        }
        Node mergeNode=null;

        if(node1.val<node2.val){
            mergeNode=node1;
            node1=node1.next;
            mergeNode.next=null;
        }else {
            mergeNode=node2;
            node2=node2.next;
            mergeNode.next=null;
        }

        while (node1!=null||node2!=null){
            if(node1.val<node2.val){
                mergeNode.next=node1;
                node1=node1.next;
                mergeNode=mergeNode.next;
                mergeNode.next=null;
            }else {
                mergeNode.next=node2;
                node2=node2.next;
                mergeNode=mergeNode.next;
                mergeNode.next=null;
            }
        }

        if(node1==null){
            mergeNode.next=node2;
        }
        if(node2==null){
            mergeNode.next=node1;
        }

        return mergeNode;

    }

    public static Node mergeSortedListRec(Node node1,Node node2){
        if(node1==null){
            return node2;
        }
        if(node2==null){
            return node1;
        }

        Node mergeNode=null;
        if(node1.val<node2.val){
            mergeNode=node1;
            mergeNode.next=mergeSortedListRec(node1.next,node2);
        }else {
            mergeNode=node2;
            mergeNode.next=mergeSortedListRec(node1,node2.next);
        }


        return mergeNode;
    }
}
