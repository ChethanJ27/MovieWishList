<jsp:useBean id="reg" class="com.uttara.bean.RegBean" scope="request">
<jsp:setProperty property="*" name="reg"/>
</jsp:useBean>
<jsp:forward page="/registering.do"></jsp:forward>