package io.sited.test.impl;

import io.sited.http.Client;

import java.util.Locale;
import java.util.UUID;

public class MockClient implements Client {
    private static final MockClient INSTANCE = new MockClient();

    private final String id = UUID.randomUUID().toString();
    private String ip = "127.0.0.1";
    private String country = Locale.getDefault().getCountry();
    private String city;
    private String os;
    private String osVersion;
    private String browser;
    private String browserVersion;
    private String userAgent;

    public static MockClient get() {
        return INSTANCE;
    }

    @Override
    public String id() {
        return id;
    }


    @Override
    public String ip() {
        return ip;
    }

    public MockClient setIP(String ip) {
        this.ip = ip;
        return this;
    }

    @Override
    public String country() {
        return country;
    }

    public MockClient setCountry(String country) {
        this.country = country;
        return this;
    }

    @Override
    public String city() {
        return city;
    }

    public MockClient setCity(String city) {
        this.city = city;
        return this;
    }

    @Override
    public String os() {
        return os;
    }

    public MockClient setOS(String os) {
        this.os = os;
        return this;
    }

    @Override
    public String osVersion() {
        return osVersion;
    }

    public MockClient setOSVersion(String osVersion) {
        this.osVersion = osVersion;
        return this;
    }

    @Override
    public String browser() {
        return browser;
    }

    public MockClient setBrowser(String browser) {
        this.browser = browser;
        return this;
    }

    @Override
    public String browserVersion() {
        return browserVersion;
    }

    public MockClient setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
        return this;
    }

    @Override
    public String userAgent() {
        return userAgent;
    }

    public MockClient setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }
}