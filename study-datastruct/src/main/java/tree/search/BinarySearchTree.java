package tree.search;

import tree.AbstractBinaryTree;
import tree.TreeNode;

public class BinarySearchTree<T extends Comparable<T>> extends AbstractBinaryTree<T> {

	/**
	 * 二叉查找树的结点
	 * @author zouziwen
	 *
	 * @param <T>
	 * 2016年5月27日 下午2:04:47
	 */
	protected static class SearchNode<T> extends TreeNode<T> {

		int freq;	// 频率
		
		public SearchNode(T data) {
			super(data);
			this.freq = 1;
		}
		
	}
	
	public BinarySearchTree() {
	}

	public BinarySearchTree(T[] initArray) {
		if (initArray != null) {
			for (T value : initArray) {
				insert(value);
			}
		}
	}

	@Override
	protected TreeNode<T> createNode(T value) {
		return new SearchNode<T>(value);
	}

	@Override
	protected boolean insert(TreeNode<T> curr, TreeNode<T> insertNode) {
		int cmp = 0;
		TreeNode<T> parent = null;
		do{
			parent = curr;
			cmp = insertNode.getValue().compareTo(curr.getValue());
			if(cmp<0) {
				curr = curr.getLeft();
			}else if(cmp > 0) {
				curr = curr.getRight();
			}else {
				SearchNode<T> s = (SearchNode<T>) curr;
				s.freq ++;
				return true;
			}
		}while(curr != null);
		if(cmp < 0) {
			parent.setLeft(insertNode);
		}else {
			parent.setRight(insertNode);
		}
		return true;
	}

	@SuppressWarnings("unused")
	@Deprecated
	private boolean insert(SearchNode<T> curr, SearchNode<T> insertNode, SearchNode<T> parent, boolean currIsLeft) {
		if (curr == null) {
			curr = insertNode;
			if (currIsLeft) {
				parent.setLeft(curr);
			} else {
				parent.setRight(curr);
			}
		} else {
			int result = curr.getValue().compareTo(insertNode.getValue());
			// 如果当前值大于插入的值
			if (result > 0) {
				return insert((SearchNode<T>)curr.getLeft(), insertNode, curr, true);
			} else if (result < 0) {
				return insert((SearchNode<T>)curr.getRight(), insertNode, curr, false);
			}else {
				curr.freq++;
			}
		}
		return true;
	}

	@Override
	public SearchNode<T> root() {
		return (SearchNode<T>) super.root();
	}

	@Override
	public SearchNode<T> find(T value) {
		return (SearchNode<T>) super.find(value);
	}

	@Override
	protected TreeNode<T> find0(TreeNode<T> node, T value) {
		if (node == null) {
			return null;
		}
		int result = node.getValue().compareTo(value);
		if (result > 0) {
			return find0(node.getLeft(), value);
		} else if (result < 0) {
			return find0(node.getRight(), value);
		}
		return node;
	}

	/** 
     * 返回以中序遍历方式遍历树时，t的直接后继 
     */  
    protected TreeNode<T> successor(TreeNode<T> t) {  
        if (t == null)  
            return null;  
        else if (t.getRight() != null) { //往右，然后向左直到尽头  
            TreeNode<T> p = t.getRight();  
            while (p.getLeft() != null)  
                p = p.getLeft();  
            return p;  
        } else {        //right为空，如果t是p的左子树，则p为t的直接后继  
            TreeNode<T> p = t.parent();  
            TreeNode<T> ch = t;  
            while (p != null && ch == p.getRight()) {    //如果t是p的右子树，则继续向上搜索其直接后继  
                ch = p;  
                p = p.parent();  
            }  
            return p;  
        }  
    }  
    
    @Override
    protected void deleteNode(TreeNode<T> deleteNode) {
    	//如果p左右子树都不为空，找到其直接后继，替换p，之后p指向s，删除p其实是删除s  
        //所有的删除左右子树不为空的节点都可以调整为删除左右子树有其一不为空，或都为空的情况。  
        if (deleteNode.isFull()) {  
        	TreeNode<T> s = successor(deleteNode);  
        	deleteNode.setValue(s.getValue());
        	deleteNode = s;  
        }  
        TreeNode<T> replacement = (deleteNode.getLeft() != null ? deleteNode.getLeft() : deleteNode.getRight()); 

        if (replacement != null) {      //如果其左右子树有其一不为空  
            if (deleteNode.parent() == null)   //如果p为root节点  
                root = replacement;  
            else if (deleteNode == deleteNode.parent().getLeft())    //如果p是左孩子  
            	deleteNode.parent().setLeft(replacement);  
            else                            //如果p是右孩子  
            	deleteNode.parent().setRight(replacement);  
  
            //这里更改了replacement的父节点，所以可以直接从它开始向上回溯  
            fixAfterDeletion(replacement);    
  
        } else if (deleteNode.parent() == null) { // 如果全树只有一个节点  
            root = null;  
        } else {  //左右子树都为空  
            fixAfterDeletion(deleteNode);    //这里从p开始回溯  
            if (deleteNode.parent() != null) {  
                if (deleteNode == deleteNode.parent().getLeft())  
                	deleteNode.parent().setLeft(null);  
                else if (deleteNode == deleteNode.parent().getRight())  
                	deleteNode.parent().setRight(null);  
            }  
        }     
    }
	
	
	protected void fixAfterDeletion(TreeNode<T> replacement) {
		
	}

	@Deprecated
	protected void deleteNode1(TreeNode<T> deleteNode) {
		TreeNode<T> deleteNodeParent = deleteNode.parent();
		if (deleteNodeParent == null) {
			// 左右子树都为空
			if (deleteNode.getLeft() == null && deleteNode.getRight() == null) {
				root = null;
			} else if (deleteNode.getLeft() == null || deleteNode.getRight() == null) {
				// 存在左子树或右子树
				if (deleteNode.getLeft() != null) {
					root = deleteNode.getLeft();
				} else {
					root = deleteNode.getRight();
				}
			} else {
				// 左右子树都不为空
				TreeNode<T> temp = deleteNode;
				TreeNode<T> rightLeft = deleteNode.getRight();
				while (rightLeft.getLeft() != null) {
					temp = rightLeft;
					rightLeft = rightLeft.getLeft();
				}
				if(temp == deleteNode) {
					deleteNode.setRight(rightLeft.getRight());
				}else {
					temp.setLeft(rightLeft.getRight());
				}
				deleteNode.setValue(rightLeft.getValue());	
			}
		} else {
			// 左右子树都为空
			if (deleteNode.getLeft() == null && deleteNode.getRight() == null) {
				// 根结点
				if (deleteNodeParent.getLeft() == deleteNode) {
					deleteNodeParent.setLeft(null);
				} else {
					deleteNodeParent.setRight(null);
				}
			} else if (deleteNode.getLeft() == null || deleteNode.getRight() == null) {
				// 存在左子树或右子树
				if (deleteNode.getLeft() != null) {
					if (deleteNodeParent.getLeft() == deleteNode) {
						// 如果待删除结点是左结点，且其存在左结点
						deleteNodeParent.setLeft(deleteNode.getLeft());
					} else {
						// 如果待删除结点是左结点，且其存在右结点
						deleteNodeParent.setRight(deleteNode.getLeft());
					}
				} else {
					if (deleteNodeParent.getRight() == deleteNode) {
						deleteNodeParent.setRight(deleteNode.getRight());
					} else {
						deleteNodeParent.setLeft(deleteNode.getRight());
					}
				}
			} else {
				// 左右子树都不为空
				TreeNode<T> temp = deleteNode;
				TreeNode<T> rightLeft = deleteNode.getRight();
				while (rightLeft.getLeft() != null) {
					temp = rightLeft;
					rightLeft = rightLeft.getLeft();
				}
				if(temp == deleteNode) {
					deleteNode.setRight(rightLeft.getRight());
				}else {
					temp.setLeft(rightLeft.getRight());
				}
				deleteNode.setValue(rightLeft.getValue());	
			}
		}

	}

}
