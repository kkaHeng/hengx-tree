# hengx-tree

## 别的语言
[English](README_en.md)
[粵語](README_zh-yue.md)

## 介绍
这是一个简单的树形结构库，包含了基于RecyclerView实现的树形列表

## 软件架构
我不知道，别问我


## 安装教程

1. 自己编译
2. 找别人编译
3. 在线下载依赖：[123云盘](https://www.123pan.com/s/RmAZVv-Yu4pH.html)

#### 使用说明

1. 添加树形列表框
- XML
```xml
<com.hengx.tree.widget.TreeView
    android:layout_height="match_parent"
    android:layout_width="match_parent"
    android:id="@+id/tree_view" />

```

- Java
```java
TreeView treeView = new TreeView(context);
treeView.setOnNodeClickListener(new OnNodeClickListener() {
    @Override
    public void onClick(TreeNode node) {
        //其中一个节点被单击
    }
});
```


2.  构建树形结构
- 基本
```java
TreeNode root = new TreeNode();
root.setTitle("根节点");
root.setDescription("这是一个根节点");
treeView.add(root);
treeView.update(); //大部分操作都要更新列表
```

- 进阶
```java
private TreeView view;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    view = findViewById(R.id.view);
    createNodes(null);
    view.update();
}
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
```


#### 参与贡献
1. 阿恒


#### 文档
- [基本文档](基本文档.md)


#### 联系开发者

1. 有问题？加群讨论：[236641851](https://qm.qq.com/q/1W5qXVqQUU)
2. 不方便加群？加开发者QQ：[3322977037](https://qm.qq.com/q/p1Utp8KkWQ)
3. 我不是中国用户，我不知道怎么使用QQ，可以联系我的邮箱：`mc_hengxing@163.com`