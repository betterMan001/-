package com.ly.a316.ly_meetingroommanagement.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ly.a316.ly_meetingroommanagement.R;


import com.ly.a316.ly_meetingroommanagement.nim.helper.SessionHelper;
import com.ly.a316.ly_meetingroommanagement.nim.reminder.ReminderManager;
import com.ly.a316.ly_meetingroommanagement.nim.session.GuessAttachment;
import com.ly.a316.ly_meetingroommanagement.nim.session.RTSAttachment;
import com.ly.a316.ly_meetingroommanagement.nim.session.RedPacketAttachment;
import com.ly.a316.ly_meetingroommanagement.nim.session.RedPacketOpenedAttachment;
import com.ly.a316.ly_meetingroommanagement.nim.session.SnapChatAttachment;
import com.ly.a316.ly_meetingroommanagement.nim.session.StickerAttachment;
import com.netease.nim.uikit.business.recent.RecentContactsCallback;
import com.netease.nim.uikit.business.recent.RecentContactsFragment;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConversationListFragment extends Fragment {

    private static final String TAG = "ConversationListFragmen";
    public ConversationListFragment() {
        // Required empty public constructor
        //setContainerId(MainTab.RECENT_CONTACTS.fragmentId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversation_list, container, false);
        return view;

    }





    private RecentContactsFragment fragment;


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        onCurrent();
        addRecentContactsFragment();
    }


    // 将最近联系人列表fragment动态集成进来。
    private void addRecentContactsFragment() {
        fragment = new RecentContactsFragment();

        fragment.setContainerId(R.id.messages_fragment);

        final UI activity = (UI) getActivity();
        Log.d(TAG, "addRecentContactsFragment: 测试强制转换");
//         //如果是activity从堆栈恢复，FM中已经存在恢复而来的fragment，此时会使用恢复来的，而new出来这个会被丢弃掉
        fragment = (RecentContactsFragment) (activity.addFragment(fragment));
//          //订制化接口
        fragment.setCallback(new RecentContactsCallback() {
            @Override
            public void onRecentContactsLoaded() {
                // 最近联系人列表加载完毕
            }

            @Override
            public void onUnreadCountChange(int unreadCount) {
                ReminderManager.getInstance().updateSessionUnreadNum(unreadCount);
            }

            @Override
            public void onItemClick(RecentContact recent) {
                // 回调函数，以供打开会话窗口时传入定制化参数，或者做其他动作
                switch (recent.getSessionType()) {
                    case P2P:
                        SessionHelper.startP2PSession(getActivity(), recent.getContactId());
                        break;
                    case Team:
                        SessionHelper.startTeamSession(getActivity(), recent.getContactId());
                        break;
                    default:
                        break;
                }
            }

            @Override
            public String getDigestOfAttachment(RecentContact recentContact, MsgAttachment attachment) {
                // 设置自定义消息的摘要消息，展示在最近联系人列表的消息缩略栏上
                // 当然，你也可以自定义一些内建消息的缩略语，例如图片，语音，音视频会话等，自定义的缩略语会被优先使用。
                if (attachment instanceof GuessAttachment) {
                    GuessAttachment guess = (GuessAttachment) attachment;
                    return guess.getValue().getDesc();
                } else if (attachment instanceof RTSAttachment) {
                    return "[白板]";
                } else if (attachment instanceof StickerAttachment) {
                    return "[贴图]";
                } else if (attachment instanceof SnapChatAttachment) {
                    return "[阅后即焚]";
                } else if (attachment instanceof RedPacketAttachment) {
                    return "[红包]";
                } else if (attachment instanceof RedPacketOpenedAttachment) {
                    return ((RedPacketOpenedAttachment) attachment).getDesc(recentContact.getSessionType(), recentContact.getContactId());
                }

                return null;
            }

            @Override
            public String getDigestOfTipMsg(RecentContact recent) {
                String msgId = recent.getRecentMessageId();
                List<String> uuids = new ArrayList<>(1);
                uuids.add(msgId);
                List<IMMessage> msgs = NIMClient.getService(MsgService.class).queryMessageListByUuidBlock(uuids);
                if (msgs != null && !msgs.isEmpty()) {
                    IMMessage msg = msgs.get(0);
                    Map<String, Object> content = msg.getRemoteExtension();
                    if (content != null && !content.isEmpty()) {
                        return (String) content.get("content");
                    }
                }

                return null;
            }
        });
    }

}
