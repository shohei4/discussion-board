/** 
 * 認証付きfetch関数（AccessTokenの付与と自動リフレッシュを行う）
*/
async function authenticatedFetch(url, options = {}) {
	const accessToken = sessionStorage.getItem('accessToken');
	
	//ヘッダー設定
	const headers = {
		'Content-Type' : 'appplication/json',
		...options.headers
	};
	
	if(accessToken) {
		headers['Authorization'] = `Bearer ${accessToken}`;
	}
	
	//fetch実行(credentials: 'include'でCokkieを送信)
	let response = await fetch(url, {
		...options,
		headers,
		credentials: 'include'
	});
	
	//3. 401(AccessToken切れ)だった場合の自動リトライ処理
	if(response.status === 401) {
		console.warn('AccessToken expired. Attempting to refresh...');
		
		//サーバーに新しいAccessTokenを発行してもらう
		const newAccessToken = await refreshAccessToken();
		
		if(newAccessToken) {
			//新しいトークンヘッダーを作り直し、全く同じ設定で再試行
			const retryHeaders = {
				...headers,
				'Authorization': `Bearer ${newAccessToken}`
			};
			return fetch(url, {...options, headers: retryHeaders, credentials: 'include'});
		} else {
			//リフレッシュも失敗した場合は、完全にセション切れ
			console.errpr('Session expired. Redirecting to login.');
			window.location.href = '/login?timeout=true';
		}
	}
	
	return response;
}