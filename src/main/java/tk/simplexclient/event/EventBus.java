package tk.simplexclient.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.module.HUDModule;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EventBus {
    @Getter
    private static EventBus instance;

    private List<Subscriber> eventSubscribers = new ArrayList<>();

    public EventBus(SimplexClient client){
        if(!new Exception().getStackTrace()[1].getClassName().equalsIgnoreCase(client.getClass().getName()))
            throw new RuntimeException("EventBus Shouldn't be called");

        instance = this;
    }

    public void subscribe(Object subscriber){
        List<Method> methods = new ArrayList<>();

        for(Method md : subscriber.getClass().getMethods()){
            if(md.isAnnotationPresent(EventTarget.class) && md.getParameters().length == 1 && md.getParameters()[0].getType().getSuperclass() != null && md.getParameters()[0].getType().getSuperclass().getName().equalsIgnoreCase(Event.class.getName())) {
                methods.add(md);
            }
        }

        eventSubscribers.add(new Subscriber(subscriber, methods));
    }

    public void unSubscribe(Object unsubscriber){
        eventSubscribers.removeIf(subscriber -> subscriber.instance == unsubscriber);
    }

    public void call(Event e){
        eventSubscribers.forEach(subscriber ->
                subscriber.subscribedMethods.forEach(method -> {
                    if(method.getParameterTypes()[0].isAssignableFrom(e.getClass())){
                        try {
                            method.invoke(subscriber.instance, e);
                        } catch (IllegalAccessException | InvocationTargetException ignored) {}
                    }
                })
        );
    }

    @Getter
    @AllArgsConstructor
    class Subscriber{
        private Object instance;
        private List<Method> subscribedMethods;
    }
}
