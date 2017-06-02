package core.structural_patterns.composite;

/**
 * 
 * @ClassName: Tree
 * @Description: 组合模式：
 * 组合模式有时又叫部分-整体模式在处理类似树形结构的问题时比较方便
 * @author qinf QQ:908247035
 * @date 2017年2月15日
 * @version V1.0
 */
public class Tree {

	TreeNode root = null;
	
	public Tree(String name){
		root = new TreeNode(name);
	}
	
	public static void main(String[] args) {
		Tree tree = new Tree("tree1");
		TreeNode node = new TreeNode("node1");
		TreeNode node2 = new TreeNode("node2");
		
		node.addNode(node2);
		
		tree.root.addNode(node);
	}
}
