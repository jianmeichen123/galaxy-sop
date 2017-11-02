package com.galaxyinternet.hologram.util;

import java.util.ArrayList;
import java.util.List;

import com.galaxyinternet.model.common.Node;

public class NodeUtil {
    /** 
     * 两层循环实现建树 
     * @param treeNodes 传入的树节点列表 
     * @return 
     */  
    public static List<Node> bulid(List<Node> treeNodes) {  
  
        List<Node> trees = new ArrayList<Node>();  
  
        for (Node treeNode : treeNodes) {  
            if (treeNode.getParentId() == 0) {  
                trees.add(treeNode);  
            }  
            for (Node it : treeNodes) {  
                if (it.getParentId().longValue() == treeNode.getId().longValue()) {  
                    if (treeNode.getChildList() == null) {  
                        treeNode.setChildList(new ArrayList<Node>());  
                    }  
                    treeNode.getChildList().add(it);  
                }  
            }  
        }  
        return trees;  
    }  
    
    /** 
     * 递归查找子节点 
     * @param treeNodes 
     * @return 
     */  
    public static Node findChildren(Node treeNode,List<Node> treeNodes) {  
        for (Node it : treeNodes) {  
            if(treeNode.getId().equals(it.getParentId())) {  
                if (treeNode.getChildList() == null) {  
                    treeNode.setChildList(new ArrayList<Node>());  
                }  
                treeNode.getChildList().add(findChildren(it,treeNodes));  
            }  
        }  
        return treeNode;  
    }
}
