# hengx-tree Tree Widget Library

## Other languages
- [中文](README.md)

## What's This?
`hengx-tree` is an ultra-practical tree widget library that's as easy to use as playing with building blocks. It's based on `RecyclerView`, allowing your lists to branch out just like a tree.

## How to Use It?
Installation is a breeze, just follow these steps:

1. **Compile it yourself**
2. **Get a pro to compile it for you**
3. **Download it ready-made**: [123 Cloud Disk](https://www.123pan.com/s/RmAZVv-Yu4pH.html), one-click download, hassle-free!

### Quick Start

#### 1. Drag a Widget in Your Layout File
```xml
<com.hengx.tree.widget.TreeView
    android:layout_height="match_parent"
    android:layout_width="match_parent"
    android:id="@+id/tree_view" />
```

#### 2. Get It Going in Your Code
```java
TreeView treeView = findViewById(R.id.tree_view);
treeView.setOnNodeClickListener((node) -> {
    // A node has been tapped, now what?
});
```

#### 3. Build Your Tree Structure, Any Way You Like
```java
TreeNode root = new TreeNode();
root.setTitle("The Boss");
root.setDescription("I'm the root, who dares to challenge?");
treeView.add(root);
treeView.update(); // Don't forget to refresh the view!
```

### Developer
- ![HengX](http://q1.qlogo.cn/g?b=qq&nk=3322977037&s=1)

### Got Questions?
- **QQ Group Chat**: Join the QQ group [236641851](https://qm.qq.com/q/1W5qXVqQUU), ask anything in the group.
- **QQ Private Chat**: Don't want to join a group? Directly QQ contact [3322977037](https://qm.qq.com/q/p1Utp8KkWQ), get one-on-one answers.
- **Email**: Not a fan of QQ? International friends can email `mc_hengxing@163.com`.

### More Documentation
- [Check out the docs](basic_docs.md) to dive deeper into how to use it.
```