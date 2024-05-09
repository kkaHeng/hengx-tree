package com.hengx.tree.base;

import android.graphics.drawable.Drawable;
import com.hengx.tree.widget.OnNodeLongClickListener;
import com.hengx.tree.widget.OnNodeExpandListener;
import com.hengx.tree.widget.OnNodeClickListener;
import com.hengx.tree.widget.TreeViewHolder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TreeNode<T extends Object>
        implements Dataable, Extendible, Descriptable, Eventable, Iterable<TreeNode<?>> {

    private String id, name;
    private TreeNode<?> parent;
    private T source;
    private List<TreeNode<?>> children;

    public TreeNode add(TreeNode node) {
        if (children == null) children = new ArrayList<>();
        if (node.getParent() != null) {
            throw new HasParentNodeException();
        }
        node.setParent(this);
        children.add(node);
        return this;
    }

    public TreeNode set(int position, TreeNode node) {
        if (children != null) {
            children.get(position).setParent(null);
            node.setParent(this);
            children.set(position, node);
        }
        return this;
    }

    public TreeNode add(int position, TreeNode node) {
        if (children == null) children = new ArrayList<>();
        if (node.getParent() != null) {
            throw new HasParentNodeException();
        }
        node.setParent(this);
        children.add(position, node);
        return this;
    }

    public TreeNode addAll(TreeNode[] nodes) {
        if (children == null) children = new ArrayList<>();
        for (TreeNode node : nodes) {
            if (node.getParent() != null) {
                throw new HasParentNodeException();
            }
            node.setParent(this);
            children.add(node);
        }
        return this;
    }

    public TreeNode addAll(int positiin, TreeNode[] nodes) {
        if (children == null) children = new ArrayList<>();
        int i = positiin;
        for (TreeNode node : nodes) {
            if (node.getParent() != null) {
                throw new HasParentNodeException();
            }
            node.setParent(this);
            children.add(i, node);
            i++;
        }
        return this;
    }

    public TreeNode get(int position) {
        return children == null ? null : children.get(position);
    }

    public int indexOf(TreeNode node) {
        return children == null ? -1 : children.indexOf(node);
    }

    public int length() {
        return children == null ? 0 : children.size();
    }

    public TreeNode remove(int position) {
        if (children != null) {
            children.get(position).setParent(null);
            children.remove(position);
            if (children.isEmpty()) children = null;
        }
        return this;
    }

    public TreeNode removeAll(TreeNode[] nodes) {
        if (children != null) {
            for (TreeNode node : nodes) {
                int i = children.indexOf(node);
                if (i != -1) {
                    node.setParent(null);
                    children.remove(i);
                }
            }
            if (children.isEmpty()) children = null;
        }
        return this;
    }

    public TreeNode clear() {
        if (children != null) {
            for (int i = 0; i < children.size(); i++) {
                TreeNode node = children.get(i);
                node.setParent(null);
            }
            children.clear();
            children = null;
        }
        return this;
    }

    public T getSource() {
        return this.source;
    }

    public TreeNode setSource(T source) {
        this.source = source;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public TreeNode setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public TreeNode setName(String name) {
        this.name = name;
        return this;
    }

    public TreeNode getParent() {
        return this.parent;
    }

    public TreeNode[] children() {
        if (children == null) return new TreeNode[0];
        return children.toArray(new TreeNode[0]);
    }

    public TreeNode setParent(TreeNode parent) {
        this.parent = parent;
        return this;
    }

    public int getLevel() {
        int level = 0;
        TreeNode current = this.parent;
        while (current != null) {
            level++;
            current = current.getParent();
        }
        return level;
    }

    @Override
    public Iterator<TreeNode<?>> iterator() {
        if (children == null) {
            return new Iterator<TreeNode<?>>() {
                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public TreeNode<?> next() {
                    return null;
                }
            };
        }
        return children.iterator();
    }

    private Map<String, Object> datas;
    private boolean expandable, expand;

    @Override
    public <E> E get(String key) {
        if (datas == null) return null;
        return (E) datas.get(key);
    }

    @Override
    public TreeNode put(String key, Object data) {
        if (datas == null) datas = new HashMap<>();
        datas.put(key, data);
        return this;
    }

    @Override
    public boolean has(String key) {
        if (datas == null) return false;
        return datas.containsKey(key);
    }

    @Override
    public TreeNode remove(String key) {
        if (datas != null) {
            datas.remove(key);
            if (datas.isEmpty()) datas = null;
        }
        return this;
    }

    @Override
    public String[] keys() {
        if (datas == null) return new String[0];
        String[] keys = new String[datas.size()];
        int i = 0;
        for (String key : datas.keySet()) {
            keys[i] = key;
            i++;
        }
        return keys;
    }

    @Override
    public boolean isExpand() {
        return this.expand;
    }

    @Override
    public boolean isExpandable() {
        return this.expandable;
    }

    @Override
    public TreeNode setExpand(boolean expand) {
        this.expand = expand;
        return this;
    }

    @Override
    public TreeNode setExpandable(boolean expandable) {
        this.expandable = expandable;
        return this;
    }

    private CharSequence title, description;
    private TreeViewHolder viewHolder;

    @Override
    public CharSequence getTitle() {
        return this.title;
    }

    @Override
    public CharSequence getDescription() {
        return this.description;
    }

    @Override
    public TreeViewHolder getViewHolder() {
        return this.viewHolder;
    }

    @Override
    public TreeNode setTitle(CharSequence title) {
        this.title = title;
        return this;
    }

    @Override
    public TreeNode setDescription(CharSequence description) {
        this.description = description;
        return this;
    }

    @Override
    public TreeNode setViewHolder(TreeViewHolder viewHolder) {
        this.viewHolder = viewHolder;
        return this;
    }

    @Override
    public String getString(String key) {
        return (String) get(key);
    }

    @Override
    public int getInt(String key) {
        return (int) get(key);
    }

    @Override
    public float getFloat(String key) {
        return (float) get(key);
    }

    @Override
    public boolean getBoolean(String key) {
        return (boolean) get(key);
    }

    @Override
    public double getDouble(String key) {
        return (double) get(key);
    }

    @Override
    public long getLong(String key) {
        return (long) get(key);
    }

    private Drawable icon;

    @Override
    public Drawable getIcon() {
        return this.icon;
    }

    @Override
    public TreeNode setIcon(Drawable icon) {
        this.icon = icon;
        return this;
    }
    
    private OnNodeClickListener click;
    private OnNodeLongClickListener longClick;
    private OnNodeExpandListener expanListener;

    @Override
    public TreeNode setOnNodeClickListener(OnNodeClickListener click) {
        this.click = click;
        return this;
    }

    @Override
    public TreeNode setOnNodeLongClickListener(OnNodeLongClickListener longClick) {
        this.longClick = longClick;
        return this;
    }

    @Override
    public TreeNode setOnNodeExpandListener(OnNodeExpandListener expand) {
        this.expanListener = expand;
        return this;
    }

    @Override
    public OnNodeClickListener getOnNodeClickListener() {
        return this.click;
    }
    

    @Override
    public OnNodeLongClickListener getOnNodeLongClickListener() {
        return this.longClick;
    }
    

    @Override
    public OnNodeExpandListener getOnNodeExpandListener() {
        return this.expanListener;
    }
    
}
