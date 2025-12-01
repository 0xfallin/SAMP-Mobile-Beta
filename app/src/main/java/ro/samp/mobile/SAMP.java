package ro.samp.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import ro.samp.mobile.GTASA;
import ro.samp.mobile.ui.AttachEdit;
import ro.samp.mobile.ui.CustomKeyboard;
import ro.samp.mobile.ui.LoadingScreen;
import ro.samp.mobile.ui.dialog.DialogManager;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class SAMP extends GTASA implements CustomKeyboard.InputListener {

    private static final String TAG = "SAMP";
    private static SAMP instance;

    private CustomKeyboard mKeyboard;
    private DialogManager mDialog;
    private AttachEdit mAttachEdit;
    private LoadingScreen mLoadingScreen;

    // Game info from MainActivity
    private String nickName;
    private String serverHost;
    private int serverPort;

    public static SAMP getInstance() {
        return instance;
    }

    // Native methods
    public native void sendDialogResponse(int dialogId, int dialogType, int response, byte[] text);
    private native void onInputEnd(byte[] str);
    private native void initializeSAMP();
    public native void onEventBackPressed();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "**** onCreate");
        super.onCreate(savedInstanceState);

        instance = this;

        // Get nickname, host, port from intent
        Intent intent = getIntent();
        nickName = intent.getStringExtra("NICK_NAME");
        serverHost = intent.getStringExtra("HOST");
        serverPort = intent.getIntExtra("PORT", 7777);

        Log.i(TAG, "Nick: " + nickName + " Host: " + serverHost + " Port: " + serverPort);

        // Initialize UI components
        mKeyboard = new CustomKeyboard(this);
        mDialog = new DialogManager(this);
        mAttachEdit = new AttachEdit(this);
        mLoadingScreen = new LoadingScreen(this);

        try {
            initializeSAMP();
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "Failed to initialize SAMP: " + e.getMessage());
        }

        // Initialize game with nick and server info
        initGame(nickName, serverHost, serverPort);
    }

    private void initGame(String nick, String host, int port) {
        // Use this method to initialize the SAMP client
        System.out.println("Initializing game: " + nick + " " + host + ":" + port);
        // TODO: call any native initialization if required
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "**** onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "**** onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "**** onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "**** onStop");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.i(TAG, "**** onRestart");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "**** onDestroy");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            onEventBackPressed();
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            try {
                onEventBackPressed();
            } catch (UnsatisfiedLinkError e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void OnInputEnd(String str) {
        byte[] toReturn = null;
        try {
            toReturn = str.getBytes("windows-1251");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "Encoding error: " + e.getMessage());
        }

        try {
            onInputEnd(toReturn);
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "Native error: " + e.getMessage());
        }
    }

    public void showDialog(int dialogId, int dialogTypeId, byte[] captionBytes, byte[] contentBytes,
                           byte[] leftBtnBytes, byte[] rightBtnBytes) {
        final String caption = new String(captionBytes, StandardCharsets.UTF_8);
        final String content = new String(contentBytes, StandardCharsets.UTF_8);
        final String leftBtn = new String(leftBtnBytes, StandardCharsets.UTF_8);
        final String rightBtn = new String(rightBtnBytes, StandardCharsets.UTF_8);

        runOnUiThread(() -> mDialog.show(dialogId, dialogTypeId, caption, content, leftBtn, rightBtn));
    }

    public void showKeyboard() {
        runOnUiThread(() -> mKeyboard.ShowInputLayout());
    }

    public void hideKeyboard() {
        runOnUiThread(() -> mKeyboard.HideInputLayout());
    }

    public void showEditObject() {
        runOnUiThread(() -> mAttachEdit.show());
    }

    public void hideEditObject() {
        runOnUiThread(() -> mAttachEdit.hide());
    }

    public void showLoadingScreen() {
        runOnUiThread(() -> mLoadingScreen.show());
    }

    public void hideLoadingScreen() {
        runOnUiThread(() -> mLoadingScreen.hide());
    }

    public void setPauseState(boolean pause) {
        runOnUiThread(() -> {
            if (pause) {
                mDialog.hideWithoutReset();
                mAttachEdit.hideWithoutReset();
            } else {
                if (mDialog.isShow) mDialog.showWithOldContent();
                if (mAttachEdit.isShow) mAttachEdit.showWithoutReset();
            }
        });
    }

    public void exitGame() {
        finishAndRemoveTask();
        System.exit(0);
    }
}
