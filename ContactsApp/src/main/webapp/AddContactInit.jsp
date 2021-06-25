<jsp:useBean id="addcontact" class="com.uttara.bean.ContactBean" scope="request">
<jsp:setProperty property="*" name="addcontact"/>
</jsp:useBean>
<jsp:forward page="/addcontactinfo.do"></jsp:forward>