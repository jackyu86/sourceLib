<%
/**
 * ================================================
 * Discuz! Ucenter API for JAVA
 * ================================================
 * JSP ����ʾ��
 * 
 * ������Ϣ��http://code.google.com/p/discuz-ucenter-api-for-java/
 * ���ߣ���ƽ (no_ten@163.com) 
 * ����ʱ�䣺2009-2-20
 */
%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.fivestars.interfaces.bbs.util.XMLHelper"%>
<%@page import="com.fivestars.interfaces.bbs.client.Client"%>
<%
Client uc = new Client();
String result = uc.uc_user_login("liangping2", "liangping");

LinkedList<String> rs = XMLHelper.uc_unserialize(result);
if(rs.size()>0){
	int $uid = Integer.parseInt(rs.get(0));
	String $username = rs.get(1);
	String $password = rs.get(2);
	String $email = rs.get(3);
	if($uid > 0) {
		response.addHeader("P3P"," CP=\"CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR\"");

		out.println("��¼�ɹ�");
		out.println($username);
		out.println($password);
		out.println($email);
		
		String $ucsynlogin = uc.uc_user_synlogin($uid);
		out.println("��¼�ɹ�"+$ucsynlogin);

		//���ص�½����
		//TODO ... ....
		
		Cookie auth = new Cookie("auth", uc.uc_authcode($password+"\t"+$uid, "ENCODE"));
		auth.setMaxAge(31536000);
		//auth.setDomain("localhost");
		response.addCookie(auth);
		
		Cookie user = new Cookie("uchome_loginuser", $username);
		response.addCookie(user);
		
	} else if($uid == -1) {
		out.println("�û�������,���߱�ɾ��");
	} else if($uid == -2) {
		out.println("�����");
	} else {
		out.println("δ����");
	}
}else{
	out.println("Login failed");
	System.out.println(result);
}
%>