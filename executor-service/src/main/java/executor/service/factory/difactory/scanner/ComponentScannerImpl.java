package executor.service.factory.difactory.scanner;

import executor.service.annotation.Component;
import org.reflections.Reflections;

import java.util.List;

public class ComponentScannerImpl implements ComponentScanner {
    private final Reflections reflections;
    public ComponentScannerImpl(String packageName){
        reflections = new Reflections(packageName);
    }
    @Override
    public List<Class<?>> getComponents() {
        return reflections.getTypesAnnotatedWith(Component.class).stream().toList();
    }
}
