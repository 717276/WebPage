document.addEventListener("DOMContentLoaded", function() {
	$.ajax({
		type: "GET",
		url: "/Game/article/get?param=0",
		success: function(result) {
			setArticles(result);
		},
		error: function(error) {
			console.log("sort error " + error);
		}
	})
	document.querySelector("#search_btn").addEventListener("click", function() {
		const search = document.querySelector("#search_input");
		if (search.value == '') {
			window.alert("검색어 입력");
			return;
		}
		$.ajax({
			type: "POST",
			url: "/Game/article/search",
			data: {
				search: search.value,
			},
			success: function(result) {
				// article list up
				setArticles(result);
			},
			error: function(error) {
				console.log("search error " + error);
			}
		});
	});

	document.querySelector(".sort_box").addEventListener("change", function() {
		const sortType = document.querySelector(".sort_box");
		$.ajax({
			type: "GET",
			url: "/Game/article/sort?param=" + sortType.value,
			success: function(result) {
				setArticles(result);
			},
			error: function(error) {
				console.log("sort error " + error);
			}
		})
	})
})

function writeArticle(){
	window.location.href="write.jsp";
}



function setArticles(articles) {
	$(".list").empty();
	const list = document.querySelector(".list");
	for (let i = 0; i < articles.length; ++i) {
		let alink = document.createElement("a");
		let ul = document.createElement("ul");

		alink.href = "/Game/article/get?param=" + articles[i].no;
		ul.className = "article_ul";
		for (let j = 0; j < 6; ++j) {
			let li = document.createElement("li");
			switch (j) {
				case 0:
					li.textContent = articles[i].createdDate;
					break;
				case 1:
					li.textContent = articles[i].title;
					break;
				case 2:
					li.textContent = articles[i].writer;
					break;
				case 3:
					li.textContent = articles[i].view_count;
					break;
				case 4:
					li.textContent = articles[i].like_count;
					break;
				case 5:
					li.textContent = articles[i].dislike_count;
					break;
				default:
					break;
			}
			ul.appendChild(li);
		}
		alink.appendChild(ul);
		list.appendChild(alink);
	}
}














