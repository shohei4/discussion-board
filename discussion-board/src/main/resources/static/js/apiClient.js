/**
 * リフレッシュ関数
 */
async function refreshAccessToken() {
	try{
		const response = await fetch('/api/auth/refresh', {
			method: 'POST',
			credentials: 'include' //別ドメインへのリクエストでも、クッキーや認証情報を送信する設定
		});
		//レスポンスが妥当であればデータからアクセストークンを返す
		if (response.ok) {	                                                  
		const data = await response.json();
		return data.accessToken;
		}
	} catch(error) {
		console.error("Refresh request failed", error);
	}
	return null;
}

/** 
 * 認証付きfetch関数（AccessTokenの付与と自動リフレッシュを行う）
*/
async function authenticatedFetch(url, options = {}) {
	let accessToken = sessionStorage.getItem('accessToken');
	
	// 2. もしトークンがないなら、まずはリフレッシュを試みる
    if (!accessToken) {
        console.warn('No AccessToken. Attempting refresh before request...');
        accessToken = await refreshAccessToken();
        
        if (accessToken) {
            sessionStorage.setItem('accessToken', accessToken);
        } else {
            // リフレッシュも不可＝完全に未ログイン
            alert("ログインが必要です");
            window.location.href = '/login?timeout=true';
            // 処理を中断させるためにエラーを投げるか、空のレスポンスを返す
            throw new Error("Authentication required");
        }
    }
	
	// 3. ヘッダー設定（タイポを修正し、取得したトークンを確実にセット）
    const headers = {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${accessToken}`, // ここで必ず付与
        ...options.headers
    };
    
	//fetch実行(credentials: 'include'でCokkieを送信)
	let response = await fetch(url, {
		...options,
		headers,
		credentials: 'include'
	});
	
	//4. 401(AccessToken切れ)だった場合の自動リトライ処理
	if(response.status === 401) {
		console.warn('AccessToken expired. Attempting to refresh...');
		
		//1. サーバーに新しいAccessTokenを発行してもらう
		const newAccessToken = await refreshAccessToken();
		
		if(newAccessToken) {
			//2. 取得したアクセストークンをsessionStrageに保存
			sessionStorage.setItem('accessToken', newAccessToken);
			
			//3.新しいトークンヘッダーを作り直し
			const retryHeaders = {
				...headers,
				'Authorization': `Bearer ${newAccessToken}`
			};
			
			//4. awaitを付けて再試行、その結果を返す
			return fetch(url, {
				...options,
				headers: retryHeaders, 
				credentials: 'include'
				});
		} else {
			//リフレッシュも失敗した場合は、完全にセション切れ
			console.error('Session expired. Redirecting to login.');
			window.location.href = '/login?timeout=true';
		}
	}
	
	return response;
}