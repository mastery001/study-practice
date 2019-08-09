package nio;

import java.nio.channels.spi.SelectorProvider;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class SelectorProviderGetter {

	private static SelectorProvider provider = null;
	
	public static void main(String[] args) {
		if (loadProviderFromProperty())
        if (loadProviderAsService())
        provider = sun.nio.ch.DefaultSelectorProvider.create();
		System.out.println(provider);
	}
	
	private static SelectorProvider get() {
		return AccessController.doPrivileged(new PrivilegedAction<SelectorProvider>() {

			@Override
			public SelectorProvider run() {
				  if (loadProviderFromProperty())
                      return provider;
                  if (loadProviderAsService())
                      return provider;
                  provider = sun.nio.ch.DefaultSelectorProvider.create();
                  return provider;
			}

		});
	}
	
	 private static boolean loadProviderFromProperty() {
	        String cn = System.getProperty("java.nio.channels.spi.SelectorProvider");
	        if (cn == null)
	            return false;
	        try {
	            Class<?> c = Class.forName(cn, true,
	                                       ClassLoader.getSystemClassLoader());
	            provider = (SelectorProvider)c.newInstance();
	            return true;
	        } catch (ClassNotFoundException x) {
	            throw new ServiceConfigurationError(null, x);
	        } catch (IllegalAccessException x) {
	            throw new ServiceConfigurationError(null, x);
	        } catch (InstantiationException x) {
	            throw new ServiceConfigurationError(null, x);
	        } catch (SecurityException x) {
	            throw new ServiceConfigurationError(null, x);
	        }
	    }

	    private static boolean loadProviderAsService() {

	        ServiceLoader<SelectorProvider> sl =
	            ServiceLoader.load(SelectorProvider.class,
	                               ClassLoader.getSystemClassLoader());
	        Iterator<SelectorProvider> i = sl.iterator();
	        for (;;) {
	            try {
	                if (!i.hasNext())
	                    return false;
	                provider = i.next();
	                return true;
	            } catch (ServiceConfigurationError sce) {
	                if (sce.getCause() instanceof SecurityException) {
	                    // Ignore the security exception, try the next provider
	                    continue;
	                }
	                throw sce;
	            }
	        }
	    }
}
