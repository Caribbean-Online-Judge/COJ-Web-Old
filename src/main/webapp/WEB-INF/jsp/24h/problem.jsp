<%@include file="/WEB-INF/jsp/include/include.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<h2 class="postheader">
	<spring:message code="pagehdr.24hproblem" />
</h2>
<div class="row postcontent">
	<!-- article-content -->
	<div class="row">
		<div class="col-xs-12">
			<div class="panel panel-primary text-center">
				<authz:authorize access="!isAuthenticated()">
    &nbsp;<a href="status.xhtml?pid=<c:url value="${problem.pid}"/>"><spring:message
							code="link.submissions" /></a>
							&nbsp;&nbsp;<a
						href="bestsolutions.xhtml?pid=<c:url value="${problem.pid}"/>"><spring:message
							code="link.bestsolutions" /></a>
							&nbsp;&nbsp;<a
						href="problempdf.xhtml?pid=<c:url value="${problem.pid}"/>">PDF</a>
					<c:if test="${not empty problem.forumLink}">
							&nbsp;&nbsp;<a
						href="${problem.forumLink}"><spring:message
							code="link.discussion" /></a>
					</c:if>
				</authz:authorize>
				<authz:authorize ifNotGranted="ROLE_ANONYMOUS">
			&nbsp;<a href="submit.xhtml?pid=<c:url value="${problem.pid}"/>"><spring:message
							code="link.submit" /></a>
			&nbsp;<a
						href="<c:url value="status.xhtml?pid=${problem.pid}&username="/><authz:authentication property="principal.username" />"><spring:message
							code="link.mysubmissions" /></a>
			&nbsp;<a href="status.xhtml?pid=<c:url value="${problem.pid}"/>"><spring:message
							code="link.submissions" /></a>
			&nbsp;<a
						href="bestsolutions.xhtml?pid=<c:url value="${problem.pid}"/>"><spring:message
							code="link.bestsolutions" /></a>
					<!--
			<authz:authorize ifAllGranted="ROLE_USER">
				&nbsp;<a
					href="<c:url value="/datagen/datasets.xhtml?problemId=${problem.pid}&mode=modelsol"/>"><spring:message
							code="link.datagen" /></a>
			</authz:authorize>
			-->
					<c:if test="${problem.solved == true}">
				&nbsp;<a href="<c:url value="votes.xhtml?pid=${problem.pid}"/>"><spring:message
								code="votes.btn" /></a>
					</c:if>
			&nbsp;<a href="problempdf.xhtml?pid=<c:url value="${problem.pid}"/>">PDF</a>
					<c:if test="${not empty problem.forumLink}">
			&nbsp;<a href="${problem.forumLink}"><spring:message
								code="link.discussion" /></a>
					</c:if>
			&nbsp;<a href="/24h/translation.xhtml?pid=${problem.pid}"><spring:message
								code="link.translation" /></a>
				</authz:authorize>
			</div>
		</div>
	</div>
	<div class="col-xs-12">
		<h3 class="text-center">
			<b class="text" data-language="en"><c:out value=" ${problem.pid}" /> - <c:out
					value="${problem.titleEN}" /></b>
			<b class="text" data-language="es"><c:out value=" ${problem.pid}" /> - <c:out
					value="${problem.titleEs}" /></b>
			<b class="text" data-language="pt"><c:out value=" ${problem.pid}" /> - <c:out
					value="${problem.titlePt}" /></b>
			
			
			<c:if test="${problem.special_judge}">
				<a data-toggle="tooltip"
					title="<spring:message code="titval.special.judge"/>"><span
					class="text-danger"><i class="fa fa-gavel"></i></span></a>
			</c:if>
		</h3>
	</div>
	<div class="row">
		<div class="col-xs-12">
			<table class="pinfo">
				<tbody>
					<tr>
						<td><spring:message code="fieldhdr.stats" /></td>
						<td><b><spring:message code="tablehdr.sub" />: </b><a
							href="status.xhtml?pid=${problem.pid}">${problem.submitions}</a>
							<c:out value=" | " /><b><spring:message code="tablehdr.ac" />:
						</b><a href="status.xhtml?pid=${problem.pid}&status=ac">${problem.ac}</a>
							<c:out value=" | " /><b><spring:message
									code="tablehdr.accpercent" />: </b> ${problem.roundedAccp} <c:out
								value=" | " /><b><spring:message code="tablehdr.score" />:
						</b> ${problem.roundedPoints}</td>
					</tr>
					<tr>
						<td><spring:message code="fieldhdr.createdby" /></td>
						<td><c:out value="${problem.author}" /></td>
					</tr>
					<tr>
						<td width="20%"><spring:message code="fieldhdr.addedby" /></td>
						<td><a
							href="<c:url value="/user/useraccount.xhtml?username=${problem.username}"/>"><c:out
									value="${problem.username}" /></a>&nbsp;(<c:out
								value="${problem.date}" />)</td>
					</tr>
					<tr>
						<td><spring:message code="fieldhdr.limits" /></td>
						<td><b><spring:message code="fieldhdr.timelimit" />:</b> <c:out
								value="${problem.time}" /> MS <c:choose>
								<c:when test="${problem.multidata}">
									<c:out value="|" />
									<b><spring:message
											code="fieldhdr.testlimit" />:</b>
									<c:out value="${problem.casetimelimit}" /> MS
					</c:when>
							</c:choose> <c:out value="|" /><b><spring:message
									code="fieldhdr.memorylimit" />:</b> <c:out
								value="${problem.memoryMB}" /> <c:out value=" | " /> <span
							style="font-weight: bold;"><spring:message
									code="fieldhdr.outputlimit" />:</span> <c:out value="64" /> MB<c:out
								value=" | " /><b><spring:message code="fieldhdr.sizelimit" />:</b>
							<c:out value="${problem.fontMB}" /></td>
					</tr>
					<tr>
						<td><spring:message code="fieldhdr.enabledlanguages" /></td>
						<td><c:forEach items="${problem.languages}" var="language"
								varStatus="status">
								<c:if test="${status.count ne 1}">
									<c:out value="|" />
								</c:if>
                        ${language.language}
                    </c:forEach></td>
					</tr>
					<c:if test="${view_pinfo}">
						<authz:authorize ifAllGranted="ROLE_USER">

							<tr>
								<td><spring:message code="fieldhdr.classif" /></td>
								<td><c:forEach items="${classifications}"
										var="classification" varStatus="status">
										<c:if test="${status.count ne 1}">
											<c:out value="|" />
										</c:if>
										<a
											href="problems.xhtml?classification=${classification.idClassification}&complexity=${classification.complexity}">
											<c:out
												value="${classification.name} ${classification.complexity}" />
										</a>
									</c:forEach></td>
							</tr>

						</authz:authorize>
					</c:if>
					<tr>
						<td><spring:message code="fieldhdr.availablein" /></td>
						<td>
							<c:if test="${problem.availableInEn}">
								<a href=""><img data-toggle="tooltip" data-placement="bottom" class="image change-language" data-language="en"
									 title="<spring:message code="titval.en"/>"
									 src="/images/i18n/en.png"/></a>
							</c:if>
							<c:if test="${problem.availableInEs}">
								<a href=""><img data-toggle="tooltip" data-placement="bottom" class="image change-language" data-language="es"
									 title="<spring:message code="titval.es"/>"
									 src="/images/i18n/es.png"/></a>
							</c:if>
							<c:if test="${problem.availableInPt}">
								<a><img data-toggle="tooltip" data-placement="bottom" class="image change-language" data-language="pt"
									 title="<spring:message code="titval.pt"/>"
									 src="/images/i18n/pt.png"/></a>
							</c:if>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12">
			<h4 class="text-primary">
				<spring:message code="fieldhdr.description" />
			</h4>
			<div class="ex row">
				<div class="col-xs-12 text" data-language="en">
					<c:url value="${problem.descriptionEN}" />
				</div>
				<div class="col-xs-12 text" data-language="es">
					<c:url value="${problem.descriptionEs}" />
				</div>
				<div class="col-xs-12 text" data-language="pt">
					<c:url value="${problem.descriptionPt}" />
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12">
			<h4 class="text-primary">
				<spring:message code="fieldhdr.inputspec" />
			</h4>
			<div class="ex row">
				<div class="col-xs-12 text" data-language="en">
					<c:url value="${problem.inputEN}" />
				</div>
				<div class="col-xs-12 text" data-language="es">
					<c:url value="${problem.inputEs}" />
				</div>
				<div class="col-xs-12 text" data-language="pt">
					<c:url value="${problem.inputPt}" />
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12">
			<h4 class="text-primary">
				<spring:message code="fieldhdr.outputspec" />
			</h4>
			<div class="row">
				<div class="col-xs-12 text" data-language="en">
					<c:url value="${problem.outputEN}" />
				</div>
				<div class="col-xs-12 text" data-language="es">
					<c:url value="${problem.outputEs}" />
				</div>
				<div class="col-xs-12 text" data-language="pt">
					<c:url value="${problem.outputPt}" />
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12">
			<h4 class="text-primary">
				<spring:message code="fieldhdr.sampleinput" />
			</h4>
			<code>
				<c:url value="${problem.inputex}" />
			</code>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12">
			<h4 class="text-primary">
				<spring:message code="fieldhdr.sampleoutput" />
			</h4>
			<code>
				<c:url value="${problem.outputex}" />
			</code>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12">
			<h4 class="text-primary">
				<spring:message code="fieldhdr.hint" />e
			</h4>
			<div class="ex row">
				<div class="col-xs-12 text" data-language="en">
					<c:url value="${problem.commentsEN}" />
				</div>
				<div class="col-xs-12 text" data-language="es">
					<c:url value="${problem.commentsEs}" />
				</div>
				<div class="col-xs-12 text" data-language="pt">
					<c:url value="${problem.commentsPt}" />
				</div>
			</div>
		</div>
	</div>
	<c:if test="${hasRecommend}">
		<div class="row">
			<div class="col-xs-12">
				<h4 class="text-primary">
					<spring:message code="problemrec.recommend" />
				</h4>
				<div class="ex">
					<authz:authorize ifAllGranted="ROLE_USER">
						<spring:message code="problemrec.recommend.message" />
					</authz:authorize>
					<authz:authorize ifAllGranted="ROLE_ANONYMOUS">
						<spring:message code="problemrec.recommend.message.notlogged" />
					</authz:authorize>
					<c:forEach items="${recommend}" var="recomm" varStatus="status">
						<c:if test="${status.count ne 1}">
							<c:out value="  |  " />
						</c:if>
						<a href="problem.xhtml?pid=<c:url value="${recomm.pid}"/>"><c:out
								value="${recomm.pid}" /></a>
					</c:forEach>
				</div>
			</div>
		</div>
	</c:if>
</div>

<script>
	$(function() {
		$("[data-toggle='tooltip']").tooltip();		
		
		$(".text").each(function(){
			if("${locale}" == $(this).data("language")) {
				$(this).removeClass("hide");
			} else {
				$(this).addClass("hide");
			}			
		});
		
		$(".change-language").click(function() {
			lang = $(this).data("language");
			$(".text").each(function(){
				if(lang == $(this).data("language")) {
					$(this).removeClass("hide");
				} else {
					$(this).addClass("hide");
				}			
			});
		});		
	});
</script>