package com.apollo.txvideolist;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.apollo.txvideolist.adapter.MyAdapter;
import com.apollo.txvideolist.video.TXVideoManager;
import com.apollo.txvideolist.visibility.calculator.SingleListViewItemActiveCalculator;
import com.apollo.txvideolist.visibility.scroll.RecyclerViewItemPositionGetter;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    int mScrollState;
    SingleListViewItemActiveCalculator mCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivityPermissionsDispatcher.startPermissionWithPermissionCheck(this);
        mRecyclerView = findViewById(R.id.recyclerView);

        final MyAdapter adapter = new MyAdapter(mRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        mCalculator = new SingleListViewItemActiveCalculator(adapter,
                new RecyclerViewItemPositionGetter(layoutManager, mRecyclerView));

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                mScrollState = newState;
                if (newState == RecyclerView.SCROLL_STATE_IDLE && adapter.getItemCount() > 0) {
                    mCalculator.onScrollStateIdle();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                mCalculator.onScrolled(mScrollState);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void startPermission(){

    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showDeniedForStorage() {
        Toast.makeText(this, "权限获取失败", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showNeverAskForStorage() {
        Toast.makeText(this, "权限获取失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        TXVideoManager.getInstance().pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TXVideoManager.getInstance().resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TXVideoManager.getInstance().stop();
    }
}
