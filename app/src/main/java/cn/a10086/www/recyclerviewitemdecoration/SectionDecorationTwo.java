package cn.a10086.www.recyclerviewitemdecoration;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import mine.ActionItem;

/**
 * @author
 * @time 2017/2/14  11:07
 * @desc ${TODD}
 */
public class SectionDecorationTwo extends RecyclerView.ItemDecoration {

    private final int topGap;
    private final int alignBottom;
    private  Paint.FontMetrics fontMetrics;
    private  TextPaint textPaint;
    private ArrayList<ActionItem> items ;
    private Context  mContext;
    private DecorationCallback callback;
    private final Paint paint;


    public SectionDecorationTwo( ArrayList<ActionItem> items, Context mContext,DecorationCallback callback) {
        this.callback = callback;
        this.items = items;
        this.mContext = mContext;

//        设置悬浮窗的画笔

        Resources resources = mContext.getResources();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(resources.getColor(R.color.paintcolor));


        //设置悬浮栏中文本的画笔
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(30);
        textPaint.setColor(Color.DKGRAY);
        textPaint.setTextAlign(Paint.Align.LEFT);
        fontMetrics = new Paint.FontMetrics();
        //决定悬浮栏的高度等
        topGap =60;
        //决定文本的显示位置等
        alignBottom = 20;
    }



    //定义一个借口方便外界的调用
    interface DecorationCallback {
        String getGroupId(int position);

        String getGroupFirstLine(int position);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int pos = parent.getChildAdapterPosition(view);
        String groupId = callback.getGroupId(pos);
        if (groupId.equals("-1")) return;
        //只有是同一组的第一个才显示悬浮栏
        if (pos == 0 || isFirstInGroup(pos)) {
            outRect.top = topGap;
            if (items.get(pos).getName() == "") {
                outRect.top = 0;
            }
        } else {
            outRect.top = 0;
        }
        Log.v("lmjssjj", "position:"+pos+"--"+"outRect.top:"+outRect.top);




    }

    private boolean isFirstInGroup(int pos) {

        if (pos == 0) {
            return true;
        } else {
            // 因为是根据 字符串内容的相同与否 来判断是不是同意组的，所以此处的标记id 要是String类型
            // 如果你只是做联系人列表，悬浮框里显示的只是一个字母，则标记id直接用 int 类型就行了
            String prevGroupId = callback.getGroupId(pos - 1);
            String groupId = callback.getGroupId(pos);
            //判断前一个字符串 与 当前字符串 是否相同
            if (prevGroupId.equals(groupId)) {
                return false;
            } else {
                return true;
            }
        }

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            String groupId = callback.getGroupId(position);
            if (groupId.equals("-1")) return;
            String textLine = callback.getGroupFirstLine(position).toUpperCase();
            if (textLine == "") {
                float top = view.getTop();
                float bottom = view.getTop();
                c.drawRect(left, top, right, bottom,paint );
                return;
            } else {
                if (position == 0 || isFirstInGroup(position)) {
                    float top = view.getTop() - topGap;
                    float bottom = view.getTop();
                    //绘制悬浮栏
                    c.drawRect(left, top, right, bottom, paint);
                    //绘制文本
                    c.drawText(textLine, left, bottom, textPaint);
                }
            }
        }
    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        float lineHeight = textPaint.getTextSize() + fontMetrics.descent;

        String preGroupId = "";
        String groupId = "-1";
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);

            preGroupId = groupId;
            groupId = callback.getGroupId(position);
            if (groupId.equals("-1") || groupId.equals(preGroupId)) continue;

            String textLine = callback.getGroupFirstLine(position).toUpperCase();
            if (TextUtils.isEmpty(textLine)) continue;

            int viewBottom = view.getBottom();
            float textY = Math.max(topGap, view.getTop());
            //下一个和当前不一样移动当前
            if (position + 1 < itemCount) {
                String nextGroupId = callback.getGroupId(position + 1);
                //组内最后一个view进入了header
                if (nextGroupId != groupId && viewBottom < textY) {
                    textY = viewBottom;
                }
            }
            //textY - topGap决定了悬浮栏绘制的高度和位置
            c.drawRect(left, textY - topGap, right, textY, paint);
            //left+2*alignBottom 决定了文本往左偏移的多少（加-->向左移）
            //textY-alignBottom  决定了文本往右偏移的多少  (减-->向上移)
            c.drawText(textLine, left + 2 * alignBottom, textY - alignBottom, textPaint);
        }
    }


}
