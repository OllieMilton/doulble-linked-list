package spliterator.test;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RandomAccessDoubleLinkedList<E> implements Collection<E> {

	private Node head;
	private Node tail;
	private Map<E, Node> randomAccessMap = new HashMap<>();

	private class Node {
		Node(E element, Node prev) {
			this.element = element;
			this.prev = prev;
			if (prev != null) {
				prev.next = this;
			}
		}
		Node prev;
		E element;
		Node next;
	}
	
	@Override
	public boolean add(E e) {		
		if (head == null) {
			head = new Node(e, null);
			tail = head;
		} else {
			tail = new Node(e, tail); 
		}
		randomAccessMap.put(e, tail);
		return true;
	}
	
	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			int checkSize = randomAccessMap.size();
			int count = 0;
			Node current = head;
			@Override
			public boolean hasNext() {
				checkForComodification();
				return count < checkSize;
			}

			@Override
			public E next() {
				checkForComodification();
				E result = null;
				if (current != null) {
					result = current.element;
					current = current.next;
					count ++;
				}
				return result;
			}
			
			private void checkForComodification() {
				if (randomAccessMap.size() != checkSize) {
					throw new ConcurrentModificationException("List size has changed");
				}
			}
		};
		
		
	}

	@Override
	public int size() {
		return randomAccessMap.size();
	}

	@Override
	public boolean isEmpty() {
		return randomAccessMap.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return randomAccessMap.containsKey(o);
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		Node remove = randomAccessMap.remove(o);
		if (remove != null) {
			if (remove.prev != null) {
				remove.prev.next = remove.next;
			}
			if (remove.next != null) {
				remove.next.prev = remove.prev.next;
			}
			if (remove == head) {
				head = remove.next;
			}
			if (remove == tail) {
				tail = remove .prev;
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return randomAccessMap.keySet().containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		for (E e : c) {
			add(e);
		}
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		for (Object e : c) {
			remove(e);
		}
		return true;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		randomAccessMap.clear();
		head = null;
		tail = null;
	}	
	
	public E get(Object o) {
		Node node = randomAccessMap.get(o);
		if (node != null) {
			return node.element;
		}
		return null;
	}
	
	public String toString() {
		Node print = head;
		StringBuilder sb = new StringBuilder("RandomAccessDoubleLinkedList: \n");
		while (print != null) {
			sb.append("Node: ");
			sb.append(print.element);
			sb.append(" Prev: ");
			if (print.prev != null) {
				sb.append(print.prev.element);
			}
			sb.append(" Next: ");
			if (print.next != null) {
				sb.append(print.next.element);
			}
			sb.append("\n");
			print = print.next;
		}
		sb.append(size());
		return sb.toString();
	}
	
}
