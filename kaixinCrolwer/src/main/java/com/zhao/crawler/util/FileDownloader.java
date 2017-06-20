package com.zhao.crawler.util;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;

public class FileDownloader {
    private WebDriver driver;
    //    private String localDownloadPath = System.getProperty("java.io.tmpdir");
    private String localDownloadPath = "d:\\素材\\pictures\\downloads\\";
    private boolean followRedirects = true;
    private boolean mimicWebDriverCookieState = true;
    private int httpStatusOfLastDownloadAttempt = 0;

    public FileDownloader(WebDriver driverObject) {
        this.driver = driverObject;
    }

    /**
     * Specify if the FileDownloader class should follow redirects when trying to download a file
     *
     * @param value
     */
    public void followRedirectsWhenDownloading(boolean value) {
        this.followRedirects = value;
    }

    /**
     * Get the current location that files will be downloaded to.
     *
     * @return The filepath that the file will be downloaded to.
     */
    public String localDownloadPath() {
        return this.localDownloadPath;
    }

    /**
     * Set the path that files will be downloaded to.
     *
     * @param filePath The filepath that the file will be downloaded to.
     */
    public void localDownloadPath(String filePath) {
        this.localDownloadPath = filePath;
    }

    /**
     * Download the file specified in the href attribute of a WebElement
     *
     * @param element
     * @return
     * @throws Exception
     */
    public String downloadFile(WebElement element) throws Exception {
        return downloader(element, "href");
    }

    /**
     * Download the image specified in the src attribute of a WebElement
     *
     * @param element
     * @return
     * @throws Exception
     */
    public String downloadImage(WebElement element) throws Exception {
        return downloader(element, "src");
    }

    /**
     * Gets the HTTP status code of the last download file attempt
     *
     * @return
     */
    public int getHTTPStatusOfLastDownloadAttempt() {
        return this.httpStatusOfLastDownloadAttempt;
    }

    /**
     * Mimic the cookie state of WebDriver (Defaults to true)
     * This will enable you to access files that are only available when logged in.
     * If set to false the connection will be made as an anonymouse user
     *
     * @param value
     */
    public void mimicWebDriverCookieState(boolean value) {
        this.mimicWebDriverCookieState = value;
    }

    /**
     * Load in all the cookies WebDriver currently knows about so that we can mimic the browser cookie state
     *
     * @param seleniumCookieSet
     * @return
     */
    private BasicCookieStore mimicCookieState(Set<Cookie> seleniumCookieSet) {
        BasicCookieStore mimicWebDriverCookieStore = new BasicCookieStore();
        for (Cookie seleniumCookie : seleniumCookieSet) {
            BasicClientCookie duplicateCookie = new BasicClientCookie(seleniumCookie.getName(), seleniumCookie.getValue());
            duplicateCookie.setDomain(seleniumCookie.getDomain());
            duplicateCookie.setSecure(seleniumCookie.isSecure());
            duplicateCookie.setExpiryDate(seleniumCookie.getExpiry());
            duplicateCookie.setPath(seleniumCookie.getPath());
            mimicWebDriverCookieStore.addCookie(duplicateCookie);
        }

        return mimicWebDriverCookieStore;
    }

    /**
     * Perform the file/image download.
     *
     * @param element
     * @param attribute
     * @return
     * @throws IOException
     * @throws NullPointerException
     */
    private String downloader(WebElement element, String attribute) throws IOException, NullPointerException, URISyntaxException {
        String fileToDownloadLocation = element.getAttribute(attribute);
        System.out.println("attribute: "+fileToDownloadLocation);
        if (fileToDownloadLocation.trim().equals("")) throw new NullPointerException("The element you have specified does not link to anything!");

        URL fileToDownload = new URL(fileToDownloadLocation);
        System.out.println("file: "+fileToDownload.getFile());
        File downloadedFile = new File(this.localDownloadPath + fileToDownload.getFile().replaceFirst("(/|\\\\).*(/|\\\\)", ""));
        if (downloadedFile.canWrite() == false) downloadedFile.setWritable(true);

        HttpClient client = new DefaultHttpClient();
        BasicHttpContext localContext = new BasicHttpContext();

        System.out.println("Mimic WebDriver cookie state: " + this.mimicWebDriverCookieState);
        if (this.mimicWebDriverCookieState) {
            localContext.setAttribute(ClientContext.COOKIE_STORE, mimicCookieState(this.driver.manage().getCookies()));
        }

        HttpGet httpget = new HttpGet(fileToDownload.toURI());
        HttpParams httpRequestParameters = httpget.getParams();
        httpRequestParameters.setParameter(ClientPNames.HANDLE_REDIRECTS, this.followRedirects);
        httpget.setParams(httpRequestParameters);

        System.out.println("Sending GET request for: " + httpget.getURI());
        HttpResponse response = client.execute(httpget, localContext);
        this.httpStatusOfLastDownloadAttempt = response.getStatusLine().getStatusCode();
        System.out.println("HTTP GET request status: " + this.httpStatusOfLastDownloadAttempt);
        if(this.httpStatusOfLastDownloadAttempt==200){
            System.out.println("Downloading file: " + downloadedFile.getName());
            FileUtils.copyInputStreamToFile(response.getEntity().getContent(), downloadedFile);
            response.getEntity().getContent().close();

            String downloadedFileAbsolutePath = downloadedFile.getAbsolutePath();
            System.out.println("File downloaded to '" + downloadedFileAbsolutePath + "'");

            return downloadedFileAbsolutePath;
        }
        return "";

    }

    public void testDownloadImages(String baseUrl) throws Exception{
//		BaiduImgDownload BaiduDownloadPics=new BaiduImgDownload();

        WebDriverWait wait=new WebDriverWait(driver,10);
        String testProject="scmtest";
        String testPlan="DownloadPicsfromBaidu";
        String testcase="download pictures from Baidu website";
        String build="DownloadPicsfromBaidu";
        String notes=null;
        String result=null;
        try{
//			driver.manage().window().maximize();
            FileDownloader fileDownloader=new FileDownloader(driver);
            driver.get(baseUrl +"search/detail?ct=503316480&z=0&ipn=false&word=头像 可爱&pn=8&spn=0&di=48693053420&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=&ie=utf-8&oe=utf-8&in=3354&cl=2&lm=-1%2C&st=&cs=2390703471%2C168602444&os=2076211555%2C1322829468&adpicid=0&ln=2000&fr=ala&fmq=1378374347070_R&ic=0&s=0%2C&se=&sme=&tab=&face=&ist=&jit=&statnum=head&cg=head&bdtype=0&objurl=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201403%2F14%2F20140314121845_8iTiY.thumb.700_0.jpeg&fromurl=http%3A%2F%2Fwww.duitang.com%2Fpeople%2Fmblog%2F204985052%2Fdetail%2F&gsm=0");
            WebElement image=driver.findElement(By.cssSelector("div.img-wrapper>img"));
            int count=1;
            while(count<6){
               // boolean isVisible=this.IsImageVisible(driver, image);
                if(true){
                    String imgAbsoluteLocat=fileDownloader.downloadImage(image);
                    if(imgAbsoluteLocat!=""){
                        System.out.println("----"+count+" picture is available");

                    }else{
                        System.out.println("----"+count+" picture is unavailable");
                    }

                    driver.findElement(By.cssSelector("span.img-next > span.img-switch-btn")).click();
                    driver.switchTo().defaultContent();
                    System.out.println("----navigate to the next img");
                }
                count++;
            }
//				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4[title='品牌']")));
//			assertNotNull("login name not found",driver.findElement(By.xpath("//h1[contains(text(),'Free Photos about valentine')]")));
//			driver.findElement(By.xpath("//h1[contains(text(),'Free Photos about valentine')]")).click();	

//				result=TestLinkAPIResults.TEST_PASSED;
//				notes="Automated Executed successfully by java at "+dateformat.format(new Date());
//				System.out.println("-success--");
            System.out.println("---bottom--");

        }catch(Exception e){
            System.out.println("=fail 2==\n"+e.getMessage());

        }catch(Error e){
            System.out.println("Error:"+e.getMessage());
        }finally{
            System.out.println("=00==");
//			automateLogin163.reportResult(testProject, testPlan, testcase, build, notes, result);
//			System.out.println("*have updated testlink testcase status**");
        }
    }
}
