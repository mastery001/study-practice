package jmx;

/**
 * 标准MBean
 * 规范：在标准 MBean 类名之后加上“MBean”后缀。若 MBean 的类名叫做 MBeansName 的话，
 * 对应的接口就要叫做 MBeansNameMBean。
 * @author zouziwen
 *
 * 2016年5月13日 下午12:03:54
 */
public interface HelloMBean extends HelloService{
	
	String status();
}
