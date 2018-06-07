package com.dragon.algorithmAndDataStructure.dataStructure;

/**
 * Created by zhushuanglong on 2018/3/26.
 */
public class BSTreeTest {
    public static void main(String[] args) {
        BSTree<String> bsTree = new BSTree<String>();
        BSTree<String>.BSTNode<String> root = bsTree.new BSTNode<String>("e", null, null, null);
        bsTree.setRoot(root);

        BSTree<String>.BSTNode<String> c = bsTree.new BSTNode<String>("c", root, null, null);
        BSTree<String>.BSTNode<String> b = bsTree.new BSTNode<String>("b", c, null, null);
        BSTree<String>.BSTNode<String> d = bsTree.new BSTNode<String>("d", c, null, null);
        c.setLeft(b);
        c.setRight(d);

        BSTree<String>.BSTNode<String> f = bsTree.new BSTNode<String>("f", root, null, null);
        root.setLeft(c);
        root.setRight(f);

        //前序遍历
        System.out.println("前序遍历");
        bsTree.preOrder();
        //中序遍历
        System.out.println("中序遍历");
        bsTree.inOrder();

        //后序遍历
        System.out.println("后序遍历");
        bsTree.postOrder();

        System.out.println("查找");
        BSTree<String>.BSTNode<String> cNode = bsTree.search("b");
        System.out.println(cNode.getKey());

        System.out.println("查找最大的");
        System.out.println(bsTree.getMax().getKey());
        System.out.println("查找最小的");
        System.out.println(bsTree.getMin().getKey());



        //构建一个树
        BSTree<String> myTree = new BSTree<>();
        myTree.insertNode("e");
        myTree.insertNode("f");
        myTree.insertNode("c");
        myTree.insertNode("b");
        myTree.insertNode("d");
        myTree.insertNode("a");
        System.out.println("前序遍历构建的树");
        myTree.preOrder();
    }


















}
