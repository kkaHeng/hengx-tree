package com.hengx.tree.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hengx.tree.R;
import com.hengx.tree.base.TreeNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class TreeView <E extends TreeNode> extends HorizontalScrollView {
    
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter<TreeViewHolder> adapter;
    private List<TreeNode> roots;
    private TreeNode[] nodes;
    private OnItemLoadListener onItemLoadListener;
    private Context attachContext;
    private OnNodeExpandListener onNodeExpandListener;
    private OnNodeClickListener onNodeClickListener;
    private OnNodeLongClickListener onNodeLongClickListener;
    private int maxWidth = -1;
    
    public TreeView(Context context) {
        super(context);
        init();
    }

    public TreeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.attachContext = getContext();
        this.roots = new ArrayList<>();
        this.recyclerView = new RecyclerView(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        addView(recyclerView, -1, -1);
        setFillViewport(true);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        this.adapter = new RecyclerView.Adapter<TreeViewHolder>() {
            @Override
            public int getItemCount() {
                return nodes == null ? 0 : nodes.length;
            }

            @Override
            public void onBindViewHolder(TreeViewHolder treeViewHolder, int position) {
                onItemLoadListener.onBindHolder(treeViewHolder, nodes[position]);
            }

            @Override
            public TreeViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
                if (viewGroup.getLayoutParams() != null) {
                    ViewGroup.LayoutParams lpm = viewGroup.getLayoutParams();
                    lpm.width = -1;
                    viewGroup.setLayoutParams(lpm);
                }
                return onItemLoadListener.onCreateHolder(viewGroup, type);
            }

            @Override
            public int getItemViewType(int position) {
                return onItemLoadListener.getItemType(position);
            }
        };
        recyclerView.setAdapter(adapter);
        setOnItemLoadListener(null);
    }
    
    public void update() {
    	update(true);
    }
    
    public void update(boolean updateView) {
    	List<TreeNode> list = new ArrayList<>();
        for (int i = 0; i < roots.size(); i++) {
            Stack<TreeNode> stack = new Stack<>();
            stack.push(roots.get(i));
            while (!stack.isEmpty()) {
                TreeNode node = stack.pop();
                list.add(node);
                if (node.isExpandable() && node.isExpand()) {
                    for (int i2 = node.length() - 1; i2 >= 0; i2--) {
                        stack.push(node.get(i2));
                    }
                }
            }
        }
        nodes = list.toArray(new TreeNode[0]);
        if (updateView) {
            adapter.notifyDataSetChanged();
        }
    }
    
    /**
     * 更新节点展开状态(在treeNode.setExpand之后使用)
     * @param node 节点
     */
    public void updateExpand(TreeNode node) {
        if (!node.isExpandable())
            return;
        int i = find(node);
        boolean isExpand = node.isExpand();
        if (i != -1) {
            int len = 0;
            List<TreeNode> list = new ArrayList<>(Arrays.asList(nodes));
            for (int i2 = 0; i2 < node.length(); i2++) {
                Stack<TreeNode> stack = new Stack<>();
                stack.push(node.get(i2));
                while (!stack.isEmpty()) {
                    TreeNode n = stack.pop();
                    len++;
                    if (isExpand)
                        list.add(i + len, n);
                    if (n.isExpandable() && n.isExpand()) {
                        for (int i3 = n.length() - 1; i3 >= 0; i3--) {
                            stack.push(n.get(i3));
                        }
                    }
                }
            }
            if (!isExpand)
                for (int i0 = 0; i0 < len; i0++) {
                    list.remove(i + 1);
                }
            nodes = list.toArray(new TreeNode[0]);
            ObjectAnimator.ofFloat((View) node.getViewHolder().expandButton.getView(), "rotation", isExpand ? 45 : 0)
                    .start();
            if (isExpand) {
                adapter.notifyItemRangeInserted(i + 1, len);
            } else {
                adapter.notifyItemRangeRemoved(i + 1, len);
            }
        }
    }
    
    /**
     * 更新一个节点及其子节点
     * @param node 节点
     */
    public void update(TreeNode node) {
        int index = find(node);
        if (index == -1) {
            update(false);
            index = find(node);
            if (index == -1)
                return;
        }
        if (node.isExpandable() && node.isExpand()) {
            adapter.notifyItemChanged(index, length(node) + 1);
        } else {
            adapter.notifyItemChanged(index);
        }
    }
    
    /**
     * 更新添加节点(在treeNode.add后使用)
     * @param node 节点
     */
    public void updateAdd(TreeNode node) {
        update(false);
        int index = find(node);
        if (index > -1) {
            if (node.isExpandable() && node.isExpand()) {
                adapter.notifyItemRangeInserted(index, length(node) + 1);
            } else {
                adapter.notifyItemInserted(index);
            }
        }
    }

    /**
     * 更新删除节点(在treeNode.remove后使用)
     * @param node 节点
     */
    public void updateDelete(TreeNode node) {
        int i = find(node);
        if (i > -1) {
            update(false);
            if (node.isExpandable() && node.isExpand()) {
                adapter.notifyItemRangeRemoved(i, length(node) + 1);
            } else {
                adapter.notifyItemRemoved(i);
            }
        }
    }
    
    /**
     * 添加一个根节点
     * @param node 节点
     */
    public void add(TreeNode node) {
        if(node.getParent() != null) {
            throw new RuntimeException("只能向列表添加根节点");
        }
        roots.add(node);
    }
    
    /**
     * 插入一个根节点
     * @param index 插入位置
     * @param node 节点
     */
    public void add(int index, TreeNode node){
        if(node.getParent() != null) {
            throw new RuntimeException("只能向列表添加根节点");
        }
        roots.add(index, node);
    }
    
    /**
     * 移除一个根节点
     * @param index 索引
     */
    public void remove(int index){
        roots.remove(index);
    }
    
    /**
     * 清空所有节点
     */
    public void clear(){
        roots.clear();
    }
    
    /**
     * 根节点数量
     * @return 数量
     */
    public int length(){
        return roots.size();
    }
    
    private int length(TreeNode node) {
        int len = 0;
        for (int i = 0; i < node.length(); i++) {
            Stack<TreeNode> stack = new Stack<>();
            stack.push(node.get(i));
            while (!stack.isEmpty()) {
                TreeNode n = stack.pop();
                len++;
                if (n.isExpandable() && n.isExpand()) {
                    for (int i3 = n.length() - 1; i3 >= 0; i3--) {
                        stack.push(n.get(i3));
                    }
                }
            }
        }
        return len;
    }

    /**
     * 查找一个根节点
     * @param node 节点
     * @return 索引
     */
    public int indexOf(TreeNode node) {
        return roots.indexOf(node);
    }
    
    public E get(int index) {
    	return (E) roots.get(index);
    }

    public int find(TreeNode node) {
        if (nodes == null)
            return -1;
        for (int i = 0; i < nodes.length; i++) {
            if (node == nodes[i])
                return i;
        }
        return -1;
    }

    public E find(int index) {
        if (nodes == null)
            return null;
        return (E) nodes[index];
    }

    public int listLength() {
        return nodes == null ? 0 : nodes.length;
    }

    public TreeViewHolder newItemView(Context attachContext) {
        FrameLayout layout = new FrameLayout(getContext());
        LinearLayout node_layout = new LinearLayout(getContext());
        ImageView expand = new ImageView(getContext());
        ImageView icon = new ImageView(getContext());
        TextView title = new TextView(getContext());
        TextView description = new TextView(getContext());
        int item_height = dip2px(40);
        int dip8 = dip2px(8);
        int dip5 = dip2px(5);
        LinearLayout.LayoutParams lpm_expand = new LinearLayout.LayoutParams(item_height, item_height);
        node_layout.addView(expand, lpm_expand);
        LinearLayout.LayoutParams lpm_icon = new LinearLayout.LayoutParams(item_height, item_height);
        node_layout.addView(icon, lpm_icon);
        LinearLayout.LayoutParams lpm_title = new LinearLayout.LayoutParams(-2, item_height);
        lpm_title.leftMargin = dip8;
        node_layout.addView(title, lpm_title);
        LinearLayout.LayoutParams lpm_des = new LinearLayout.LayoutParams(-2, item_height);
        lpm_des.leftMargin = dip8;
        node_layout.setPadding(dip8, 0, dip8, 0);
        node_layout.addView(description, lpm_des);
        layout.setLayoutParams(new RecyclerView.LayoutParams(-1, item_height));
        layout.addView(node_layout, -1, -1);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(color(android.R.attr.textColorPrimary));
        title.setSingleLine(true);
        title.setTextSize(14f);
        description.setGravity(Gravity.CENTER);
        description.setTextColor(color(android.R.attr.textColorHint));
        description.setSingleLine(true);
        description.setTextSize(14f);
        expand.setImageDrawable(attachContext.getDrawable(R.drawable.arrow));
        icon.setPadding(dip5, dip5, dip5, dip5);
        Resources.Theme theme = getContext().getTheme();
        {
            TypedValue typedValue = new TypedValue();
            theme.resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
            int[] attribute = new int[] { android.R.attr.selectableItemBackground };
            TypedArray typedArray = theme.obtainStyledAttributes(typedValue.resourceId, attribute);
            layout.setForeground(typedArray.getDrawable(0));
            layout.setClickable(true);
        }
        {
            TypedValue typedValue = new TypedValue();
            theme.resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, typedValue, true);
            int[] attribute = new int[] { android.R.attr.selectableItemBackgroundBorderless };
            TypedArray typedArray = theme.obtainStyledAttributes(typedValue.resourceId, attribute);
            expand.setBackground(typedArray.getDrawable(0));
            expand.setClickable(true);
        }
        TreeViewHolder holder = new TreeViewHolder(layout);
        holder.expandButton = createPictureControl(expand);
        holder.icon = createPictureControl(icon);
        holder.title = createTextControl(title);
        holder.description = createTextControl(description);
        holder.layout = node_layout;
        layout.post(() -> {
            layout.setMinimumWidth(recyclerView.getWidth());
        });
        return holder;
    }
    
    private PictureControl createPictureControl(ImageView view) {
    	return new PictureControl<ImageView>() {
            @Override
            public PictureControl setDrawable(Drawable drawable) {
                view.setImageDrawable(drawable);
                return this;
            }
            @Override
            public Drawable getDrawable() {
                return view.getDrawable();
            }
            @Override
            public ImageView getView() {
            	return view;
            }
        };
    }
    private TextControl createTextControl(TextView view) {
    	return new TextControl<TextView>() {
            @Override
            public CharSequence getText() {
                return view.getText();
            }
            @Override
            public TextControl setText(CharSequence text) {
                view.setText(text);
                return this;
            }
            @Override
            public TextView getView() {
            	return view;
            }
        };
    }
    
    public int color(int id) {
        int[] attribute = new int[] { id };
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(id, attribute);
        return typedArray.getColor(0, 0x000000);
    }

    public int dip2px(int value) {
        if (value <= 0) {
            return value;
        }
        float scale = getResources().getDisplayMetrics().density;
        return (int) (value * scale + 0.5f);
    }

    /**
     * 获取高级列表框
     * @return 高级列表框
     */
    public RecyclerView getRecyclerView() {
        return this.recyclerView;
    }

    /**
     * 获取布局管理器
     * @return 布局管理器
     */
    public RecyclerView.LayoutManager getLayoutManager() {
        return this.layoutManager;
    }

    /**
     * 获取适配器
     * @return 适配器
     */
    public RecyclerView.Adapter getAdapter() {
        return this.adapter;
    }

    /**
     * 设置附加上下文，该上下文将用于加载res资源，如果不设置，将使用默认上下文
     * @param attachContext 上下文
     */
    public void setAttachContext(Context attachContext) {
        this.attachContext = attachContext;
    }

    /**
     * 获取附加上下文
     * @return 上下文
     */
    public Context getAttachContext() {
        return this.attachContext;
    }

    /**
     * 设置项目加载监听器，传入null将使用默认的
     * @param onItemLoadListener 项目加载监听器
     */
    public void setOnItemLoadListener(OnItemLoadListener onItemLoadListener) {
        this.onItemLoadListener = onItemLoadListener;
        if (this.onItemLoadListener == null) {
            this.onItemLoadListener = new OnItemLoadListener() {
                @Override
                public int getItemType(int position) {
                    return 0;
                }

                @Override
                public TreeViewHolder onCreateHolder(ViewGroup layoit, int type) {
                    return newItemView(getAttachContext());
                }

                @Override
                public void onBindHolder(TreeViewHolder holder, TreeNode node) {
                    node.setViewHolder(holder);
                    int left = dip2px(20) * node.getLevel();
                    ViewGroup.LayoutParams params = holder.layout.getLayoutParams();
                    
                    if (params instanceof ViewGroup.MarginLayoutParams) {
                        ViewGroup.MarginLayoutParams lpm = (ViewGroup.MarginLayoutParams) params;
                        lpm.setMargins(left, 0, 0, 0);
                        holder.layout.setLayoutParams(params);
                    }
                    ImageView expandButton = (ImageView) holder.expandButton.getView();
                    ImageView iconView = (ImageView) holder.icon.getView();
                    TextView titleView = (TextView) holder.title.getView();
                    TextView descriptionView = (TextView) holder.description.getView();
                    expandButton.setRotation(node.isExpand() ? 45 : 0);
                    expandButton.setVisibility(node.isExpandable() ? View.VISIBLE : View.INVISIBLE);
                    if (node.getTitle() != null) {
                        titleView.setText(node.getTitle());
                    } else {
                        titleView.setText("");
                    }
                    if (node.getDescription() != null) {
                        descriptionView.setText(node.getDescription());
                    } else {
                        descriptionView.setText("");
                    }
                    Drawable icon = node.getIcon();
                    if (icon == null) {
                        icon = getAttachContext().getDrawable(
                                node.isExpandable() ? R.drawable.ic_directory : R.drawable.ic_file_unknown);
                    }
                    holder.icon.setDrawable(icon);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (onNodeClickListener != null)
                                onNodeClickListener.onClick(node);
                            if (node.getOnNodeClickListener() != null)
                                node.getOnNodeClickListener().onClick(node);
                        }
                    });
                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            if (node.getOnNodeLongClickListener() != null) {
                                if (node.getOnNodeLongClickListener().onLongClick(node))
                                    return true;
                            }
                            if (onNodeLongClickListener != null) {
                                return onNodeLongClickListener.onLongClick(node);
                            }
                            return false;
                        }
                    });
                    ((View) holder.expandButton.getView()).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (node.getOnNodeExpandListener() != null) {
                                if (node.getOnNodeExpandListener().onExpand(node))
                                    return;
                            }
                            if (onNodeExpandListener != null) {
                                if (onNodeExpandListener.onExpand(node))
                                    return;
                            }
                            node.setExpand(!node.isExpand());
                            updateExpand(node);
                        }
                    });
                }
            };
        }
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return recyclerView.onTouchEvent(ev) | super.onTouchEvent(ev);
    }

    /**
     * 设置节点展开监听器
     * @param onNodeExpandListener 节点展开监听器
     */
    public void setOnNodeExpandListener(OnNodeExpandListener onNodeExpandListener) {
        this.onNodeExpandListener = onNodeExpandListener;
    }

    /**
     * 设置节点长按监听器
     * @param onNodeLongClickListener 节点长按监听器
     */
    public void setOnNodeLongClickListener(OnNodeLongClickListener onNodeLongClickListener) {
        this.onNodeLongClickListener = onNodeLongClickListener;
    }

    /**
     * 设置节点单击监听器
     * @param onNodeClickListener 单击监听器
     */
    public void setOnNodeClickListener(OnNodeClickListener onNodeClickListener) {
        this.onNodeClickListener = onNodeClickListener;
    }
    
    /*@Override
    protected void onMeasure(int arg0, int arg1) {
        super.onMeasure(arg0, arg1);
        maxWidth = recyclerView.getWidth();
    }
    
    @Override
    protected void onSizeChanged(int arg0, int arg1, int arg2, int arg3) {
        super.onSizeChanged(arg0, arg1, arg2, arg3);
        maxWidth = recyclerView.getWidth();
    }*/
    
    
    
    public static interface OnItemLoadListener {
        
        int getItemType(int position);

        TreeViewHolder onCreateHolder(ViewGroup layoit, int type);

        void onBindHolder(TreeViewHolder holder, TreeNode node);
        
    }
    
}
