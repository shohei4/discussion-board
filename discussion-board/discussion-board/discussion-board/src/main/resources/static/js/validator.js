
/**バリデーションmethod */

const rules = {
	username: (v) => {
		if (!v || v.length < 3 || v.length > 30)
			return "ユーザー名は3〜30文字で入力してください";
	},
	email: (v) => {
		if (!v || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(v))
			return "正しいメール形式で入力してください";
	},
	password: (v) => {
		if (!v || v.length < 8)
			return "パスワードは8文字以上で入力してください";
	},
	
	// --- 議題フォーム ---
    title: (v) => {
        if (!v || v.trim() === "")
            return "タイトルは必須です";
        if (v.length > 100)
            return "タイトルは100文字以内で入力してください";
    },
    body: (v) => {
        if (!v || v.trim() === "")
            return "本文は必須です";
        if (v.length > 1000)
            return "本文は1000文字以内で入力してください";
    }
}

/**
 * @param {Object} data - フォームの入力値
 * @param {Object} rules - バリデーションルール
 * @returns {Object} エラーオブジェクト（field: message）
 */
function validateForm(data, rules) {
	const errors = {};

	for (const field in data) {
		if (rules[field]) {
			const error = rules[field](data[field]);
			if (error) errors[field] = error;
		}
	}
	return errors; 
}
