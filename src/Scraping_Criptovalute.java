

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


public class Scraping_Criptovalute {
	
	public static WebDriver MakeChromedriver(){
		URL path1 = Scraping_Criptovalute.class.getResource("chromedriver.exe");
		File file = new File(path1.getFile());
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		WebDriver driver = new ChromeDriver();
		return driver;
	}	
	
	public static String NameForCsv (){
		Date oggi = new Date();
		String name="Criptovalute_"+oggi.getDate()+oggi.getMonth()+oggi.getYear()+"_"+oggi.getHours()+oggi.getMinutes()+".csv";
		return name;
	}
	
	public static void main( String[] args) throws InterruptedException, IOException{
		String link = "https://criptovalute.io/"; 
		WebDriver driver = MakeChromedriver();
		driver.manage().window().setSize(new Dimension(900,700)); //ALLARGO FINESTRA PER SELEZIONE NAV
		String csvfile=NameForCsv();
		BufferedWriter writer = new BufferedWriter(new FileWriter(csvfile));
		try {
			driver.get(link);
			Thread.sleep(3000);
			String nome = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/div/div[2]/div[2]/div[1]/div[1]/b/a")).getText();
			String prezzo = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/div/div[2]/div[2]/div[1]/div[2]")).getText();
			Vector<String> cripto = new Vector<String>();
			String nomi_col="Nome,Prezzo USD, Cambio 24H \n";
			writer.write(nomi_col);
			for (int i=2; i<102; i++) {
				String nome_c = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/div/div["+i+"]/div[2]/div[1]/div[1]/b/a")).getText();
				String prezzo_c = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/div/div["+i+"]/div[2]/div[1]/div[2]")).getText();
				String cambio_c = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/div/div["+i+"]/div[2]/div[1]/div[3]/div")).getText();
				String crip= nome_c + ","+ prezzo_c +","+cambio_c+"\n";
				writer.write(nome_c + ","+ prezzo_c +","+cambio_c+"\n");	
				cripto.add(crip);
			}
			writer.close();
			System.out.println(cripto);
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.close();
		}
	}







}
