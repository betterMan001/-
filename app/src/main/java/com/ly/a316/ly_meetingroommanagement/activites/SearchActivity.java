package com.ly.a316.ly_meetingroommanagement.activites;

import android.app.SearchManager;
import android.content.Intent;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.utils.SearchSuggestionSampleProvider;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getData();
    }
    private void getData(){
        Intent intent=getIntent();
        //获得搜索的内容
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query=intent.getStringExtra(SearchManager.QUERY);
            Log.d(TAG, "getData: "+query);
            searchQuery(query);
        }
    }
    private void searchQuery(String query) {
        //保存搜索记录
        SearchRecentSuggestions suggestions=new SearchRecentSuggestions(this,
                SearchSuggestionSampleProvider.AUTHORITY, SearchSuggestionSampleProvider.MODE);
        suggestions.saveRecentQuery(query, null);

    }
}
