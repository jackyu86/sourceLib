package ly.main;

import ly.entity.QQ;

/**
 * 程序的入口
 *
 * @name        类名
 * @author                  ★李洋★     liyangchiyue@gmail.com
 * @createtTime 2013-11-2  下午5:13:35
 * @version     1.0
 * @since       1.0
 *
 */
public class Main {

	public static void main(String[] args) {
		QQ qq = new QQ("3296144290","790819lijun");
		qq.login();                            //登录
		System.out.println( qq.getCookie() ); //获取登录Cookie
		qq.praise("7b6eb61a5cd674523b910000"); //说说赞
	}
}
