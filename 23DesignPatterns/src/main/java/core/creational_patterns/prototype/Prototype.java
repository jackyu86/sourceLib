package core.creational_patterns.prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @ClassName: Prototype
 * @Description: 原型模式：
 * 原型模式虽然是创建型的模式，但是与工程模式没有关系，
 * 从名字即可看出，该模式的思想就是将一个对象作为原型，
 * 对其进行复制、克隆，产生一个和原对象类似的新对象。
 * @author qinf QQ:908247035
 * @date 2017年2月15日
 * @version V1.0
 */
public class Prototype implements Cloneable,Serializable {
	
	private static final long serialVersionUID = 8789841309971544805L;

	private String string;  
	  
    private SerializableObject obj;  
	/**
	 * 在Java中，复制对象是通过clone()实现的
	 * 
	 */
	
	/* 浅复制 */  
	public Object clone() throws CloneNotSupportedException {  
        Prototype proto = (Prototype) super.clone();  
        return proto;  
    }  
	
	/* 深复制 */  
    public Object deepClone() throws IOException, ClassNotFoundException {  
  
        /* 写入当前对象的二进制流 */  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        ObjectOutputStream oos = new ObjectOutputStream(bos);  
        oos.writeObject(this);  
  
        /* 读出二进制流产生的新对象 */  
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());  
        ObjectInputStream ois = new ObjectInputStream(bis);  
        return ois.readObject();  
    }  
    
    public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public SerializableObject getObj() {
		return obj;
	}

	public void setObj(SerializableObject obj) {
		this.obj = obj;
	}

	class SerializableObject implements Serializable {  
        private static final long serialVersionUID = 1L;  
    }  
}
