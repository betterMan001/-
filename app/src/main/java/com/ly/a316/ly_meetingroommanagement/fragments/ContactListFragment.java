package com.ly.a316.ly_meetingroommanagement.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ly.a316.ly_meetingroommanagement.R;
import com.netease.nim.uikit.business.contact.ContactsFragment;
import com.netease.nim.uikit.common.activity.UI;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactListFragment extends Fragment {
    private ContactsFragment fragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.contacts_list, container, false);
        return view;

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addContactFragment();

    }
    // 将通讯录列表fragment动态集成进来。 开发者也可以使用在xml中配置的方式静态集成。
    private void addContactFragment() {
        fragment = new ContactsFragment();
        fragment.setContainerId(R.id.contact_fragment);
      final  UI activity = (UI) getActivity();
        // 如果是activity从堆栈恢复，FM中已经存在恢复而来的fragment，此时会使用恢复来的，而new出来这个会被丢弃掉
        fragment = (ContactsFragment) activity.addFragment(fragment);

    }
}
