package stepsFiles;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.*;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Login {

    WebDriver driver;
    @Before
    public void setup(){

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\alexandru.fodor.DESKOVER\\cucumberframework\\src\\test\\java\\resources\\chromedriver.exe");
        this.driver = new ChromeDriver();
        this.driver.manage().window().maximize();
        this.driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        this.driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() throws InterruptedException{

//        try
//        {
//            Thread.sleep(3000);
//        }
//        catch (InterruptedException e)
//        {
//            Thread.currentThread().interrupt(); // restore interrupted status
//        }
        this.driver.manage().deleteAllCookies();
        this.driver.quit();
        this.driver = null;
    }

    @Given("^User navigates to \"([^\"]+)\"$")
    public void user_navigates_to_https_www_bbc_co_uk(String url) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        driver.get(url);
//        throw new PendingException();
    }

    @Given("^User clicks on the login button on homepage$")
    public void user_clicks_on_the_login_button_on_homepage() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Thread.sleep(3000);
        WebElement loginButton = driver.findElement(By.id("idcta-username"));

        loginButton.click();

    }

    @Given("^user enters a valid username \"([^\"]+)\"$")
    public void user_enters_a_valid_user_name(String username) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        WebElement usernameField = driver.findElement(By.name("username"));
        usernameField.sendKeys(username);
    }

    @Given("^user enters a valid password \"([^\"]+)\"$")
    public void user_enters_a_valid_password(String password) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        WebElement passowrdField = driver.findElement(By.name("password"));
        passowrdField.sendKeys(password);
    }

    @When("^User clicks on the sign in button$")
    public void user_clicks_on_the_login_button() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        WebElement signInButton = driver.findElement(By.id("submit-button"));

        signInButton.click();
        System.out.println("Clicked Sign in");
    }


    @Then("^User should be taken to the succesfull login page$")
    public void user_should_be_taken_to_the_succesfull_login_page() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Thread.sleep(3000);

        WebElement userPanel = driver.findElement(By.id("idcta-username"));
        String userPanelText = userPanel.getText();
        Assert.assertEquals("Your account", userPanelText);
    }

    @And("^I navigate to TV guide page$")
    public void navigateToTVGuidePage() throws Throwable{
        WebElement moreButton = driver.findElement(By.cssSelector("a[data-alt='More']"));
        moreButton.click();
        Thread.sleep(3000);
        WebElement tvButton = driver.findElement(By.cssSelector("div [id='orb-panels'] a[href='/tv/']"));
        tvButton.click();
        Thread.sleep(3000);

        WebElement element = driver.findElement(By.cssSelector("a[href='#ro']"));

        Boolean isPresent = driver.findElements(By.cssSelector("a[href='#ro']")).size() > 0;
        Assert.assertEquals(true, isPresent);
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
        Thread.sleep(3000);
        driver.findElement(By.cssSelector("a[href='http://www.bbcchannels.com/bbc-europe']")).click();
        Thread.sleep(5000);

        //get nr of tabs and switch to second tab
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));


        System.out.println("\nClicking tv guide");
        Boolean tvGuidePresent = driver.findElements(By.linkText("TV guide")).size() > 0;
        Assert.assertEquals(true, tvGuidePresent);

        driver.findElement(By.cssSelector("[class='main'] a[href='/bbc-europe/tv-guide/']")).click();
        Thread.sleep(3000);


    }

    @And("^User searches for the show \"([^\"]+)\"$")
    public void searchShow(String show) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        WebElement showSearch = driver.findElement(By.cssSelector("a[href='#schedule-search'"));
        showSearch.click();
        Thread.sleep(3000);

        WebElement input = driver.findElement(By.cssSelector("input[type='text']"));
        input.sendKeys(show);
        Thread.sleep(3000);

        String foundShow = driver.findElement(By.cssSelector("[class^='input-container'] a[href]:nth-of-type(1)")).getText();
        Assert.assertEquals(show, foundShow);
    }

    @And("^User checks when the new episode is up$")
    public void nextEpisode() throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        driver.findElement(By.cssSelector("[class^='input-container'] a[href]:nth-of-type(1)")).click();
        Thread.sleep(3000);
        String day = driver.findElement(By.cssSelector("[class=\"results-container\"] [class=\" important\"]")).getText();
        String time = driver.findElement(By.cssSelector("[class=\"result-set\"]:nth-of-type(1) [class=\"result-item\"]:nth-of-type(1) [itemprop=\"startDate\"]")).getText();
        System.out.println("\nDay the next episode will run is: " + day + " at " + time + ".");
    }

}
