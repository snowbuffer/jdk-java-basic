package com.wangwenjun.concurrency.custombus;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-02-18 15:38
 */
public class CustomRegistry {

    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<CustomSubscriber>> subscriberContainer
            = new ConcurrentHashMap<>();

    public void bind(Object subscriber) {
        List<Method> subscribers = getSubscriberMethods(subscriber);
        subscribers.forEach(item -> tierSubscriber(subscriber, item));
    }

    private void tierSubscriber(Object subscriber, Method method) {
        final CustomSubscribe customSubscribe = method.getDeclaredAnnotation(CustomSubscribe.class);
        String topic = customSubscribe.topic();
        subscriberContainer.computeIfAbsent(topic, t -> new ConcurrentLinkedQueue());
        subscriberContainer.get(topic).add(new CustomSubscriber(subscriber, method));
    }

    private List<Method> getSubscriberMethods(Object subscriber) {
        Objects.requireNonNull(subscriber);

        final List<Method> methods = new ArrayList<>();
        Class<?> clazz = subscriber.getClass();
        while (clazz != null) {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            Arrays.stream(declaredMethods).filter(method -> method.isAnnotationPresent(CustomSubscribe.class) && method.getParameterCount() == 1
                    && method.getModifiers() == Modifier.PUBLIC)
                    .forEach(methods::add);
            clazz = clazz.getSuperclass();
        }
        return methods;
    }

    public void unbind(Object subscriber) {
        subscriberContainer.forEach((key, queue) -> {
            queue.forEach(customSubscriber -> {
                if (customSubscriber.getSubscriberObject() == subscriber) {
                    customSubscriber.setDisabled(true);
                }
            });
        });
    }

    public ConcurrentLinkedQueue<CustomSubscriber> scanSubscriber(String toTopic) {
        return subscriberContainer.get(toTopic);
    }
}

