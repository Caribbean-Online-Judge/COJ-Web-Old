<%@include file="/WEB-INF/jsp/include/include.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script type="text/javascript" src="<c:url value="/js/coj.js"/>"></script>

<h2 class="postheader" style="clear: both">
	<a class="linkheader"
		href="<c:url value="contestview.xhtml?cid=${contest.cid}"/>"
		title="Go to Contest">${contest.name}</a> <br />
	<spring:message code="pagehdr.contest.awards" />
</h2>
<c:if
	test="${(contest.past && contest.blocked) || (contest.running && (contest.full_frozen || contest.frozen))}">
	<center>
		<c:choose>
			<c:when test="${contest.full_frozen == true}">
				<span class="label label-danger"><i class="fa fa-warning"></i>&nbsp;<spring:message
						code="text.deadtime" /></span>
			</c:when>
			<c:otherwise>
				<span class="label label-primary"><i
					class="fa fa-info-circle"></i>&nbsp;<spring:message
						code="text.frozentime" /></span>
			</c:otherwise>
		</c:choose>
	</center>
</c:if>
<c:if
	test="${(contest.past && not contest.blocked) || (contest.running && not contest.full_frozen && not contest.frozen)}">
	<c:if test="${not empty users}">
		<div class="row">
			<c:forEach items="${users}" var="user">
				<div class="col-xs-offset-3 col-xs-6">
					<div class="panel panel-primary">
						<div class="panel-body">
							<img src="/images/<c:url value="${user.medal}"/>"
								alt="${user.rank}"
								title="<spring:message code="titval.${user.medal}"/>" /> <a
								href="<c:url value="/contest/cuseraccount.xhtml?uid=${user.username}&cid=${cid}" />">${user.nick}</a>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</c:if>

	<c:if test="${not empty fastAward}">
		<div class="row">
			<div class="col-xs-6 col-xs-offset-3">
				<div class="panel panel-primary">
					<div class="panel-body">
						<div class="row">
							<div class="col-xs-12">
								<img src="/images/<c:url value="fast.png"/>"
									title="<spring:message code="contest.award.fast" />"> <a
									href="<c:url value="/contest/cuseraccount.xhtml?uid=${fastAward.uid}&cid=${fastAward.cid}" />">${fastAward.nick}</a>
								&nbsp;${fastAward.description}
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</c:if>
	<c:if test="${not empty exclusiveAward}">
		<div class="row">
			<div class="col-xs-6 col-xs-offset-3">
				<div class="panel panel-primary">
					<div class="panel-body">
						<div class="row">
							<div class="col-xs-12">
								<img src="/images/<c:url value="exclusive.png"/>"
									title="<spring:message code="contest.award.exclusive" />">
								<a
									href="<c:url value="/contest/cuseraccount.xhtml?uid=${exclusiveAward.uid}&cid=${exclusiveAward.cid}" />">${exclusiveAward.nick}</a>
								&nbsp;${exclusiveAward.description}
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</c:if>
	<c:if test="${not empty accurateAward}">
		<div class="row">
			<div class="col-xs-6 col-xs-offset-3">
				<div class="panel panel-primary">
					<div class="panel-body">
						<div class="row">
							<div class="col-xs-12">
								<img src="/images/<c:url value="accurate.png"/>"
									title="<spring:message code="contest.award.accurate" />">
								<a
									href="<c:url value="/contest/cuseraccount.xhtml?uid=${accurateAward.uid}&cid=${accurateAward.cid}" />">${accurateAward.nick}</a>
								&nbsp;${accurateAward.description}
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</c:if>
</c:if>
<script>
	$(function() {
		$("[data-toggle='tooltip']").tooltip();
	});
</script>
