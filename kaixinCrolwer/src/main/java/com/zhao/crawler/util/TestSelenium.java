package com.zhao.crawler.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ArrayList;

/**
 * @author GinPonson
 */
public class TestSelenium {

    static final String HOST = "127.0.0.1";
    static final String PORT = "80";
    static final String USER = "gin";
    static final String PWD = "12345";

    public static void main(String[] args){
        System.setProperty("phantomjs.binary.path", "./phantomjs.exe");
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();

        //设置代理或者其他参数
        ArrayList<String> cliArgsCap = new ArrayList<>();
        //cliArgsCap.add("--proxy=http://"+HOST+":"+PORT);
        //cliArgsCap.add("--proxy-auth=" + USER + ":" + PWD);
        //cliArgsCap.add("--proxy-type=http");
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
        //capabilities.setCapability("phantomjs.page.settings.userAgent", "");

        WebDriver driver = new PhantomJSDriver(capabilities);
        driver.get("http://kaixin65.com/member.php?mod=logging&action=login&infloat=yes&handlekey=login&inajax=1&ajaxtarget=fwin_content_logi");
        System.out.println(driver.getPageSource());
        driver.quit();
    }
}