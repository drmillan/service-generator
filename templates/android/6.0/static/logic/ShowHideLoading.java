package {{{staticPackage}}}.logic;

import android.view.View;

/**
 * ShowHideLoading is an OnLoadingListener implementation which shows/hides a given control
 * @author Service Generator
 *
 * Generated Class - DO NOT MODIFY
 */
public class ShowHideLoading implements OnLoadingListener {

    private View loadingView;
    private int hideType = View.GONE;

    public ShowHideLoading(View loadingView) {
        this.loadingView = loadingView;
    }

    @Override
    public void onLoadingStarted() {
        if (loadingView != null) {
            loadingView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoadingFinished() {
        if (loadingView != null) {
            loadingView.setVisibility(hideType);
        }
    }

    public int getHideType() {
        return hideType;
    }

    public void setHideType(int hideType) {
        this.hideType = hideType;
    }
}
