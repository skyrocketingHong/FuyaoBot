package ninja.skyrocketing.robot.handler.impl;

import ninja.skyrocketing.robot.handler.ResultSetHandlerInterface;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-07 007 14:59:10
 * @Version 1.0
 */
public class BeanHandler<T> implements ResultSetHandlerInterface {
	// 创建字节码对象
	private Class<T> clazz;
	
	// 创建有参构造函数，用于传入具体操作对象的类型
	public BeanHandler(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	/**
	 * 数据库集操作类
	 * @param rSet 数据库处理结果集
	 * @return 数据库结果集 List 集合
	 */
	@Override
	public List<T> handler(ResultSet rSet) {
		// 创建 List 用于存放装箱后的对象
		List<T> list = new ArrayList<T>();
		try {
			// 获取类的属性描述符
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
			PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
			// 对结果集进行装箱操作
			while (rSet.next()) {
				T obj = clazz.newInstance();
				for (PropertyDescriptor descriptor : descriptors) {
					Object value = rSet.getObject(descriptor.getName());
					descriptor.getWriteMethod().invoke(obj, value);
				}
				list.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
