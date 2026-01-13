/**
 * APIレスポンスのエラーを共通処理する関数
 */
export async function handleApiError(response) {
    // JSONがない場合やパースエラーを考慮
    const errorData = await response.json().catch(() => ({})); 
    
    const message = errorData.message || '予期せぬエラーが発生しました';
    const redirectUrl = errorData.redirectUrl; // バックエンドから届くURL
    
    // 1. メッセージを表示
    alert(message);
    
    // 2. バックエンドからリダイレクト指示があれば遷移する
    if (redirectUrl) {
        window.location.href = redirectUrl;
        return; // 遷移するのでここで終了
    }

    // 3. (オプション) redirectUrlがない場合の予備処理
    if (response.status === 403) {
        window.location.href = "/view/gidai/list";
    }
}