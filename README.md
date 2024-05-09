# hengx-tree 树形控件库

## 其他语言
- [English](README.en.md)

## 这是个啥？
`hengx-tree` 是一个超实用的树形控件库，用起来就像搭积木一样简单。它基于 `RecyclerView`，让你的列表也能长出树杈来。

## 怎么用？
安装起来贼简单，就几步：

1. **自己搞定编译**
2. **找大神帮你编译**
3. **直接下载现成的**：[123网盘](https://www.123pan.com/s/RmAZVv-Yu4pH.html)，一键下载，省事儿！

### 快速上手

#### 1. 在布局文件里拖个控件
```xml
<com.hengx.tree.widget.TreeView
    android:layout_height="match_parent"
    android:layout_width="match_parent"
    android:id="@+id/tree_view" />
```

#### 2. 在代码里搞起来
```java
TreeView treeView = findViewById(R.id.tree_view);
treeView.setOnNodeClickListener((node) -> {
    // 点到节点了，干点啥好呢？
});
```

#### 3. 树形结构，想怎么搭就怎么搭
```java
TreeNode root = new TreeNode();
root.setTitle("老大");
root.setDescription("我是根，我怕谁");
treeView.add(root);
treeView.update(); // 记得刷新界面哦
```

### 开发者
1. ![阿恒](http://q1.qlogo.cn/g?b=qq&nk=3322977037&s=1)

### 有啥不懂的？
- **QQ群交流**：加入QQ群 [236641851](https://qm.qq.com/q/1W5qXVqQUU)，有啥问题群里问
- **QQ私聊**：不想加群？直接QQ联系 [3322977037](https://qm.qq.com/q/p1Utp8KkWQ)，一对一解答
- **邮箱联系**：用不惯QQ的外国朋友可以发邮件到 `mc_hengxing@163.com`

### 还有文档
- [看看文档](基本文档.md)，更深入了解怎么用