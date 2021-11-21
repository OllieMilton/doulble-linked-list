package spliterator.test;

import java.util.Collection;

public class Main {

	public static void main(String[] args) {
		
		Collection<String> col = new RandomAccessDoubleLinkedList<>();
		
		col.add("one");
		col.add("two");
		col.add("three");
		col.add("four");
		col.add("five");
		col.add("six");
		col.add("severn");
		
		
		System.out.println(col.toString());
		
		col.stream().forEach((item) -> System.out.println(item));
		
		col.remove("four");
		System.out.println(col.toString());
		
		col.stream().forEach((item) -> System.out.println(item));
		
		col.clear();
		System.out.println(col.toString());
		
		col.stream().forEach((item) -> System.out.println(item));
	}
}
