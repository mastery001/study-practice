package tree.search;

import tree.TreeNode;

public class AVLBinaryTree<T extends Comparable<T>> extends BinarySearchTree<T>{

	
	
	public AVLBinaryTree() {
		super();
	}

	public AVLBinaryTree(T[] initArray) {
		super(initArray);
	}

	private static final int LH = 1;    //左高  
    private static final int EH = 0;    //等高  
    private static final int RH = -1;   //右高  
	
	static class AVLNode<T> extends SearchNode<T> {

		int balance = EH;
		
		public AVLNode(T data) {
			super(data);
		}
		
	}
	

    /** 
     * @param p 最小旋转子树的根节点 
     * 向左旋转之后p移到p的左子树处，p的右子树B变为此最小子树根节点， 
     * B的左子树变为p的右子树 
     * 比如：     	A(-2)                     B(1) 
     *             \        左旋转       		  /   \ 
     *             B(0)     ---->       A(0)   \        
     *             /   \                   \    \ 
     *           BL(0)  BR(0)              BL(0) BR(0)  
     *  旋转之后树的深度之差不超过1 
     */  
    private void rotateLeft(AVLNode<T>  p) {  
        if(p!=null){  
            AVLNode<T>  r = (AVLNode<T>) p.getRight();  
            p.setRight(r.getLeft());  //把p右子树的左节点嫁接到p的右节点，如上图，把BL作为A的右子节点  
            r.setParent(p.parent());   //A的父节点设为B的父节点  
            if (p.parent() == null)         //如果p是根节点  
                root = r;                 //r变为父节点，即B为父节点  
            else if (p.parent().getLeft() == p)  //如果p是左子节点  
                p.parent().setLeft(r);       //p的父节点的左子树为r  
            else                          //如果p是右子节点  
            	p.parent().setRight(r);       //p的父节点的右子树为r  
            r.setLeft(p);                   //p变为r的左子树，即A为B的左子树  
        }  
    }  
      
    /** 
     * @param p 最小旋转子树的根节点 
     * 向右旋转之后，p移到p的右子节点处，p的左子树B变为最小旋转子树的根节点 
     * B的右子节点变为p的左节点、 
     * 例如:       A(2)                     B(-1) 
     *            /         右旋转          			/    \ 
     *          B(0)       ------>         /     A(0) 
     *         /   \                      /      / 
     *       BL(0) BR(0)                BL(0)  BR(0)  
     */  
    private void rotateRight(AVLNode<T>  p){  
        if(p!=null){  
            AVLNode<T>  l = (AVLNode<T>) p.getLeft();    
            p.setLeft(l.getRight());   //把B的右节点BR作为A的左节点  
            l.setParent(p.parent());  //A的父节点赋给B的父节点  
            if (p.parent() == null)   //如果p是根节点  
                root = l;          //B为根节点  
            else if (p.parent().getRight() == p) //如果A是其父节点的左子节点  
                p.parent().setRight(l);    //B为A的父节点的左子树  
            else                        //如果A是其父节点的右子节点  
            	 p.parent().setLeft(l);       //B为A的父节点的右子树  
            l.setRight(p);     //A为B的右子树  
        }  
    }

	@Override
	protected AVLNode<T> createNode(T value) {
		return new AVLNode<T>(value);
	}

	@Override
	protected boolean insert(TreeNode<T> curr, TreeNode<T> insertNode) {
		boolean flag =  super.insert(curr, insertNode);
		AVLNode<T> parent = (AVLNode<T>) insertNode.parent();
		int cmp = 0;
		//自下向上回溯，查找最近不平衡节点  
        while(parent!=null){  
			cmp = insertNode.getValue().compareTo(parent.getValue());
            if(cmp < 0){    //插入节点在parent的左子树中  
                parent.balance++;  
            }else{           //插入节点在parent的右子树中  
                parent.balance--;  
            }  
            if(parent.balance == 0){    //此节点的balance为0，不再向上调整BF值，且不需要旋转  
                break;  
            }  
            if(Math.abs(parent.balance) == 2){  //找到最小不平衡子树根节点  
                fixAfterInsertion(parent);  
                break;                  //不用继续向上回溯  
            }  
            parent = (AVLNode<T>) parent.parent();  
        }  
		return flag;
	}

	private void fixAfterInsertion(AVLNode<T> p) {
		if (p.balance == 2) {
			leftBalance(p);
		}
		if (p.balance == -2) {
			rightBalance(p);
		}
	}
	
    
    /** 
     * 做左平衡处理 
     * 平衡因子的调整如图： 
     *         t                       rd 
     *       /   \                   /    \ 
     *      l    tr   左旋后右旋   		 l       t 
     *    /   \       ------->    /  \    /  \ 
     *  ll    rd                ll   rdl rdr  tr 
     *       /   \ 
     *     rdl  rdr 
     *      
     *   情况2(rd的BF为0) 
     *  
     *    
     *         t                       rd 
     *       /   \                   /    \ 
     *      l    tr   左旋后右旋    		l       t 
     *    /   \       ------->    /  \       \ 
     *  ll    rd                ll   rdl     tr 
     *       /    
     *     rdl   
     *      
     *   情况1(rd的BF为1) 
     *   
     *    
     *         t                       rd 
     *       /   \                   /    \ 
     *      l    tr   左旋后右旋    		l       t 
     *    /   \       ------->    /       /  \ 
     *  ll    rd                ll       rdr  tr 
     *           \ 
     *          rdr 
     *      
     *   情况3(rd的BF为-1) 
     *  
     *    
     *         t                         l 
     *       /       右旋处理          /    \ 
     *      l        ------>          ll     t 
     *    /   \                             / 
     *   ll   rd                           rd 
     *   情况4(L等高) 
     */  
    private boolean leftBalance(AVLNode<T> t){  
        boolean heightLower = true;  
        AVLNode<T> l = (AVLNode<T>) t.getLeft();  
        switch (l.balance) {  
        case LH:            //左高，右旋调整,旋转后树的高度减小  
            t.balance = l.balance = EH;  
            rotateRight(t);  
            break;   
        case RH:            //右高，分情况调整                                            
            AVLNode<T> rd = (AVLNode<T>) l.getRight();  
            switch (rd.balance) {   //调整各个节点的BF  
            case LH:    //情况1  
                t.balance = RH;  
                l.balance = EH;  
                break;  
            case EH:    //情况2  
                t.balance = l.balance = EH;  
                break;  
            case RH:    //情况3  
                t.balance = EH;  
                l.balance = LH;  
                break;  
            }  
            rd.balance = EH;  
            rotateLeft((AVLNode<T>)t.getLeft());  
            rotateRight(t);  
            break;  
        case EH:      //特殊情况4,这种情况在添加时不可能出现，只在移除时可能出现，旋转之后整体树高不变  
            l.balance = RH;  
            t.balance = LH;  
            rotateRight(t);  
            heightLower = false;  
            break;  
        }  
        return heightLower;  
    }  
      
    /** 
     * 做右平衡处理 
     * 平衡因子的调整如图： 
     *           t                               ld 
     *        /     \                          /     \ 
     *      tl       r       先右旋再左旋     t       r 
     *             /   \     -------->      /   \    /  \ 
     *           ld    rr                 tl   ldl  ldr rr 
     *          /  \ 
     *       ldl  ldr 
     *       情况2(ld的BF为0) 
     *        
     *           t                               ld 
     *        /     \                          /     \ 
     *      tl       r       先右旋再左旋     t       r 
     *             /   \     -------->      /   \       \ 
     *           ld    rr                 tl   ldl      rr 
     *          /   
     *       ldl   
     *       情况1(ld的BF为1) 
     *        
     *           t                               ld 
     *        /     \                          /     \ 
     *      tl       r       先右旋再左旋     t       r 
     *             /   \     -------->      /        /  \ 
     *           ld    rr                 tl        ldr rr 
     *             \ 
     *             ldr 
     *       情况3(ld的BF为-1) 
     *        
     *           t                                  r 
     *             \          左旋                /   \ 
     *              r        ------->           t      rr      
     *            /   \                          \ 
     *           ld   rr                         ld 
     *        情况4(r的BF为0)    
     */  
    private boolean rightBalance(AVLNode<T> t){  
        boolean heightLower = true;  
        AVLNode<T> r = (AVLNode<T>) t.getRight();  
        switch (r.balance) {  
        case LH:            //左高，分情况调整  
            AVLNode<T> ld = (AVLNode<T>) r.getLeft();  
            switch (ld.balance) {   //调整各个节点的BF  
            case LH:    //情况1  
                t.balance = EH;  
                r.balance = RH;  
                break;  
            case EH:    //情况2  
                t.balance = r.balance = EH;  
                break;  
            case RH:    //情况3  
                t.balance = LH;  
                r.balance = EH;  
                break;  
            }  
            ld.balance = EH;  
            rotateRight((AVLNode<T>) t.getRight());  
            rotateLeft(t);  
            break;  
        case RH:            //右高，左旋调整  
            t.balance = r.balance = EH;  
            rotateLeft(t);  
            break;  
        case EH:       //特殊情况4  
            r.balance = LH;  
            t.balance = RH;  
            rotateLeft(t);  
            heightLower = false;  
            break;  
        }  
        return heightLower;  
    }  


	/** 
     * 删除某节点p后的调整方法： 
     * 1.从p开始向上回溯，修改祖先的BF值，这里只要调整从p的父节点到根节点的BF值， 
     * 调整原则为，当p位于某祖先节点(简称A)的左子树中时，A的BF减1，当p位于A的 
     * 右子树中时A的BF加1。当某个祖先节点BF变为1或-1时停止回溯，这里与插入是相反的， 
     * 因为原本这个节点是平衡的，删除它的子树的某个节点并不会改变它的高度 
     *  
     * 2.检查每个节点的BF值，如果为2或-2需要进行旋转调整，调整方法如下文， 
     * 如果调整之后这个最小子树的高度降低了，那么必须继续从这个最小子树的根节点(假设为B)继续 
     * 向上回溯，这里和插入不一样，因为B的父节点的平衡性因为其子树B的高度的改变而发生了改变， 
     * 那么就可能需要调整，所以删除可能进行多次的调整。 
     *  
     */  
    @Override
    protected void fixAfterDeletion(TreeNode<T> p){  
        boolean heightLower = true;     //看最小子树调整后，它的高度是否发生变化，如果减小，继续回溯  
        AVLNode<T> t = (AVLNode<T>) p.parent();  
        int cmp;  
        //自下向上回溯，查找不平衡的节点进行调整  
        while(t!=null && heightLower){  
            cmp = p.getValue().compareTo(t.getValue());  
            /** 
             * 删除的节点是右子树，等于的话，必然是删除的某个节点的左右子树不为空的情况 
             * 例如：     	  10 
             *          /    \ 
             *         5     15 
             *       /   \ 
             *      3    6  
             * 这里删除5，是把6的值赋给5，然后删除6，这里6是p，p的父节点的值也是6。 
             * 而这也是右子树的一种 
             */   
            if(cmp >= 0 ){     
                t.balance ++;  
            }else{  
                t.balance --;  
            }  
            if(Math.abs(t.balance) == 1){   //父节点经过调整平衡因子后，如果为1或-1，说明调整之前是0，停止回溯。  
                break;  
            }  
            AVLNode<T> r = t;  
            //这里的调整跟插入一样  
            if(t.balance == 2){  
                heightLower = leftBalance(r);  
            }else if(t.balance==-2){  
                heightLower = rightBalance(r);  
            }  
            t = (AVLNode<T>) t.parent();  
        }  
    }  
    
    
	
}
