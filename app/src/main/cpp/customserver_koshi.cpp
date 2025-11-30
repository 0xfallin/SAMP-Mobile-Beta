#include <jni.h>
#include "customserver_koshi.h"
#include "net/netgame.h"
#include "game/game.h"
#include <cstdlib>
#include <string.h>

extern CNetGame *pNetGame;
extern CGame *pGame;

// Input buffers from native code
extern char szNickInputBuffer[100];
extern char szIPInputBuffer[100];
extern char szPortInputBuffer[100];
extern char szPasswordInputBuffer[100];

extern "C" {

// Set nickname
JNIEXPORT void JNICALL
Java_ro_samp_mobile_SAMPLauncher_setNick(JNIEnv *env, jobject thiz, jstring nick) {
    const char *str = env->GetStringUTFChars(nick, nullptr);
    strncpy(szNickInputBuffer, str, sizeof(szNickInputBuffer));
    env->ReleaseStringUTFChars(nick, str);
}

// Set server IP
JNIEXPORT void JNICALL
Java_ro_samp_mobile_SAMPLauncher_setServerIP(JNIEnv *env, jobject thiz, jstring ip) {
    const char *str = env->GetStringUTFChars(ip, nullptr);
    strncpy(szIPInputBuffer, str, sizeof(szIPInputBuffer));
    env->ReleaseStringUTFChars(ip, str);
}

// Set port
JNIEXPORT void JNICALL
Java_ro_samp_mobile_SAMPLauncher_setPort(JNIEnv *env, jobject thiz, jint port) {
    snprintf(szPortInputBuffer, sizeof(szPortInputBuffer), "%d", port);
}

// Set password
JNIEXPORT void JNICALL
Java_ro_samp_mobile_SAMPLauncher_setPassword(JNIEnv *env, jobject thiz, jstring pass) {
    const char *str = env->GetStringUTFChars(pass, nullptr);
    strncpy(szPasswordInputBuffer, str, sizeof(szPasswordInputBuffer));
    env->ReleaseStringUTFChars(pass, str);
}

// Connect to server
JNIEXPORT void JNICALL
Java_ro_samp_mobile_SAMPLauncher_connect(JNIEnv *env, jobject thiz) {
    char *nick = szNickInputBuffer;
    char *address = szIPInputBuffer;
    int port = atoi(szPortInputBuffer);
    char *pass = szPasswordInputBuffer;

    if(pNetGame) pNetGame->ShutDownForGameRestart();
    pNetGame = new CNetGame(address, port, nick, pass);

    if(pGame) pGame->FindPlayerPed()->TogglePlayerControllable(true);
}
}
