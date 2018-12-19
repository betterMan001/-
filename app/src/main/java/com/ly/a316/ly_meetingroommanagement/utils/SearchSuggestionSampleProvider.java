package com.ly.a316.ly_meetingroommanagement.utils;

import android.content.SearchRecentSuggestionsProvider;

/*
Date:2018/12/19
Time:18:46
auther:xwd
*/
public class SearchSuggestionSampleProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY="com.ly.a316.ly_meetingroommanagement.utils.SearchSuggestionSampleProvider";
    public final static int MODE=DATABASE_MODE_QUERIES;

    public SearchSuggestionSampleProvider(){
        super();
        setupSuggestions(AUTHORITY, MODE);
    }
}
