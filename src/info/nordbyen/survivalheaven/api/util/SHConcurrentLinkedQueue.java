package info.nordbyen.survivalheaven.api.util;

import java.util.concurrent.atomic.*;
import java.io.*;
import java.util.*;

public class SHConcurrentLinkedQueue<E> extends AbstractQueue<E> implements Queue<E>, Serializable
{
    private static final long serialVersionUID = 7144889821407157242L;
    private static final AtomicReferenceFieldUpdater<SHConcurrentLinkedQueue, Node> tailUpdater;
    private static final AtomicReferenceFieldUpdater<SHConcurrentLinkedQueue, Node> headUpdater;
    private transient volatile Node<E> head;
    private transient volatile Node<E> tail;
    
    static {
        tailUpdater = AtomicReferenceFieldUpdater.newUpdater(SHConcurrentLinkedQueue.class, Node.class, "tail");
        headUpdater = AtomicReferenceFieldUpdater.newUpdater(SHConcurrentLinkedQueue.class, Node.class, "head");
    }
    
    private boolean casTail(final Node<E> cmp, final Node<E> val) {
        return SHConcurrentLinkedQueue.tailUpdater.compareAndSet(this, cmp, val);
    }
    
    private boolean casHead(final Node<E> cmp, final Node<E> val) {
        return SHConcurrentLinkedQueue.headUpdater.compareAndSet(this, cmp, val);
    }
    
    public SHConcurrentLinkedQueue() {
        this.head = new Node<E>(null, null);
        this.tail = new Node<E>(null, null);
    }
    
    public SHConcurrentLinkedQueue(final Collection<? extends E> c) {
        this.head = new Node<E>(null, null);
        this.tail = new Node<E>(null, null);
        final Iterator<? extends E> it = c.iterator();
        while (it.hasNext()) {
            this.add(it.next());
        }
    }
    
    @Override
    public boolean add(final E e) {
        return this.offer(e);
    }
    
    @Override
    public boolean offer(final E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        final Node<E> n = new Node<E>(e, null);
        while (true) {
            final Node<E> t = this.tail;
            final Node<E> s = t.getNext();
            if (t == this.tail) {
                if (s != null) {
                    return false;
                }
                if (t.casNext(s, n)) {
                    this.casTail(t, n);
                    return true;
                }
                continue;
            }
        }
    }
    
    @Override
    public E poll() {
        while (true) {
            final Node<E> h = this.head;
            final Node<E> t = this.tail;
            final Node<E> first = h.getNext();
            if (h == this.head) {
                if (h == t) {
                    if (first == null) {
                        return null;
                    }
                    this.casTail(t, first);
                }
                else {
                    if (!this.casHead(h, first)) {
                        continue;
                    }
                    final E item = first.getItem();
                    if (item != null) {
                        first.setItem(null);
                        return item;
                    }
                    continue;
                }
            }
        }
    }
    
    @Override
    public E peek() {
        while (true) {
            final Node<E> h = this.head;
            final Node<E> t = this.tail;
            final Node<E> first = h.getNext();
            if (h == this.head) {
                if (h == t) {
                    if (first == null) {
                        return null;
                    }
                    this.casTail(t, first);
                }
                else {
                    final E item = first.getItem();
                    if (item != null) {
                        return item;
                    }
                    this.casHead(h, first);
                }
            }
        }
    }
    
    Node<E> first() {
        while (true) {
            final Node<E> h = this.head;
            final Node<E> t = this.tail;
            final Node<E> first = h.getNext();
            if (h == this.head) {
                if (h == t) {
                    if (first == null) {
                        return null;
                    }
                    this.casTail(t, first);
                }
                else {
                    if (first.getItem() != null) {
                        return first;
                    }
                    this.casHead(h, first);
                }
            }
        }
    }
    
    @Override
    public boolean isEmpty() {
        return this.first() == null;
    }
    
    @Override
    public int size() {
        int count = 0;
        for (Node<E> p = this.first(); p != null && (p.getItem() == null || ++count != Integer.MAX_VALUE); p = p.getNext()) {}
        return count;
    }
    
    @Override
    public boolean contains(final Object o) {
        if (o == null) {
            return false;
        }
        for (Node<E> p = this.first(); p != null; p = p.getNext()) {
            final E item = p.getItem();
            if (item != null && o.equals(item)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean remove(final Object o) {
        if (o == null) {
            return false;
        }
        for (Node<E> p = this.first(); p != null; p = p.getNext()) {
            final E item = p.getItem();
            if (item != null && o.equals(item) && p.casItem(item, null)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Object[] toArray() {
        final ArrayList<E> al = new ArrayList<E>();
        for (Node<E> p = this.first(); p != null; p = p.getNext()) {
            final E item = p.getItem();
            if (item != null) {
                al.add(item);
            }
        }
        return al.toArray();
    }
    
    @Override
    public <T> T[] toArray(final T[] a) {
        int k;
        Node<E> p;
        E item;
        for (k = 0, p = this.first(); p != null && k < a.length; p = p.getNext()) {
            item = p.getItem();
            if (item != null) {
                a[k++] = (T)item;
            }
        }
        if (p == null) {
            if (k < a.length) {
                a[k] = null;
            }
            return a;
        }
        final ArrayList<E> al = new ArrayList<E>();
        for (Node<E> q = this.first(); q != null; q = q.getNext()) {
            final E item2 = q.getItem();
            if (item2 != null) {
                al.add(item2);
            }
        }
        return al.toArray(a);
    }
    
    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }
    
    private void writeObject(final ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        for (Node<E> p = this.first(); p != null; p = p.getNext()) {
            final Object item = p.getItem();
            if (item != null) {
                s.writeObject(item);
            }
        }
        s.writeObject(null);
    }
    
    private void readObject(final ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.head = new Node<E>(null, null);
        this.tail = this.head;
        while (true) {
            final E item = (E)s.readObject();
            if (item == null) {
                break;
            }
            this.offer(item);
        }
    }
    
    private static class Node<E>
    {
        private volatile E item;
        private volatile Node<E> next;
        private static final AtomicReferenceFieldUpdater<Node, Node> nextUpdater;
        private static final AtomicReferenceFieldUpdater<Node, Object> itemUpdater;
        
        static {
            nextUpdater = AtomicReferenceFieldUpdater.newUpdater(Node.class, Node.class, "next");
            itemUpdater = AtomicReferenceFieldUpdater.newUpdater(Node.class, Object.class, "item");
        }
        
        Node(final E x) {
            this.item = x;
        }
        
        Node(final E x, final Node<E> n) {
            this.item = x;
            this.next = n;
        }
        
        E getItem() {
            return this.item;
        }
        
        boolean casItem(final E cmp, final E val) {
            return Node.itemUpdater.compareAndSet(this, cmp, val);
        }
        
        void setItem(final E val) {
            Node.itemUpdater.set(this, val);
        }
        
        Node<E> getNext() {
            return this.next;
        }
        
        boolean casNext(final Node<E> cmp, final Node<E> val) {
            return Node.nextUpdater.compareAndSet(this, cmp, val);
        }
        
        void setNext(final Node<E> val) {
            Node.nextUpdater.set(this, val);
        }
    }
    
    private class Itr implements Iterator<E>
    {
        private Node<E> nextNode;
        private E nextItem;
        private Node<E> lastRet;
        
        Itr() {
            this.advance();
        }
        
        private E advance() {
            this.lastRet = this.nextNode;
            final E x = this.nextItem;
            for (Node<E> p = (this.nextNode == null) ? SHConcurrentLinkedQueue.this.first() : this.nextNode.getNext(); p != null; p = p.getNext()) {
                final E item = p.getItem();
                if (item != null) {
                    this.nextNode = p;
                    this.nextItem = item;
                    return x;
                }
            }
            this.nextNode = null;
            this.nextItem = null;
            return x;
        }
        
        @Override
        public boolean hasNext() {
            return this.nextNode != null;
        }
        
        @Override
        public E next() {
            if (this.nextNode == null) {
                throw new NoSuchElementException();
            }
            return this.advance();
        }
        
        @Override
        public void remove() {
            final Node<E> l = this.lastRet;
            if (l == null) {
                throw new IllegalStateException();
            }
            l.setItem(null);
            this.lastRet = null;
        }
    }
}
