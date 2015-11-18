<%@include file="/WEB-INF/jsp/include/include.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<script  type="text/javascript"
src="<c:url value="/js/WYSIWYG/source.js" />"></script>


<div class="row">
    <div class="col-xs-10">
        <form:form method="post" enctype="multipart/form-data"
                   commandName="announcement" cssClass="form-horizontal">

            <!-- NAME OF VIEW -->
            <legend>
                <spring:message code="page.general.admin.header" />: <spring:message code="pagehdr.aaddannouncement" />
            </legend>

            <!-- TEXT OF ANNOUNCEMENT -->
            <authz:authorize ifAnyGranted="ROLE_USER,ROLE_TEAM">
                <div class="form-group">
                    <label class="control-label col-xs-3">
                        <spring:message code="fieldhdr.content" />
                    </label>                    
                    <div class="col-xs-8">
                        <form:textarea path="content" />
                    </div>
                    <div class="error col-xs-8 col-xs-offset-3">
                        <span class="label label-danger"><form:errors path="content" /></span>
                    </div>  
                    <a>
                        <i data-toggle="tooltip" class="fa fa-asterisk"
                           title="<spring:message code="mandatory.field"/>">
                        </i>
                    </a>
                </div>
            </authz:authorize>

            <!-- ENABLED OF ANNOUNCEMENT -->
            <div class="form-group">
                <label class="control-label col-xs-3">
                    <spring:message code="fieldhdr.enabled" />
                </label>
                <div class="col-xs-8">
                    <form:checkbox path="enabled" />
                </div>    
                <div class="error col-xs-8 col-xs-offset-3">
                        <span class="label label-danger"><form:errors path="enabled" /></span>
                    </div>  
                <a>
                    <i data-toggle="tooltip" class="fa fa-asterisk"
                       title="<spring:message code="mandatory.field"/>">
                    </i>
                </a>
            </div>

            <!-- CONTEST OF ANNOUNCEMENT -->
            <div class="form-group">
                <label class="control-label col-xs-3">
                    <spring:message code="fieldhdr.contest" />
                </label>
                <!-- ADD ALL CONTESTS AND SELECT THE OWNER -->
                <div class="col-xs-8">
                    <form:select cssClass="form-control" path="contest">
                        <form:options items="${contests}" itemLabel="name" itemValue="cid" />
                    </form:select>
                </div>
                <div class="error col-xs-8 col-xs-offset-3">
                    <span class="label label-danger"><form:errors path="contest" /></span>
                </div>
                <a>
                    <i data-toggle="tooltip" class="fa fa-asterisk"
                       title="<spring:message code="mandatory.field"/>">
                    </i>
                </a>
            </div>   


            <div class="form-actions pull-right">
                <input class="btn btn-primary" type="submit" name="submit"
                       id="submit" value="<spring:message code="judge.register.submit.value"/>" /> <input
                       class="btn btn-primary" type="reset" name="reset" id="reset"
                       value="<spring:message code="judge.register.reset.value"/>" />
            </div>
        </form:form>
    </div>
</div>
<script>    
    $("[data-toggle='tooltip']").tooltip();
</script>