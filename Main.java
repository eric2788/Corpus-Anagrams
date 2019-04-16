import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		List<String> stringList = new ArrayList<>();
		System.out.println("type string until type 0");
		String line = "";
		while(!line.equals("0")) {
			line = scanner.nextLine();
			try {
				Integer.parseInt(line);
				System.out.println("you can't type a number!!");
				continue;
			}catch(NumberFormatException ignore) {
			}
			stringList.add(line);
			System.out.println("you inserted "+line);
		}
		
		stringList.forEach(System.out::println);
		System.out.println("The result after analyse:");
		anagram(stringList).forEach(System.out::println);
		
	}
	
	private static List<String> anagram(List<String> line){
		LinkedList<String> results = new LinkedList<>();
		for(String l : line){
			char[] ch = l.toCharArray();
			boolean equal = false, contains = false;
			for(int i = 0; i < results.size(); i++) {
				String liner = results.get(i);
				char[] ch2 = liner.contains(",") ? liner.split(",")[0].toCharArray() : liner.toCharArray();
				equal = equalCharArray(ch,ch2);
				contains = results.get(i).contains(l);
				if(equal) {
					String original = results.get(i);
					results.remove(i);
					results.add(original+", "+l);
					break;
				}
			}
			if(!equal && !contains) results.add(l);
		}
		return results;
	}
	
	private static List<Character> toList(char[] c){
		List<Character> chars = new ArrayList<>();
		for (int i = 0; i < c.length; i++) {
			chars.add(c[i]);
		}
		return chars;
	}
	
	private static boolean equalCharArray(char[] ch, char[] ch2) {
		List<Character> chlist = toList(ch);
		List<Character> chlist2 = toList(ch2);
		Collections.sort(chlist);
		Collections.sort(chlist2);
		return chlist.equals(chlist2);
	}
}
