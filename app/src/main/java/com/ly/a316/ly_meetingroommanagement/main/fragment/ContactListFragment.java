package com.ly.a316.ly_meetingroommanagement.main.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.main.MainActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.activities.SearchViewActivity;
import com.ly.a316.ly_meetingroommanagement.nim.activity.AddFriendActivity;
import com.ly.a316.ly_meetingroommanagement.nim.activity.AdvancedTeamSearchActivity;
import com.ly.a316.ly_meetingroommanagement.nim.viewHolder.FuncViewHolder;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.model.contact.ContactsCustomization;
import com.netease.nim.uikit.business.contact.ContactsFragment;
import com.netease.nim.uikit.business.contact.core.item.AbsContactItem;
import com.netease.nim.uikit.business.contact.core.viewholder.AbsContactViewHolder;
import com.netease.nim.uikit.business.contact.selector.activity.ContactSelectActivity;
import com.netease.nim.uikit.business.team.helper.TeamHelper;
import com.netease.nim.uikit.common.activity.UI;
import com.zaaach.toprightmenu.TopRightMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactListFragment extends Fragment {
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    Unbinder unbinder;
    @BindView(R.id.do_something)
    ImageView doSomething;
    private ContactsFragment fragment;
    TopRightMenu mToRightMenu;//右上角的菜单栏
    private static final int REQUEST_CODE_NORMAL = 1;
    private static final int REQUEST_CODE_ADVANCED = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.contacts_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;

    }

    private void initView() {
        //绑定toolBar
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolBar);
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
        final UI activity = (UI) getActivity();
        // 如果是activity从堆栈恢复，FM中已经存在恢复而来的fragment，此时会使用恢复来的，而new出来这个会被丢弃掉
        fragment = (ContactsFragment) activity.addFragment(fragment);
        // 功能项定制
        fragment.setContactsCustomization(new ContactsCustomization() {
            @Override
            public Class<? extends AbsContactViewHolder<? extends AbsContactItem>> onGetFuncViewHolderClass() {
                return FuncViewHolder.class;
            }

            @Override
            public List<AbsContactItem> onGetFuncItems() {
                return FuncViewHolder.FuncItem.provide();
            }

            @Override
            public void onFuncItemClick(AbsContactItem item) {
                FuncViewHolder.FuncItem.handle(getActivity(), item);
            }
        });

    }
    public void showMenu() {
        mToRightMenu = new TopRightMenu(getActivity());
        final List<com.zaaach.toprightmenu.MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new com.zaaach.toprightmenu.MenuItem("添加好友"));
        menuItems.add(new com.zaaach.toprightmenu.MenuItem("创建讨论组"));
        menuItems.add(new com.zaaach.toprightmenu.MenuItem("创建高级群"));
        menuItems.add(new com.zaaach.toprightmenu.MenuItem("搜索高级群"));
        mToRightMenu
                .setHeight(500)     //默认高度480
                                   //默认宽度wrap_content
                .showIcon(false)     //显示菜单图标，默认为true
                .dimBackground(true)           //背景变暗，默认为true
                .needAnimationStyle(true)   //显示动画，默认为true
                .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //动画样式 默认为R.style.TRM_ANIM_STYLE
                .addMenuList(menuItems)
                .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick(int position) {
                        switch (position) {
                            //添加好友
                            case 0:
                                //用新api
                                AddFriendActivity.start((MainActivity) getActivity());
                                break;
                            //创建讨论组
                            case 1:
                                ContactSelectActivity.Option option = TeamHelper.getCreateContactSelectOption(null, 50);
                                NimUIKit.startContactSelector((MainActivity) getActivity(), option, REQUEST_CODE_NORMAL);
                                break;
                                //创建高级群
                            case 2:
                                ContactSelectActivity.Option advancedOption = TeamHelper.getCreateContactSelectOption(null, 50);
                                NimUIKit.startContactSelector((MainActivity) getActivity(), advancedOption, REQUEST_CODE_ADVANCED);
                                break;
                            //搜索高级群
                            case 3:
                                AdvancedTeamSearchActivity.start((MainActivity) getActivity());
                                break;
                        }
                    }
                }).showAsDropDown(doSomething, -160, 0);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.do_something)
    public void onViewClicked() {
        showMenu();
    }
}
