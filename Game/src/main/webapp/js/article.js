function writeComment(){
	const writer = document.querySelector("#nickname");
	const articleNo = document.querySelector(".article_no");
	const content = document.querySelector("#comment_content");
	const writerNo = document.querySelector("#writer_no");
	
	$.ajax({		
		type:"POST",
		url:"/Game/comment/write",
		data:{
			writer:writer.value,
			content: content.value,
			articleNo:articleNo.value,
			writerNo:writerNo.value,
		},
		success:function(result){
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
		error:function(error){
			console.log("error: " + error);
		}
	});
}