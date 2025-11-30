#include <jni.h>
#include <dlfcn.h>
#include <android/log.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/stat.h>

#define LOG_TAG "native_loader"
#define ALOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define ALOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

// helper to append to samp_logs.txt in app files dir (path passed from java)
static void append_file_log(const char *filesDir, const char *msg) {
    if (!filesDir || !msg) return;
    char path[1024];
    snprintf(path, sizeof(path), "%s/%s", filesDir, "samp_logs.txt");

    FILE *f = fopen(path, "a");
    if (f) {
        fprintf(f, "%lld: %s\n", (long long)time(NULL), msg);
        fclose(f);
    } else {
        // try to log to logcat as fallback
        ALOGE("Failed to open log file: %s", path);
    }
}

JNIEXPORT jint JNICALL
Java_ro_samp_mobile_MainActivity_nativeLoadLibraries(JNIEnv *env, jobject thiz,
                                                    jstring jlib1, jstring jlib2, jstring jfilesDir) {
    const char *lib1 = (*env)->GetStringUTFChars(env, jlib1, NULL);
    const char *lib2 = (*env)->GetStringUTFChars(env, jlib2, NULL);
    const char *filesDir = jfilesDir ? (*env)->GetStringUTFChars(env, jfilesDir, NULL) : NULL;

    char msg[512];

    // try loading first lib
    void *h1 = dlopen(lib1 ? lib1 : "libGTASA.so", RTLD_NOW | RTLD_LOCAL);
    if (!h1) {
        const char *err = dlerror();
        snprintf(msg, sizeof(msg), "dlopen(%s) failed: %s", lib1 ? lib1 : "libGTASA.so", err ? err : "unknown");
        ALOGE("%s", msg);
        append_file_log(filesDir, msg);
        if (lib1) (*env)->ReleaseStringUTFChars(env, jlib1, lib1);
        if (lib2) (*env)->ReleaseStringUTFChars(env, jlib2, lib2);
        if (filesDir) (*env)->ReleaseStringUTFChars(env, jfilesDir, filesDir);
        return -1;
    } else {
        snprintf(msg, sizeof(msg), "dlopen(%s) SUCCESS", lib1 ? lib1 : "libGTASA.so");
        ALOGI("%s", msg);
        append_file_log(filesDir, msg);
    }

    // try loading second lib
    void *h2 = dlopen(lib2 ? lib2 : "libsamp.so", RTLD_NOW | RTLD_LOCAL);
    if (!h2) {
        const char *err = dlerror();
        snprintf(msg, sizeof(msg), "dlopen(%s) failed: %s", lib2 ? lib2 : "libsamp.so", err ? err : "unknown");
        ALOGE("%s", msg);
        append_file_log(filesDir, msg);
        // close h1 before returning
        if (h1) dlclose(h1);
        if (lib1) (*env)->ReleaseStringUTFChars(env, jlib1, lib1);
        if (lib2) (*env)->ReleaseStringUTFChars(env, jlib2, lib2);
        if (filesDir) (*env)->ReleaseStringUTFChars(env, jfilesDir, filesDir);
        return -2;
    } else {
        snprintf(msg, sizeof(msg), "dlopen(%s) SUCCESS", lib2 ? lib2 : "libsamp.so");
        ALOGI("%s", msg);
        append_file_log(filesDir, msg);
    }

    // Both libs loaded - normally you would look up symbols and call init functions here.
    // Keep handles open; cleanup left to OS on process exit (or add code to dlclose later).
    if (lib1) (*env)->ReleaseStringUTFChars(env, jlib1, lib1);
    if (lib2) (*env)->ReleaseStringUTFChars(env, jlib2, lib2);
    if (filesDir) (*env)->ReleaseStringUTFChars(env, jfilesDir, filesDir);
    return 0;
}