//処理結果メッセージ表示メソッド
function showMessage(text, isError = true) {
	const messageDiv = document.getElementById('message');
	messageDiv.className = `mt-3 text-center fw-bold ${isError ? 'text-danger' : 'text-success'}`;
	messageDiv.innerText = text;
}

function showFieldErrors(errors) {
	// 初期化(前回のエラーを消す)
	//.form-control の要素を全部取得し、forEachで１件ずつ処理
	document.querySelectorAll(".form-control").forEach(input => {
		input.classList.remove("is-invalid");
	});
	document.querySelectorAll(".invalid-feedback").forEach(el => {
		el.innerText = "";
	});

	// エラー反映
	for (const field in errors) {
		const input = document.getElementById(field);
		const feedback = input.nextElementSibling; //inputの隣の要素を取得（エラーメッセージの表示場所を確保）

		if (input) input.classList.add("is-invalid");
		if (feedback) feedback.innerText = errors[field];
	}
}