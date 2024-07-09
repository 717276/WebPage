function writeComment() {
	const writer = document.querySelector("#nickname");
	const articleNo = document.querySelector(".article_no");
	const content = document.querySelector("#comment_content");
	const writerNo = document.querySelector("#writer_no");

	$.ajax({
		type: "POST",
		url: "/Game/comment/write",
		data: {
			writer: writer.value,
			content: content.value,
			articleNo: articleNo.value,
			writerNo: writerNo.value,
		},
		success: function(result) {
			if (result === "SUCCESS") {
				window.alert("댓글");
				let table = document.querySelector(".comment_table");
				let tr = document.createElement("tr");

				let newWriterNo = document.createElement("td");
				let newWriter = document.createElement("td");
				let newContent = document.createElement("td");
				let newLike = document.createElement("td");
				let newDislike = document.createElement("td");
				let newCreatedDate = document.createElement("td");

				let hiddenWriterNo = document.createElement("input");
				hiddenWriterNo.type = "hidden";
				hiddenWriterNo.name = "writer_no";
				hiddenWriterNo.value = writerNo;
				newWriterNo.appendChild(hiddenWriterNo);
				newWriterNo.textContent = writerNo;

				newWriter.textContent = writer.value;
				newContent.textContent = content.value;
				newLike.textContent = 0;
				newDislike.textContent = 0;
				newCreatedDate.textContent = result.createdDate;
				console.log("result.createdDate");
				tr.appendChild(newWriterNo);
				tr.appendChild(newWriter);
				tr.appendChild(newContent);
				tr.appendChild(newLike);
				tr.appendChild(newDislike);
				tr.appendChild(newCreatedDate);

				table.appendChild(tr);
			}
			window.location.reload();
		},
		error: function(error) {
			console.log("error: " + error);
		}
	});
}
let g_titleVal = null;
let g_articleVal = null;
function updateArticle() {
	changeBtn(2);
	
	const title = document.querySelector("#title");
	const article = document.querySelector("#article");
	g_articleVal = article.value;
	g_titleVal = title.value;
	if (title) {
		title.readOnly = false;
		title.focus();
	}

	if (article) {
		article.readOnly = false;
		article.focus();
	}
}
function changeBtn(change){
	const delete_btn = document.querySelector("#delete_btn");
	const update_btn = document.querySelector("#update_btn");
			
	const cancel_btn = document.querySelector("#cancel_btn");
	const commit_btn = document.querySelector("#commit_btn");

	if (change == 1) {		
		delete_btn.style.display = "block";
		update_btn.style.display = "block";
				
		cancel_btn.style.display = "none";
		commit_btn.style.display = "none";
	} else {
		delete_btn.style.display = "none";
		update_btn.style.display = "none";
			
		cancel_btn.style.display = "block";
		commit_btn.style.display = "block";
	}
}
function commit(idx) {
	const title = document.querySelector(".title");
	const article = document.querySelector(".article");
	
	const reTitle = title.value;
	const reArticle = article.value;
	changeBtn(1);

	$.ajax({
		type:"POST",
		url:"/Game/article/update",
		data:{
			no: idx,
			title:title.value,
			article:article.value,
		},
		success:function(result){
			window.alert(result);
			title.value = reTitle;
			article.value = reArticle;		
		},
		error:function(error) {
			window.alert("article update error: " + error);
		}		
	});
}
function cancel() {
	changeBtn(1);

	const title = document.querySelector(".title");
	const article = document.querySelector(".article");
	
	title.value = g_titleVal;
	article.value = g_articleVal;
}
function deleteArticle(idx) {
	window.confirm("삭제합니다.");
	$.ajax({
		type: "GET",
		url: "/Game/article/delete?param=" + idx,
		success: function(result) {
			if (result === "SUCCESS") {
				window.alert("삭제");
				window.location.href = "/Game/community.jsp";
			}
		},
		error: function() {
			window.alert("delete article is error");
		}
	})
}

function changeCommentBtn(number, cNo){
	const editComment_btn = document.querySelector("#editComment_btn"+cNo);
	const deleteComment_btn = document.querySelector("#deleteComment_btn"+cNo);
			
	const commitComment_btn = document.querySelector("#commitComment_btn"+cNo);
	const cancelComment_btn = document.querySelector("#cancelComment_btn"+cNo);
	if (number == 1) {
		editComment_btn.style.display="none";
		deleteComment_btn.style.display="none";
		
		commitComment_btn.style.display="block";
		cancelComment_btn.style.display="block";
	} else {
		editComment_btn.style.display="block";
		deleteComment_btn.style.display="block";
		
		commitComment_btn.style.display="none";
		cancelComment_btn.style.display="none";
	}	
}

let g_comment;
function editComment(cNo){
	const ct = document.querySelector("#content_td" + cNo);
	g_comment = ct.value;
	changeCommentBtn(1,cNo);
	if(ct) {
		ct.readOnly = false;
		ct.focus();
	}
}
function commitComment(cNo){
	const ct = document.querySelector("#content_td" + cNo);
	const reVal = ct.value;
	
	$.ajax({
		type:"POST",
		url:"/Game/comment/update",
		data:{
			no: cNo,
			content:reVal,
		},
		success:function(result){
			window.alert("수정");
			ct.value = reVal;
			changeCommentBtn(2,cNo);
		},	
		error:function(error){
			window.alert("commit comment is error");
		}
	})
}

function cancelComment(cNo){
	changeCommentBtn(2,cNo);
	const ct = document.querySelector("#content_td"+cNo);
	ct.value = g_comment;
	ct.readOnly = true;
}
function deleteComment(cNo){
	window.confirm("삭제합니다.");
	$.ajax({
		type:"GET",
		url:"/Game/comment/delete?param="+cNo,
		success:function(result) {
			if (result){
				window.alert("댓글 삭제");
			}
		},
		error:function(error){
			window.alert("댓글 삭제 실패");
		}
	})	
}