<%@ page language="java" pageEncoding="UTF-8"%>
<div id="empty"></div>
<script>
setPageId("meetingRecord_error");
layer.msg("${msg}");
$("#empty").closest("#powindow").find(".close").click();
</script>