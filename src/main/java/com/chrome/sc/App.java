package com.chrome.sc;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class App 
{
    private static  WebDriver driver;
    private static TakesScreenshot screenshot;
    public static void main( String[] args )
    {
        System.setProperty("webdriver.chrome.driver", "/home/mrinal/Downloads/chromedriver_linux64/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=/home/mrinal/Downloads/profile");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        String url = getRecentUrl();
        driver.get(url);
        screenshot = (TakesScreenshot) driver;
        try {
            takeScreenShot(url);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void takeScreenShot(String url) throws Exception{
        File source = screenshot.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source,new File("/home/mrinal/Pictures/screenshot.png"));
    }

    public static String  getRecentUrl()
    {
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;

        try
        {
            Class.forName ("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:/home/mrinal/Downloads/profile/Default/History");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM urls ORDER BY  id DESC LIMIT 1");
            return resultSet.getString("url");
        }

        catch (Exception e)
        {
            e.printStackTrace ();
        }

        finally
        {
            try
            {
                resultSet.close ();
                statement.close ();
                connection.close ();
            }

            catch (Exception e)
            {
                e.printStackTrace ();
            }
        }
        return "";
    }
}


