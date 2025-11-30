#pragma once

#include <jni.h>

class CCustomServer
{
public:
    CCustomServer();
    ~CCustomServer();

    // Core functions
    void Render();
    void Clear();
    void Show(bool bShow);

    // Setters from C++ side (optional)
    void SetNick(const char* nick);
    void SetIP(const char* ip);
    void SetPort(int port);
    void SetPassword(const char* pass);

    // JNI Integration
    static void RegisterJNI(JNIEnv* env);

public:
    bool m_bIsActive;

    // Connection info
    char* address;
    char* nick;
    char* pass;
    int port;

    // Flags
    bool m_bFixer;
    bool m_bFixer2;
    bool m_bLamb;

    // Input buffers
    static char szNickInputBuffer[100];
    static char utf8NickInputBuffer[100*3];

    static char szPasswordInputBuffer[100];
    static char utf8PasswordInputBuffer[100*3];

    static char szIPInputBuffer[100];
    static char utf8IPInputBuffer[100*3];

    static char szPortInputBuffer[100];
    static char utf8PortInputBuffer[100*3];
};

// ------------------------
// JNI function declarations
// ------------------------
extern "C" {
    JNIEXPORT void JNICALL Java_ro_samp_mobile_SAMPLauncher_setNick(JNIEnv* env, jobject thiz, jstring nick);
    JNIEXPORT void JNICALL Java_ro_samp_mobile_SAMPLauncher_setIP(JNIEnv* env, jobject thiz, jstring ip);
    JNIEXPORT void JNICALL Java_ro_samp_mobile_SAMPLauncher_setPort(JNIEnv* env, jobject thiz, jint port);
    JNIEXPORT void JNICALL Java_ro_samp_mobile_SAMPLauncher_setPassword(JNIEnv* env, jobject thiz, jstring pass);
}
