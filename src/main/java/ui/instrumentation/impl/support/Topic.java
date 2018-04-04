package ui.instrumentation.impl.support;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Topic {

    /**
     * Value - Enum for valid {@link Topic} values. Any green field topics for new instrumentaion framework should be
     * added to this enum.
     */
    public enum Value {
        NEW1,
        NEW2,
        LEGACY(true);

        private boolean isLegacy;

        Value() {
            this(false);
        }

        Value(boolean isLegacy) {
            this.isLegacy = isLegacy;
        }

        public boolean isLegacy() {
            return isLegacy;
        }

        public String toKeyCode() {
            return name().toLowerCase();
        }
    }

    private static final EnumSet<Value> greenFieldTopicValues = EnumSet.complementOf(EnumSet.of(Value.LEGACY));
    private static final Map<String, Topic> topicsCache;

    public static final int MAX_TOPICS_TO_CACHE = 1000;

    private static LoadingCache<String, Topic> legacyTopicsCache = CacheBuilder.newBuilder()
            .maximumSize(MAX_TOPICS_TO_CACHE)
            .build(new CacheLoader<String, Topic>() {
                @Override
                public Topic load(String stringValue) {
                    return new Topic(Value.LEGACY, stringValue);
                }
            });

    private String keyCode;
    private Value value;


    static {
        topicsCache = greenFieldTopicValues.stream().collect(Collectors.toMap(v -> v.toKeyCode(), v -> new Topic(v)));
    }

    private Topic(Value value) {
        if (value.isLegacy) {
            throw new IllegalArgumentException("This constructor should be used only for non-legacy topic values");
        }
        this.value = value;
        this.keyCode = value.toKeyCode();
    }

    private Topic(Value value, String keyCode) {
        if (!value.isLegacy) {
            throw new IllegalArgumentException("This constructor should be used only non-legacy topic values");
        }
        this.value = value;
        this.keyCode = keyCode.toLowerCase().toLowerCase();
    }


    /**
     * Creates a Topic from String value. It first looks up {@link Value} for green field topics and if it can't find a
     * green field topic value, it returns a legacy topic with give stringValue as keyCode. Topic instances are always cached.
     *
     * @param stringValue of Topic
     * @return Topic A Green field topic or Topic representing legacy key code of app-analytics framework.
     */
    public static Topic fromValue(String stringValue) {

        return topicsCache.getOrDefault(stringValue.toLowerCase(), legacyTopicsCache.getUnchecked(stringValue));
    }

    /**
     * Creates a Topic from enum {@link Value}. Topic instances are always cached.
     *
     * @param value enum of Topic.
     * @return Topic Representing a Green field Topic.
     */
    public static Topic fromValue(Value value) {
        return fromValue(value.name());
    }

    public boolean isLegacy() {
        return value.isLegacy();
    }

    /**
     * Convenient method to translate a Topic to keyCode used by legacy app-analytics framework.
     * Will be deprecated in future once we completely migrate app-analytics to new framework.
     *
     * @return String keycode.
     */
    public String getKeyCode() {
        return keyCode;
    }

    /**
     * Return Topic value enum for this topic.
     * @return Value Topic value enum.
     */
    public Value getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return Objects.equals(keyCode, topic.keyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyCode);
    }

    @Override
    public String toString() {
        return keyCode.toUpperCase();
    }

    public static void main(String[] args) {

        List<Topic> topics = Lists.newArrayList(Topic.fromValue(Value.NEW1),Topic.fromValue(Value.NEW2),Topic.fromValue("old"));

        topics.forEach(t -> {
            if (t == Topic.fromValue(Value.NEW1)){
                System.out.println("Work with New1");
            }else if (t == Topic.fromValue(Value.NEW2)){
                System.out.println("Work with New2");
            }else{
                System.out.println(t);
            }


        });

    }

}
