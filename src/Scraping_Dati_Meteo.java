

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

/*Il seguente progetto effettua scraping da un sito di dati meteo, 
 * in particolare preleva i dati dalla pagina principale, li stampa 
 * in console e li salva su un file csv.*/

public class Scraping_Dati_Meteo {
	
/* Il metodo makechromedriver preleva il file chromedriver.exe salvato 
 * nella stessa cartella della classe Java che stiamo utilizzando e crea 
 * un nuovo oggetto di tipo webdriver che restituisce in uscita. */

	public static WebDriver MakeChromedriver(){
		URL path1 = Scraping_Dati_Meteo.class.getResource("chromedriver.exe");
		File file = new File(path1.getFile());
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		WebDriver driver = new ChromeDriver();
		return driver;
	}	
	
/* Il metodo nameforcsv preleva la data attuale e la usa per nominare il file 
 * csv che verrà esportato*/

	public static String NameForCsv (){
		Date oggi = new Date();
		String name="DatiMeteo_"+oggi.getDate()+oggi.getMonth()+oggi.getYear()+"_"+oggi.getHours()+oggi.getMinutes()+".csv";
		return name;
	}


	public static void main( String[] args) throws InterruptedException, IOException{
		String link = "https://www.timeanddate.com/weather/"; 
		WebDriver driver = MakeChromedriver();
		driver.manage().window().setSize(new Dimension(900,700)); //Dimensiona la finestra aperta da chromedriver
		String csvfile=NameForCsv();
		BufferedWriter writer = new BufferedWriter(new FileWriter(csvfile)); 
		try {
			driver.get(link);
			Thread.sleep(3000); //attende qualche secondo per veder comparire la finestra dei cookie
			driver.findElement(By.xpath("/html/body/div[8]/div[2]/div[1]/div[2]/div[2]/button[1]/p")).click(); //Clicca accetto sulla finestra dei cookie
			Thread.sleep(2000);
			Vector<String> tuples = new Vector<String>(); //crea un vettore dove inserire le varie righe del dataset
			String nomi_col="Città,Clima,Temperatura \n"; //prima colonna del dataset
			writer.write(nomi_col);

			/*la tabella da cui preleviamo è divida in tre colonne che separano le informazioni che andremo a prelevare, ogni colonna ha 48 righe, sfrutteremo 
			  quindi un for che cicla tutte le righe per ogni colonna*/
			
			int lastrow=46; /*è capitato spesso che modificassero il numero delle righe nella tabella, quindi ho inserito questa variabile 
			                  per evitare di scriverlo più volte nei for */
			
			//1 colonna
			 System.out.println("Analizzando la 1 colonna...");
			for (int i=2; i<lastrow; i++) {
				String city = driver.findElement(By.xpath("/html/body/div[6]/section[1]/div/section/div[1]/div/table/tbody/tr["+i+"]/td[1]/a")).getText();
				String we = driver.findElement(By.xpath("/html/body/div[6]/section[1]/div/section/div[1]/div/table/tbody/tr["+i+"]/td[3]/img")).getAttribute("title");
				String temperature = driver.findElement(By.xpath("/html/body/div[6]/section[1]/div/section/div[1]/div/table/tbody/tr["+i+"]/td[4]")).getText();
				String tupla= city+","+ we +","+temperature+"\n";
				writer.write(tupla);	
				tuples.add(tupla);
			}
			System.out.println("Analizzando la 2 colonna...");
			//2 colonna 
			for (int i=2; i<lastrow; i++) {
				String city = driver.findElement(By.xpath("/html/body/div[6]/section[1]/div/section/div[1]/div/table/tbody/tr["+i+"]/td[5]/a")).getText();
				String we = driver.findElement(By.xpath("/html/body/div[6]/section[1]/div/section/div[1]/div/table/tbody/tr["+i+"]/td[7]/img")).getAttribute("title");
				String temperature = driver.findElement(By.xpath("/html/body/div[6]/section[1]/div/section/div[1]/div/table/tbody/tr["+i+"]/td[8]")).getText();
				String tupla= city+","+ we +","+temperature+"\n";
				writer.write(tupla);	
				tuples.add(tupla);
			}
			System.out.println("Analizzando la 3 colonna...");
			//3 colonna
			for (int i=2; i<lastrow; i++) {
				String city = driver.findElement(By.xpath("/html/body/div[6]/section[1]/div/section/div[1]/div/table/tbody/tr["+i+"]/td[9]/a")).getText();
				String we = driver.findElement(By.xpath("/html/body/div[6]/section[1]/div/section/div[1]/div/table/tbody/tr["+i+"]/td[11]/img")).getAttribute("title");
				String temperature = driver.findElement(By.xpath("/html/body/div[6]/section[1]/div/section/div[1]/div/table/tbody/tr["+i+"]/td[12]")).getText();
				String tupla= city+","+ we +","+temperature+"\n";
				writer.write(tupla);	
				tuples.add(tupla);
			}
			
			System.out.println("Realizzando il dataset...");
			Thread.sleep(2000);
			writer.close();
			System.out.println("Ecco i dati che ho raccolto...");
			System.out.println(tuples);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.close();
		}
	}







}
