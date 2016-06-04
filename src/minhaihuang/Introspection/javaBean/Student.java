package minhaihuang.Introspection.javaBean;
/**
 * 测试一个javaBean的类，只有属性和set,get方法和构造器，至少需要一个无参构造
 * 类的结构：1，所有的属性都为私有，2，为所有的属性都设置set(),get()方法。3，构造器
 * @author 黄帅哥
 *
 */
public class Student {

	public Student() {
		super();
	}
	public Student(String name, int age, String id) {
		super();
		this.name = name;
		this.age = age;
		this.id = id;
	}
	private String name;
	private int age;
	private String id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
