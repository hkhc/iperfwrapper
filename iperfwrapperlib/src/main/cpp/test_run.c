//
// Created by Herman Cheung on 6/3/2017.
//

#include <jni.h>
#include <stdio.h>
#include <sys/select.h>
#include <iperf_api.h>
#include <android/log.h>
#include <iperf.h>

JNIEXPORT jlong JNICALL
Java_hkhc_iperfwrapper_Iperf3_newTestImpl( JNIEnv* env, jobject thiz )
{

    struct iperf_test *test;
    test = iperf_new_test();
    __android_log_print(ANDROID_LOG_DEBUG, "IPERF", "new test %lld", (long long)test);

    return (jlong)test;

}

JNIEXPORT void JNICALL
Java_hkhc_iperfwrapper_Iperf3_freeTestImpl( JNIEnv* env, jobject thiz, jlong ref)
{

    struct iperf_test *test;
    test = (struct iperf_test *)ref;

    iperf_free_test(test);

}

JNIEXPORT void JNICALL
Java_hkhc_iperfwrapper_Iperf3_testRoleImpl( JNIEnv* env, jobject thiz, jlong ref, jchar role)
{

    struct iperf_test *test;
    test = (struct iperf_test *)ref;

    __android_log_print(ANDROID_LOG_DEBUG, "IPERF", "set role %c %lld", (char)role, 'c');

    iperf_set_test_role(test, (char)role );

}

JNIEXPORT void JNICALL
Java_hkhc_iperfwrapper_Iperf3_defaultsImpl( JNIEnv* env, jobject thiz, jlong ref)
{

    struct iperf_test *test;
    test = (struct iperf_test *)ref;

    iperf_defaults(test);

}

JNIEXPORT void JNICALL
Java_hkhc_iperfwrapper_Iperf3_hostnameImpl( JNIEnv* env, jobject thiz, jlong ref, jstring j_hostname)
{

    struct iperf_test *test;
    test = (struct iperf_test *)ref;

    const char *hostname = (*env)->GetStringUTFChars(env, j_hostname, 0);

    iperf_set_test_server_hostname(test, hostname);

    (*env)->ReleaseStringUTFChars(env, j_hostname, hostname);


}

JNIEXPORT void JNICALL
Java_hkhc_iperfwrapper_Iperf3_tempFileTemplateImpl( JNIEnv* env, jobject thiz, jlong ref, jstring j_template)
{

    struct iperf_test *test;
    test = (struct iperf_test *)ref;

    const char *template = (*env)->GetStringUTFChars(env, j_template, 0);

    iperf_set_test_template(test, template);

    (*env)->ReleaseStringUTFChars(env, j_template, template);


}

JNIEXPORT void JNICALL
Java_hkhc_iperfwrapper_Iperf3_durationImpl( JNIEnv* env, jobject thiz, jlong ref, jint duration)
{

    struct iperf_test *test;
    test = (struct iperf_test *)ref;

    iperf_set_test_duration(test, duration);


}

JNIEXPORT void JNICALL
Java_hkhc_iperfwrapper_Iperf3_runClientImpl( JNIEnv* env, jobject thiz, jlong ref)
{

    struct iperf_test *test;
    test = (struct iperf_test *)ref;

    test->outfile = fopen(test->logfile, "a+");
    iperf_run_client(test);

}

JNIEXPORT void JNICALL
Java_hkhc_iperfwrapper_Iperf3_outputJsonImpl( JNIEnv* env, jobject thiz, jlong ref, jboolean useJson)
{

    struct iperf_test *test;
    test = (struct iperf_test *)ref;

    iperf_set_test_json_output(ref, (int)useJson);

}

/*
 * Command line options
 */


// OPT_LOG
JNIEXPORT void JNICALL
Java_hkhc_iperfwrapper_Iperf3_logFileImpl( JNIEnv* env, jobject thiz, jlong ref, jstring j_logfile)
{

    struct iperf_test *test;
    test = (struct iperf_test *)ref;

    const char *logfile = (*env)->GetStringUTFChars(env, j_logfile, 0);

    test->logfile = strdup(logfile);

    (*env)->ReleaseStringUTFChars(env, j_logfile, logfile);


}

