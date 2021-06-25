<jsp:useBean id="log" class="com.uttara.bean.RegBean" scope="request">
<jsp:setProperty property="*" name="log"/>
</jsp:useBean>
<jsp:forward page="/logingin.do"></jsp:forward>