package executor.service.execution.facade;

/**
 * The ParallelFlowExecutor interface serves as a facade for orchestrating the parallel execution
 * of scenarios or tasks.
 * <p>
 * This interface abstracts the complexities of parallel execution, providing a simplified interface
 * for clients to manage concurrent operations effectively.
 */
public interface ParallelFlowExecutor {
    /**
     * Initiates the parallel execution of scenarios.
     */
    void runInParallelFlow() throws InterruptedException;
}