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
document.addEventListener('DOMContentLoaded', function() {
	setLoginButtonEvent();
})
function setLoginButtonEvent() {
    document.querySelector("#log_btn").addEventListener("click", function() {
        const email = document.querySelector("#email");
        const password = document.querySelector("#password");
        const check = document.querySelector("#user_no");
        console.log(check.value);
        if (check.value == -1) {
            $.ajax({
                type: "POST",
                url: "home/login",
                data: {
                    email: email.value,
                    password: password.value,
                },
                success: function(result) {
                    if (result.login_session != -1) {
                        $(".login").empty();
                        $(".login").html(`
                            <div class="login_box">
                                <h4>환영합니다.</h4>
                                <input type="hidden" id="user_no" name="user_no" value="${result.login_session}">
                                <table class="login_table">
                                    <tr>
                                        <td>Email:</td>
                                        <td>${result.email}</td>
                                    </tr>
                                    <tr>
                                        <td>NickName:</td>
                                        <td>${result.nickname}</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2"><button id="log_btn">로그아웃</button></td>
                                    </tr>
                                </table>
                            </div>
                        `);
                        setLoginButtonEvent(); // 새로운 #log_btn에 이벤트 리스너를 설정
                    } else {
						email.value="";
	                    password.value="";
	                    document.getElementById("loginError").style.display = "block";
					}
                },
                error: function(error) {
                    console.log(error);                   
                }
            });
        } else {
            $(".login").empty();
            $(".login").html(`
                <div class="login_box">
                    <h4>로그인</h4>
                    <input type="hidden" id="user_no" name="user_no" value=-1>
                    <table class="login_table">
                        <tr>
                            <td><label for="email">Email:</label></td>
                            <td><input id="email" name="email" type="email" placeholder="Email" maxlength="30" size="30"></td>
                        </tr>
                        <tr>
                            <td><label for="password">Password:</label></td>
                            <td><input id="password" name="password" type="password" placeholder="Password" maxlength="30" size="30"></td>						
						</tr>
						<tr>
							<td colspan=2><span id="loginError" style="display: none;">로그인 실패 아이디 패스워드 확인</span></td>
						</tr>
                        <tr>
                            <td colspan="2"><button id="log_btn">로그인</button></td>
                        </tr>
                    </table>
                </div>
                <div class="login_api">
					<button>
						<img id="google_api" alt="" src="">Google
					</button>
					<button>
						<img id="naver_api" alt="" src="">Naver
					</button>
				</div>
            `);
            setLoginButtonEvent(); // 새로운 #log_btn에 이벤트 리스너를 설정
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
            window.alert("articles " + articles.length);
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








