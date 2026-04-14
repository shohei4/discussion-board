
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
