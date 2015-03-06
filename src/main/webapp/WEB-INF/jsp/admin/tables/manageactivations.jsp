<%@include file="/WEB-INF/jsp/include/include.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page buffer="16kb" autoFlush="true"%>
<display:table id="activation" name="activations" class="volume"
	requestURI="" defaultsort="0" defaultorder="descending">
	<display:column property="id" titleKey="tablehdr.id"
		headerClass="headid" />
	<display:column property="username" titleKey="tablehdr.user" />
	<display:column property="token" titleKey="tablehdr.token" />
	<display:column property="date" titleKey="tablehdr.date" />
	<display:column titleKey="tablehdr.actions">
		<a
			href="<c:url value="/admin/accountactivation.xhtml?key=${activation.token}"/>"><i
			class="text-success fa fa-check-circle"></i>&nbsp; <a
			href="<c:url value="/admin/resendactivations.xhtml?act_id=${activation.id}"/>">
				<i class="fa fa-envelope-o"></i>&nbsp;
		</a> <a
			href="<c:url value="/admin/deleteactivation.xhtml?key=${activation.token}"/>">
				<i class="fa fa-trash"></i>
		</a>
	</display:column>
</display:table>