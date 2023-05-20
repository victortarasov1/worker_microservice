package executor.service.factory.difactory;

public interface DependencyInjectionFactory {

    <T> T createInstance(Class<T> interfaceClass);

}
