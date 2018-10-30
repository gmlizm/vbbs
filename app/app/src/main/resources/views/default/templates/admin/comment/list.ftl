<#include "../common/layout.ftl"/>
<@html page_title="评论列表" page_tab="admin">
<div class="row">
  <div class="col-md-2 hidden-sm hidden-xs">
    <#include "../components/admin_left.ftl">
    <@admin_left page_tab="comment"/>
  </div>
  <div class="col-md-10">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a href="/">主页</a> / 评论列表
      </div>
      <div class="table-responsive">
        <#include "../components/comment_list.ftl"/>
        <@user_replies replies=page.getRecords()/>
      </div>
      <div class="panel-body" style="padding: 0 15px;">
        <#include "../components/paginate.ftl"/>
        <@paginate currentPage=(page.getCurrent() + 1) totalPage=page.getPages() actionUrl="/admin/comment/list" urlParas="" showdivide="no"/>
      </div>
    </div>
  </div>
</div>
</@html>