package zon.easyrefresh;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

    RefreshableListView mRefreshableListView;
    ListView listView;
    ArrayAdapter<String> adapter;
    String[] items = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mRefreshableListView = (RefreshableListView) findViewById(R.id.refreshable_view);
        listView = (ListView) findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        mRefreshableListView.setOnRefreshListener(new RefreshableListView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mRefreshableListView.finishRefreshing();
            }
        }, 0);
    }

}
