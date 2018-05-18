package com.buckeyeconnection;

//import android.support.v7.A;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.buckeyeconnection.R;
//import co.

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ChatViewN chatview= (ChatViewN)findViewById(R.id.chat_view);

        chatview.setOnSentMessageListener(new ChatViewN.OnSentMessageListener() {
            @Override
            public boolean sendMessage(ChatMessage chatMessage) {
                return true;
            }
        });

        chatview.setTypingListener(new ChatViewN.TypingListener() {
            @Override
            public void userStartedTyping() {

            }

            @Override
            public void userStoppedTyping() {

            }
        });
    }
}
