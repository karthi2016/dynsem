/**
 * 
 */
package org.metaborg.meta.interpreter.framework;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.spoofax.interpreter.terms.IStrategoList;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.interpreter.terms.ITermFactory;

/**
 * @author vladvergu
 *
 */
public class NodeList<T> implements INodeList<T> {

	public T head;
	public INodeList<T> tail;

	public final int size;

	private static final INodeList<Object> NIL = new NodeList<Object>(null,
			null);

	@SuppressWarnings("unchecked")
	public static final <X> INodeList<X> NIL() {
		return (INodeList<X>) NIL;
	}

	@SuppressWarnings("unchecked")
	public static final <X> INodeList<X> NIL(Class<X> clazz) {
		return (INodeList<X>) NIL;
	}

	public NodeList(T head, INodeList<T> tail) {
		this.head = head;
		this.tail = tail;

		this.size = (head == null ? 0 : 1) + (tail == null ? 0 : tail.size());
	}

	@Override
	public T head() {
		if (head == null) {
			throw new InterpreterException("No such element exception");
		}
		return head;
	}

	@Override
	public void replaceHead(T newHead) {
		this.head = newHead;
	}

	@Override
	public INodeList<T> tail() {
		if (tail == null) {
			throw new InterpreterException("No such element exception");
		}
		return tail;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return head == null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((head == null) ? 0 : head.hashCode());
		result = prime * result + size;
		result = prime * result + ((tail == null) ? 0 : tail.hashCode());
		return result;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NodeList other = (NodeList) obj;
		if (this.size() != other.size()) {
			return false;
		}
		if (this.isEmpty() && other.isEmpty()) {
			return false;
		}
		if (head == null) {
			if (other.head != null)
				return false;
		} else if (!head.equals(other.head))
			return false;
		if (size != other.size)
			return false;
		if (tail == null) {
			if (other.tail != null)
				return false;
		} else if (!tail.equals(other.tail))
			return false;
		return true;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			private INodeList<T> tail = NodeList.this;

			@Override
			public boolean hasNext() {
				return !tail.isEmpty();
			}

			@Override
			public T next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}

				T t = tail.head();
				tail = tail.tail();
				return t;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public IStrategoList toStrategoTerm(ITermFactory factory) {
		if (this.isEmpty()) {
			return factory.makeList();
		}
		IStrategoTerm headTerm = null;
		if (head instanceof IConvertibleToStrategoTerm) {
			headTerm = ((IConvertibleToStrategoTerm) head)
					.toStrategoTerm(factory);
		} else if (head instanceof String) {
			headTerm = factory.makeString((String) head);
		} else if (head instanceof Integer) {
			headTerm = factory.makeInt((Integer) head);
		} else if (head instanceof Double) {
			headTerm = factory.makeReal((Double) head);
		} else {
			throw new RuntimeException("Unsupported list element: " + head);
		}

		return factory.makeListCons(headTerm,
				(IStrategoList) tail.toStrategoTerm(factory));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		for (T elem : this) {
			sb.append(elem.toString());
			sb.append(", ");
		}
		sb.append(" ]");
		return sb.toString();
	}

}