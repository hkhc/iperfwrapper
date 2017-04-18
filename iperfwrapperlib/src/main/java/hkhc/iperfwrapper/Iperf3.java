package hkhc.iperfwrapper;

/**
 * Created by herman on 6/3/2017.
 */

public class Iperf3 {

    static {
        System.loadLibrary("jni");
    }

    public static final char ROLE_SERVER = 's';
    public static final char ROLE_CLIENT = 'c';

    private long testRef = 0;

    private native long newTestImpl();
    private native void freeTestImpl(long ref);
    private native void testRoleImpl(long ref, char role);
    private native void defaultsImpl(long ref);
    private native void hostnameImpl(long ref, String host);
    private native void tempFileTemplateImpl(long ref, String template);
    private native void durationImpl(long ref, int duration);
    private native void logFileImpl(long ref, String logfile);
    private native void runClientImpl(long ref);
    private native void outputJsonImpl(long ref, boolean useJson);

    public Iperf3 newTest() throws IperfException {
        if (testRef!=0) {
            freeTest();
        }
        testRef = newTestImpl();
        if (testRef==0) {
            throw new IperfException("Failed to initialize test");
        }
        return this;
    }

    public void freeTest() {
        if (testRef!=0) {
            freeTestImpl(testRef);
            testRef=0;
        }
    }

    public Iperf3 testRole(char role) {
        testRoleImpl(testRef, role);
        return this;
    }

    public Iperf3 defaults() {
        defaultsImpl(testRef);
        return this;
    }

    public Iperf3 defaults(DefaultConfig config) {
        defaultsImpl(testRef);
        config.defaults(this);
        return this;
    }

    public Iperf3 hostname(String host) {
        hostnameImpl(testRef, host);
        return this;
    }

    public Iperf3 tempFileTemplate(String template) {
        tempFileTemplateImpl(testRef, template);
        return this;
    }

    public Iperf3 durationInSeconds(int duration) {
        durationImpl(testRef, duration);
        return this;
    }

    public Iperf3 logfile(String file) {
        logFileImpl(testRef, file);
        return this;
    }

    public Iperf3 runClient() {
        runClientImpl(testRef);
        return this;
    }

    public Iperf3 outputJson(boolean useJson) {
        outputJsonImpl(testRef, useJson);
        return this;
    }

}
