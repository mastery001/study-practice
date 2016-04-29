package study.nio.reactor.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

import study.nio.reactor.Filter;
import study.nio.reactor.Filter.NextFilter;
import study.nio.reactor.FilterChain;
import study.nio.reactor.FilterChain.Entry;
import study.nio.reactor.FilterChainBuilder;

@SuppressWarnings({"rawtypes","unchecked"})
public class DefaultFilterChainBuilder implements FilterChainBuilder,
        Cloneable {
   
	private final List entries;

    /**
     * Creates a new instance with an empty filter list.
     */
    public DefaultFilterChainBuilder() {
        entries = new CopyOnWriteArrayList();
    }

    /**
     * @see FilterChain#getEntry(String)
     */
    public Entry getEntry(String name) {
        for (Iterator i = entries.iterator(); i.hasNext(); ) {
            Entry e = (Entry) i.next();
            if (e.getName().equals(name)) {
                return e;
            }
        }
        
        return null;
    }

    /**
     * @see FilterChain#get(String)
     */
    public Filter get(String name) {
        Entry e = getEntry(name);
        if (e == null) {
            return null;
        }

        return e.getFilter();
    }

    /**
     * @see FilterChain#getAll()
     */
    public List getAll() {
        return new ArrayList(entries);
    }

    /**
     * @see FilterChain#getAllReversed()
     */
    public List getAllReversed() {
        List result = getAll();
        Collections.reverse(result);
        return result;
    }

    /**
     * @see FilterChain#contains(String)
     */
    public boolean contains(String name) {
        return getEntry(name) != null;
    }

    /**
     * @see FilterChain#contains(Filter)
     */
    public boolean contains(Filter filter) {
        for (Iterator i = entries.iterator(); i.hasNext();) {
            Entry e = (Entry) i.next();
            if (e.getFilter() == filter) {
                return true;
            }
        }

        return false;
    }

    /**
     * @see FilterChain#contains(Class)
     */
	public boolean contains(Class filterType) {
        for (Iterator i = entries.iterator(); i.hasNext();) {
            Entry e = (Entry) i.next();
            if (filterType.isAssignableFrom(e.getFilter().getClass())) {
                return true;
            }
        }

        return false;
    }

    /**
     * @see FilterChain#addFirst(String, Filter)
     */
    public synchronized void addFirst(String name, Filter filter) {
        register(0, new EntryImpl(name, filter));
    }

    /**
     * @see FilterChain#addLast(String, Filter)
     */
    public synchronized void addLast(String name, Filter filter) {
        register(entries.size(), new EntryImpl(name, filter));
    }

    /**
     * @see FilterChain#addBefore(String, String, Filter)
     */
    public synchronized void addBefore(String baseName, String name,
            Filter filter) {
        checkBaseName(baseName);

        for (ListIterator i = entries.listIterator(); i.hasNext();) {
            Entry base = (Entry) i.next();
            if (base.getName().equals(baseName)) {
                register(i.previousIndex(), new EntryImpl(name, filter));
                break;
            }
        }
    }

    /**
     * @see FilterChain#addAfter(String, String, Filter)
     */
    public synchronized void addAfter(String baseName, String name,
            Filter filter) {
        checkBaseName(baseName);

        for (ListIterator i = entries.listIterator(); i.hasNext();) {
            Entry base = (Entry) i.next();
            if (base.getName().equals(baseName)) {
                register(i.nextIndex(), new EntryImpl(name, filter));
                break;
            }
        }
    }

    /**
     * @see FilterChain#remove(String)
     */
    public synchronized Filter remove(String name) {
        if (name == null) {
            throw new NullPointerException("name");
        }

        for (ListIterator i = entries.listIterator(); i.hasNext();) {
            Entry e = (Entry) i.next();
            if (e.getName().equals(name)) {
                entries.remove(i.previousIndex());
                return e.getFilter();
            }
        }

        throw new IllegalArgumentException("Unknown filter name: " + name);
    }

    /**
     * @see FilterChain#clear()
     */
    public synchronized void clear() throws Exception {
        entries.clear();
    }

    public void buildFilterChain(FilterChain chain) throws Exception {
        for (Iterator i = entries.iterator(); i.hasNext();) {
            Entry e = (Entry) i.next();
            chain.addLast(e.getName(), e.getFilter());
        }
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("{ ");

        boolean empty = true;

        for (Iterator i = entries.iterator(); i.hasNext();) {
            Entry e = (Entry) i.next();
            if (!empty) {
                buf.append(", ");
            } else {
                empty = false;
            }

            buf.append('(');
            buf.append(e.getName());
            buf.append(':');
            buf.append(e.getFilter());
            buf.append(')');
        }

        if (empty) {
            buf.append("empty");
        }

        buf.append(" }");

        return buf.toString();
    }

    public Object clone() {
        DefaultFilterChainBuilder ret = new DefaultFilterChainBuilder();
        for (Iterator i = entries.iterator(); i.hasNext();) {
            Entry e = (Entry) i.next();
            ret.addLast(e.getName(), e.getFilter());
        }
        return ret;
    }

    private void checkBaseName(String baseName) {
        if (baseName == null) {
            throw new NullPointerException("baseName");
        }
        
        if (!contains(baseName)) {
            throw new IllegalArgumentException("Unknown filter name: "
                    + baseName);
        }
    }

    private void register(int index, Entry e) {
        if (contains(e.getName())) {
            throw new IllegalArgumentException(
                    "Other filter is using the same name: " + e.getName());
        }

        entries.add(index, e);
    }

    private static class EntryImpl implements Entry {
        private final String name;

        private final Filter filter;

        private EntryImpl(String name, Filter filter) {
            if (name == null) {
                throw new NullPointerException("name");
            }
            if (filter == null) {
                throw new NullPointerException("filter");
            }

            this.name = name;
            this.filter = filter;
        }

        public String getName() {
            return name;
        }

        public Filter getFilter() {
            return filter;
        }

        public NextFilter getNextFilter() {
            throw new IllegalStateException();
        }

        public String toString() {
            return "(" + getName() + ':' + filter + ')';
        }
    }
}
