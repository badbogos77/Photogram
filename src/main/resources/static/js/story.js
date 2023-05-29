/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 안좋아요
	(4) 댓글쓰기
	(5) 댓글삭제
 */
 
 let page = 0;

// (1) 스토리 로드하기
function storyLoad() {
	$.ajax({
		url: `/api/images?page=${page}`,
		dataType: "json"
	}).done(res=>{
		console.log(res.data);		
		res.data.content.forEach(r=> {
			let storyitem = getStoryItem(r);
			$("#storyList").append(storyitem);
		})
	}).fail(error=>{
		console.log(error);
	});
}

storyLoad();

function getStoryItem(image) {
	let itemstory =`<div class="story-list__item">
	<div class="sl__item__header">
		<div>
			<img class="profile-image" src="/upload/${image.user.profileImageUrl}"
				onerror="this.src='/images/person.jpeg'" />
		</div>
		<div>${image.user.username}</div>
	</div>
	<div class="sl__item__img">
		<img src="/upload/${image.postImageUrl}" />
	</div>
	<div class="sl__item__contents">
		<div class="sl__item__contents__icon">
			<button>`;
			 if (image.likesState) {
	 			itemstory +=	`<i class="fas fa-heart active" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`
			} else {
				itemstory +=	`<i class="far fa-heart " id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`
			}
			itemstory += `	
			</button>
		</div>
		<span class="like"><b id="storyLikeCount-${image.id}">${image.likesCount}</b>likes</span>
		<div class="sl__item__contents__content">
			<p>${image.caption}</p>
		</div>
		<div id="storyCommentList-1">
			<div class="sl__item__contents__comment" id="storyCommentItem-1">
				<p>
					<b>Lovely :</b> 부럽습니다.
				</p>
				<button>
					<i class="fas fa-times"></i>
				</button>
			</div>
		</div>
		<div class="sl__item__input">
			<input type="text" placeholder="댓글 달기..." id="storyCommentInput-${image.id}" />
			<button type="button" onClick="addComment()">게시</button>
		</div>
	</div>
</div>`;

	return itemstory;
}

// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => {
/*	console.log("window scrolltop : ", $(window).scrollTop());
	console.log("document.height : ", $(document).height());
	console.log("window height: ", $(window).height());*/
	
	let checkNum =  $(window).scrollTop() - ($(document).height() - $(window).height());
	
//	console.log("checkNum", checkNum);	
	
	if (checkNum < 1 && checkNum > -1  ) {
		page++;
		storyLoad();
	}
});


// (3) 좋아요, 안좋아요
function toggleLike(imageId) {
	let likeIcon = $(`#storyLikeIcon-${imageId}`);
	if (likeIcon.hasClass("far")) {  // 좋아요를 눌렀을때
		$.ajax({
			type: "post",
			url: `/api/images/${imageId}/likes`,
			dataType: "json"
		}).done(res=> {
			console.log("success", res);
			likeIcon.addClass("fas");
			likeIcon.addClass("active");
			likeIcon.removeClass("far");
			
			let likecount = Number($(`#storyLikeCount-${imageId}`).text()) + 1;
			console.log("likecount", likecount);
			$(`#storyLikeCount-${imageId}`).text(likecount);
		
		}).fail(error => {
			console.log("error", error);
		});
	} else {									  // 좋아요를 취소할때
		$.ajax({
			type: "delete",
			url: `/api/images/${imageId}/likes`,
			dataType: "json"
		}).done(res=> {
			console.log("success", res);
			likeIcon.removeClass("fas");
			likeIcon.removeClass("active");
			likeIcon.addClass("far");
			
			let likecount = Number($(`#storyLikeCount-${imageId}`).text()) - 1;
			console.log("likecount", likecount);
			$(`#storyLikeCount-${imageId}`).text(likecount);
			
		}).fail(error => {
			console.log("error", error);
		});		
	}
}

// (4) 댓글쓰기
function addComment() {

	let commentInput = $("#storyCommentInput-1");
	let commentList = $("#storyCommentList-1");

	let data = {
		content: commentInput.val()
	}

	if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}

	let content = `
			  <div class="sl__item__contents__comment" id="storyCommentItem-2""> 
			    <p>
			      <b>GilDong :</b>
			      댓글 샘플입니다.
			    </p>
			    <button><i class="fas fa-times"></i></button>
			  </div>
	`;
	commentList.prepend(content);
	commentInput.val("");
}

// (5) 댓글 삭제
function deleteComment() {

}







