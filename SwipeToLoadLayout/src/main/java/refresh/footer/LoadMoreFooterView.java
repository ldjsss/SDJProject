package refresh.footer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.TextView;

import refresh.swipe.SwipeLoadMoreFooterLayout;
import sun.bo.lin.refresh.R;


public class LoadMoreFooterView extends SwipeLoadMoreFooterLayout {
    private TextView tvLoadMore;
    private ProgressBar progressBar;
    private int mFooterHeight;

    public LoadMoreFooterView(Context context) {
        this(context, null);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mFooterHeight = getResources().getDimensionPixelOffset(R.dimen.load_more_footer_height_classic);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvLoadMore = (TextView) findViewById(R.id.tvLoadMore);
        progressBar = (ProgressBar) findViewById(R.id.pro_bar);
    }

    @Override
    public void onPrepare() {
    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            if (-y >= mFooterHeight) {
                tvLoadMore.setText("释放加载更多");
            } else {
                tvLoadMore.setText("上拉加载更多");
            }
        }
    }

    @Override
    public void onLoadMore() {
        progressBar.setVisibility(VISIBLE);
        tvLoadMore.setText("加载中...");
    }

    @Override
    public void onRelease() {
    }

    @Override
    public void onComplete() {
        progressBar.setVisibility(GONE);
        tvLoadMore.setText("加载完成");
    }

    @Override
    public void onReset() {
    }
}