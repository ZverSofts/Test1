package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class Main {


    public static int findFirstDifferenceIndex(String str1, String str2) {
        int minLength = Math.min(str1.length(), str2.length());
        int diffIndex = -1;

        for (int i = 0; i < minLength; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                diffIndex = i;
                break;
            }
        }

        if (diffIndex == -1 && str1.length() != str2.length()) {
            diffIndex = minLength;
        }

        return diffIndex;
    }

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();


        WebElement[] OutputElements = new WebElement[4];
        WebElement[] inputFields = new WebElement[4];
        String[] InputElements = new String[4];

        InputElements[0] = "Иванов Иван Иванович";
        InputElements[1] = "IvanovIvanov@gmail.com";
        InputElements[2] = "г. Москва, ул. Пушкина, д. 8";
        InputElements[3] = "г. Санкт-Петербург, ул. Жукова, д. 123";


        driver.get("https://demoqa.com");

        WebElement Element1 = driver.findElement(By.xpath("//div[@class='card mt-4 top-card']"));
        Element1.click();

        WebElement Element2 = driver.findElement(By.xpath("//li[@id='item-0']"));
        Element2.click();


        inputFields[0] = driver.findElement(By.xpath("//input[@placeholder='Full Name']"));
        inputFields[1] = driver.findElement(By.xpath("//input[@placeholder='name@example.com']"));
        inputFields[2] = driver.findElement(By.xpath("//textarea[@placeholder='Current Address']"));
        inputFields[3] = driver.findElement(By.xpath("//textarea[@id='permanentAddress']"));
        WebElement ButtonSubmit = driver.findElement(By.xpath("//button[@id='submit']"));


        for (int i = 0; i < 4; i++) {
            inputFields[i].sendKeys(InputElements[i]);
        }

        ButtonSubmit.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

        OutputElements[0] = driver.findElement(By.xpath("//p[@id='name']"));
        OutputElements[1] = driver.findElement(By.xpath("//p[@id='email']"));
        OutputElements[2] = driver.findElement(By.xpath("//p[@id='currentAddress']"));
        OutputElements[3] = driver.findElement(By.xpath("//p[@id='permanentAddress']"));

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));


        for (int i = 0; i < 4; i++) {
            String outputTextContent = OutputElements[i].getAttribute("textContent");
            outputTextContent = outputTextContent.substring(outputTextContent.indexOf(":") + 1, outputTextContent.length());
            if (!outputTextContent.equals(InputElements[i])) {
                System.out.println("Строка: " + (i + 1) + " с деффектом!");
                int diffIndex = findFirstDifferenceIndex(outputTextContent, InputElements[i]);
                if (diffIndex != -1) {
                    System.out.println("Первый отличающийся символ на позиции: " + (diffIndex + 1));
                }
                System.out.println("Input(" + InputElements[i] + ") > Output(" + outputTextContent + ")");
            }


        }
    }
}