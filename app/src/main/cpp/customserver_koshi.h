#pragma once

#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif

// Set nickname
JNIEXPORT void JNICALL
Java_ro_samp_mobile_SAMPLauncher_setNick(JNIEnv *env, jobject thiz, jstring nick);

// Set server IP
JNIEXPORT void JNICALL
Java_ro_samp_mobile_SAMPLauncher_setServerIP(JNIEnv *env, jobject thiz, jstring ip);

// Set port
JNIEXPORT void JNICALL
Java_ro_samp_mobile_SAMPLauncher_setPort(JNIEnv *env, jobject thiz, jint port);

// Set password
JNIEXPORT void JNICALL
Java_ro_samp_mobile_SAMPLauncher_setPassword(JNIEnv *env, jobject thiz, jstring pass);

// Connect to server
JNIEXPORT void JNICALL
Java_ro_samp_mobile_SAMPLauncher_connect(JNIEnv *env, jobject thiz);

#ifdef __cplusplus
}
#endif
