package client.example.com.server.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;

import client.example.com.server.R;
import client.example.com.server.adapter.ExpandableListViewAdapter;


public class FriendlyActivity extends AppCompatActivity {

    private ExpandableListView elv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendly);
        elv = (ExpandableListView) findViewById(R.id.elv);

        ExpandableListViewAdapter adapter = new ExpandableListViewAdapter(this);

        elv.setAdapter(adapter);

        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(FriendlyActivity.this, ChatOnlineActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }
}
