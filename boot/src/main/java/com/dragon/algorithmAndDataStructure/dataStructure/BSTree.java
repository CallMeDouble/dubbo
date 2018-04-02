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

    /**
     * 插入
     * @param key
     */
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

    /**
     * 前驱结点
     * @    return
     */
    public BSTNode<T> predeccessor(BSTNode<T> bstNode){
        if(bstNode.getLeft()!=null){
            return getMax(bstNode);
        }
        BSTNode<T> parent = bstNode.parent;
        while((parent != null) && (bstNode == parent.getLeft())){
            bstNode = parent;
            parent = parent.getParent();
        }
        return parent;
    }

    /**
     * 后继结点
     * @param bstNode
     * @return
     */
    public BSTNode<T> successor(BSTNode<T> bstNode){
        if(bstNode.getRight()!=null){
            return getMin(bstNode);
        }

        BSTNode<T> parent = bstNode.getParent();
        while((parent!=null) && (bstNode == parent.getRight())){
            bstNode = parent;
            parent = parent.getParent();
        }
        return parent;
    }

    /**
     * 删除
     * @param bst
     * @param z
     * @return
     */
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
            //获取后继结点
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
            bst.root = x;
        else if (y == y.parent.left)
            y.parent.left = x;
        else
            y.parent.right = x;

        //针对情况三的删除转移、做值替换
        if (y != z)
            z.key = y.key;

        return y;
    }


    /**
     * 功能简述>
     * 上面的函数难以理解
     * 针对晦涩难懂的变量重新命名
     *
     * @author: xuke
     *
     * @param tree 二叉树
     * @param wantDelete 删除的结点
     * 情况一 该节点是叶节点（没有子节点）
     * 情况二 该节点有一个子节点（或左或右）
     * 情况三 该节点有两个子节点
     * @return
     * @see [类、类#方法、类#成员]
     * @date 2017-9-12
     */
    private BSTNode<T> removeEasyUnderstand(BSTree<T> tree, BSTNode<T> wantDelete)
    {
        //被删节点的全量子树：把树理解成双向链表来处理
        //为什么这么理解：主要原因是下面的删除动作比较隐晦
        BSTNode<T> realDeleteChildren = null;

        //真正被删除的节点
        BSTNode<T> realDelete = null;

        //待删除节点不具有双子树：包含情况一二
        //① wantDelete.left = wantDelete.right = null
        //② wantDelete.left = null wantDelete.right != null
        //③ wantDelete.left ！= null wantDelete.right = null
        //此种情况真正删除的节点realDelete就是wantDelete
        //realDelete可能仅具有左子树、也可能仅具有右子树、也可能是个独节点
        if ((wantDelete.left == null) || (wantDelete.right == null))
            realDelete = wantDelete;
        else
            //删除节点wantDelete具有双子树，在这种情况下隐晦的删除动作是删除转移,转移为删除realDelete
            //寻找realDelete则为寻找后继节点。因为后继节点是大于当前节点的最小值
            //而且当前节点的右子树不为空，所以最终获取的realDelete肯定没有左子树了！也即realDelete.left = null
            //但是有没有右子树不可确定
            //① realDelete.left = realDelete.right = null
            //② realDelete.left = null realDelete.right != null
            realDelete = successor(wantDelete);

        //说明是wantDelete本身就是仅有左子树的单节点情况
        //属于情况二：当wantDelete的左子树不为空的时候，右子树绝对为空，所以此时realDeleteChildren代表全量孩子realDeleteChildren = realDelete.left;
        //引出全量孩子的目的是便于利用双向链表的思想删除节点realDelete：realDeleteChildren.parent = realDelete.parent;把.parent看成.pre
        if (realDelete.left != null)
            //待删节点的全量子树，此种情况不可能有右子树。也就是仅为左分支
            realDeleteChildren = realDelete.left;
            //情况一三，当realDelete的左子树为空了那么此时realDeleteChildren代表全量孩子只能等于realDeleteChildren = realDelete.right;尽管realDelete.right也可能为空
            //但是我们不在乎、管他空不空、我们只是为了利用指针做隐晦删除
        else
            realDeleteChildren = realDelete.right;

        //情况二三 ：做隐晦删除
        if (realDeleteChildren != null)
            //删除realDelete：通过指针越过自己达到删除的目的、并且保证realDelete的子树接上realDelete的父亲、保证树的完整：双线链表思想
            //但是此时只保证了从下往上指，还缺少从上往下指、才能双向绑定：把树理解成双向链表来处理，此处不需要考虑左右分支
            //因为realDeleteChildren就代表了全量的左右子分支合体
            realDeleteChildren.parent = realDelete.parent;
        //此时会不会有疑问？当realDeleteChildren == null怎么删呢？这种情况不需要删呀都为空了删个毛

        //此处为什么要讨论realDelete.parent呢？
        //为了完善互指，即将要做从上往下指
        if (realDelete.parent == null)
            //情况一二中的特殊情况，情况三不会出现这种情况。
            tree.root = realDeleteChildren;
            //保证树的完整性：此时由上往下指,需要区分左右子树。不可抽象成简单的双向链表、此时是三向链表
        else if (realDelete == realDelete.parent.left)
            realDelete.parent.left = realDeleteChildren;
        else
            realDelete.parent.right = realDeleteChildren;

        //情况三特殊，因为情况三的删除是删除转移！所以删除个替身，那么留下的node要使用替身的身份证key
        if (realDelete != wantDelete)
            wantDelete.key = realDelete.key;

        //也就是说删除动作所返回的node所包含的key和原打算删除节点包含的key不一样
        //但是删除完成之后、要删的node值已不存在就是通过wantDelete.key = realDelete.key;实现的
        return realDelete;
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
