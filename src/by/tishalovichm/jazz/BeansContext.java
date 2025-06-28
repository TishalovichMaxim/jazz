package by.tishalovichm.jazz;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BeansContext {

    private static final Logger logger = Logger.getLogger(BeansContext.class.getName());

    private final Map<Class<?>, Object> beansMap = new HashMap<>();

    public void getBeans(List<Class<?>> classes) {
        var filteredClasses = classes.stream()
            .filter(cl -> cl.isAnnotationPresent(Bean.class))
            .toList();

        logger.log(Level.INFO, "Beans classes: {0}", filteredClasses.toString());
    }

    public <T> T getBean(Class<T> clazz) {
        Bean annotation = clazz.getAnnotation(Bean.class);
        BeanCreationType creationType = annotation.creationType();

        if (creationType == BeanCreationType.SINGLETON && beansMap.containsKey(clazz)) {
            return (T) beansMap.get(clazz);
        }

        T obj = createBean(clazz);
        if (creationType == BeanCreationType.SINGLETON) {
            beansMap.put(clazz, obj);
        }

        return obj;
    }

    private <T> T createBean(Class<T> clazz) throws RuntimeException {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
