package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import model.entities.Sale;

public class Program {
	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		List<Sale> sales = new ArrayList<>();

		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.nextLine();

		try (BufferedReader in = new BufferedReader(new FileReader(path))) {
			while (in.ready()) {
				String line = in.readLine();
				String[] vect = line.split(",");
				int month = Integer.parseInt(vect[0]);
				int year = Integer.parseInt(vect[1]);
				String seller = vect[2];
				int items = Integer.parseInt(vect[3]);
				double total = Double.parseDouble(vect[4]);
				sales.add(new Sale(month, year, seller, items, total));
			}
			
			System.out.println();
			
			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio");
			Comparator<Sale> comp = (x, y) -> x.averagePrice().compareTo(y.averagePrice());
			sales.stream().filter(s -> s.getYear() == 2016).sorted(comp.reversed()).limit(5)
					.forEach(System.out::println);
			
			System.out.println();
			
			double sum = sales.stream().filter(
					s -> (s.getMonth() == 1 || s.getMonth() == 7) && s.getSeller().toUpperCase().equals("LOGAN"))
					.map(s -> s.getTotal()).reduce(0.0, (x, y) -> x + y);
			
			System.out.printf("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = %.2f", sum);
		} catch (FileNotFoundException e) {
			System.out.printf("Erro: %s", e.getMessage());
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			sc.close();
		}
	}
}
