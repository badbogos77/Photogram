// (1) 회원정보 수정
function update(userid, event) {
	event.preventDefault();  // onsubmit 
	
	var data = $("#profileUpdate").serialize();
	
	//console.log(data);

	$.ajax({
		type: "put",
		url:  `/api/user/${userid}`,
		data: data,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		dataType: "json"		
	})
	.done(res=> {		
		console.log("success", res);
		location.href=`/user/${userid}/update`;
	})
	.fail(error=> {
		console.log("error", error)
		if (error.responseJSON.data == null) {
			alert(JSON.stringify(error.responseJSON.message));
		} else {
			alert(JSON.stringify(error.responseJSON.data));
		}		
	});
}