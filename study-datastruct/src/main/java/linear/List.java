package linear;

import exception.StructureException;

/**
 * @Description 实现线性表结构的接口
 * @author mastery
 * @Date 2015年6月29日下午8:14:33
 * @param <T>
 */
public interface List<T> {

	/**
	 * 在线性表指定位置插入元素t
	 * @param index
	 * @param t
	 */
	void insert(int index , T t) throws StructureException;
	
	/**
	 * 删除该线性表中指定位置的元素
	 * @param index
	 */
	void delete(int index) throws StructureException;
	
	/**
	 * 获得该线性表中指定位置的元素
	 * @param index
	 * @return
	 */
	T get(int index) throws StructureException;
	
	/**
	 * 得到该线性表当前的存在多少个元素
	 * @return
	 */
	int size() throws StructureException;
	
	/**
	 * 判断该线性表是否为空
	 * @return
	 */
	boolean isEmpty() throws StructureException;
}
