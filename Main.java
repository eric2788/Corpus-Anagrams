import java.io.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Main {
	public static void main(String[] args) throws IOException {
		LocalTime start = LocalTime.now();
		System.out.println("Please enter file name: ");
		String fileN = new Scanner(System.in).nextLine();
		File file = new File(fileN);
		if (!file.exists()) {
			System.out.println("File not exist !");
			return;
		}
		Set<String> stringlist = new TreeSet<>();
		FileReader fileReader = new FileReader(file);
		BufferedReader reader = new BufferedReader(fileReader);
		System.out.println("Processing.....");
		System.out.println("Adding words to Cache...");
		while (reader.ready()) {
			String[] str = reader.readLine().split(" ");
			for (String s : str) {
				if (!s.isBlank())
					stringlist.add(s.trim());
			}
		}
		reader.close();
		outputAlpha(stringlist);
		output(anagram(stringlist));
		LocalTime end = LocalTime.now();
		Duration dur = Duration.between(start, end);
		System.out.println("Process finished.");
		System.out.println("Used Time : " + dur.toMinutes() + " mins");
	}

	private static void outputAlpha(Set<String> list) throws IOException {
		File file = new File("alphabet.txt");
		if (!file.exists()) file.createNewFile();
		PrintWriter writer = new PrintWriter(new FileOutputStream(file));
		ConcurrentLinkedDeque<String> queue = new ConcurrentLinkedDeque<>(list);
		System.out.println("Outputting alphabet file.....");
		while (!queue.isEmpty()) {
			String line = queue.poll();
			writer.println(line);
		}
		writer.close();
	}


	private static void output(Set<String> list) throws IOException {
		File file = new File("output.txt");
		if (!file.exists()) file.createNewFile();
		PrintWriter writer = new PrintWriter(new FileOutputStream(file));
		ConcurrentLinkedDeque<String> queue = new ConcurrentLinkedDeque<>(list);
		System.out.println("Outputting file.....");
		while (!queue.isEmpty()) {
			String line = queue.poll();
			String[] main = line.split(", ");
			writer.println("Anagram words for " + main[0]);
			writer.println(line);
			writer.println("");
			writer.println("");
		}
		writer.close();
	}

	private static Set<String> anagram(Set<String> line) {
		Set<String> results = new TreeSet<>();
		System.out.println("Sorting the words......");
		for(String l : line){
			char[] ch = l.toCharArray();
			boolean equal = false, contains = false;
			for (String liner : results) {
				char[] ch2 = liner.contains(",") ? liner.split(",")[0].toCharArray() : liner.toCharArray();
				equal = equalCharArray(ch,ch2);
				contains = liner.contains(l);
				if(equal) {
					results.add(liner + ", " + l);
					results.remove(liner);
					break;
				}
			}
			if(!equal && !contains) results.add(l);
		}
		return results;
	}

	private static Set<Character> toList(char[] c) {
		Set<Character> chars = new TreeSet<>();
		for (char c1 : c) {
			if (c1 == ' ') continue;
			chars.add(c1);
		}
		return chars;
	}
	
	private static boolean equalCharArray(char[] ch, char[] ch2) {
		Set<Character> chlist = toList(ch);
		Set<Character> chlist2 = toList(ch2);
		return chlist.size() == chlist2.size() && chlist.equals(chlist2);
	}
}
