package ui.instrumentation.impl.support;

import com.google.common.collect.ImmutableMap;

import java.util.*;

public class MessageHeaders implements Map<String, Object> {

    private final Map<String, Object> headers;

    /**
     * Constructs MessageHeaders with empty values.
     */
    public MessageHeaders() {
        this.headers = new HashMap<>();
    }

    /**
     * Construct a {@link MessageHeaders} with the given headers.
     *
     * @param headers a map with headers to add
     */
    public MessageHeaders(Map<String, Object> headers) {
        this.headers = (headers != null ? new HashMap<>(headers) : new HashMap<String, Object>());
    }


    protected Map<String, Object> getRawHeaders() {
        return this.headers;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Object key, Class<T> type) {
        Object value = this.headers.get(key);
        if (value == null) {
            return null;
        }
        if (!type.isAssignableFrom(value.getClass())) {
            throw new IllegalArgumentException("Incorrect type specified for header '" +
                    key + "'. Expected [" + type + "] but actual type is [" + value.getClass() + "]");
        }
        return (T) value;
    }


    // Delegating Map implementation

    @Override
    public boolean containsKey(Object key) {
        return this.headers.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.headers.containsValue(value);
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return Collections.unmodifiableMap(this.headers).entrySet();
    }

    @Override
    public Object get(Object key) {
        return this.headers.get(key);
    }

    @Override
    public boolean isEmpty() {
        return this.headers.isEmpty();
    }

    @Override
    public Set<String> keySet() {
        return Collections.unmodifiableSet(this.headers.keySet());
    }

    @Override
    public int size() {
        return this.headers.size();
    }

    @Override
    public Collection<Object> values() {
        return Collections.unmodifiableCollection(this.headers.values());
    }


    // Unsupported Map operations

    /**
     * Since MessageHeaders are immutable, the call to this method
     * will result in {@link UnsupportedOperationException}.
     */
    @Override
    public Object put(String key, Object value) {
        throw new UnsupportedOperationException("MessageHeaders is immutable");
    }

    /**
     * Since MessageHeaders are immutable, the call to this method
     * will result in {@link UnsupportedOperationException}.
     */
    @Override
    public void putAll(Map<? extends String, ? extends Object> map) {
        throw new UnsupportedOperationException("MessageHeaders is immutable");
    }

    /**
     * Since MessageHeaders are immutable, the call to this method
     * will result in {@link UnsupportedOperationException}.
     */
    @Override
    public Object remove(Object key) {
        throw new UnsupportedOperationException("MessageHeaders is immutable");
    }

    /**
     * Since MessageHeaders are immutable, the call to this method
     * will result in {@link UnsupportedOperationException}.
     */
    @Override
    public void clear() {
        throw new UnsupportedOperationException("MessageHeaders is immutable");
    }

    // equals, hashCode, toString

    @Override
    public boolean equals(Object other) {
        return (this == other ||
                (other instanceof MessageHeaders && this.headers.equals(((MessageHeaders) other).headers)));
    }

    @Override
    public int hashCode() {
        return this.headers.hashCode();
    }

    @Override
    public String toString() {
        return this.headers.toString();
    }

    public static void main(String[] args) {
        MessageHeaders headers = new MessageHeaders(ImmutableMap.of("H1", "Val1", "H2",  5));

        System.out.println(headers);
    }

}