

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class Scraping_Dati_Meteo {

	public static WebDriver MakeChromedriver(){
		URL path1 = Scraping_Dati_Meteo.class.getResource("chromedriver.exe");
		File file = new File(path1.getFile());
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		WebDriver driver = new ChromeDriver();
		return driver;
	}	

	public static String NameForCsv (){
		Date oggi = new Date();
		String name="DatiMeteo_"+oggi.getDate()+oggi.getMonth()+oggi.getYear()+"_"+oggi.getHours()+oggi.getMinutes()+".csv";
		return name;
	}

	public static void main( String[] args) throws InterruptedException, IOException{
		String link = "https://www.timeanddate.com/weather/"; 
		WebDriver driver = MakeChromedriver();
		driver.manage().window().setSize(new Dimension(900,700)); //ALLARGO FINESTRA PER SELEZIONE NAV
		String csvfile=NameForCsv();
		BufferedWriter writer = new BufferedWriter(new FileWriter(csvfile));
		try {
			driver.get(link);
			Thread.sleep(3000);
			driver.findElement(By.xpath("/html/body/div[8]/div[2]/div[1]/div[2]/div[2]/button[1]/p")).click();
			Thread.sleep(2000);
			Vector<String> tuples = new Vector<String>();
			String nomi_col="Città,Clima,Temperatura \n";
			writer.write(nomi_col);

			//1 colonna
			for (int i=2; i<48; i++) {
				String city = driver.findElement(By.xpath("/html/body/div[6]/section[1]/div/section/div[1]/div/table/tbody/tr["+i+"]/td[1]/a")).getText();
				String we = driver.findElement(By.xpath("/html/body/div[6]/section[1]/div/section/div[1]/div/table/tbody/tr["+i+"]/td[3]/img")).getAttribute("title");
				String temperature = driver.findElement(By.xpath("/html/body/div[6]/section[1]/div/section/div[1]/div/table/tbody/tr["+i+"]/td[4]")).getText();
				String tupla= city+","+ we +","+temperature+"\n";
				writer.write(tupla);	
				tuples.add(tupla);
			}

			//2 colonna 
			for (int i=2; i<48; i++) {
				String city = driver.findElement(By.xpath("/html/body/div[6]/section[1]/div/section/div[1]/div/table/tbody/tr["+i+"]/td[5]/a")).getText();
				String we = driver.findElement(By.xpath("/html/body/div[6]/section[1]/div/section/div[1]/div/table/tbody/tr["+i+"]/td[7]/img")).getAttribute("title");
				String temperature = driver.findElement(By.xpath("/html/body/div[6]/section[1]/div/section/div[1]/div/table/tbody/tr["+i+"]/td[8]")).getText();
				String tupla= city+","+ we +","+temperature+"\n";
				writer.write(tupla);	
				tuples.add(tupla);
			}

			//3 colonna
			for (int i=2; i<48; i++) {
				String city = driver.findElement(By.xpath("/html/body/div[6]/section[1]/div/section/div[1]/div/table/tbody/tr["+i+"]/td[9]/a")).getText();
				String we = driver.findElement(By.xpath("/html/body/div[6]/section[1]/div/section/div[1]/div/table/tbody/tr["+i+"]/td[11]/img")).getAttribute("title");
				String temperature = driver.findElement(By.xpath("/html/body/div[6]/section[1]/div/section/div[1]/div/table/tbody/tr["+i+"]/td[12]")).getText();
				String tupla= city+","+ we +","+temperature+"\n";
				writer.write(tupla);	
				tuples.add(tupla);
			}

			writer.close();
			System.out.println(tuples);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.close();
		}
	}







}
