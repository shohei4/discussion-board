/**
 * APIレスポンスのエラーを共通処理する関数
 * @param {Response} response - fetchのレスポンスオブジェクト
 */
export async function handleApiError(response) {
	const errorData = await response.json().catch(() => ({})); //JSONがない場合の考慮
	const status = response.status;
	const code = errorData.code || 'UNKNOWN_ERROR';
	const message = errorData.message || '予期せぬエラーが発生しました';
	
	//共通の振り分けロジック
	switch(status) {
		case 403:// 権限なし
			alert("この操作を行う権限がありません。")
			window.location.href = "/view/gidai/list"
			break;
			
		case 404:
			if(code === "GIDAI")
			
	}
}