document.addEventListener('DOMContentLoaded', function() {
	const wrapper = document.querySelector(".wrapper");

	const wrapperWidth = wrapper.scrollWidth;

	let slideWidth = wrapperWidth / 5;
	let currentTranslateX = 0;

	document.querySelector('.left').addEventListener('click', function() {
		if (currentTranslateX >= 0) {
			currentTranslateX = -(wrapperWidth - slideWidth);
		} else {
			currentTranslateX += slideWidth;
		}
		wrapper.style.transform = `translateX(${currentTranslateX}px)`;
	});
	document.querySelector('.right').addEventListener('click', function() {
		if (currentTranslateX <= -(wrapperWidth - slideWidth)) {
			currentTranslateX = 0;
		} else {
			currentTranslateX -= slideWidth;
		}
		wrapper.style.transform = `translateX(${currentTranslateX}px)`;
	});
});

function login() {
    const email = document.querySelector("#email").value;
    const password = document.querySelector("#password").value;
    const check = document.querySelector("#user_no").value;

    if (check === "-1") {
        $.ajax({
            type: "POST",
            url: "home/login",
            data: {
                email: email,
                password: password,
            },
            success: function(result) {
                if (result === "SUCCESS") {
                    window.alert("로그인 성공!");
					window.location.reload();
                } else {
                    window.alert("로그인 실패!");
                }
            },
            error: function(error) {
                console.error("로그인 요청 중 오류 발생:", error);
                window.alert("로그인 요청 중 오류가 발생했습니다.");
            }
        });
    }
}

function logout(){
	 $.ajax({
            type: "GET",
            url: "home/logout",
            success: function(result) {
                if (result === "SUCCESS") {
                    window.alert("로그아웃");
					window.location.reload();
                } else {
                    window.alert("로그아웃 실패!");
                }
            },
            error: function(error) {
                console.error("로그아웃 요청 중 오류 발생:", error);                
            }
        });
	
}
document.addEventListener("DOMContentLoaded", function() {
	let articles;
	$.ajax({
		type: "GET",
		url: "/Game/article/get?param=0",
		success: function(result) {
			articles = result;
			populateArticles(articles);
		},
		error: function(xhr, status, error) {
			window.alert("Error: " + error);
			console.log("AJAX Error Status: " + status);
			console.log("AJAX Error Details: " + error);
			console.log("AJAX Error Response Text: " + xhr.responseText);
		}
	});

	function populateArticles(articles) {
		const best_articles = document.querySelector(".best_articles");

		for (let i = 0; i < articles.length; ++i) {
			const article_box = document.createElement("div");
			article_box.className = "article_box";

			const alink = document.createElement("a");
			alink.className = "article";
			alink.href = "/article/get?param=" + articles[i].no;
			alink.textContent = articles[i].title;

			article_box.appendChild(alink);
			best_articles.appendChild(article_box);
		}
	}
});








