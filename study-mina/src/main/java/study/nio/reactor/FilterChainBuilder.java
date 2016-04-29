package study.nio.reactor;

public interface FilterChainBuilder {
	
	 /**
     * Modifies the specified <tt>chain</tt>.
     */
    void buildFilterChain(FilterChain chain) throws Exception;
}
