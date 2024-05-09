
package com.hengx.tree.app;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.hengx.tree.base.TreeNode;
import com.hengx.tree.widget.TreeView;

public class MainActivity extends AppCompatActivity {
    
    private TreeView view;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.view);
        createNodes(null);
        view.update();
    }
    
    /*public void createNodes(TreeNode parent, int level) {
        if (level > 0) {
            int l = parent == null ? 0 : parent.getLevel();
            for (int i = 0; i < 5; i++) {
                TreeNode root = new TreeNode();
                root.setTitle((l + 1) + "级节点" + (i + 1));
                root.setExpand(true);
                root.setExpandable(true);
                if (parent != null) {
                    parent.add(root);
                } else {
                    view.add(root);
                }
                createNodes(root, level - 1);
            }
        }
    }*/
    
    public void createNodes(TreeNode parent) {
        int level = parent == null ? 0 : parent.getLevel() + 1;
    	for (int i = 0; i < 10; i++) {
            TreeNode root = new TreeNode();
            root.setTitle((level + 1) + "级节点" + (i + 1));
            root.setDescription("This is a node");
            if (i < 5) {
                root.setExpandable(true);
                root.setOnNodeClickListener(node -> {
                    //实现无限创建节点
                    if (!node.isExpand() && node.length() == 0) {
                        createNodes(node);
                    }
                    node.setExpand(!node.isExpand());
                    view.updateExpand(node);
                });
                root.setOnNodeExpandListener(node -> {
                    //实现无限创建节点
                    if (!node.isExpand() && node.length() == 0) {
                        createNodes(node);
                        node.setExpand(!node.isExpand());
                        view.updateExpand(node);
                        return true;
                    }
                    return false;
                });
            } else {
                root.setOnNodeClickListener(node -> {
                    Toast.makeText(this, node.getTitle(), 0).show();
                });
            }
            if (parent != null) {
                parent.add(root);
            } else {
                view.add(root);
            }
        }
    }
    
}
