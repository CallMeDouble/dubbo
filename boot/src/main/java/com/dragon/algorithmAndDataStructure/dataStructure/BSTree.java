package com.dragon.algorithmAndDataStructure.dataStructure;

/**
 * Created by zhushuanglong on 2018/3/26.
 */
public class BSTree<T extends Comparable<T>> {
    private BSTNode<T> root;
    public class BSTNode<T extends Comparable<T>>{
        private T key;
        BSTNode<T> parent;
        BSTNode<T> left;
        BSTNode<T> right;

        public BSTNode(T key, BSTNode<T> parent, BSTNode<T> left, BSTNode<T> right) {
            this.key = key;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        public T getKey() {
            return key;
        }

        public void setKey(T key) {
            this.key = key;
        }

        public BSTNode<T> getParent() {
            return parent;
        }

        public void setParent(BSTNode<T> parent) {
            this.parent = parent;
        }

        public BSTNode<T> getLeft() {
            return left;
        }

        public void setLeft(BSTNode<T> left) {
            this.left = left;
        }

        public BSTNode<T> getRight() {
            return right;
        }

        public void setRight(BSTNode<T> right) {
            this.right = right;
        }
    }



    public BSTNode<T> search(T key){
        return search(getRoot(), key);
    }

    private BSTNode<T> search(BSTNode bstNode, T key){
        if(bstNode.getKey().equals(key)){
            System.out.println("bstNode:" + bstNode.getKey());
            return bstNode;
        }
        if(bstNode.getKey().compareTo(key)<0){
            System.out.println("bstNode.getRight():" + bstNode.getRight().getKey());
            return search(bstNode.getRight(), key);
        }else{
            System.out.println("bstNode.getLeft():" + bstNode.getLeft().getKey());
            return search(bstNode.getLeft(), key);
        }
    }

    public BSTNode<T> getMax(){
        return getMax(getRoot());
    }

    private BSTNode<T> getMax(BSTNode bstNode){
        while(bstNode.getRight() != null){
            bstNode = getMax(bstNode.getRight());
        }
        return bstNode;
    }

    public BSTNode<T> getMin(){
        return getMin(getRoot());
    }

    private BSTNode<T> getMin(BSTNode bstNode){
        while(bstNode.getLeft() != null){
            bstNode = getMin(bstNode.getLeft());
        }
        return bstNode;
    }


    public void insertNode(T key){
        BSTNode<T> bstNode = new BSTNode<>(key, null, null, null);
        if(getRoot() == null){
            setRoot(bstNode);
            return;
        }

        BSTNode parent = getRoot();
        BSTNode currentNode = null;
        while(parent != null){
            currentNode = parent;
            if(parent.getKey().compareTo(key) > 0){
                parent = parent.getLeft();
            }else{
                parent = parent.getRight();
            }
        }

        bstNode.setParent(currentNode);
        if(currentNode.getKey().compareTo(key) > 0){
            currentNode.setLeft(bstNode);
        }else{
            currentNode.setRight(bstNode);
        }
    }


    private BSTNode<T> remove(BSTree<T> bst, BSTNode<T> z)
    {
        //真正删除节点的子树：左右子树合体的抽象
        BSTNode<T> x = null;

        //真正删除的节点
        BSTNode<T> y = null;

        //获取真正删除节点
        if ((z.left == null) || (z.right == null)) {
            y = z;
        }else {
            y = successor(z);
        }
        //获取真正删除节点的子树：左右子树合体的抽象
        if (y.left != null) {
            x = y.left;
        }else {
            x = y.right;
        }
        //删除 真正删除节点
        if (x != null)
            x.parent = y.parent;

        //删除之后把子树折断了，准备焊接
        if (y.parent == null)
            bst.mRoot = x;
        else if (y == y.parent.left)
            y.parent.left = x;
        else
            y.parent.right = x;

        //针对情况三的删除转移、做值替换
        if (y != z)
            z.key = y.key;

        return y;
    }





    //    2.1 前序遍历
    //    若二叉树非空，则执行以下操作：
    //    (01) 访问根结点；
    //    (02) 先序遍历左子树；
    //    (03) 先序遍历右子树。
    public void preOrder(){
        preOrder(getRoot());
    }

    private void preOrder(BSTree.BSTNode bstNode){
        if(bstNode != null){
            System.out.println(bstNode.getKey()+"、");
            preOrder(bstNode.getLeft());
            preOrder(bstNode.getRight());
        }
    }

    //    2.2 中序遍历
    //    若二叉树非空，则执行以下操作：
    //    (01) 中序遍历左子树；
    //    (02) 访问根结点；
    //    (03) 中序遍历右子树。
    public void inOrder(){
        inOrder(getRoot());
    }

    private void inOrder(BSTree.BSTNode bstNode){
        if(bstNode != null){
            inOrder(bstNode.left);
            System.out.println(bstNode.getKey()+"、");
            inOrder(bstNode.right);
        }
    }

    //    2.3 后序遍历
    //    若二叉树非空，则执行以下操作：
    //    (01) 后序遍历左子树；
    //    (02) 后序遍历右子树；
    //    (03) 访问根结点。
    public void postOrder(){
        inOrder(getRoot());
    }

    private void postOrder(BSTree.BSTNode bstNode){
        if(bstNode != null){
            inOrder(bstNode.left);
            inOrder(bstNode.right);
            System.out.println(bstNode.getKey()+"、");
        }
    }











    public BSTNode<T> getRoot() {
        return root;
    }

    public void setRoot(BSTNode<T> root) {
        this.root = root;
    }
}
