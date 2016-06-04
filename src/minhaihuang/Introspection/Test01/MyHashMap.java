package minhaihuang.Introspection.Test01;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *自己写一个MyHashMap,使其可以存储三个对象提供void put(key,v1,v2),Object[] get(Object key),
 *Object[] remove(Object key)
 *思路：利用put(key,value[])方法
 *
 */

public class MyHashMap {

	//利用数组加链表的思想来存储数据
	//创建链表数组
	LinkedList[] arr=new LinkedList[50];
	
	//准备元素
	Entry e;
	
	
	//添加元素的方法，一次性添加3个对象
	public void put(Object key,Object v1,Object v2){
		//初始化各个元素
		
		//注意，新建的Object[]数组一定要在方法内部，否则，因为每一次都需新建立数组，若不放在方法内部，会发生多态问题
		//例：第一次put("hhm", 90, 10),第二次put("hhc", 90, 100)，当查询get("hhm")时，会获得90，100
		//这是因为发生了多态的问题，因为存放在map中的value地址指向了新的对象
		Object[] value=new Object[2];
		value[0]=v1;
		value[1]=v2;
		e=new Entry(key,value);
		
		//根据索引来存储数据,求HashCode()%arr.length
		int index=key.hashCode()%arr.length;
		
		//遍历链表，判断元素是否存在,
		if(arr[index]==null){
			//若链表为空，证明还没有元素，创建一个新的链表
			LinkedList list=new LinkedList();
			arr[index]=list;
			
			//加入元素
			list.add(e);
		}else{
			//若索引链表不为空，则遍历链表，看键是否已经存在，若存在，则覆盖值，若不存在，直接添加对象
			Iterator it=arr[index].iterator();
			Entry e1=new Entry();
			
			while(it.hasNext()){
				
				e1=(Entry) it.next();
				if(e1.key.equals(key)){//若键存在，覆盖值，结束方法
					e1.value=value;
					return;
				}
			}
			
			//遍历完成后如果都没有覆盖，则直接添加元素
			arr[index].add(e);
		}
		
	}
	
	//查询值得方法，根据键来查询，返回一个Object[]数组,若查不到，返回null
	public Object[] get(Object key){
		
		//利用hashCode对数组长度求余，得到链表的索引，遍历索引链表，查询值
		//1，获得索引
		int index=key.hashCode()%arr.length;
		
		//2，如果索引链表不为空，则遍历链表，查询者
		if(arr[index]!=null){
			//准备数据
			Object[] obj=new Object[2];
			Entry e2=new Entry();
			
			Iterator it=arr[index].iterator();
			while(it.hasNext()){
				e2=(Entry) it.next();
				//如果键相等，返回值
				if(e2.key.equals(key)){
					obj=e2.value;
					return obj;
				}
			}
		}
	
		return null;
	}
	
	//删除数据的方法。返回一个所删除的值。若所删除的键不存在，返回null
	public Object[] remove(Object key){
		//1，利用hashCode对数组长度的求余获得索引，找到目标链表的位置
		int index=key.hashCode()%arr.length;
		
		//若目标链表不为空，遍历寻找key相等的对象，删除该对象，返回对象
		if(arr[index]!=null){
			//准备数据
			Object[] obj=new Object[2];
			Entry e3=new Entry();

			//遍历链表
			Iterator it=arr[index].iterator();
			while(it.hasNext()){
				e3=(Entry) it.next();
				//如果键相等，返回值，删除数据
				if(e3.key.equals(key)){
					obj=e3.value;
					arr[index].remove(e3);
					return obj;
				}
			}
		}
		return null;
	}
	public static void main(String[] args) {
		MyHashMap map=new MyHashMap();
		map.put("hhm", 90, 100);
		map.put("hhc", 90, 10);
		map.put("hhm", 90, 80);
		map.remove("hhm");
		Object[] value=map.get("hhm");
		for(int i=0;i<value.length;i++){
			System.out.println(value[i]);
		}
	}
}


//定义一个存储元素的类型的类
class Entry{
	
	Object key;
	Object[] value;
	
	public Entry(){
		
	}
	
	public Entry(Object key, Object[] value) {
		super();
		this.key = key;
		this.value = value;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public Object[] getValue() {
		return value;
	}

	public void setValue(Object[] value) {
		this.value = value;
	}
	
	
}