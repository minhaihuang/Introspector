package minhaihuang.Introspection.Test01;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import minhaihuang.Introspection.javaBean.*;
/**
 * 尝试用普通方法，反射机制，还有java内省三种方法分别初始化一个对象的属性
 * @author 黄帅哥
 *
 */
public class InteospectionTest01 {

	public static void main(String[] args) throws IntrospectionException {
		//创建一个新的Student对象
		Student stu=new Student();
		
		/*//用自己写的一个MyHashMap来存储一组数据,第一个为key,第二个和第三个组成vlaue[]
		MyHashMap map=new MyHashMap();
		map.put("003", "hhm", 21);
		//封装数据前,打印出：封装数据前：null:null:0
		System.out.println("封装数据前："+stu.getId()+":"+stu.getName()+":"+stu.getAge());
		
		//封装数据后，打印出：封装数据后：003:hhm:21
		//initObject01(stu,map);
		initObject02(stu);
		System.out.println("封装数据后："+stu.getId()+":"+stu.getName()+":"+stu.getAge());
		*/
		
		//测试用内省初始化数据的方法
		Map map2=new HashMap();
		map2.put("id", "003");
		map2.put("name", "hhm");
		map2.put("age", 21);
		initObject03(stu,map2);
		System.out.println("封装数据后："+stu.getId()+":"+stu.getName()+":"+stu.getAge());
	}
	
	//1，普通方法,这里的初始数据从一个Map容器中获取得到
	public static void initObject01(Student stu,MyHashMap map){
		
		//利用id作为Key,姓名和年龄组成的数组作为value
		String id=(String) map.e.key;
		
		//1，初始化id
		stu.setId(id);
		//2，获得由姓名和年龄组成的数组。
		Object[] value=map.get(id);
		//3，初始化姓名
		stu.setName((String)value[0]);
		stu.setAge((Integer)value[1]);
	}
	
	//2,利用反射机制来初始化数据，数据直接指明
	public static void initObject02(Student stu){
		
		try {
			//1,利用反射机制加载类,创建一个Class对象
			Class clazz=Class.forName("minhaihuang.Introspection.javaBean.Student");
			
			//创建一个学生的对象
			//2，利用反射加载类的方法,初始化各个属性
			//初始化Id
			Method setId=clazz.getDeclaredMethod("setId", String.class);
			setId.invoke(stu, "003");
			//初始化姓名
			Method setName=clazz.getDeclaredMethod("setName", String.class);
			setName.invoke(stu, "hhm");
			//初始化年龄
			Method setAge=clazz.getDeclaredMethod("setAge", int.class);
			setAge.invoke(stu, 21);
		} catch (ClassNotFoundException e) {
			System.out.println("加载类失败"+e.getMessage());
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//3,利用java内省来初始化对象,数据由一个Map传入
	//当利用java内省时，要求所要初始化的对象的类必须符合javaBean思想。
	public static void initObject03(Student stu,Map map) throws IntrospectionException{
		//结合java反射获取一个BeanInfo对象
	BeanInfo bean=Introspector.getBeanInfo(Student.class);
	
		//利用BeanInfo对象中的getPropertyDescriptors()方法来获得一个包含类的所有信息的PropertyDescriptor数组
	PropertyDescriptor[] propde=bean.getPropertyDescriptors();
	
		//用增强for遍历数组
	for(PropertyDescriptor prop:propde){
		//获取属性
		String fieldName=prop.getName();
		try {
			//获取set方法,有可能获取为空，所以需加上判断语句
			Method setMethod=prop.getWriteMethod();
			//如果不为空，激活方法，初始化数据
			if(setMethod!=null){
			setMethod.invoke(stu, map.get(fieldName));
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	}
}
